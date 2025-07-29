package PROM.PROM_17684;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class Solution {
    public static int[] solution(String msg) {
        List<Integer> answer = new ArrayList<>();
        List<String> dictionary = new ArrayList<>(Arrays.asList(""));

        // ���̰� 1�� ��� �ܾ �����ϵ��� ������ �ʱ�ȭ
        IntStream.rangeClosed(1, 26).forEach(i->dictionary.add(getAlphabet(i)));

        while(msg.length() > 0){
            // �������� ���� �Է°� ��ġ�ϴ� ���� �� ���ڿ� w�� Ž��
            String w = findLongestWord(msg, dictionary);
            // w�� �ش��ϴ� ������ ���ι�ȣ ����ϰ�, �Է¿��� w�� ����
            answer.add(dictionary.indexOf(w));
            msg = deleteWord(msg, w);
            // �Է¿��� ó������ ���� ���� ���ڰ� �����ִٸ�(c), w+c�� �ش��ϴ� �ܾ ������ ���
            if(existNextChracter(msg)){
                dictionary.add(w + getFirstChracter(msg));
            }
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }

    private static String getAlphabet(int i){
        return String.valueOf((char)(64 + i));
    }

    private static String findLongestWord(String msg, List<String> dictionary) {
        for(int i = 1; i <= msg.length(); i++){
            if(!dictionary.contains(getSubMsg(msg, i))){
                return getSubMsg(msg, i-1);
            }
        }
        return msg;
    }

    private static String getSubMsg(String text, int idx){
        return text.substring(0, idx);
    }

    private static String deleteWord(String msg, String deleteText){
        return msg.replaceFirst(deleteText, "");
    }

    private static boolean existNextChracter(String text){
        return text.length() > 0;
    }

    private static String getFirstChracter(String text){
        return String.valueOf(text.charAt(0));
    }
}
