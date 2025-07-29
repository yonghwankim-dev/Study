package graph.graph11_dijkstra_shortestpath;

import graph.graph11_dijkstra_shortestpath.DirectedGraph.Node;

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
		
		directedGraph.addEdge(s, t, 10);
		directedGraph.addEdge(s, y, 5);
		directedGraph.addEdge(t, y, 2);
		directedGraph.addEdge(t, x, 1);
		directedGraph.addEdge(x, z, 4);
		directedGraph.addEdge(y, t, 3);
		directedGraph.addEdge(y, z, 2);
		directedGraph.addEdge(y, x, 9);
		directedGraph.addEdge(z, s, 7);
		directedGraph.addEdge(z, x, 6);
		
		directedGraph.dijkstra(0);
		directedGraph.dijkstra2(0);
	}
}
