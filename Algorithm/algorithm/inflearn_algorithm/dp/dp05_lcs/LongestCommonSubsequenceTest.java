package dp.dp05_lcs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LongestCommonSubsequenceTest {

	@Test
	void test() {
		String x = "ABCBDAB";
		String y = "BDCABA";
		
		LongestCommonSubsequence lcs = new LongestCommonSubsequence(x, y);
		for(int i=0; i<=x.length(); i++)
		{
			System.out.printf("m=%d, n=%d, lcsLength=%d, lcsString=%s\n",
							  i, y.length(), lcs.lcsLength(i, y.length()),
							  lcs.lcsString(i, y.length()));
		}
	}
}
