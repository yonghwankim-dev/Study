package BOJ.BOJ_24479;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

class Main {

    static ArrayList<Integer>[] graph;
    static int n;
    static int m;
    static int[] answer;
    static int idx;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        n = Integer.parseInt(line[0]);
        m = Integer.parseInt(line[1]);
        int r = Integer.parseInt(line[2]);
        String[] edges = new String[m];

        for(int i = 0; i < m; i++){
            edges[i] = br.readLine();
        }
        Main.solution(r, edges);
        for(int i = 1; i <= n; i++){
            System.out.println(answer[i]);
        }
    }

    public static void solution(int r, String[] edges){
        boolean[] visited = new boolean[n + 1];
        idx   = 0;
        answer = new int[n + 1];
        graph = new ArrayList[n + 1];

        // ���� �ʱ�ȭ
        for(int i = 1; i <= n; i++){
            graph[i] = new ArrayList<>();
        }

        // ���� ����
        for(String edge : edges){
            String[] info = edge.split(" ");
            int start = Integer.parseInt(info[0]);
            int end   = Integer.parseInt(info[1]);

            graph[start].add(end);
            graph[end].add(start);
        }

        // �������� ����
        for(int i = 1; i <= n; i++){
            Collections.sort(graph[i]);
        }

        visited[r] = true;
        dfs(r, visited);
    }


    private static void dfs(int start, boolean[] visited){
        answer[start] = ++idx;
        for(int adjNode : graph[start]){
            if(!visited[adjNode]){
                visited[adjNode] = true;
                dfs(adjNode, visited);
            }
        }
    }
}
