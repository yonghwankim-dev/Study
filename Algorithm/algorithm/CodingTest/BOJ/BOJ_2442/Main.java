package BOJ.BOJ_2442;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Main.solution(n);
    }

    public static void solution(int n){
        for(int i = 1; i <= n; i++){
            int starCnt = 2*i-1;
            int blankCnt = n - i;
            String line = getBlank(blankCnt) + getStar(starCnt) + " ";
            System.out.println(line);
        }
    }

    private static String getBlank(int n){
        StringBuilder sb = new StringBuilder();
        IntStream.rangeClosed(1, n).forEach(i->sb.append(" "));
        return sb.toString();
    }

    private static String getStar(int n){
        StringBuilder sb = new StringBuilder();
        IntStream.rangeClosed(1, n).forEach(i->sb.append("*"));
        return sb.toString();
    }
}
