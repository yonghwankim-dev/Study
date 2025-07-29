package kr.yh.chap06_dp.lis;

class LongestIncreasingSubsequence {
    // array 정수 배열에서 최장 증가 부분 수열이 되는 인덱스 k를 구하시오
    // O(n^2)
    public int solution(int[] array){
        int n = array.length;
        int[] length = new int[n];
        for(int k = 0; k < n; k++){
            length[k] = 1;
            for(int i = 0; i < k; i++){
                if(array[i] < array[k]){
                    length[k] = Math.max(length[k], length[i] + 1);
                }
            }
        }

        return getIdxByMaxValue(length);
    }

    private int getIdxByMaxValue(int[] arr){
        int maxIdx = 0;
        int maxValue = arr[0];
        for(int i = 1; i < arr.length; i++){
            if(arr[i] > maxValue){
                maxIdx = i;
                maxValue = arr[i];
            }
        }
        return maxIdx;
    }
}
