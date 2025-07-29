package graph.graph10_bellmanford_shortestpath;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import graph.graph10_bellmanford_shortestpath.DirectedGraph.Node;

class DirectedGraphTest {

	@AfterEach
	void setup() {
		System.out.println();
	}
	
	@Test
	void test() {
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
	
	@Test
	void test2() {
		int V = 6, E = 9;
		DirectedGraph<String> directedGraph = new DirectedGraph<String>(V,E);
		
		Node<String> s = new Node<String>(0, "s");
		Node<String> t = new Node<String>(1, "t");
		Node<String> A = new Node<String>(2, "A");
		Node<String> B = new Node<String>(3, "B");
		Node<String> C = new Node<String>(4, "C");
		Node<String> D = new Node<String>(5, "D");
		
		directedGraph.addEdge(s, A, 5);
		directedGraph.addEdge(s, C, -2);
		directedGraph.addEdge(A, B, 1);
		directedGraph.addEdge(B, t, 3);
		directedGraph.addEdge(B, D, -1);
		directedGraph.addEdge(C, A, 2);
		directedGraph.addEdge(C, B, 4);
		directedGraph.addEdge(C, D, 4);
		directedGraph.addEdge(D, t, 1);
		
		
		directedGraph.bellmanFord(0);
	}

}
