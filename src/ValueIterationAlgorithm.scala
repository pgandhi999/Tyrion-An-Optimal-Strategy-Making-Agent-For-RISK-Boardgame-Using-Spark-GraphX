package riskMdpGraphX

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx._

import scala.collection.mutable.Map

case class StateSpaceGraphVertex(state_id: Long, reward: Int)
case class StateSpaceGraphEdge(from_state_id: Long, to_state_id: Long, from_country: String, to_country: String, probability: Double)

object ValueIterationAlgorithm extends Serializable {

  var graph: Graph[String, String] = null
  var optimalPolicy: Map[Long, Long] = Map()
  var utility_states_old: Map[Long, Double] = Map()
  var optimal_policy_max_product: Map[Long, Double] = Map()

  def parseStateSpaceGraphVertex(str: String): StateSpaceGraphVertex = {
    val line = str.split(",")
    StateSpaceGraphVertex(line(0).toLong, line(1).toInt)
  }
  def parseStateSpaceGraphEdge(str: String): StateSpaceGraphEdge = {
    val line = str.split(",")
    StateSpaceGraphEdge(line(0).toLong, line(1).toLong, line(2), line(3), line(4).toDouble)
  }

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("RiskMDPGraphX")
    val sc = new SparkContext(conf)
    // Create an RDD by passing the path to the vertex csv file data created in the Java class GenerateStateSpaceGraphData.java
    val textRDDVertex = sc.textFile("C:\\Users\\user\\Desktop\\statespacegraphverticesdata.csv")
    val mdpRDDVertex = textRDDVertex.map(parseStateSpaceGraphVertex).cache()
    val stateVertices = mdpRDDVertex.map(state => (state.state_id, (state.reward.toString))).distinct
    // default vertex nowhere
    val nowhere = ("0")
    // Create an RDD by passing the path to the edge csv file data created in the Java class GenerateStateSpaceGraphData.java
    val textRDDEdge = sc.textFile("C:\\Users\\user\\Desktop\\statespacegraphedgesdata.csv")
    val mdpRDDEdge = textRDDEdge.map(parseStateSpaceGraphEdge).cache()
    val stateEdges: RDD[Edge[String]] = mdpRDDEdge.map(state => Edge[String](state.from_state_id, state.to_state_id, state.from_country + ":" + state.to_country + ":" + state.probability.toString)).distinct
    this.graph = Graph(stateVertices, stateEdges, nowhere)

    val numVertices = this.graph.numVertices
    var a = 0l
    for (a <- 0l to numVertices - 1) {
      this.utility_states_old.put(a, 0.0)
    }
    for (a <- 0l to numVertices - 1) {
      this.optimal_policy_max_product.put(a, Double.MinValue)
    }
    for (a <- 0l to numVertices - 1) {
      this.optimalPolicy.put(a, -1l)
    }
    computeTheBestPossibleStrategy(sc)
    parseOptimalPolicyAndPrintOutput()
  }

  /*
	 * Implementation of the value iteration algorithm that takes the
	 * Markovian state space graph generated above as the input and computes
	 * the optimal policy for all the states in the state space
	 */
  def computeTheBestPossibleStrategy(sc: SparkContext): Unit = {
    var delta = 1.0
    /*
		 * gamma can be tuned depending on whether or not a player needs
		 * immediate rewards. This can be kept to a higher value, when
		 * playing for global domination for better results. Refer
		 * literature on the value iteration algorithm for further
		 * details.
		 */
    val gamma = sc.broadcast(0.1)
    while (delta > 0.000001) {
      delta = 0.0
      var broadcastUtilityStates = sc.broadcast(this.utility_states_old)

      var maximumProductEdgeMap = this.graph.edges.map(eid => {
        var probabilityAdjacentState = eid.attr.split(":")(2).toDouble
        var adjacentState = eid.dstId
        var product = Double.MinValue
        if (probabilityAdjacentState != 2.0) {
          product = broadcastUtilityStates.value.get(adjacentState).get * probabilityAdjacentState + (1 - probabilityAdjacentState) * broadcastUtilityStates.value.get(adjacentState + 1).get
        }
        (eid.srcId, product)
      }).reduceByKey(math.max(_, _)).collectAsMap()

      var broadcastMaxProductEdgeMap = sc.broadcast(maximumProductEdgeMap)

      var utility_states_new = this.graph.vertices.map(vertfunct => {
        var vertexId = vertfunct._1
        var reward = vertfunct._2.toDouble
        var maxProduct = broadcastMaxProductEdgeMap.value.get(vertexId)
        var newUtility = reward
        if (!maxProduct.isEmpty) {
        newUtility = reward + gamma.value * maxProduct.get
        }
        (vertexId, newUtility)
      }).collectAsMap()

      for (i <- 0l to utility_states_new.size - 1) {
        if (Math.abs(utility_states_new.get(i).get - this.utility_states_old.get(i).get) > delta) {
          delta = Math.abs(utility_states_new.get(i).get - this.utility_states_old.get(i).get)
        }
        this.utility_states_old.put(i, utility_states_new.get(i).get)
      }
    }
    var broadcastUtilityStates = sc.broadcast(this.utility_states_old)

    var individualEdgeProductDataArray = this.graph.edges.map(eid => {
      var probabilityAdjacentState = eid.attr.split(":")(2).toDouble
      var adjacentState = eid.dstId
      var product = Double.MinValue
      if (probabilityAdjacentState != 2.0) {
        product = broadcastUtilityStates.value.get(adjacentState).get * probabilityAdjacentState + (1 - probabilityAdjacentState) * broadcastUtilityStates.value.get(adjacentState + 1).get
      }
      (eid.srcId, eid.dstId, product)
    }).collect()

    for (x <- individualEdgeProductDataArray) {
      var maximumProduct = this.optimal_policy_max_product.get(x._1).get
      var individualEdgeProduct = x._3
      if (individualEdgeProduct > maximumProduct) {
        this.optimalPolicy.put(x._1, x._2)
        this.optimal_policy_max_product.put(x._1, individualEdgeProduct)
      } else if (maximumProduct == Double.MinValue) {
          this.optimalPolicy.put(x._1, x._2)
          this.optimal_policy_max_product.put(x._1, individualEdgeProduct)
      }
    }
  }

  /*
	 * Outputs the best possible strategy at every step starting from the
	 * initial state. It takes the feedback from player after every attack
	 * and accordingly outputs the next move depending on whether the attack
	 * was a success or failure.
	 * Note: Run the following function ONLY in SPARK CLIENT MODE.
	 */
  def parseOptimalPolicyAndPrintOutput(): Unit = {
    var src_state_id = 0l
    while (this.optimalPolicy.get(src_state_id).get != -1l) {
      var dst_state_id = this.optimalPolicy.get(src_state_id).get
      var fromToCountries = this.graph.edges.filter(e => (e.srcId == src_state_id && e.dstId == dst_state_id)).collect()(0).attr
      println("Optimal move will be to attack from " + fromToCountries.split(":")(0) + " to " + fromToCountries.split(":")(1))
      println("Was the attack a success?")
      var input = scala.io.StdIn.readLine()
      if (input.trim.equalsIgnoreCase("yes")) {
        src_state_id = dst_state_id
      } else {
        src_state_id = dst_state_id + 1
      }
    }
    println("I hope I was of some help here!")
  }
}
