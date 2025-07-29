package org.example.ch13_graph.step01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        
        LinkedList<int[]>[] graph = new LinkedList[n + 1];
        for(int i = 1; i <= n; i++){
            graph[i] = new LinkedList();
        }
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());
            graph[src].add(new int[]{dst, 0});
        }
        
        List<Integer> answer = solution(graph, n, k, x);
        Collections.sort(answer);
        if(answer.isEmpty()){
            System.out.println(-1);
        }else{
            for(int target : answer){
                System.out.println(target);
            }            
        }
    }
    
    private static List<Integer> solution(LinkedList<int[]>[] graph, int n, int k, int start){
        List<Integer> result = new ArrayList();
        Queue<int[]> queue = new LinkedList();
        boolean[] visited = new boolean[n + 1];
        
        queue.offer(new int[]{start, 0});
        visited[start] = true;
        
        while(!queue.isEmpty()){
            int[] now = queue.poll();
            if(now[1] == k){
                result.add(now[0]);
            }
            for(int[] adj : graph[now[0]]){
                if(!visited[adj[0]]){
                    int dist = now[1] + 1;
                    queue.offer(new int[]{adj[0], dist});
                    visited[adj[0]] = true;
                }
            }
        }
        return result;
    }
}
