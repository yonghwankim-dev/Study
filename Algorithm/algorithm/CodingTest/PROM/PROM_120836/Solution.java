package PROM.PROM_120836;

class Solution {
    public int solution(int n) {
        int answer = 0;
        for(int a = 1; a <= n; a++){
            if(n % a == 0){
                answer++;
            }
        }
        return answer;
    }
}
