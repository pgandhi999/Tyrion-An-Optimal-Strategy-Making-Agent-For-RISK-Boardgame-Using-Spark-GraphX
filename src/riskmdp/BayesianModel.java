package riskmdp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/* 
 * This class uses methods to build a Bayesian Network, given the number of troops each player has.
 * The bayesian network is constructed based on the rules of the game. For going through the rules,
 * please refer https://www.hasbro.com/common/instruct/risk.pdf.
 */
public class BayesianModel
{
	public static int nodeId = 0;

	/*
	 * This method calculates the probability that an attack launched by the given player is
	 * successful, given an enemy territory is chosen for the attack. The function takes in the number of
	 * troops that both the players have in those respective territories and builds a
	 * Bayesian Network from that information. The construction of the network follows certain assumptions derived from the rules of the game:
	 * 
	 * 1. At any given instance, to wage an attack, any of the following six troop formation models are permitted:
	 * 
	 *     - 3 troops Attack, 2 troops Defend
	 *     - 3 troops Attack, 1 troop Defends
	 *     - 2 troops Attack, 2 troops Defend
	 *     - 2 troops Attack, 1 troop Defends
	 *     - 1 troop Attacks, 2 troops Defend
	 *     - 1 troop Attacks, 1 troop Defends
	 *     
	 * 2. If an attack is launched, it shall not stop till either 0 troops are left to defend or 0 troops left to attack. (I admit it is kind of a rigid assumption but it certainly makes it easier to build the Bayesian Network.)
	 * 
	 * The probabilities for all the six models above have been computed based on the programs written in test_framework package.
	 * 
	 * @ fromNumTroops: The number of troops in the offense territory
	 * @ toNumTroops: The number of troops in the defense territory
	 */
	public double computeProbabilityUsingBayesianModel( int fromNumTroops, int toNumTroops )
	{
		ArrayList< BayesianNetworkTreeNode > bayesianNetworkTree = new ArrayList< BayesianNetworkTreeNode >();
		Queue< BayesianNetworkTreeNode > bfs = new LinkedList< BayesianNetworkTreeNode >();
		BayesianNetworkTreeNode bt = new BayesianNetworkTreeNode();
		bt.setId( nodeId );
		nodeId++;
		bt.setLeaf( false );
		bt.setAdjNodes( new ArrayList< Integer >() );
		bt.setTotalProbability( 1d );
		bt.setStatus( null );
		bt.setFromTroops( fromNumTroops );
		bt.setToTroops( toNumTroops );
		bayesianNetworkTree.add( bt );
		bfs.add( bt );
		BayesianNetworkTreeNode bt1 = null;
		while( !bfs.isEmpty() )
		{
			bt1 = bfs.remove();
			if( bt1.isLeaf() )
			{
				continue;
			}
			buildBayesianNetwork( bt1, bt1.getFromTroops(), bt1.getToTroops(), bayesianNetworkTree, bfs );
		}
		double loseProb = 0d;
		BayesianNetworkTreeNode bt2 = null;
		for( int i = 0; i < bayesianNetworkTree.size(); i++ )
		{
			bt2 = bayesianNetworkTree.get( i );
			if( bt2.isLeaf() && bt2.getStatus().equalsIgnoreCase( "Lose" ) )
			{
				loseProb = loseProb + bt2.getTotalProbability();
			}
		}
		double winProb = 1 - loseProb;
		nodeId = 0;
		bayesianNetworkTree.clear();
		return winProb;
	}

