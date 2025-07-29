package dp.dp05_lcs;

import org.junit.jupiter.api.Test;

public class Driver {
	public static void main(String[] args)
	{
		String x = "ABCBDAB";
		String y = "BDCABA";
		
		LongestCommonSubsequence lcs = new LongestCommonSubsequence(x, y);
		System.out.println(lcs.lcsLength(x.length(), y.length()));
	}
}
