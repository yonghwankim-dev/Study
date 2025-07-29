package BOJ.BOJ_11659;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] info = br.readLine().split(" ");
        int n = Integer.parseInt(info[0]);
        int m = Integer.parseInt(info[1]);
        int[] arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        String[] query = new String[m];
        for(int i = 0; i < m; i++){
            query[i] = br.readLine();
        }
        String answer = Main.solution(n, m, arr, query);
        System.out.println(answer);
    }

    public static String solution(int n, int m, int[] arr, String[] query){
        StringBuilder sb = new StringBuilder();
        int[] reduce = new int[n + 1];

        for(int i = 1; i <= n; i++){
            reduce[i] = reduce[i-1] + arr[i-1];
        }

        for(String q : query){
            String[] ranges = q.split(" ");
            int start = Integer.parseInt(ranges[0]);
            int end = Integer.parseInt(ranges[1]);
            sb.append(reduce[end] - reduce[start-1]).append("\n");
        }
        return sb.toString().trim();
    }

    private static int sum(int[] arr){
        return Arrays.stream(arr).sum();
    }
}
