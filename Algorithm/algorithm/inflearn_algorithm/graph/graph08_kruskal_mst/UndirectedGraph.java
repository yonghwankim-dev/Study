package graph.graph08_kruskal_mst;

import java.lang.Character.Subset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class UndirectedGraph implements Graph{
		
	int V,E;	// V : ������ ����, E : ������ ����
	ArrayList<Edge> edge;	// �������� ����Ʈ
	
	public UndirectedGraph(int v, int e) {
		V = v;
		E = e;
		edge = new ArrayList<>();
	}
	
	// ���� �߰�
	public void addEdge(int src, int dest, int weight) {
		edge.add(new Edge(src,dest, weight));
	}
	
	// ���� i�� �����ִ� �κ������� ��ǥ ����(Ʈ���� ��Ʈ ����)�� Ž����
	// ��� ����(Path Compression) ��� �����
	// �ð����⵵ : O(log*N)
	public int find(Subset[] subsets, int i)
	{
		if(subsets[i].parent!=i)
		{
			subsets[i].parent = find(subsets, subsets[i].parent);
		}
		return subsets[i].parent;
	}
	
	// x�� �����ִ� �κ����հ� y�� �����ִ� �κ������� ��������
	// rank�� ������ ���� rank ū Ʈ������ �ڽ����� ��
	// x.rank=y.rank�� ��� y Ʈ���� �ڽ����� ���� y.rank+1��
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

	// ������ �׷������� �ּ� ����ġ ���� Ʈ���� Ž����
	// �ð����⵵ : O(|E| log |E|)
	public void kruskalMST()
	{
		Edge[] result = new Edge[V];		// �ּ� ����ġ�� ����ִ� ���� �迭
		Subset[] subsets = new Subset[V];	// �κ����� �迭
		
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
		// union-find �ð����⵵(union by rank and path compression �������) : O(N+Mlog*N) = O(1)
		while(e<V-1)
		{
			Edge next_edge = edge.get(i);
			i++;
			
			int x = find(subsets, next_edge.src);
			int y = find(subsets, next_edge.dest);
			
			if(x!=y)	// x!=y�̸� ����Ŭ�� �߻����� ����, x==y�̸� ����Ŭ �߻�
			{
				result[e] = next_edge;
				union(subsets, x,y);
				e++;
			}
		}
		
		System.out.println("Following are the edges in the constructed MST");
		int minimumCost = 0;
		int edge_n = result.length-1;	// ������ ������ ������ ����-1��
		for(i=0;i<edge_n;i++)
		{
			System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight);
			minimumCost += result[i].weight;
		}
		System.out.println("Minimum Cost Spanning Tree " + minimumCost);		
	}
}
