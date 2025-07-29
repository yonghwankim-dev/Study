package BOJ.BOJ_14425;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Main {
    public static int solution(Set<String> set, List<String> list){
        int count = list.stream().filter(s->set.contains(s)).collect(Collectors.toList()).size();
        return count;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        Set<String> set = new HashSet<>();
        List<String> list = new ArrayList<>();
        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);
        int answer;

        for(int i = 0; i < n; i++){
            set.add(br.readLine());
        }
        for(int i = 0; i < m; i++){
            list.add(br.readLine());
        }

        answer = Main.solution(set, list);
        System.out.println(answer);
    }
}
