package BOJ.BOJ_1920;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        long[] arr = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        int m = Integer.parseInt(br.readLine());
        long[] problems = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        int[] answer = Main.solution(n, arr, m, problems);
        Arrays.stream(answer).forEach(System.out::println);
    }

    private static int[] solution(int n, long[] arr, int m, long[] problems) {
        List<Integer> result = new ArrayList<>();
        Set<Long> set = new HashSet<>();
        Arrays.stream(arr).forEach(set::add);

        Arrays.stream(problems).forEach(i->{
            if(set.contains(i)){
                result.add(1);
            }else{
                result.add(0);
            }
        });
        return result.stream().mapToInt(Integer::intValue).toArray();
    }


}
