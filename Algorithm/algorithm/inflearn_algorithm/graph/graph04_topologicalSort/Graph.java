package graph.graph04_topologicalSort;

import java.util.Stack;

public interface Graph<E> {
	static class Node<E>{
		private int num;	// 노드 번호
		private E value;	// 노드 값
		
		public Node(int num, E value) {
			this.num = num;
			this.value = value;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public E getValue() {
			return value;
		}

		public void setValue(E value) {
			this.value = value;
		}
	}
	
	public void addEdge(Node<E> u, Node<E> v);
	public void printGraph();
	public void bfsAll();
	public void bfs(int s, boolean[] visited);
	public void dfsAll();
	public void dfs(int s, boolean[] visited);
	public void topologicalSort();
	public void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack);
}
