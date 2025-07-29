package BOJ.BOJ_2805;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {
    private static int n;
    private static int[] heights;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] split = br.readLine().split(" ");
        int n = Integer.parseInt(split[0]);
        long m = Long.parseLong(split[1]);
        int[] heights = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        long answer = Main.solution(n, m, heights);
        System.out.println(answer);
    }

    public static long solution(int n, long m, int[] heights){
        Main.n = n;
        Main.heights = heights;
        long start   = 0;
        long end     = getMaxArray(heights);
        long minWood = getSumArray(heights);
        long h = 0;
        long sum;
        while(start <= end){
            h = findMiddleVal(start, end);
            sum = getWoods(h);

            if(sum == m){
                break;
            }else if(sum > m){
                start = h;
            }else if(sum < m){
                end = h;
            }
            minWood = getMin(minWood, sum);
        }
        return h;
    }

    private static long getMin(long a, long b) {
        return a < b ? a : b;
    }

    private static long getWoods(long h) {
        long sum = 0;
        for(int i = 0; i < n; i++){
            if(heights[i] > h) {
                sum += heights[i] - h;
            }
        }
        return sum;
    }

    private static int getMaxArray(int[] arr) {
        return Arrays.stream(arr).max().getAsInt();
    }

    private static long getSumArray(int[] arr){
        return Arrays.stream(arr).sum();
    }
    private static long findMiddleVal(long start, long end){
        return (start + end) / 2;
    }
}
