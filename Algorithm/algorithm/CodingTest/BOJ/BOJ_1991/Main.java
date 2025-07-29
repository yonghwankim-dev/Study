package BOJ.BOJ_1991;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class BinaryTree
{
	Node root;
	
	static class Node{
		String data;
		Node left, right;
		
		public Node(String data)
		{
			this.data = data;
			left = right = null;
		}
	}
	
	public BinaryTree()
	{
		root = null;
	}
	
	public void add(String data, String left, String right)
	{
		if(root==null)
		{
			root = new Node(data);
			if(!left.equals("."))
			{
				root.left = new Node(left);
			}
			if(!right.equals("."))
			{
				root.right = new Node(right);
			}
		}
		else
		{
			add(root,data,left,right);
		}
		
	}
	private void add(Node root, String data, String left, String right)
	{
		if(root==null)
		{
			return;
		}
		else if(root.data.equals(data))
		{
			if(!left.equals("."))
			{
				root.left = new Node(left);
			}
			if(!right.equals("."))
			{
				root.right = new Node(right);
			}
		}
		else
		{
			add(root.left, data, left, right);
			add(root.right, data, left, right);
		}
	}
					
	public void inorderTraversal()
	{
		inorderTraversal(this.root);
		System.out.println();
	}
	private void inorderTraversal(Node root)
	{
		if(root==null)
		{
			return;
		}
		inorderTraversal(root.left);
		System.out.print(root.data);
		inorderTraversal(root.right);
	}
	
	public void preorderTraversal()
	{
		preorderTraversal(this.root);
		System.out.println();
	}
	
	private void preorderTraversal(Node root)
	{
		if(root==null)
		{
			return;
		}
		System.out.print(root.data);
		preorderTraversal(root.left);
		preorderTraversal(root.right);
	}
	
	public void postorderTraversal()
	{
		postorderTraversal(this.root);
		System.out.println();
	}
	private void postorderTraversal(Node root)
	{
		if(root==null)
		{
			return;
		}
		
		postorderTraversal(root.left);
		postorderTraversal(root.right);
		System.out.print(root.data);
	}
}


public class Main {
		
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		BinaryTree bt = new BinaryTree();
		
		for(int i=0; i<n; i++)
		{
			String[] str = br.readLine().split(" ");
			bt.add(str[0], str[1], str[2]);	
		}
		bt.preorderTraversal();
		bt.inorderTraversal();
		bt.postorderTraversal();
		
	}
}
