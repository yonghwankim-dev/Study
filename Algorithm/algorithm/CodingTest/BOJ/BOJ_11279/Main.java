
package BOJ.BOJ_11279;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
	public static StringBuilder sb;
	public static void solution(int n, int from, int to, int by)
	{
		if(n==1)
		{
			sb.append(from + " " + to + "\n");
			return;
		}
		solution(n-1, from, by, to);
		sb.append(from + " " + to + "\n");
		solution(n-1, by, to, from);
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		System.out.printf((int) Math.pow(2, n)-1+"\n");
		sb = new StringBuilder();
		solution(n,1,3,2);
		System.out.println(sb.toString());
	}
}
