package BOJ.BOJ_9935;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        String boom = br.readLine();
        String answer = Main.solution(s, boom);
        System.out.println(answer);
    }
    public static String solution(String s, String boom){
        StringBuilder text = new StringBuilder(s);
        StringBuilder pattern = new StringBuilder(boom);

        // pattern �ε����� ���ư� �� ����
        Stack<Integer> index_stack = new Stack<>();

        // ��� ���ڿ� ����
        Stack<Character> char_stack = new Stack<>();

        int[] p = new int[pattern.length()];

        return null;
    }
}
