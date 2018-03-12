package test_framework;

/* 
 * This class calculates the probability for the case when one troop attacks and two troops defend.
 */
public class ProbabilityOneVsTwo
{
	public static void main( String[] args )
	{
		int smpleSpce = 6 * 6 * 6;
		int loseCnt = 0;
		int secMatchOneVsOneCnt = 0;
		for( int i = 1; i < 7; i++ )
		{
			for( int j = 1; j < 7; j++ )
			{
				for( int k = 1; k < 7; k++ )
				{
					int mm = Math.max( j, k );
					if( mm > i )
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
		float loseCntF = ( float ) loseCnt;
		float secMatchOneVsOneCntF = ( float ) secMatchOneVsOneCnt;
		float smpleSpaceF = ( float ) smpleSpce;
		float l = 0f;
		float s = 0f;
		l = ( float ) ( loseCntF / smpleSpaceF );
		s = ( float ) ( secMatchOneVsOneCntF / smpleSpaceF );
		System.out.println( "Lose prob= " + loseCnt + "/" + smpleSpce + " = " + l );
		System.out.println( "SecondMatchOneVsOne prob= " + secMatchOneVsOneCnt + "/" + smpleSpce + " = " + s );
	}
}
