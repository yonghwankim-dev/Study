package graph.graph09_prim_mst;

import graph.graph09_prim_mst.UndirectedGraph.Node;

public class Driver {

	public static void main(String[] args) {
		UndirectedGraph<String> graph = new UndirectedGraph<String>(9);
		
		Node<String> a = new Node<String>(0, "a");
		Node<String> b = new Node<String>(1, "b");
		Node<String> c = new Node<String>(2, "c");
		Node<String> d = new Node<String>(3, "d");
		Node<String> e = new Node<String>(4, "e");
		Node<String> f = new Node<String>(5, "f");
		Node<String> g = new Node<String>(6, "g");
		Node<String> h = new Node<String>(7, "h");
		Node<String> i = new Node<String>(8, "i");
		
		graph.addEdge(a, b, 4);
		graph.addEdge(a, h, 8);
		graph.addEdge(b, c, 8);
		graph.addEdge(b, h, 11);
		graph.addEdge(c, d, 7);
		graph.addEdge(c, i, 2);
		graph.addEdge(c, f, 4);
		graph.addEdge(d, f, 14);
		graph.addEdge(d, e, 9);
		graph.addEdge(e, f, 10);
		graph.addEdge(f, g, 2);
		graph.addEdge(g, i, 6);
		graph.addEdge(g, h, 1);
		graph.addEdge(h, i, 7);
	
		//graph.printGraph();
		graph.primMST2(0);
	}

}
