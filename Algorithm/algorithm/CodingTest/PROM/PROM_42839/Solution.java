package PROM.PROM_42839;

import java.util.*;

public class Solution{
	static int answer = 0;
	static Set<Integer> set = new HashSet<>();
	public boolean isPrime(int n){
		if(n < 2){
			return false;
		}

		for(int i = 2; i*i <= n; i++){
			if(n % i == 0){
				return false;
			}
		}
		return true;
	}

	public void permutation(int n, int r, List<Integer> perArr, boolean[] perCheck, int[] arr){
		if(perArr.size() == r){
			String s = "";
			int target = 0;
			for(int i : perArr){
				s += i;
			}
			target = Integer.parseInt(s);
			if(isPrime(target) && !set.contains(target)){
				set.add(target);
				answer++;
			}
			return;
		}
		for(int i = 0; i < n; i++){
			if(!perCheck[i]){
				perArr.add(arr[i]);
				perCheck[i] = true;
				permutation(n, r, perArr, perCheck, arr);
				perCheck[i] = false;
				perArr.remove(perArr.size()-1);
			}
		}
	}
	
    public int solution(String numbers) {

		int[] arr = Arrays.stream(numbers.split("")).mapToInt(Integer::parseInt).toArray();
		int n = arr.length;
		answer = 0;

		for(int i = 1; i <= n; i++){
			List<Integer> perArr = new ArrayList<>();
			boolean[] perCheck = new boolean[n];
			permutation(n, i, perArr, perCheck, arr);
		}

		return answer;

    }
    
	public static void main(String args[])
	{
		String numbers = "011";
		int answer = new Solution().solution(numbers);
		System.out.println(answer);
	}
}
