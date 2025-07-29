package BOJ.BOJ_10757;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;




public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str[] = br.readLine().split(" ");
		BigInteger A = new BigInteger(str[0]);
		BigInteger B = new BigInteger(str[1]);
		BigInteger answer = A.add(B);
		System.out.println(answer);
		
	}
}
