package riskmdp;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import com.csvreader.CsvWriter;

/*
 * This class generates the data to be used for constructing the state space graph using Apache
 * Spark GraphX. To run the class, call the main method by passing the paths of the vertex csv file
 * and the edge csv file to be created respectively.
 */
public class GenerateStateSpaceGraphData
{
	public static ArrayList< TerritoriesGraphVertex > listTerr = new ArrayList< TerritoriesGraphVertex >();
	public static short divisor = 100;
	public static HashMap< Integer, Integer > hopCountTerr = new HashMap< Integer, Integer >();
	public static HashMap< Integer, HashSet< Integer > > hopCountConnectionTerr = new HashMap< Integer, HashSet< Integer > >();

	private enum players
	{
		Blue, Yellow, Green;
	}

	private enum objectives
	{
		CONTROL_EUROPE, CONTROL_ASIA, CONTROL_AFRICA, CONTROL_NORTH_AMERICA, CONTROL_AUSTRALIA, CONTROL_SOUTH_AMERICA;
	}

	public static void main( String[] args )
	{
		GenerateStateSpaceGraphData main = new GenerateStateSpaceGraphData();
		main.populateGraph();
		String objective = objectives.CONTROL_EUROPE.toString();
		String player = players.Blue.toString();
		main.buildStateSpaceGraph( objective, player, args[0], args[1] );
	}

