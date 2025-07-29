package BOJ.BOJ_11478;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        int answer = Main.solution(s);
        System.out.println(answer);
    }

    public static int solution(String s){
        Set<String> set = new HashSet<>();

        for(int splitUnit = 1; splitUnit <= s.length(); splitUnit++){
            String subStr = splitString(s, splitUnit);
            String[] subStrs = subStr.split(" ");
            set.addAll(List.of(subStrs));
        }

        return set.size();
    }

    private static String splitString(String s, int splitUnit) {
        StringBuilder sb = new StringBuilder();
        String result;
        for(int i = 0; i < s.length(); i++){
            result = i+splitUnit < s.length() ? s.substring(i, i+splitUnit) : s.substring(i);
            sb.append(result + " ");
        }
        return sb.toString().trim();
    }


}
