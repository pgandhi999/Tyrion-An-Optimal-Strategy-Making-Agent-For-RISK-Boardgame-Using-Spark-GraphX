package test_framework;

/* 
 * This class calculates the probability for the case when three troops attack and one troop defends.
 */
public class ProbabilityThreeVsOne
{
	public static void main( String[] args )
	{
		int smpleSpce = 6 * 6 * 6 * 6;
		int winCnt = 0;
		int secMatchTwoVsOneCnt = 0;
		for( int i = 1; i < 7; i++ )
		{
			for( int j = 1; j < 7; j++ )
			{
				for( int k = 1; k < 7; k++ )
				{
					for( int l = 1; l < 7; l++ )
					{
						int mm = Math.max( Math.max( i, j ), k );
						if( mm > l )
						{
							winCnt++;
						}
						else
						{
							secMatchTwoVsOneCnt++;
						}
					}
				}
			}
		}
		float winCntF = ( float ) winCnt;
		float secMatchTwoVsOneCntF = ( float ) secMatchTwoVsOneCnt;
		float smpleSpaceF = ( float ) smpleSpce;
		float w = 0f;
		float s = 0f;
		w = ( float ) ( winCntF / smpleSpaceF );
		s = ( float ) ( secMatchTwoVsOneCntF / smpleSpaceF );
		System.out.println( "Win prob= " + winCnt + "/" + smpleSpce + " = " + w );
		System.out.println( "SecondMatchTwoVsOne prob= " + secMatchTwoVsOneCnt + "/" + smpleSpce + " = " + s );
	}
}
