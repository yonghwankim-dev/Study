package PROM.PROM_12949;

class Solution {
	public int[][] solution(int[][] arr1, int[][] arr2) {
		int row = arr1.length;
		int col = arr1[0].length;
		int col2 = arr2[0].length;
		int[][] answer = new int[row][col2];

		for(int i = 0; i < row; i++){
			for(int j = 0; j < col2; j++){
				for(int k = 0; k < col; k++){
					answer[i][j] += arr1[i][k] * arr2[k][j];
				}
			}
		}
		return answer;
	}

	public static void main(String[] args){
		int[][] arr1 = {{1,4}, {3,2}, {4,1}};
		int[][] arr2 = {{3,3,3},{3,3,3}};

		new Solution().solution(arr1, arr2);
	}
}
