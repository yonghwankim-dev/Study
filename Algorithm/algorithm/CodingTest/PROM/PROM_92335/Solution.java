package PROM.PROM_92335;

class Solution {
	public static int solution(int n, int k) {
		int answer = 0;
		int start = 0;
		String k_num = Integer.toString(n, k);

		for(int i = 0; i < k_num.length(); i++){
			if(isZero(k_num, i) ||
			   isLastIndex(k_num, i)){
				String sub = k_num.substring(start, i+1);

				if(isPrime(sub)){
					answer++;
				}

				start = i;
			}
		}
		return answer;
	}

	private static boolean isZero(String text, int i){
		return text.charAt(i) == '0';
	}

	private static boolean isLastIndex(String text, int i){
		return text.length() - 1 == i;
	}

	public static boolean isPrime(String k_num){
		String s = k_num.replace("0", "");
		s = isEmpty(s) ? "0" : s;
		long decimal = Long.parseLong(s);

		if(decimal <= 1){
			return false;
		}

		for(long i = 2; i*i <= decimal; i++){
			if(decimal % i == 0){
				return false;
			}
		}

		return true;
	}

	private static String getRemovedZeroString(String text){
		return text.replace("0","");
	}

	private static boolean isEmpty(String text){
		return text == null || text.equals("");
	}
}