	/*
	 * This method takes the entire initial state of the board game and
	 * creates a graph with territories as vertices
	 */
	private void populateGraph()
	{
		int idCnt = 0;
		TerritoriesGraphVertex t1 = new TerritoriesGraphVertex();
		ArrayList< Integer > at1 = new ArrayList< Integer >();
		at1.add( 1 );
		at1.add( 3 );
		setTerrObject( t1, idCnt, "Eastern Australia", true, "Australia", false, "Blue", 1, at1 );
		idCnt++;
		TerritoriesGraphVertex t2 = new TerritoriesGraphVertex();
		ArrayList< Integer > at2 = new ArrayList< Integer >();
		at2.add( 0 );
		at2.add( 2 );
		at2.add( 3 );
		setTerrObject( t2, idCnt, "Western Australia", false, "Australia", false, "Yellow", 2, at2 );
		idCnt++;
		TerritoriesGraphVertex t3 = new TerritoriesGraphVertex();
		ArrayList< Integer > at3 = new ArrayList< Integer >();
		at3.add( 1 );
		at3.add( 3 );
		at3.add( 41 );
		setTerrObject( t3, idCnt, "Indonesia", false, "Australia", false, "Green", 3, at3 );
		idCnt++;
		TerritoriesGraphVertex t4 = new TerritoriesGraphVertex();
		ArrayList< Integer > at4 = new ArrayList< Integer >();
		at4.add( 0 );
		at4.add( 1 );
		at4.add( 2 );
		setTerrObject( t4, idCnt, "New Guinea", false, "Australia", true, "Yellow", 4, at4 );
		idCnt++;
		TerritoriesGraphVertex t5 = new TerritoriesGraphVertex();
		ArrayList< Integer > at5 = new ArrayList< Integer >();
		at5.add( 5 );
		at5.add( 6 );
		at5.add( 22 );
		setTerrObject( t5, idCnt, "Venezuela", false, "South America", false, "Yellow", 1, at5 );
		idCnt++;
		TerritoriesGraphVertex t6 = new TerritoriesGraphVertex();
		ArrayList< Integer > at6 = new ArrayList< Integer >();
		at6.add( 4 );
		at6.add( 6 );
		at6.add( 7 );
		setTerrObject( t6, idCnt, "Peru", false, "South America", false, "Green", 2, at6 );
		idCnt++;
		TerritoriesGraphVertex t7 = new TerritoriesGraphVertex();
		ArrayList< Integer > at7 = new ArrayList< Integer >();
		at7.add( 4 );
		at7.add( 5 );
		at7.add( 7 );
		at7.add( 9 );
		setTerrObject( t7, idCnt, "Brazil", true, "South America", false, "Green", 4, at7 );
		idCnt++;
		TerritoriesGraphVertex t8 = new TerritoriesGraphVertex();
		ArrayList< Integer > at8 = new ArrayList< Integer >();
		at8.add( 5 );
		at8.add( 6 );
		setTerrObject( t8, idCnt, "Argentina", false, "South America", true, "Green", 3, at8 );
		idCnt++;
		TerritoriesGraphVertex t9 = new TerritoriesGraphVertex();
		ArrayList< Integer > at9 = new ArrayList< Integer >();
		at9.add( 9 );
		at9.add( 10 );
		at9.add( 29 );
		at9.add( 39 );
		setTerrObject( t9, idCnt, "Egypt", true, "Africa", false, "Green", 1, at9 );
		idCnt++;
		TerritoriesGraphVertex t10 = new TerritoriesGraphVertex();
		ArrayList< Integer > at10 = new ArrayList< Integer >();
		at10.add( 6 );
		at10.add( 8 );
		at10.add( 10 );
		at10.add( 11 );
		at10.add( 28 );
		at10.add( 29 );
		setTerrObject( t10, idCnt, "North Africa", false, "Africa", false, "Green", 2, at10 );
		idCnt++;
		TerritoriesGraphVertex t11 = new TerritoriesGraphVertex();
		ArrayList< Integer > at11 = new ArrayList< Integer >();
		at11.add( 8 );
		at11.add( 9 );
		at11.add( 11 );
		at11.add( 12 );
		at11.add( 13 );
		at11.add( 39 );
		setTerrObject( t11, idCnt, "East Africa", false, "Africa", false, "Blue", 4, at11 );
		idCnt++;
		TerritoriesGraphVertex t12 = new TerritoriesGraphVertex();
		ArrayList< Integer > at12 = new ArrayList< Integer >();
		at12.add( 9 );
		at12.add( 10 );
		at12.add( 12 );
		setTerrObject( t12, idCnt, "Central Africa", false, "Africa", false, "Yellow", 2, at12 );
		idCnt++;
		TerritoriesGraphVertex t13 = new TerritoriesGraphVertex();
		ArrayList< Integer > at13 = new ArrayList< Integer >();
		at13.add( 10 );
		at13.add( 11 );
		at13.add( 13 );
		setTerrObject( t13, idCnt, "South Africa", true, "Africa", false, "Blue", 4, at13 );
		idCnt++;
		TerritoriesGraphVertex t14 = new TerritoriesGraphVertex();
		ArrayList< Integer > at14 = new ArrayList< Integer >();
		at14.add( 10 );
		at14.add( 12 );
		setTerrObject( t14, idCnt, "Madagascar", false, "Africa", false, "Yellow", 1, at14 );
		idCnt++;
		TerritoriesGraphVertex t15 = new TerritoriesGraphVertex();
		ArrayList< Integer > at15 = new ArrayList< Integer >();
		at15.add( 15 );
		at15.add( 17 );
		at15.add( 33 );
		setTerrObject( t15, idCnt, "Alaska", false, "North America", false, "Yellow", 1, at15 );
		idCnt++;
		TerritoriesGraphVertex t16 = new TerritoriesGraphVertex();
		ArrayList< Integer > at16 = new ArrayList< Integer >();
		at16.add( 14 );
		at16.add( 16 );
		at16.add( 17 );
		at16.add( 18 );
		setTerrObject( t16, idCnt, "Northwest Territory", false, "North America", false, "Blue", 2, at16 );
		idCnt++;
		TerritoriesGraphVertex t17 = new TerritoriesGraphVertex();
		ArrayList< Integer > at17 = new ArrayList< Integer >();
		at17.add( 15 );
		at17.add( 18 );
		at17.add( 19 );
		at17.add( 23 );
		setTerrObject( t17, idCnt, "Greenland", false, "North America", true, "Blue", 4, at17 );
		idCnt++;
		TerritoriesGraphVertex t18 = new TerritoriesGraphVertex();
		ArrayList< Integer > at18 = new ArrayList< Integer >();
		at18.add( 14 );
		at18.add( 15 );
		at18.add( 18 );
		at18.add( 20 );
		setTerrObject( t18, idCnt, "Alberta", false, "North America", false, "Green", 2, at18 );
		idCnt++;
		TerritoriesGraphVertex t19 = new TerritoriesGraphVertex();
		ArrayList< Integer > at19 = new ArrayList< Integer >();
		at19.add( 15 );
		at19.add( 16 );
		at19.add( 17 );
		at19.add( 19 );
		at19.add( 20 );
		at19.add( 21 );
		setTerrObject( t19, idCnt, "Ontario", true, "North America", false, "Green", 1, at19 );
		idCnt++;
		TerritoriesGraphVertex t20 = new TerritoriesGraphVertex();
		ArrayList< Integer > at20 = new ArrayList< Integer >();
		at20.add( 16 );
		at20.add( 18 );
		at20.add( 21 );
		setTerrObject( t20, idCnt, "Eastern Canada", false, "North America", false, "Blue", 2, at20 );
		idCnt++;
		TerritoriesGraphVertex t21 = new TerritoriesGraphVertex();
		ArrayList< Integer > at21 = new ArrayList< Integer >();
		at21.add( 17 );
		at21.add( 18 );
		at21.add( 21 );
		at21.add( 22 );
		setTerrObject( t21, idCnt, "Western United States", true, "North America", false, "Yellow", 1, at21 );
		idCnt++;
		TerritoriesGraphVertex t22 = new TerritoriesGraphVertex();
		ArrayList< Integer > at22 = new ArrayList< Integer >();
		at22.add( 18 );
		at22.add( 19 );
		at22.add( 20 );
		at22.add( 22 );
		setTerrObject( t22, idCnt, "Eastern United States", true, "North America", false, "Yellow", 1, at22 );
		idCnt++;
		TerritoriesGraphVertex t23 = new TerritoriesGraphVertex();
		ArrayList< Integer > at23 = new ArrayList< Integer >();
		at23.add( 4 );
		at23.add( 20 );
		at23.add( 21 );
		setTerrObject( t23, idCnt, "Central America", false, "North America", false, "Yellow", 1, at23 );
		idCnt++;
		TerritoriesGraphVertex t24 = new TerritoriesGraphVertex();
		ArrayList< Integer > at24 = new ArrayList< Integer >();
		at24.add( 16 );
		at24.add( 24 );
		at24.add( 26 );
		setTerrObject( t24, idCnt, "Iceland", false, "Europe", false, "Blue", 1, at24 );
		idCnt++;
		TerritoriesGraphVertex t25 = new TerritoriesGraphVertex();
		ArrayList< Integer > at25 = new ArrayList< Integer >();
		at25.add( 23 );
		at25.add( 25 );
		at25.add( 26 );
		at25.add( 27 );
		setTerrObject( t25, idCnt, "Scandinavia", false, "Europe", false, "Yellow", 2, at25 );
		idCnt++;
		TerritoriesGraphVertex t26 = new TerritoriesGraphVertex();
		ArrayList< Integer > at26 = new ArrayList< Integer >();
		at26.add( 24 );
		at26.add( 27 );
		at26.add( 29 );
		at26.add( 30 );
		at26.add( 37 );
		at26.add( 39 );
		setTerrObject( t26, idCnt, "Russia", true, "Europe", false, "Yellow", 2, at26 );
		idCnt++;
		TerritoriesGraphVertex t27 = new TerritoriesGraphVertex();
		ArrayList< Integer > at27 = new ArrayList< Integer >();
		at27.add( 23 );
		at27.add( 24 );
		at27.add( 27 );
		at27.add( 28 );
		setTerrObject( t27, idCnt, "Great Britain", true, "Europe", false, "Green", 2, at27 );
		idCnt++;
		TerritoriesGraphVertex t28 = new TerritoriesGraphVertex();
		ArrayList< Integer > at28 = new ArrayList< Integer >();
		at28.add( 24 );
		at28.add( 25 );
		at28.add( 26 );
		at28.add( 28 );
		at28.add( 29 );
		setTerrObject( t28, idCnt, "Northern Europe", true, "Europe", false, "Blue", 3, at28 );
		idCnt++;
		TerritoriesGraphVertex t29 = new TerritoriesGraphVertex();
		ArrayList< Integer > at29 = new ArrayList< Integer >();
		at29.add( 9 );
		at29.add( 26 );
		at29.add( 27 );
		at29.add( 29 );
		setTerrObject( t29, idCnt, "Western Europe", true, "Europe", false, "Blue", 3, at29 );
		idCnt++;
		TerritoriesGraphVertex t30 = new TerritoriesGraphVertex();
		ArrayList< Integer > at30 = new ArrayList< Integer >();
		at30.add( 8 );
		at30.add( 9 );
		at30.add( 25 );
		at30.add( 27 );
		at30.add( 28 );
		at30.add( 39 );
		setTerrObject( t30, idCnt, "Southern Europe", true, "Europe", false, "Blue", 3, at30 );
		idCnt++;
		TerritoriesGraphVertex t31 = new TerritoriesGraphVertex();
		ArrayList< Integer > at31 = new ArrayList< Integer >();
		at31.add( 25 );
		at31.add( 31 );
		at31.add( 37 );
		at31.add( 38 );
		setTerrObject( t31, idCnt, "Ural", false, "Asia", false, "Green", 1, at31 );
		idCnt++;
		TerritoriesGraphVertex t32 = new TerritoriesGraphVertex();
		ArrayList< Integer > at32 = new ArrayList< Integer >();
		at32.add( 30 );
		at32.add( 32 );
		at32.add( 34 );
		at32.add( 35 );
		at32.add( 38 );
		setTerrObject( t32, idCnt, "Siberia", false, "Asia", false, "Green", 1, at32 );
		idCnt++;
		TerritoriesGraphVertex t33 = new TerritoriesGraphVertex();
		ArrayList< Integer > at33 = new ArrayList< Integer >();
		at33.add( 31 );
		at33.add( 33 );
		at33.add( 34 );
		setTerrObject( t33, idCnt, "Yakutsk", false, "Asia", false, "Yellow", 2, at33 );
		idCnt++;
		TerritoriesGraphVertex t34 = new TerritoriesGraphVertex();
		ArrayList< Integer > at34 = new ArrayList< Integer >();
		at34.add( 14 );
		at34.add( 32 );
		at34.add( 34 );
		at34.add( 35 );
		at34.add( 36 );
		setTerrObject( t34, idCnt, "Kamchatka", false, "Asia", false, "Blue", 1, at34 );
		idCnt++;
		TerritoriesGraphVertex t35 = new TerritoriesGraphVertex();
		ArrayList< Integer > at35 = new ArrayList< Integer >();
		at35.add( 31 );
		at35.add( 32 );
		at35.add( 33 );
		at35.add( 35 );
		setTerrObject( t35, idCnt, "Irkutsk", false, "Asia", false, "Green", 2, at35 );
		idCnt++;
		TerritoriesGraphVertex t36 = new TerritoriesGraphVertex();
		ArrayList< Integer > at36 = new ArrayList< Integer >();
		at36.add( 31 );
		at36.add( 33 );
		at36.add( 34 );
		at36.add( 36 );
		at36.add( 38 );
		setTerrObject( t36, idCnt, "Mongolia", false, "Asia", false, "Green", 2, at36 );
		idCnt++;
		TerritoriesGraphVertex t37 = new TerritoriesGraphVertex();
		ArrayList< Integer > at37 = new ArrayList< Integer >();
		at37.add( 33 );
		at37.add( 35 );
		setTerrObject( t37, idCnt, "Japan", true, "Asia", false, "Green", 4, at37 );
		idCnt++;
		TerritoriesGraphVertex t38 = new TerritoriesGraphVertex();
		ArrayList< Integer > at38 = new ArrayList< Integer >();
		at38.add( 25 );
		at38.add( 30 );
		at38.add( 38 );
		at38.add( 40 );
		at38.add( 39 );
		setTerrObject( t38, idCnt, "Afghanistan", false, "Asia", false, "Blue", 1, at38 );
		idCnt++;
		TerritoriesGraphVertex t39 = new TerritoriesGraphVertex();
		ArrayList< Integer > at39 = new ArrayList< Integer >();
		at39.add( 30 );
		at39.add( 31 );
		at39.add( 35 );
		at39.add( 37 );
		at39.add( 40 );
		at39.add( 41 );
		setTerrObject( t39, idCnt, "China", true, "Asia", false, "Yellow", 3, at39 );
		idCnt++;
		TerritoriesGraphVertex t40 = new TerritoriesGraphVertex();
		ArrayList< Integer > at40 = new ArrayList< Integer >();
		at40.add( 8 );
		at40.add( 10 );
		at40.add( 25 );
		at40.add( 29 );
		at40.add( 37 );
		at40.add( 40 );
		setTerrObject( t40, idCnt, "Middle East", false, "Asia", false, "Blue", 1, at40 );
		idCnt++;
		TerritoriesGraphVertex t41 = new TerritoriesGraphVertex();
		ArrayList< Integer > at41 = new ArrayList< Integer >();
		at41.add( 37 );
		at41.add( 38 );
		at41.add( 39 );
		at41.add( 41 );
		setTerrObject( t41, idCnt, "India", true, "Asia", false, "Yellow", 3, at41 );
		idCnt++;
		TerritoriesGraphVertex t42 = new TerritoriesGraphVertex();
		ArrayList< Integer > at42 = new ArrayList< Integer >();
		at42.add( 2 );
		at42.add( 38 );
		at42.add( 40 );
		setTerrObject( t42, idCnt, "Southeast Asia", false, "Asia", false, "Yellow", 4, at42 );
	}

