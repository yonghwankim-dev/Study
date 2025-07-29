package PROM.PROM_70129;

class Solution {
    public static int[] solution(String s) {
        int[] answer = new int[2];

        while(!s.equals("1")){
            int ori_len = s.length();
            s = s.replace("0", "");     // 1. �������� 0 ����
            int len = s.length();                       // 2. 0�� ������ �������� ���� ����
            int del_zero_cnt = ori_len - len;
            // 3. ���̸� �������� ��ȯ
            s = Integer.toBinaryString(len);

            answer[0]++;
            answer[1] += del_zero_cnt;
        }


        return answer;
    }
}
