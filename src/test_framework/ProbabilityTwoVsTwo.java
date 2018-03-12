package test_framework;

/* 
 * This class calculates the probability for the case when two troops attack and two troops defend.
 */
public class ProbabilityTwoVsTwo
{
	public static void main( String[] args )
	{
		int smpleSpce = 6 * 6 * 6 * 6;
		int winCnt = 0;
		int secMatchOneVsOneCnt = 0;
		int loseCnt = 0;
		for( int i = 1; i < 7; i++ )
		{
			for( int j = 1; j < 7; j++ )
			{
				for( int k = 1; k < 7; k++ )
				{
					for( int l = 1; l < 7; l++ )
					{
						int mma = Math.max( i, j );
						int mna = Math.min( i, j );
						int mmd = Math.max( k, l );
						int mnd = Math.min( k, l );
						if( mma > mmd && mna > mnd )
						{
							winCnt++;
						}
						else if( mma <= mmd && mna <= mnd )
						{
							loseCnt++;
						}
						else
						{
							secMatchOneVsOneCnt++;
						}
					}
				}
			}
		}
		float winCntF = ( float ) winCnt;
		float secMatchOneVsOneCntF = ( float ) secMatchOneVsOneCnt;
		float loseCntF = ( float ) loseCnt;
		float smpleSpaceF = ( float ) smpleSpce;
		float w = 0f;
		float l = 0f;
		float s = 0f;
		w = ( float ) ( winCntF / smpleSpaceF );
		l = ( float ) ( loseCntF / smpleSpaceF );
		s = ( float ) ( secMatchOneVsOneCntF / smpleSpaceF );
		System.out.println( "Win prob= " + winCnt + "/" + smpleSpce + " = " + w );
		System.out.println( "Lose prob= " + loseCnt + "/" + smpleSpce + " = " + l );
		System.out.println( "SecondMatchOneVsOne prob= " + secMatchOneVsOneCnt + "/" + smpleSpce + " = " + s );
	}
}
