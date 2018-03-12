package riskmdp;

import java.util.ArrayList;

public class TerritoriesGraphVertex implements Cloneable
{
	private int id = 0;
	private String country = null;
	private boolean isCity = false;
	private String continent = null;
	private boolean isCapital = false;
	private String occupiedBy = null;
	private int numTroops = 0;
	private ArrayList< Integer > adjTerr = null;

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry( String country )
	{
		this.country = country;
	}

	public boolean isCity()
	{
		return isCity;
	}

	public void setCity( boolean isCity )
	{
		this.isCity = isCity;
	}

	public String getContinent()
	{
		return continent;
	}

	public void setContinent( String continent )
	{
		this.continent = continent;
	}

	public boolean isCapital()
	{
		return isCapital;
	}

	public void setCapital( boolean isCapital )
	{
		this.isCapital = isCapital;
	}

	public String getOccupiedBy()
	{
		return occupiedBy;
	}

	public void setOccupiedBy( String occupiedBy )
	{
		this.occupiedBy = occupiedBy;
	}

	public int getNumTroops()
	{
		return numTroops;
	}

	public void setNumTroops( int numTroops )
	{
		this.numTroops = numTroops;
	}

	public ArrayList< Integer > getAdjTerr()
	{
		return adjTerr;
	}

	public void setAdjTerr( ArrayList< Integer > adjTerr )
	{
		this.adjTerr = adjTerr;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		try
		{
			return super.clone();
		}
		catch( Exception e )
		{
			return null;
		}
	}
}
