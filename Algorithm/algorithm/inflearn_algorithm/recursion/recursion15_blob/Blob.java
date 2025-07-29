/**
 * Counting Cells in a Blob
 */
package recursion.recursion15_blob;

public class Blob {
	private static int N=8;
	private static int[][] image = {
			{1,0,0,0,0,0,0,1},
			{0,1,1,0,0,1,0,0},
			{1,1,0,0,1,0,1,0},
			{0,0,0,0,0,1,0,0},
			{0,1,0,1,0,1,0,0},
			{0,1,0,1,0,1,0,0},
			{1,0,0,0,1,0,0,1},
			{0,1,1,0,0,1,1,1},
			
	};
	private static final int BACKGROUND_PIXCEL = 0; // πË∞Ê «»ºø
	private static final int IMAGE_PIXCEL = 1;	 	// ¿ÃπÃ¡ˆ «»ºø
	private static final int VISITED_PIXCEL = 2;	// πÊπÆ«— «»ºø
	
	public static int countingCell(int y, int x)
	{
		if(y<0 || x<0 || y>=N || x>=N)
		{
			return 0;
		}
		else if(image[y][x]!=IMAGE_PIXCEL || image[y][x]==VISITED_PIXCEL)
		{
			return 0;
		}
		else 
		{
			image[y][x] = VISITED_PIXCEL;
			return 1 +
			countingCell(y-1, x) +
			countingCell(y-1, x+1) +
			countingCell(y, x+1) +
			countingCell(y+1, x+1) +
			countingCell(y+1, x) +
			countingCell(y+1, x-1) +
			countingCell(y, x-1) +
			countingCell(y-1, x-1);
			
		}
	}

	public static void main(String[] args) {
		System.out.println(countingCell(3,5));
	}
}
