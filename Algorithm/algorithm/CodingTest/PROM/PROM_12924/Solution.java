package PROM.PROM_12924;

public class Solution {
    public int solution(int n) {

        int answer = 0;

        for(int i = 1; i <= n; i++) {
            int sum = 0;
            int num = i;
            while (true) {
                sum += num;
                if (sum == n) {
                    answer++;
                } else if (sum < n) {
                    num++;
                    continue;
                } else {
                    break;
                }
            }
        }

        return answer;
    }

    public static void main(String[] args){
        System.out.println(new Solution().solution(10000));
    }
}