	private void buildBayesianNetwork( BayesianNetworkTreeNode bnt, int fromTroops, int toTroops, ArrayList< BayesianNetworkTreeNode > bayesianNetworkTree, Queue< BayesianNetworkTreeNode > bfs )
	{
		int maxFromTroops = 3;
		int maxToTroops = 2;
		if( fromTroops <= 3 )
		{
			maxFromTroops = fromTroops - 1;
		}
		if( toTroops < 2 )
		{
			maxToTroops = toTroops;
		}
		if( maxFromTroops == 3 && maxToTroops == 2 )
		{
			buildBayesianNetworkThreeVsTwo( bnt, fromTroops, toTroops, bayesianNetworkTree, bfs );
		}
		else if( maxFromTroops == 3 && maxToTroops == 1 )
		{
			buildBayesianNetworkThreeVsOne( bnt, fromTroops, toTroops, bayesianNetworkTree, bfs );
		}
		else if( maxFromTroops == 2 && maxToTroops == 2 )
		{
			buildBayesianNetworkTwoVsTwo( bnt, fromTroops, toTroops, bayesianNetworkTree, bfs );
		}
		else if( maxFromTroops == 2 && maxToTroops == 1 )
		{
			buildBayesianNetworkTwoVsOne( bnt, fromTroops, toTroops, bayesianNetworkTree, bfs );
		}
		else if( maxFromTroops == 1 && maxToTroops == 2 )
		{
			buildBayesianNetworkOneVsTwo( bnt, fromTroops, toTroops, bayesianNetworkTree, bfs );
		}
		else if( maxFromTroops == 1 && maxToTroops == 1 )
		{
			buildBayesianNetworkOneVsOne( bnt, fromTroops, toTroops, bayesianNetworkTree, bfs );
		}
	}

	private void buildBayesianNetworkOneVsOne( BayesianNetworkTreeNode bnt, int fromTroops, int toTroops, ArrayList< BayesianNetworkTreeNode > bayesianNetworkTree,
			Queue< BayesianNetworkTreeNode > bfs )
	{
		BayesianNetworkTreeNode bnt1 = new BayesianNetworkTreeNode();
		bnt1.setId( nodeId );
		nodeId++;
		bnt1.setAdjNodes( new ArrayList< Integer >() );
		bnt1.setTotalProbability( bnt.getTotalProbability() * 0.41666666d );
		bnt1.setFromTroops( fromTroops );
		bnt1.setToTroops( toTroops - 1 );
		determineLeafAndStatusNode( bnt1 );

		BayesianNetworkTreeNode bnt2 = new BayesianNetworkTreeNode();
		bnt2.setId( nodeId );
		nodeId++;
		bnt2.setAdjNodes( new ArrayList< Integer >() );
		bnt2.setTotalProbability( bnt.getTotalProbability() * 0.5833333d );
		bnt2.setFromTroops( fromTroops - 1 );
		bnt2.setToTroops( toTroops );
		determineLeafAndStatusNode( bnt2 );

		bayesianNetworkTree.add( bnt1 );
		bfs.add( bnt1 );
		bnt.getAdjNodes().add( bnt1.getId() );
		bayesianNetworkTree.add( bnt2 );
		bfs.add( bnt2 );
		bnt.getAdjNodes().add( bnt2.getId() );
	}

	private void buildBayesianNetworkOneVsTwo( BayesianNetworkTreeNode bnt, int fromTroops, int toTroops, ArrayList< BayesianNetworkTreeNode > bayesianNetworkTree,
			Queue< BayesianNetworkTreeNode > bfs )
	{
		BayesianNetworkTreeNode bnt1 = new BayesianNetworkTreeNode();
		bnt1.setId( nodeId );
		nodeId++;
		bnt1.setAdjNodes( new ArrayList< Integer >() );
		bnt1.setTotalProbability( bnt.getTotalProbability() * 0.4212963d );
		bnt1.setFromTroops( fromTroops );
		bnt1.setToTroops( toTroops - 1 );
		determineLeafAndStatusNode( bnt1 );

		BayesianNetworkTreeNode bnt2 = new BayesianNetworkTreeNode();
		bnt2.setId( nodeId );
		nodeId++;
		bnt2.setAdjNodes( new ArrayList< Integer >() );
		bnt2.setTotalProbability( bnt.getTotalProbability() * 0.5787037d );
		bnt2.setFromTroops( fromTroops - 1 );
		bnt2.setToTroops( toTroops );
		determineLeafAndStatusNode( bnt2 );

		bayesianNetworkTree.add( bnt1 );
		bfs.add( bnt1 );
		bnt.getAdjNodes().add( bnt1.getId() );
		bayesianNetworkTree.add( bnt2 );
		bfs.add( bnt2 );
		bnt.getAdjNodes().add( bnt2.getId() );
	}

