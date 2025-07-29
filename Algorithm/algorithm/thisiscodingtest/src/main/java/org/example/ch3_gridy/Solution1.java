package org.example.ch3_gridy;

import java.util.Arrays;

// 큰수의 법칙
public class Solution1 {


	public int solution(int[] arr, int m, int k){
		// 1. 배열을 오름차순으로 정렬
		// 2. 배열의 n-i번째 인덱스 위치부터 인덱스 위치의 숫자를 M번 횟수에 도달할때까지 숫자를 더한다.
		// 단, 해당 n-i번째 숫자가 k와 같을때까지 더한다.
		// 3. 더한 횟수가 m번이 되면 합계 sum을 반환한다
		Arrays.sort(arr);

		int n = arr.length;
		int count = 0;
		int sum = 0;
		while (count < m){
			int index = n - 1;
			for (int i = 0; i < k; i++){
				sum += arr[index];
				count++;
			}

			if (count >= m){
				break;
			}

			index = n - 2;
			sum += arr[index];
			count++;
		}
		return sum;
	}

	public int solution2(int[] arr, int m, int k){
		Arrays.sort(arr);
		int n = arr.length;
		int first = arr[n - 1];
		int second = arr[n - 2];
		// k + 1 : 수열이 반복되느 길이, ex) 6 6 6 5
		int count = (m / (k + 1)) * k;
		count += m % (k + 1);

		return first * count + second * (m - count);
	}
}
