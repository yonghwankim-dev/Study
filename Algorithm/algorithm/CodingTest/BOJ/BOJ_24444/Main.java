package BOJ.BOJ_24444;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

class Main {
    private static int[] answer;
    private static int idx;
    private static ArrayList<Integer>[] edges;

    public static void main(String[] args) throws IOException {
        Main.solution();
    }

    public static void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int start = Integer.parseInt(st.nextToken());
        boolean[] visited = new boolean[n + 1];
        answer = new int[n + 1];
        idx = 0;
        edges = createEdges(n);
        inputEdges(m, br);
        sortedEdges();

        bfs(start, visited);
        System.out.println(getResult());
    }

    private static ArrayList<Integer>[] createEdges(int n) {
        ArrayList<Integer>[] result = new ArrayList[n + 1];
        IntStream.rangeClosed(1, n).forEach(i->result[i] = new ArrayList<>());
        return result;
    }

    private static void inputEdges(int m, BufferedReader br) throws IOException {
        while(m-->0){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to   = Integer.parseInt(st.nextToken());
            edges[from].add(to);
            edges[to].add(from);
        }
    }

    private static void sortedEdges(){
        for(int i = 1; i < edges.length; i++){
            Collections.sort(edges[i]);
        }
    }

    private static void bfs(int start, boolean[] visited){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        visited[start] = true;
        answer[start] = ++idx;

        while(!queue.isEmpty()){
            int popped = queue.poll();
            for(int adjNode : edges[popped]){
                if(!visited[adjNode]){
                    visited[adjNode] = true;
                    answer[adjNode] = ++idx;
                    queue.add(adjNode);
                }
            }
        }
    }

    private static String getResult(){
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < answer.length; i++){
            sb.append(answer[i]).append("\n");
        }
        return sb.toString();
    }
}
