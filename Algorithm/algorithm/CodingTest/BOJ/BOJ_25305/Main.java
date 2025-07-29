package BOJ.BOJ_25305;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static int solution(int n, int person, int[] scores){
        List<Integer> list = Arrays.stream(scores).boxed().sorted(((o1, o2) -> o1.compareTo(o2) * -1)).collect(Collectors.toList());
        return list.get(person-1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] info = br.readLine().split(" ");
        int[] scores = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = Integer.parseInt(info[0]);
        int person = Integer.parseInt(info[1]);

        int answer = Main.solution(n, person, scores);
        System.out.println(answer);
    }
}
