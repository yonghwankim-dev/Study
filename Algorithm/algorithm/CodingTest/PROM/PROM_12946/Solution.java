
package PROM.PROM_12946;

class Solution {
	int i = 0;
	public void hanoi(int n, int from, int to, int aux, int[][] answer){
		if(n == 1){
			answer[i][0] = from;
			answer[i][1] = to;
			i++;
			return;
		}
		hanoi(n-1, from, aux, to, answer);

		answer[i][0] = from;
		answer[i][1] = to;
		i++;

		hanoi(n-1, aux, to, from, answer);
	}

	public int[][] solution(int n) {
		int size = (int)(Math.pow(2,n)-1);
		int[][] answer = new int[size][2];

		hanoi(n, 1, 3, 2, answer);

		return answer;
	}

	public static void main(String[] args){
		int[][] answer = new Solution().solution(2);
	}
}
