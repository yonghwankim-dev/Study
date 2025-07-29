package graph.graph08_kruskal_mst;

import java.util.Stack;

public interface Graph<E> {
	static class Edge implements Comparable<Edge>
	{
		int src, dest, weight;

		public Edge() {
		}

		public Edge(int src, int dest, int weight) {
			this.src = src;
			this.dest = dest;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Edge compareEdge) 
		{
			return this.weight - compareEdge.weight;
		}	
	}
	
	static class Subset
	{		int parent, rank;

		public Subset(int parent, int rank) {
			this.parent = parent;
			this.rank = rank;
		}
		
	}
	
}
