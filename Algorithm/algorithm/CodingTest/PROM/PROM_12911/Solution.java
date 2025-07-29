package PROM.PROM_12911;

class Solution{
	public int solution(int n) {
		int answer = 0;
		int cnt_n = Integer.bitCount(n);

		for(int i = n+1;;i++){
			int cnt_i = Integer.bitCount(i);
			if(cnt_n == cnt_i){
				answer = i;
				break;
			}
		}

		return answer;
	}

	public static void main(String[] args){
		int answer = new Solution().solution(78);
		System.out.println(answer);

	}
}
