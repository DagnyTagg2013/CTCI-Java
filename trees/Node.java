package trees;

public class Node {
	public char val;
	public Node left;
	public Node right;
	
	public Node(char val, Node left, Node right) {
		this.val = val;
		this.left = left;
		this.right = right;
	}
}