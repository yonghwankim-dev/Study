package graph.graph05_iscycle;

import java.util.Stack;

public interface Graph<E> {
	static class Node<E>{
		private int num;	// ��� ��ȣ
		private E value;	// ��� ��
		
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

}
