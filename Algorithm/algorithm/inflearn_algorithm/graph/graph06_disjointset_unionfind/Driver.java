package graph.graph06_disjointset_unionfind;

import graph.graph06_disjointset_unionfind.Graph.Node;

public class Driver {

	public static void main(String[] args) {
		int V = 3;
		UnDirectedGraph<Integer> unDirectedGraph = new UnDirectedGraph<Integer>(V);
		
		Node<Integer> node0 = new Node<Integer>(0, 0);
		Node<Integer> node1 = new Node<Integer>(1, 1);
		Node<Integer> node2 = new Node<Integer>(2, 2);
		
		
		unDirectedGraph.addEdge(node0, node1);
		unDirectedGraph.addEdge(node0, node2);
		unDirectedGraph.addEdge(node1, node2);
		
		if(unDirectedGraph.isCycle())
		{
			System.out.println("이 그래프는 싸이클이 존재합니다.");
		}
		else
		{
			System.out.println("이 그래프는 싸이클이 존재하지 않습니다.");
		}
	}

}
