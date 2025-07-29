package BOJ.BOJ_13305;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		long[] distances = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
		long[] cities = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();

		long answer = Main.solution(n, cities, distances);
		System.out.println(answer);
	}

	public static long solution(int n, long[] cities, long[] distances){
		long cost = 0;
		long prevCityPrice = 0;
		for(int cur = 0; cur < n - 1; cur++){
			long curCityPrice = cities[cur];
			long distance     = distances[cur];

			// ù��° �����̰ų� ���� ������ ������ �� �� ���
			if(isFirstCity(cur) || isCurrentCityPriceNotMoreThanPrevCityPrice(curCityPrice, prevCityPrice)) {
				cost += getPrice(curCityPrice, distance);
				prevCityPrice = curCityPrice;
				continue;
			}

			// ���� ������ ������ �� �� ���
			cost += getPrice(prevCityPrice, distance);
		}
		return cost;
	}

	private static boolean isFirstCity(int num){
		return num == 0;
	}

	private static boolean isCurrentCityPriceNotMoreThanPrevCityPrice(long curCityPrice, long prevCityPrice){
		return curCityPrice <= prevCityPrice;
	}

	private static long getPrice(long price, long distance){
		return price * distance;
	}
}
