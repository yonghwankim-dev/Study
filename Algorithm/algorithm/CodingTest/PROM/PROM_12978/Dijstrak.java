package PROM.PROM_12978;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class Dijstrak {
    static class Node{
        int num; // ����ȣ
        int dst; // ���� �������� �ּҰŸ�

        public Node(int num, int dst) {
            this.num = num;
            this.dst = dst;
        }

    }

    static class Edge{
        int dest; // ������ ������ȣ
        int cost; // ����ġ

        public Edge(int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }

    private static int[] dist;


    public static int[] solution(int n, int e, int[][] edge){
        int[] answer;
        ArrayList<Edge>[] graph = createGraph(n); // 1���� ����

        for(int i = 0; i < e; i++){
            int from = edge[i][0];
            int to   = edge[i][1];
            int cost = edge[i][2];
            graph[from].add(new Edge(to, cost));
            graph[to].add(new Edge(from, cost));
        }
        answer = Dijstrak.dijkstra(1, n, graph);
        return answer;
    }

    private static ArrayList<Edge>[] createGraph(int n){
        ArrayList<Edge>[] graph = new ArrayList[n + 1];
        for(int i = 1; i <= n; i++){
            graph[i] = new ArrayList<>();
        }
        return graph;
    }


    private static int[] dijkstra(int start, int n, ArrayList<Edge>[] graph){
        // �Ÿ��� ������ ����Ʈ �ʱ�ȭ(1���� ����)
        dist = new int[n + 1];
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.dst));

        for(int i = 1; i < dist.length; i++){
            dist[i] = Integer.MAX_VALUE;
        }

        dist[start] = 0; // ���� ����� �Ÿ� ������Ʈ
        pq.add(new Node(start, 0));

        while(!pq.isEmpty()){
            Node curNode = pq.poll();

            // ���� ����� ������� Ž��
            for(Edge adjEdge : graph[curNode.num]){
                boolean isUpdate = updateDistance(curNode, adjEdge);
                if(isUpdate){
                    pq.add(new Node(adjEdge.dest, dist[adjEdge.dest]));
                }
            }
        }
        return dist;
    }

    private static boolean updateDistance(Node curNode, Edge adjEdge){
        int to   = adjEdge.dest;
        int cost = curNode.dst + adjEdge.cost;
        if(cost < dist[to]){
            dist[to] = cost;
            return true;
        }
        return false;
    }




}
