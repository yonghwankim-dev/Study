package BOJ.BOJ_10815;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static String solution(int[] cards, int[] questions){
        StringBuilder sb = new StringBuilder();
        Set<Integer> cardSet = new HashSet<>();
        Arrays.stream(cards).forEach(cardSet::add);

        for(int question : questions){
            int result = cardSet.contains(question) ? 1 : 0;
            sb.append(result + " ");
        }
        return sb.toString().trim();
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        String[] strCards = br.readLine().split(" ");
        int[] cards = Arrays.stream(strCards).mapToInt(Integer::parseInt).toArray();
        int m = Integer.parseInt(br.readLine());
        String[] strQuestions = br.readLine().split(" ");
        int[] questions = Arrays.stream(strQuestions).mapToInt(Integer::parseInt).toArray();

        String answer = Main.solution(cards, questions);
        System.out.println(answer);
    }

}
