package trees;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
 * 
 * - NOTE: this assumes PREORDER traversal of SORTED binary tree structure
 * - NOTE: keeping a List of FIFO QUEUE nodes ACROSS a given level assures BREADTH-FIRST traversal
 * - NOTE: keeping a Set of alreadyVisited nodes prevents CYCLEs in visits in graph
 * - NOTE: remember to return from recursion immediately on null input, or if toVisitNext List is empty
 * 
 */

public class Breadthsearch {

	private Node recursiveSearch(Node currentNode, List<Node> toVisitNext, int targetVal) {
		
		// returning null if traversed to this point without finding targetVal
		if (currentNode == null) {
			return null;
		}
		
		System.out.printf("Current Node val is:  %c\n", currentNode.val);
		
		// return early on finding targetVal 
		if (currentNode.val == targetVal) {
			return currentNode;
		}
		
		// otherwise, add children to END of toVisitNext QUEUE in FIFO order
		// using PREORDER traversal:  ROOT, LEFT, RIGHT
		toVisitNext.add(currentNode.left);
		toVisitNext.add(currentNode.right);
		
		// BREADTH-FIRST:
		// - recurses to NEXT item in queue of FIFO queue to visit;
		// or NEXT node on THIS CURRENT level of tree structure!
		// - returns early if NOTHING left to visit
		Node nextNode = null;
		if (toVisitNext.isEmpty()) {
			return null;
		}
		else {
			nextNode = toVisitNext.remove(0);
		    // alreadyVisitedSet.add(currentNode);
	        return recursiveSearch(nextNode, toVisitNext, targetVal);
		}
	}
	
	// returns null if NOT able to find result!
	public Node search(Node treeRoot, int targetVal) {
	
		// NOTE choosing LINKEDLIST OVER ARRAYLIST,
		// * adding to END, and removing from START is relatively cheap as no linear find traversals
		// * ALSO no resizing of contiguous data needed on variable sizing here!
		List<Node> nodesToVisit = new LinkedList<Node>();
		Set<Node> nodesVisited = new HashSet<Node>();
		
		Node foundNode = recursiveSearch(treeRoot, nodesToVisit, targetVal);
		
		
		return foundNode;
	}
	
	public static void main(String[] args) {
		
		Breadthsearch searcher = new Breadthsearch();
		
		// INITIALIZE INPUT DATA of (UNORDERED) tree STARTing from deepest LEAVES up toward ROOT
		// level 3
		Node leafNode = new Node('F', null, null);
		// level 2
		Node eNode = new Node('E', leafNode, null);
		Node dNode = new Node('D', null, null);
		// level 1
		Node bNode = new Node('B', dNode, eNode);
		Node cNode = new Node('C', null, null);
		// level 0
		Node rootNode = new Node('A', bNode, cNode);
		
		// CORE TEST CASE:  search for node position matching targetValue which DOES exist
		char targetValueExists = 'C';
		// EXPECT node value matching cNode above
		Node foundNode = searcher.search(rootNode, targetValueExists);
	    System.out.printf("If successfully found C Node:  %b\n", (foundNode == cNode));
		
		// BOUNDARY TEST CASE:  search for node position matching targetValue which does NOT exist
		char targetValueNotExists = 'Z';
		// EXPECT node value of null
		foundNode = searcher.search(rootNode, targetValueNotExists);
        System.out.printf("If successfully did NOT find Z node:  %b\n", (foundNode == null));		
	}
		
}
