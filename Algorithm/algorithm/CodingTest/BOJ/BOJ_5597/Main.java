package BOJ.BOJ_5597;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = 28;
        int[] numbers = new int[n];
        for(int i = 0; i < n; i++){
            numbers[i] = Integer.parseInt(br.readLine());
        }
        int[] result = Main.solution(numbers);

        System.out.println(result[0]);
        System.out.println(result[1]);
    }

    public static int[] solution(int[] numbers) {
        boolean[] check = new boolean[30];
        int[] result = new int[2];

        for(int i = 0; i < numbers.length; i++){
            check[numbers[i]-1] = true;
        }

        for(int i = 0, j = 0; i < check.length; i++){
            if(!check[i]){
                result[j++] = i+1;
            }
        }
        Arrays.sort(result);
        return result;
    }
}
