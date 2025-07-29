package BOJ.BOJ_2475;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int answer = Main.solution(nums);
        System.out.println(answer);
    }

    public static int solution(int[] nums){
        int sum = Arrays.stream(nums).map(i->i*i).sum();
        int validationNum = sum % 10;
        return validationNum;
    }
}
