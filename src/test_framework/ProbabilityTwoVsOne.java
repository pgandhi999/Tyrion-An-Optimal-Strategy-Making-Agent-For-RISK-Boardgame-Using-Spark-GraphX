package test_framework;

/* 
 * This class calculates the probability for the case when two troops attack and one troop defends.
 */
public class ProbabilityTwoVsOne
{
	public static void main( String[] args )
	{
		int smpleSpce = 6 * 6 * 6;
		int winCnt = 0;
		int secMatchOneVsOneCnt = 0;
		for( int i = 1; i < 7; i++ )
		{
			for( int j = 1; j < 7; j++ )
			{
				for( int k = 1; k < 7; k++ )
				{
					int mm = Math.max( i, j );
					if( mm > k )
					{
						winCnt++;
					}
					else
					{
						secMatchOneVsOneCnt++;
					}
				}
			}
		}
		float winCntF = ( float ) winCnt;
		float secMatchOneVsOneCntF = ( float ) secMatchOneVsOneCnt;
		float smpleSpaceF = ( float ) smpleSpce;
		float w = 0f;
		float s = 0f;
		w = ( float ) ( winCntF / smpleSpaceF );
		s = ( float ) ( secMatchOneVsOneCntF / smpleSpaceF );
		System.out.println( "Win prob= " + winCnt + "/" + smpleSpce + " = " + w );
		System.out.println( "SecondMatchOneVsOne prob= " + secMatchOneVsOneCnt + "/" + smpleSpce + " = " + s );
	}
}
