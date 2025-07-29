package BOJ.BOJ_2587;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = 5;
        int[] numbers = new int[n];
        for(int i = 0; i < n; i++){
            numbers[i] = Integer.parseInt(br.readLine());
        }
        Main.solution(numbers);
    }

    public static void solution(int[] numbers){
        Arrays.sort(numbers);
        int avg = getAvg(numbers);
        int middle = getMiddle(numbers);
        System.out.println(avg);
        System.out.println(middle);
    }

    private static int getAvg(int[] numbers){
        return (int) Arrays.stream(numbers).average().getAsDouble();
    }

    private static int getMiddle(int[] numbers){
        return numbers[numbers.length / 2];
    }

}