	private void setTerrObject( TerritoriesGraphVertex t, int idCnt, String country, boolean isCity, String continent, boolean isCapital, String player, int numTroops, ArrayList< Integer > at )
	{
		t.setId( idCnt );
		t.setCountry( country );
		t.setCity( isCity );
		t.setContinent( continent );
		t.setCapital( isCapital );
		t.setOccupiedBy( player );
		t.setNumTroops( numTroops );
		t.setAdjTerr( at );
		listTerr.add( t );
	}

	private void buildStateSpaceGraph( String objective, String player, String csvOutputVerticesFilePath, String csvOutputEdgesFilePath )
	{
		if( objective.equalsIgnoreCase( objectives.CONTROL_EUROPE.toString() ) )
		{
			buildStateSpaceGraphForControlContinent( player, csvOutputVerticesFilePath, csvOutputEdgesFilePath, "Europe", 7, 23, 29 );
		}
		if( objective.equalsIgnoreCase( objectives.CONTROL_ASIA.toString() ) )
		{
			buildStateSpaceGraphForControlContinent( player, csvOutputVerticesFilePath, csvOutputEdgesFilePath, "Asia", 12, 30, 41 );
		}
		if( objective.equalsIgnoreCase( objectives.CONTROL_NORTH_AMERICA.toString() ) )
		{
			buildStateSpaceGraphForControlContinent( player, csvOutputVerticesFilePath, csvOutputEdgesFilePath, "North America", 9, 14, 22 );
		}
		if( objective.equalsIgnoreCase( objectives.CONTROL_AUSTRALIA.toString() ) )
		{
			buildStateSpaceGraphForControlContinent( player, csvOutputVerticesFilePath, csvOutputEdgesFilePath, "Australia", 4, 0, 3 );
		}
		if( objective.equalsIgnoreCase( objectives.CONTROL_AFRICA.toString() ) )
		{
			buildStateSpaceGraphForControlContinent( player, csvOutputVerticesFilePath, csvOutputEdgesFilePath, "Africa", 6, 8, 13 );
		}
		if( objective.equalsIgnoreCase( objectives.CONTROL_SOUTH_AMERICA.toString() ) )
		{
			buildStateSpaceGraphForControlContinent( player, csvOutputVerticesFilePath, csvOutputEdgesFilePath, "South America", 4, 4, 7 );
		}
	}

