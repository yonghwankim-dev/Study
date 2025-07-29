package BOJ.BOJ_10807;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] numbers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int v = Integer.parseInt(br.readLine());

        int answer = Main.solution(n, numbers, v);

        System.out.println(answer);
    }

    public static int solution(int n, int[] numbers, int v) {
        int answer = 0;
        for(int number : numbers){
            if(number == v){
                answer++;
            }
        }
        return answer;
    }
}
