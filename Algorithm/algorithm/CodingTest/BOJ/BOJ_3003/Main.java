package BOJ.BOJ_3003;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Main {
    public static String solution(int[] items){
        final int[] chess = {1,1,2,2,2,8};
        StringBuilder sb = new StringBuilder();
        String result = IntStream.range(0, items.length)
                                 .map(i -> chess[i] - items[i])
                                 .boxed()
                                 .map(String::valueOf)
                                 .collect(Collectors.joining(" "));

        return result;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] items = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        String answer = Main.solution(items);
        System.out.println(answer);
    }
}
