package BOJ.BOJ_1269;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] size = br.readLine().split(" ");
        int a = Integer.parseInt(size[0]);
        int b = Integer.parseInt(size[1]);
        int[] a_set = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] b_set = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int answer = Main.solution(a, b, a_set, b_set);
        System.out.println(answer);
    }

    public static int solution(int a, int b, int[] a_set, int[] b_set){
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        Arrays.stream(a_set).forEach(set1::add);
        Arrays.stream(b_set).forEach(set2::add);
        Set<Integer> set1_copy = new HashSet<>(set1);
        Set<Integer> set2_copy = new HashSet<>(set2);

        set1.removeAll(set2_copy);
        set2.removeAll(set1_copy);
        return set1.size() + set2.size();
    }
}
