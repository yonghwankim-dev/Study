package BOJ.BOJ_11866;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Main {
    static class Number{
        int num;
        boolean status; // true : ���ŵ�, false : ���ŵ��� ����

        public Number(int num) {
            this.num = num;
            this.status = false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int k = Integer.parseInt(line[1]);
        String answer = Main.solution(n, k);
        System.out.println(answer);
    }

    private static String solution(int n, int k) {
        List<Integer> result = new ArrayList<>();
        Queue<Number> queue = new LinkedList<>();
        IntStream.rangeClosed(1, n).forEach(i->queue.add(new Number(i)));
        int delCount = 0;
        int kCnt = 0;
        while(delCount < n){
            Number poped = queue.poll();

            if(poped.status == false) {
                kCnt++;
            }

            if(kCnt == k){
                kCnt = 0;
                poped.status = true;
                delCount++;
                result.add(poped.num);
            }

            queue.add(poped);
        }
        return result.stream().map(String::valueOf).collect(Collectors.joining(", ","<",">"));
    }
}
