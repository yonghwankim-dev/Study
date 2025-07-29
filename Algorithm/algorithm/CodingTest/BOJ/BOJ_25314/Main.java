package BOJ.BOJ_25314;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int num = Integer.parseInt(br.readLine());
		int mok = num / 4;
		String[] arr = new String[mok + 1];
		Arrays.fill(arr, "long");
		arr[arr.length - 1] = "int";
		System.out.println(String.join(" ", arr));
	}
}
