package graph.graph10_bellmanford_shortestpath;

import graph.graph10_bellmanford_shortestpath.DirectedGraph.Node;

public class Driver {

	public static void main(String[] args)
	{
		int V = 5, E = 10;
		DirectedGraph<String> directedGraph = new DirectedGraph<String>(V,E);
		
		Node<String> s = new Node<String>(0, "s");
		Node<String> t = new Node<String>(1, "t");
		Node<String> x = new Node<String>(2, "x");
		Node<String> y = new Node<String>(3, "y");
		Node<String> z = new Node<String>(4, "z");
		
		directedGraph.addEdge(s, t, 6);
		directedGraph.addEdge(s, y, 7);
		directedGraph.addEdge(t, y, 8);
		directedGraph.addEdge(t, z, -4);
		directedGraph.addEdge(t, x, 5);
		directedGraph.addEdge(x, t, -2);
		directedGraph.addEdge(y, x, -3);
		directedGraph.addEdge(y, z, 9);
		directedGraph.addEdge(z, s, 2);
		directedGraph.addEdge(z, x, 7);
		
		directedGraph.bellmanFord(0);
	}
}
