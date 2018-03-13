# Optimal-Strategy-Making-Agent-For-RISK-Boardgame-Using-Spark-GraphX

This program simulates an optimal strategy making agent for RISK boardgame which devises
an optimal strategy to help the concerned player to attempt to control an entire continent
in his or her turn. The agent tries it's best to acquire all the territories within the turn
if it is feasible, else will decide on a strategy such that the player can control
the said continent in the next turn.

The agent is built using MDP(Markov Decision Process) model and Bayesian Network. It implements
the Value Iteration Algorithm using Apache Spark GraphX on the constructed MDP chain graph and precomputes the optimal policy
before the start of the player's turn. It takes in the initial state of the game, the continent
to be controlled and the player for which to devise the optimal strategy as the input.

To demonstrate the working of the agent, the initial state of the game has been entered based on
the image that has been uploaded as sample_initial_state.jpg. As an example, the objective chosen
was to Control Europe and the player for which the agent computes the strategy is the Blue player.
For the gamma value of 0.1, the results have been uploaded as sample_result.txt.
To run the program, run the file GenerateStateSpaceGraphData.java by passing the paths of the vertex csv file
and the edge csv file to be created respectively. For the above example, the csv files generated will be found in data folder.
Once, the respective files are generated, run the program ValueIterationAlgorithm.scala in Client Mode.
The program will output the first optimal move. If the move was a success, type 'yes' else, type 'no'.
Depending on your feedback, the agent will output the next optimal move accordingly.

**Dependencies:**
- Download JavaCSV API jar and add it to the project classpath.

**Running the Program:**
- Import the project into an IDE and then run GenerateStateSpaceGraphData.java alongwith the arguments as below:
    java GenerateStateSpaceGraphData output_vertex_filepath output_edge_filepath
  
- Once the csv files are generated, specify their paths in ValueIterationAlgorithm.scala and run the code in Spark Client mode.

- As the program starts printing the game moves, it will ask whether or not the move was a success. Reply with a "yes" or "no".

**License:**
See the LICENSE file for license rights and limitations (MIT).