	/*
	 * Builds the Markovian state space graph where state is defined as the
	 * current number and position of the troops in the game. Every possible
	 * attack that a player can launch from this state in his turn creates
	 * two new states where the first state is the result of the attack
	 * being successful whereas the second state is the result of an
	 * unsuccessful attack.
	 * 
	 * @player: The player for which the state space graph is built
	 * indicated by the colour of the troops
	 * 
	 * @csvOutputVerticesFilePath: The path where the csv file to store the
	 * data for vertices shall be generated
	 * 
	 * @csvOutputEdgesFilePath: The path where the csv file to store the
	 * data for edges shall be generated
	 * 
	 * @continent: The continent which the player is attempting to control
	 * 
	 * @numTerr: The number of territories the continent consists of
	 * 
	 * @startId: The id of the first territory of the continent as created
	 * in the method populateGraph()
	 * 
	 * @endId: The id of the last territory of the continent as created in
	 * the method populateGraph()
	 */
	private void buildStateSpaceGraphForControlContinent( String player, String csvOutputVerticesFilePath, String csvOutputEdgesFilePath, String continent, int numTerr, int startId, int endId )
	{
		try
		{
			buildHopCountTerrForControlContinent( startId, endId );
			Queue< State > bfs = new LinkedList< State >();
			State s = new State();
			s.setId( 0l );
			s.setReward( 0 );
			s.setUtility( 0d );
			s.setActions( null );
			s.setAdjStates( new ArrayList< Long >() );
			s.setProbability( 1d );
			bfs.add( s );
			State st = null;
			long iCnt = 0;
			int eCnt = 0;
			for( int i = 0; i < listTerr.size(); i++ )
			{
				if( listTerr.get( i ).getContinent().equalsIgnoreCase( continent ) && listTerr.get( i ).getOccupiedBy().equalsIgnoreCase( player ) )
				{
					eCnt++;
				}
			}
			if( eCnt == numTerr )
			{
				return;
			}
			ArrayList< TerritoriesGraphVertex > terr1 = new ArrayList< TerritoriesGraphVertex >();
			TerritoriesGraphVertex t = null;
			State s2 = null;
			State s3 = null;
			HashSet< Integer > fromConn = null;
			HashSet< Integer > toConn = null;
			ArrayList< Short > actions2 = null;
			ArrayList< Short > actions3 = null;
			BayesianModel bmodel = new BayesianModel();
			File fv = new File( csvOutputVerticesFilePath );
			File fe = new File( csvOutputEdgesFilePath );
			if( fv.exists() )
			{
				fv.delete();
				fv = new File( csvOutputVerticesFilePath );
			}
			if( fe.exists() )
			{
				fe.delete();
				fe = new File( csvOutputEdgesFilePath );
			}
			CsvWriter csvOutputVertices = new CsvWriter( new FileWriter( csvOutputVerticesFilePath, true ), ',' );
			CsvWriter csvOutputEdges = new CsvWriter( new FileWriter( csvOutputEdgesFilePath, true ), ',' );
			while( !bfs.isEmpty() )
			{
				st = bfs.remove();
				long sid = st.getId();
				try
				{
					for( int k = 0; k < listTerr.size(); k++ )
					{
						terr1.add( ( TerritoriesGraphVertex ) listTerr.get( k ).clone() );
					}
				}
				catch( CloneNotSupportedException e )
				{
					e.printStackTrace();
				}
				ArrayList< Short > actions1 = st.getActions();
				if( actions1 != null )
				{
					for( int i = 0; i < actions1.size(); i++ )
					{
						short act = actions1.get( i );
						int from = ( int ) ( act / divisor );
						int to = ( int ) ( act % divisor );
						terr1.get( to ).setOccupiedBy( player );
						terr1.get( to ).setNumTroops( terr1.get( from ).getNumTroops() - 1 );
						terr1.get( from ).setNumTroops( 1 );
					}
				}
				for( int i = 0; i < terr1.size(); i++ )
				{
					t = terr1.get( i );
					if( !( t.getOccupiedBy().equalsIgnoreCase( player ) ) || t.getNumTroops() == 1 )
					{
						continue;
					}
					ArrayList< Integer > adjTerrPlayer = t.getAdjTerr();
					int otid = t.getId();
					int hopCntFrom = hopCountTerr.get( otid );
					for( int j = 0; j < adjTerrPlayer.size(); j++ )
					{
						int tid = adjTerrPlayer.get( j );
						if( terr1.get( tid ).getOccupiedBy().equalsIgnoreCase( player ) )
						{
							continue;
						}
						int hopCntTo = hopCountTerr.get( tid );
						/*
						 * The below two if conditions
						 * have been added to prune the
						 * state space as it could get
						 * really large! Feel free to
						 * remove them if your system
						 * has enough memory to generate
						 * all the possible states. The
						 * first pruning criteria is
						 * based on the assumption that
						 * no player will attack a
						 * territory which is farther
						 * away from the continent to be
						 * controlled than the territory
						 * from which the attack is
						 * being considered
						 */
						if( hopCntTo > hopCntFrom )
						{
							continue;
						}
						/*
						 * The second pruning criteria
						 * is based on the assumption
						 * that any attack staged from a
						 * territory outside the desired
						 * continent to control to
						 * another territory having the
						 * same distance to that
						 * continent is redundant if
						 * there exists a territory or
						 * territories which are
						 * accessible by both
						 * territories and which lie
						 * closer to that continent and
						 * attacking the said territory
						 * will eventually lead to
						 * attacking either of these
						 * territories.
						 */
						if( ( hopCntTo == hopCntFrom ) && ( hopCntTo != 0 ) )
						{
							fromConn = hopCountConnectionTerr.get( otid );
							toConn = hopCountConnectionTerr.get( tid );
							boolean toAttack = false;
							for( int k : toConn )
							{
								if( !fromConn.contains( k ) )
								{
									toAttack = true;
								}
							}
							if( !toAttack )
							{
								continue;
							}
						}
						int div = ( int ) divisor;
						short atr = ( short ) ( otid * div + tid );
						// s2 is the state of the player
						// if the attack is a success
						// with probability p computed
						// below.
						actions2 = new ArrayList< Short >();
						actions3 = new ArrayList< Short >();
						if( actions1 != null )
						{
							for( int k = 0; k < actions1.size(); k++ )
							{
								actions2.add( actions1.get( k ) );
								actions3.add( actions1.get( k ) );
							}
						}
						actions2.add( atr );
						s2 = new State();
						s2.setId( ++iCnt );
						s2.setActions( actions2 );
						if( terr1.get( tid ).getContinent().equalsIgnoreCase( "Europe" ) )
						{
							eCnt++;
							s2.setReward( 10 * eCnt );
						}
						else
						{
							s2.setReward( 0 );
						}
						s2.setUtility( 0d );
						s2.setAdjStates( new ArrayList< Long >() );
						// Calculates the probability
						// that the attack will be
						// successful based using a
						// Bayesian Network
						double probability = bmodel.computeProbabilityUsingBayesianModel( terr1.get( otid ).getNumTroops(), terr1.get( tid ).getNumTroops() );
						s2.setProbability( probability );
						st.getAdjStates().add( iCnt );
						bfs.add( s2 );
						csvOutputEdges.write( String.valueOf( sid ) );
						csvOutputEdges.write( String.valueOf( iCnt ) );
						csvOutputEdges.write( terr1.get( otid ).getCountry() );
						csvOutputEdges.write( terr1.get( tid ).getCountry() );
						csvOutputEdges.write( String.valueOf( probability ) );
						csvOutputEdges.endRecord();
						short atr1 = ( short ) ( otid * div + otid );
						actions3.add( atr1 );
						/*
						 * s3 is the state of the player
						 * if the attack fails. The
						 * assumption is that the attack
						 * was a complete failure so
						 * only one troop is left and
						 * the player cannot attack from
						 * that territory anymore. Thus,
						 * the probability for this
						 * state has been set to 2, so
						 * that it is distinguishable
						 * from the success state. Apart
						 * from that, the number 2 holds
						 * no meaning.
						 */
						s3 = new State();
						s3.setId( ++iCnt );
						s3.setActions( actions3 );
						s3.setReward( st.getReward() - 5 );
						s3.setAdjStates( new ArrayList< Long >() );
						s3.setProbability( 2d );
						s3.setUtility( 0d );
						st.getAdjStates().add( iCnt );
						bfs.add( s3 );
						csvOutputEdges.write( String.valueOf( sid ) );
						csvOutputEdges.write( String.valueOf( iCnt ) );
						csvOutputEdges.write( terr1.get( otid ).getCountry() );
						csvOutputEdges.write( terr1.get( otid ).getCountry() );
						csvOutputEdges.write( String.valueOf( 2d ) );
						csvOutputEdges.endRecord();
					}
				}
				terr1.clear();
				csvOutputVertices.write( String.valueOf( sid ) );
				csvOutputVertices.write( String.valueOf( st.getReward() ) );
				csvOutputVertices.endRecord();
			}
			csvOutputVertices.close();
			csvOutputEdges.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	/*
	 * Returns a map consisting of the minimum number of adjacent hops that
	 * a particular territory of another continent is located from any
	 * territory in the continent to be controlled. In other words, the key
	 * is the territory id whereas the value is the minimum number of
	 * territories in the way between that territory and any territory in
	 * the continent to be controlled.
	 * 
	 * @startId: The id of the first territory of the continent as created
	 * in the method populateGraph()
	 * 
	 * @endId: The id of the last territory of the continent as created in
	 * the method populateGraph()
	 */
	private void buildHopCountTerrForControlContinent( int startId, int endId )
	{
		Queue< Integer > bfs = new LinkedList< Integer >();
		ArrayList< Integer > adj = null;
		for( int i = startId; i <= endId; i++ )
		{
			hopCountTerr.put( i, 0 );
			bfs.add( i );
		}
		while( !bfs.isEmpty() )
		{
			int t = bfs.remove();
			int hopCnt = hopCountTerr.get( t );
			adj = listTerr.get( t ).getAdjTerr();
			for( int i = 0; i < adj.size(); i++ )
			{
				int j = adj.get( i );
				if( !hopCountTerr.containsKey( j ) )
				{
					hopCountTerr.put( j, hopCnt + 1 );
					HashSet< Integer > al1 = new HashSet< Integer >();
					al1.add( t );
					hopCountConnectionTerr.put( j, al1 );
					bfs.add( j );
				}
				else
				{
					int toHop = hopCountTerr.get( j );
					if( hopCnt + 1 == toHop )
					{
						hopCountConnectionTerr.get( j ).add( t );
					}
				}
			}
		}
	}
}