	private void buildBayesianNetworkTwoVsOne( BayesianNetworkTreeNode bnt, int fromTroops, int toTroops, ArrayList< BayesianNetworkTreeNode > bayesianNetworkTree,
			Queue< BayesianNetworkTreeNode > bfs )
	{
		BayesianNetworkTreeNode bnt1 = new BayesianNetworkTreeNode();
		bnt1.setId( nodeId );
		nodeId++;
		bnt1.setAdjNodes( new ArrayList< Integer >() );
		bnt1.setTotalProbability( bnt.getTotalProbability() * 0.5787037d );
		bnt1.setFromTroops( fromTroops );
		bnt1.setToTroops( toTroops - 1 );
		determineLeafAndStatusNode( bnt1 );

		BayesianNetworkTreeNode bnt2 = new BayesianNetworkTreeNode();
		bnt2.setId( nodeId );
		nodeId++;
		bnt2.setAdjNodes( new ArrayList< Integer >() );
		bnt2.setTotalProbability( bnt.getTotalProbability() * 0.4212963d );
		bnt2.setFromTroops( fromTroops - 1 );
		bnt2.setToTroops( toTroops );
		determineLeafAndStatusNode( bnt2 );

		bayesianNetworkTree.add( bnt1 );
		bfs.add( bnt1 );
		bnt.getAdjNodes().add( bnt1.getId() );
		bayesianNetworkTree.add( bnt2 );
		bfs.add( bnt2 );
		bnt.getAdjNodes().add( bnt2.getId() );
	}

	private void buildBayesianNetworkTwoVsTwo( BayesianNetworkTreeNode bnt, int fromTroops, int toTroops, ArrayList< BayesianNetworkTreeNode > bayesianNetworkTree,
			Queue< BayesianNetworkTreeNode > bfs )
	{
		BayesianNetworkTreeNode bnt1 = new BayesianNetworkTreeNode();
		bnt1.setId( nodeId );
		nodeId++;
		bnt1.setAdjNodes( new ArrayList< Integer >() );
		bnt1.setTotalProbability( bnt.getTotalProbability() * 0.22762346d );
		bnt1.setFromTroops( fromTroops );
		bnt1.setToTroops( toTroops - 2 );
		determineLeafAndStatusNode( bnt1 );

		BayesianNetworkTreeNode bnt2 = new BayesianNetworkTreeNode();
		bnt2.setId( nodeId );
		nodeId++;
		bnt2.setAdjNodes( new ArrayList< Integer >() );
		bnt2.setTotalProbability( bnt.getTotalProbability() * 0.32407406d );
		bnt2.setFromTroops( fromTroops - 1 );
		bnt2.setToTroops( toTroops - 1 );
		determineLeafAndStatusNode( bnt2 );

		BayesianNetworkTreeNode bnt3 = new BayesianNetworkTreeNode();
		bnt3.setId( nodeId );
		nodeId++;
		bnt3.setAdjNodes( new ArrayList< Integer >() );
		bnt3.setTotalProbability( bnt.getTotalProbability() * 0.44830248d );
		bnt3.setFromTroops( fromTroops - 2 );
		bnt3.setToTroops( toTroops );
		determineLeafAndStatusNode( bnt3 );

		bayesianNetworkTree.add( bnt1 );
		bfs.add( bnt1 );
		bnt.getAdjNodes().add( bnt1.getId() );
		bayesianNetworkTree.add( bnt2 );
		bfs.add( bnt2 );
		bnt.getAdjNodes().add( bnt2.getId() );
		bayesianNetworkTree.add( bnt3 );
		bfs.add( bnt3 );
		bnt.getAdjNodes().add( bnt3.getId() );
	}

