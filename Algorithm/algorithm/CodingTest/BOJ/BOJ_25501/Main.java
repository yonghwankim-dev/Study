package BOJ.BOJ_25501;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    private static int cnt;

    public static void main(String[] args) throws IOException {
        String answer = Main.solution();
        System.out.println(answer);
    }
    private static String solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());

        for(int i = 0; i < n; i++){
            String word = br.readLine();
            cnt = 0;
            int result = isPalindrome(word);
            sb.append(result + " " + cnt).append("\n");
        }
        return sb.toString();
    }
    private static int isPalindrome(String word){
        return recursion(word, 0, word.length()-1);
    }

    private static int recursion(String word, int left, int right) {
        cnt++;
        if(left >= right){
            return 1;
        }else if(word.charAt(left) != word.charAt(right)){
            return 0;
        }
        return recursion(word, left + 1, right - 1);
    }

}
