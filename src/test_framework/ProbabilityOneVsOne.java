package test_framework;

/* 
 * This class calculates the probability for the case when one troop attacks and one troop defends.
 */
public class ProbabilityOneVsOne
{
	public static void main( String[] args )
	{
		int smpleSpce = 6 * 6;
		int winCnt = 0;
		int loseCnt = 0;
		for( int i = 1; i < 7; i++ )
		{
			for( int j = 1; j < 7; j++ )
			{
				if( i > j )
				{
					winCnt++;
				}
				else
				{
					loseCnt++;
				}
			}
		}
		float winCntF = ( float ) winCnt;
		float loseCntF = ( float ) loseCnt;
		float smpleSpaceF = ( float ) smpleSpce;
		float w = 0f;
		float l = 0f;
		w = ( float ) ( winCntF / smpleSpaceF );
		l = ( float ) ( loseCntF / smpleSpaceF );
		System.out.println( "Win prob= " + winCnt + "/" + smpleSpce + " = " + w );
		System.out.println( "Lose prob= " + loseCnt + "/" + smpleSpce + " = " + l );
	}
}
