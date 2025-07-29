package graph.graph06_disjointset_unionfind;

import java.util.Stack;

public interface Graph<E> {
	static class Node<E>{
		int num;	// ��� ��ȣ
		E value;	// ��� ��
		
		public Node(int num, E value) {
			this.num = num;
			this.value = value;
		}
	}
	static class Edge{
		int src, dest;
		public Edge(int src, int dest) {
			this.src = src;
			this.dest = dest;
		}

	}
	
	public void addEdge(Node<E> u, Node<E> v);
	public int find(int[] parent, int i);
	public void union(int[] parent, int u, int v);
	public boolean isCycle();
}
