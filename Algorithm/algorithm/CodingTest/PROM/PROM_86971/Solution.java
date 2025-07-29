package PROM.PROM_86971;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

class Solution {
    private static LinkedList<Integer>[] graph;
    public static int solution(int n, int[][] wires) {
        int answer = n;
        graph = new LinkedList[n+1];
        for(int i = 1; i <= n; i++){
            graph[i] = new LinkedList<>();
        }

        for(int[] wire : wires){
            addGraph(wire);
        }

        for(int[] wire : wires){
            sliceWire(wire);
            int diff = bfsAll(n);
            answer = Math.min(answer, diff);
            addGraph(wire);
        }

        return answer;
    }

    private static void addGraph(int[] wire){
        int v1 = wire[0];
        int v2 = wire[1];

        graph[v1].add(v2);
        graph[v2].add(v1);
    }

    private static void sliceWire(int[] wire){
        Integer v1 = wire[0];
        Integer v2 = wire[1];

        graph[v1].remove(v2);
        graph[v2].remove(v1);
    }

    private static int bfsAll(int n){
        boolean[] visited = new boolean[n+1];
        int[] countArr = new int[2];
        int idx = 0;
        String result;
        for(int i = 1; i <= n; i++){
            if(!visited[i]){
                result = bfs(i, visited);
                countArr[idx++] = result.split(" ").length;
            }
        }
        return diffCountVertex(countArr);
    }



    private static String bfs(int start, boolean[] visited){
        StringBuilder sb = new StringBuilder();
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        while(!queue.isEmpty()){
            start = queue.poll();
            sb.append(start + " ");
            ListIterator<Integer> itr = graph[start].listIterator();

            while(itr.hasNext()){
                int adjVertx = itr.next();
                if(!visited[adjVertx]){
                    visited[adjVertx] = true;
                    queue.add(adjVertx);
                }
            }
        }
        return sb.toString().trim();
    }

    private static int diffCountVertex(int[] countArr) {
        return Math.abs(countArr[0] - countArr[1]);
    }

}
