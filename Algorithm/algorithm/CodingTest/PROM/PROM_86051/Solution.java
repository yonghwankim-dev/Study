package PROM.PROM_86051;

class Solution {

	public static int sumToN(int n){
		return (n * (n+1)) / 2;
	}

	// sum에서 numbers 배열에 있는 수들을 뺀 다음 반환
	public static int minus(int sum, int[] numbers){
		for(int number : numbers){
			sum -= number;
		}
		return sum;
	}

	public static int solution(int[] numbers)
	{
		int answer = 0;
		int sum = sumToN(9);

		answer = minus(sum, numbers);

		return answer;
	}
}
