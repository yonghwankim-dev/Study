package BOJ.BOJ_24416;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    private static int[] f;
    private static int[] result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String answer = Main.solution(n);
        System.out.println(answer);
    }

    public static String solution(int n) {
        f = new int[n + 1];
        result = new int[2];
        fib(n);
        fibonacci(n);
        return result[0] + " " + result[1];
    }

    private static int fib(int n){
        if(n == 1 || n == 2){
            result[0]++;
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    private static int fibonacci(int n) {
        f[1] = f[2] = 1;
        for(int i = 3; i <= n; i++){
            f[i] = f[i - 1] + f[i - 2];
            result[1]++;
        }
        return f[n];
    }
}
