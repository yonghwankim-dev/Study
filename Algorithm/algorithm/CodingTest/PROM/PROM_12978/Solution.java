package PROM.PROM_12978;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

class Solution {
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

    private static int[] dist; // ���������� �ּ� �Ÿ� �迭
    private static ArrayList<Edge>[] graph;
    private static int n;   // ������ ����
    public static int solution(int N, int[][] road, int K) {
        int answer = 0;
        n = N;
        graph = createGraph(N);            // 1���� ����
        initializeGraph(road);             // �׷����� �������� �ֱ�
        calculateShortestDistance(1); // 1�� ��忡������ �����Ͽ� �ٸ� �������� �ּҰŸ��� ����Ͽ� dist �迭�� ����
        answer = getDeliveryCount(K); // 1�� ��忡������ ��� ������ ��� ���� ��ȯ
        return answer;
    }



    private static ArrayList<Edge>[] createGraph(int n){
        ArrayList<Edge>[] graph = new ArrayList[n + 1];
        IntStream.rangeClosed(1, n).forEach(i->graph[i] = new ArrayList<>());
        return graph;
    }

    private static void initializeGraph(int[][] road) {
        for(int i = 0; i < road.length; i++){
            int from = road[i][0];
            int to   = road[i][1];
            int cost = road[i][2];
            graph[from].add(new Edge(to, cost));
            graph[to].add(new Edge(from, cost));
        }
    }

    private static void calculateShortestDistance(int start){
        dist = initializeDistance(); // �Ÿ��� ������ ����Ʈ �ʱ�ȭ(1���� ����)
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.dst)); // �������� �ּҰŸ��� ��������

        dist[start] = 0; // ���� ����� �Ÿ� ������Ʈ
        pq.add(new Node(start, 0));

        while(!pq.isEmpty()){
            Node curNode = pq.poll();

            // ���� ����� �������� Ž��
            for(Edge adjEdge : graph[curNode.num]){
                boolean isUpdate = updateDistance(curNode, adjEdge); // ������� ���������� ���� ���� ������ �ּ� �Ÿ� ������Ʈ
                if(isUpdate){
                    pq.add(new Node(adjEdge.dest, dist[adjEdge.dest]));
                }
            }
        }
    }

    private static int[] initializeDistance(){
        int[] result = new int[n + 1];
        IntStream.rangeClosed(1, n).forEach(i->result[i]=Integer.MAX_VALUE);
        return result;
    }

    private static boolean updateDistance(Node curNode, Edge adjEdge) {
        int to   = adjEdge.dest;
        int cost = curNode.dst + adjEdge.cost;
        if(cost < dist[to]){
            dist[to] = cost;
            return true;
        }
        return false;
    }

    private static int getDeliveryCount(int k) {
        int count = 0;
        for(int i = 1; i <= n; i++){
            if(dist[i] <= k){
                count++;
            }
        }
        return count;
    }
}
