package riskmdp;

import java.util.ArrayList;

public class BayesianNetworkTreeNode
{
	private int id = 0;
	private boolean isLeaf = false;
	private ArrayList< Integer > adjNodes = null;
	private double totalProbability = 0d;
	private String status = null;
	private int fromTroops = 0;
	private int toTroops = 0;

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public boolean isLeaf()
	{
		return isLeaf;
	}

	public void setLeaf( boolean isLeaf )
	{
		this.isLeaf = isLeaf;
	}

	public ArrayList< Integer > getAdjNodes()
	{
		return adjNodes;
	}

	public void setAdjNodes( ArrayList< Integer > adjNodes )
	{
		this.adjNodes = adjNodes;
	}

	public double getTotalProbability()
	{
		return totalProbability;
	}

	public void setTotalProbability( double totalProbability )
	{
		this.totalProbability = totalProbability;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus( String status )
	{
		this.status = status;
	}

	public int getFromTroops()
	{
		return fromTroops;
	}

	public void setFromTroops( int fromTroops )
	{
		this.fromTroops = fromTroops;
	}

	public int getToTroops()
	{
		return toTroops;
	}

	public void setToTroops( int toTroops )
	{
		this.toTroops = toTroops;
	}
}