	private void buildBayesianNetworkThreeVsOne( BayesianNetworkTreeNode bnt, int fromTroops, int toTroops, ArrayList< BayesianNetworkTreeNode > bayesianNetworkTree,
			Queue< BayesianNetworkTreeNode > bfs )
	{
		BayesianNetworkTreeNode bnt1 = new BayesianNetworkTreeNode();
		bnt1.setId( nodeId );
		nodeId++;
		bnt1.setAdjNodes( new ArrayList< Integer >() );
		bnt1.setTotalProbability( bnt.getTotalProbability() * 0.6597222d );
		bnt1.setFromTroops( fromTroops );
		bnt1.setToTroops( toTroops - 1 );
		determineLeafAndStatusNode( bnt1 );

		BayesianNetworkTreeNode bnt2 = new BayesianNetworkTreeNode();
		bnt2.setId( nodeId );
		nodeId++;
		bnt2.setAdjNodes( new ArrayList< Integer >() );
		bnt2.setTotalProbability( bnt.getTotalProbability() * 0.3402778d );
		bnt2.setFromTroops( fromTroops - 1 );
		bnt2.setToTroops( toTroops );
		determineLeafAndStatusNode( bnt2 );

		bayesianNetworkTree.add( bnt1 );
		bfs.add( bnt1 );
		bnt.getAdjNodes().add( bnt1.getId() );
		bayesianNetworkTree.add( bnt2 );
		bfs.add( bnt2 );
		bnt.getAdjNodes().add( bnt2.getId() );
	}

	private void buildBayesianNetworkThreeVsTwo( BayesianNetworkTreeNode bnt, int fromTroops, int toTroops, ArrayList< BayesianNetworkTreeNode > bayesianNetworkTree,
			Queue< BayesianNetworkTreeNode > bfs )
	{
		BayesianNetworkTreeNode bnt1 = new BayesianNetworkTreeNode();
		bnt1.setId( nodeId );
		nodeId++;
		bnt1.setAdjNodes( new ArrayList< Integer >() );
		bnt1.setTotalProbability( bnt.getTotalProbability() * 0.3716564d );
		bnt1.setFromTroops( fromTroops );
		bnt1.setToTroops( toTroops - 2 );
		determineLeafAndStatusNode( bnt1 );

		BayesianNetworkTreeNode bnt2 = new BayesianNetworkTreeNode();
		bnt2.setId( nodeId );
		nodeId++;
		bnt2.setAdjNodes( new ArrayList< Integer >() );
		bnt2.setTotalProbability( bnt.getTotalProbability() * 0.33577675d );
		bnt2.setFromTroops( fromTroops - 1 );
		bnt2.setToTroops( toTroops - 1 );
		determineLeafAndStatusNode( bnt2 );

		BayesianNetworkTreeNode bnt3 = new BayesianNetworkTreeNode();
		bnt3.setId( nodeId );
		nodeId++;
		bnt3.setAdjNodes( new ArrayList< Integer >() );
		bnt3.setTotalProbability( bnt.getTotalProbability() * 0.29256687d );
		bnt3.setFromTroops( fromTroops - 2 );
		bnt3.setToTroops( toTroops );
		determineLeafAndStatusNode( bnt3 );

		bayesianNetworkTree.add( bnt1 );
		bfs.add( bnt1 );
		bnt.getAdjNodes().add( bnt1.getId() );
		bayesianNetworkTree.add( bnt2 );
		bfs.add( bnt2 );
		bnt.getAdjNodes().add( bnt2.getId() );
		bayesianNetworkTree.add( bnt3 );
		bfs.add( bnt3 );
		bnt.getAdjNodes().add( bnt3.getId() );
	}

	private void determineLeafAndStatusNode( BayesianNetworkTreeNode bntn )
	{
		if( bntn.getFromTroops() == 1 )
		{
			bntn.setLeaf( true );
			bntn.setStatus( "Lose" );
			return;
		}
		else if( bntn.getToTroops() == 0 )
		{
			bntn.setLeaf( true );
			bntn.setStatus( "Win" );
			return;
		}
		bntn.setLeaf( false );
		bntn.setStatus( null );
	}
}
