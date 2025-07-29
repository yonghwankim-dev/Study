package graph.graph08_kruskal_mst;

import java.lang.Character.Subset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class UndirectedGraph implements Graph{
		
	int V,E;	// V : 정점의 개수, E : 간선의 개수
	ArrayList<Edge> edge;	// 간선들의 리스트
	
	public UndirectedGraph(int v, int e) {
		V = v;
		E = e;
		edge = new ArrayList<>();
	}
	
	// 간선 추가
	public void addEdge(int src, int dest, int weight) {
		edge.add(new Edge(src,dest, weight));
	}
	
	// 원소 i가 속해있는 부분집합의 대표 원소(트리의 루트 원소)를 탐색함
	// 경로 압축(Path Compression) 기술 사용함
	// 시간복잡도 : O(log*N)
	public int find(Subset[] subsets, int i)
	{
		if(subsets[i].parent!=i)
		{
			subsets[i].parent = find(subsets, subsets[i].parent);
		}
		return subsets[i].parent;
	}
	
	// x가 속해있는 부분집합과 y가 속해있는 부분집합을 합집합함
	// rank가 더작은 쪽이 rank 큰 트리쪽의 자식으로 들어감
	// x.rank=y.rank인 경우 y 트리의 자식으로 들어가고 y.rank+1함
	public void union(Subset[] subsets, int x, int y)
	{
		int xroot = find(subsets, x);
		int yroot = find(subsets, y);
		
		if(subsets[xroot].rank < subsets[yroot].rank)
		{
			subsets[xroot].parent = yroot;
		}
		else if(subsets[xroot].rank > subsets[yroot].rank)
		{
			subsets[yroot].parent = xroot;
		}
		else
		{
			subsets[xroot].parent = yroot;
			subsets[yroot].rank++;
		}		
	}

	// 무방향 그래프에서 최소 가중치 신장 트리를 탐색함
	// 시간복잡도 : O(|E| log |E|)
	public void kruskalMST()
	{
		Edge[] result = new Edge[V];		// 최소 가중치가 들어있는 간선 배열
		Subset[] subsets = new Subset[V];	// 부분집합 배열
		
		// O(|V|)
		for(int i=0;i<V;i++)
		{
			result[i] = new Edge();
			subsets[i] = new Subset(i,0);
		}
		
		// O(|E|log|E|)
		edge.sort(new Comparator<Edge>() {

			@Override
			public int compare(Edge e1, Edge e2) {
				return e1.weight - e2.weight;
			}
		});
		
		
		int i=0;
		int e=0;
		// O(|E|)
		// union-find 시간복잡도(union by rank and path compression 기술적용) : O(N+Mlog*N) = O(1)
		while(e<V-1)
		{
			Edge next_edge = edge.get(i);
			i++;
			
			int x = find(subsets, next_edge.src);
			int y = find(subsets, next_edge.dest);
			
			if(x!=y)	// x!=y이면 싸이클이 발생하지 않음, x==y이면 싸이클 발생
			{
				result[e] = next_edge;
				union(subsets, x,y);
				e++;
			}
		}
		
		System.out.println("Following are the edges in the constructed MST");
		int minimumCost = 0;
		int edge_n = result.length-1;	// 간선의 개수는 정점의 개수-1개
		for(i=0;i<edge_n;i++)
		{
			System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight);
			minimumCost += result[i].weight;
		}
		System.out.println("Minimum Cost Spanning Tree " + minimumCost);		
	}
}
