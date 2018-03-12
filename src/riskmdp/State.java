package riskmdp;

import java.util.ArrayList;

/*
 * Represents the state vertex object in the state space graph.
 * id: state id
 * actions: The list of attack moves to a particular territory from a particular territory that led
 * to this state from the initial state. Used to generate any given state from the initial state
 * hence, storing only the initial state is needed rather than storing all the states thus, saving memory.
 * reward: The reward associated with this state
 * adjStates: List of all child states derived from this state
 * probability: The probability of reaching this state from it's parent state
 * utility: The utility of this state
 */
public class State
{
	private long id = 0;
	private ArrayList< Short > actions = null;
	private int reward = 0;
	private ArrayList< Long > adjStates = null;
	private double probability = 0d;
	private double utility = 0d;

	public long getId()
	{
		return id;
	}

	public void setId( long id )
	{
		this.id = id;
	}

	public ArrayList< Short > getActions()
	{
		return actions;
	}

	public void setActions( ArrayList< Short > actions )
	{
		this.actions = actions;
	}

	public int getReward()
	{
		return reward;
	}

	public void setReward( int reward )
	{
		this.reward = reward;
	}

	public ArrayList< Long > getAdjStates()
	{
		return adjStates;
	}

	public void setAdjStates( ArrayList< Long > adjStates )
	{
		this.adjStates = adjStates;
	}

	public double getProbability()
	{
		return probability;
	}

	public void setProbability( double probability )
	{
		this.probability = probability;
	}

	public double getUtility()
	{
		return utility;
	}

	public void setUtility( double utility )
	{
		this.utility = utility;
	}
}