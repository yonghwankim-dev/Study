package BOJ.BOJ_24480;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

class Main {
    private static int[] answer;
    private static ArrayList<Integer>[] edges;
    private static int idx = 0;
    private static boolean[] visited;
    public static void main(String[] args) throws IOException {
        Main.solution();
    }

    public static void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        edges = createEdges(n);
        answer = new int[n+1];
        visited = new boolean[n+1];

        inputEdge(m, br);
        reverseSortedEdges(n);

        visited[r] = true;
        dfs(r);
        System.out.println(getResult(n));
    }

    private static ArrayList<Integer>[] createEdges(int n){
        ArrayList<Integer>[] edges = new ArrayList[n + 1];
        IntStream.rangeClosed(1, n).forEach(i->edges[i] = new ArrayList<>());
        return edges;
    }

    private static void inputEdge(int m, BufferedReader br) throws IOException {
        StringTokenizer st;
        while(m-->0){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to   = Integer.parseInt(st.nextToken());
            edges[from].add(to);
            edges[to].add(from);
        }
    }

    private static void reverseSortedEdges(int n){
        IntStream.rangeClosed(1, n).forEach(i->Collections.sort(edges[i], Collections.reverseOrder()));
    }

    private static void dfs(int cur){
        answer[cur] = ++idx;
        for(int next : edges[cur]){
            if(!visited[next]){
                visited[next] = true;
                dfs(next);
            }
        }
    }

    private static String getResult(int n){
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= n; i++){
            sb.append(answer[i]).append("\n");
        }
        return sb.toString();
    }
}
