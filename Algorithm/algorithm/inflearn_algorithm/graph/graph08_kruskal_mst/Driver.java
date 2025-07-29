package graph.graph08_kruskal_mst;

public class Driver {

	public static void main(String[] args) {
		int V = 4;
		int E = 5;
		
		UndirectedGraph graph = new UndirectedGraph(V, E);
		
		graph.addEdge(0, 1, 10);
		graph.addEdge(0, 2, 6);
		graph.addEdge(0, 3, 5);
		graph.addEdge(1, 3, 15);
		graph.addEdge(2, 3, 4);
		
		graph.kruskalMST();

	}

}
