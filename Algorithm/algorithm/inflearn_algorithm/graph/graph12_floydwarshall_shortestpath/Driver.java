package graph.graph12_floydwarshall_shortestpath;

import graph.graph12_floydwarshall_shortestpath.DirectedGraph.Node;

public class Driver {

	public static void main(String[] args)
	{
		int V = 4, E = 4;
		DirectedGraph<String> directedGraph = new DirectedGraph<String>(V,E);
		
		Node<String> s = new Node<String>(0, "s");
		Node<String> t = new Node<String>(1, "t");
		Node<String> x = new Node<String>(2, "x");
		Node<String> y = new Node<String>(3, "y");
		
		directedGraph.addEdge(s, t, 5);
		directedGraph.addEdge(s, y, 10);
		directedGraph.addEdge(t, x, 3);
		directedGraph.addEdge(x, y, 1);
		
//		directedGraph.printGraph();
		directedGraph.floydWarshall();
	}
}
