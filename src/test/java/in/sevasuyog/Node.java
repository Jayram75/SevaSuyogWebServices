package in.sevasuyog;

import java.util.LinkedList;
import java.util.Queue;

public class Node {
	public Node left;
	public Node right;
	public int data;
	
	public Node(int data) {
		this.data = data;
	}
	
	public static void main(String[] args) throws Throwable {
		System.out.println(Solution.inOrderTraversal(createTree("10 5 11 2 7 n n n n 6 8")));
	}
	
	public static Node createTree(String str) throws Throwable {
		String possibleNodes[] = str.split("\\s+");
		
		Node root = new Node(Integer.valueOf(possibleNodes[0]));
		Node currentNode = null;
		Queue<Node> q = new LinkedList<Node>();
		q.add(root);
		
		int index = 1;
		while(!q.isEmpty()) {
			currentNode = q.poll();
			if(index == possibleNodes.length) {
				break;
			}
			String temp = possibleNodes[index];
			if(!temp.equalsIgnoreCase("N")) {
				Node left = new Node(Integer.valueOf(temp));
				currentNode.left = left;
				q.add(left);
			}
			index++;
			
			if(index == possibleNodes.length) {
				break;
			}
			temp = possibleNodes[index];
			if(!temp.equalsIgnoreCase("N")) {
				Node right = new Node(Integer.valueOf(temp));
				currentNode.right = right;
				q.add(right);
			}
			index++;
		}
		
		return root;
	}
	
	
}