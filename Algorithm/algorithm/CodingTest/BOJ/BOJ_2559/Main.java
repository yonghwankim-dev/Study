package BOJ.BOJ_2559;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int k = Integer.parseInt(line[1]);
        long[] arr = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        long answer = Main.solution(n, k, arr);
        System.out.println(answer);
    }

    public static long solution(int n, int k, long[] arr){
        long[] reduce = new long[n + 1];
        long sum;
        long max = Long.MIN_VALUE;

        // 1. �־��� �迭�� ������ ����
        for(int i = 1; i <= n; i++){
            reduce[i] = reduce[i-1] + arr[i-1];
        }

        // 2. ������ ���� ���ϰ� �ִ밪�� ����
        for(int start = 1, end = k; end <= n; start++, end++){
            sum = reduce[end] - reduce[start - 1];
            max = Math.max(max, sum);
        }

        return max;
    }
}
