package test_framework;

import java.util.ArrayList;
import java.util.Collections;

/* 
 * This class calculates the probability for the case when three troops attack and two troops defend.
 */
public class ProbabilityThreeVsTwo
{
	public static void main( String[] args )
	{
		int smpleSpce = 6 * 6 * 6 * 6 * 6;
		int winCnt = 0;
		int secMatchOneVsTwoCnt = 0;
		int secMatchTwoVsOneCnt = 0;
		ArrayList< Integer > al = new ArrayList< Integer >();
		for( int i = 1; i < 7; i++ )
		{
			for( int j = 1; j < 7; j++ )
			{
				for( int k = 1; k < 7; k++ )
				{
					for( int l = 1; l < 7; l++ )
					{
						for( int m = 1; m < 7; m++ )
						{
							al.add( i );
							al.add( j );
							al.add( k );
							Collections.sort( al );
							int mm = Math.max( l, m );
							int mn = Math.min( l, m );
							if( al.get( 2 ) > mm && al.get( 1 ) > mn )
							{
								winCnt++;
							}
							else if( al.get( 2 ) <= mm && al.get( 1 ) <= mn )
							{
								secMatchOneVsTwoCnt++;
							}
							else
							{
								secMatchTwoVsOneCnt++;
							}
							al.clear();
						}
					}
				}
			}
		}
		float winCntF = ( float ) winCnt;
		float secMatchOneVsTwoCntF = ( float ) secMatchOneVsTwoCnt;
		float secMatchTwoVsOneCntF = ( float ) secMatchTwoVsOneCnt;
		float smpleSpaceF = ( float ) smpleSpce;
		float w = 0f;
		float l = 0f;
		float s = 0f;
		w = ( float ) ( winCntF / smpleSpaceF );
		l = ( float ) ( secMatchOneVsTwoCntF / smpleSpaceF );
		s = ( float ) ( secMatchTwoVsOneCntF / smpleSpaceF );
		System.out.println( "Win prob= " + winCnt + "/" + smpleSpce + " = " + w );
		System.out.println( "SecondMatchOneVsTwo prob= " + secMatchOneVsTwoCnt + "/" + smpleSpce + " = " + l );
		System.out.println( "SecondMatchTwoVsOne prob= " + secMatchTwoVsOneCnt + "/" + smpleSpce + " = " + s );
	}
}
