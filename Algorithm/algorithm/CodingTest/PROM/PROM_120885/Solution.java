package PROM.PROM_120885;

class Solution {
    public String solution(String bin1, String bin2) {
        String answer = "";
        int sum = Integer.parseInt(bin1, 2) + Integer.parseInt(bin2, 2);
        answer = Integer.toBinaryString(sum);
        return answer;
    }
}
