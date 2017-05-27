package recursion;

/*

TODO:  go see PYTHON version!

- SEARCH is O(logN)

- PREFER: ArrayQueue implementation of Deque for FIFO/LIFO operations!
http://stackoverflow.com/questions/6129805/what-is-the-fastest-java-collection-with-the-basic-functionality-of-a-queue

- ATTN: EXIT or RETURN from RECURSION on null!
- ATTN: ITERATE to NEXT, or INCREMENT recurse-level IMMEDIATELY PRIOR to recursion call!

 */

import java.util.ArrayDeque;
import java.util.Deque;

public class BSTRec {

    BSTNode root;

    public BSTRec() {

        root = null;

    }

    // ATTN:
    // - currNode on STACK,
    // - ITERATE to LEFT or RIGHT
    // - RETURN on null at LEAF!
    private boolean rsearch(int valueToFind, BSTNode scanNode) {

        // ATTN:  check FIRST for the null case!
        if (scanNode == null) {
            return false;
        }
        else if (scanNode.value == valueToFind) {
            // ATTN:  return on FINDING value
            return true;
        }
        else if (valueToFind < scanNode.value) {
            return rsearch(valueToFind, scanNode.left);
        }
        else {
            return rsearch(valueToFind, scanNode.right);
        }

    }

    // ATTN:  DEPTH-RECURSION occurs DOWN LEFT SIDE before POPPING-OUT!
    //        THEN ARC to ROOT, then DOWN to RIGHT
    //        BUBBLE-UP from BOTTOM to TOP!
    //
    private void rTraverseInOrder(BSTNode currNode, int recurseLevel) {

       if (currNode == null) {
           System.out.println(String.format("NULL @ RECURSE-LEVEL:  %d", recurseLevel));
           return;
       }

       if (currNode.left != null)
           rTraverseInOrder(currNode.left, (recurseLevel + 1));

       System.out.println(String.format("%d @ RECURSE-LEVEL:  %d", currNode.value, recurseLevel));

       if (currNode.right != null)
           rTraverseInOrder(currNode.right, (recurseLevel + 1));

    }

    // BREADTH-FIRST SEARCH
    // * uses Queue FILO data structure
    // * or ADD-REMOVE from OPPOSITE sides queue
    public void bfs()
    {

        BSTNode currNode = null;
        Deque<BSTNode> queue = new ArrayDeque<>();
        queue.add(this.root);

        while(!queue.isEmpty()) {

            currNode = queue.remove();
            System.out.println(String.format(", %d", currNode.value));

            if (currNode.left != null)
                queue.add(currNode.left);

            if (currNode.right != null)
                queue.add(currNode.right);

        }

    }

    // DEPTH-FIRST SEARCH
    // * uses Stack FIFO data structure
    // * or ADD/REMOVE from SAME side
    public void dfs() {

        // DFS uses Stack data structure
        Deque<BSTNode> stack = new ArrayDeque<>();
        stack.push(this.root);

        BSTNode currNode = null;
        while(!stack.isEmpty()) {

            currNode = stack.pop();
            System.out.println(String.format(", %d", currNode.value));

            if (currNode.left != null)
                stack.push(currNode.left);

            if (currNode.right != null)
                stack.push(currNode.right);
        }

    }

    /*
CAVEAT:  NOT sufficient to check that within ONE given level; this property holds, for successive levels
This instead must hold ACROSS ALL levels for left and right subtrees:
eg the following is not a BST since 4 > 3.

            3
           / \
          2   5
         / \
        1   4
        KEY:
        - must track STACK-STATE-NARROWING RANGEs!
        - INT_MAX = 4294967296
        - INT_MIN = -4294967296
        ATTN:
        - Python test for null is "not xxx"
        - NEED TO MODIFY INSERT to support RECORDING MIN and MAX on tree!
        DATA:
        - min, max, SIZE of subtree incl this node
        EXIT:
        - null or 1-node  case, return TRUE
        TEST:
        - current node value is NOT > MIN, < MAX, return FALSE
        - recurse on LEFT child; is BST between MIN on THIS parent node, and this parent node's root value
        - AND recurse on RIGHT child; is BST between THIS parent node value, and MAX
     */
    public boolean rIsBST(BSTNode current, Integer min, Integer max) {

        // test for NULL or SINGLE-NODE case!
        if (       (current == null)
                || ((current.left == null) && (current.right == null))) {
            return true;
        }

        // test for immediate BST-property violations!
        if ((current.value < min)  || (current.value > max)) {
            return false;
        }

        // ATTN:  test for NULL FIRST!!!
        // ATTN:  NARROW range +-1 RELATIVE to current.value!
        boolean isLeftBST =    (current.left == null)
                            || ((current.left != null) && rIsBST(current.left, min, (current.value - 1)));
        boolean isRightBST =   (current.right == null)
                            || ((current.right != null) && rIsBST(current.right, (current.value + 1), max));

        // test for RECURSIVE SUBTREE property validation on sub-range!
        return (isLeftBST && isRightBST);

    }

    // ATTN:
    // - currNode on STACK
    // - RETURN of currNode
    // - ASSIGN recursive result to LEFT and RIGHT!
    private BSTNode rInsert(int value, BSTNode currNode) {

        // ATTN:  ASSIGNMENT to RETURNED value on LHS; DONE, no need to do anything further!
        if (currNode == null)  {
            currNode = new BSTNode(value);
            return currNode;
        }

        if (value < currNode.value) {
            currNode.left = rInsert(value, currNode.left);
        }
        else if (value > currNode.value) {
            currNode.right = rInsert(value, currNode.right);
        }
        else if (value == currNode.value) {
            System.out.println(String.format("DO-NOTHING as DUPLICATE for %d is not permitted.", currNode.value));
        }

        return currNode;

    }

    public void insert(int value) {

        root = rInsert(value, root);
    }

    public void printContents() {

        rTraverseInOrder(root, 0);
    }

    public boolean isContains(int value) {

        return rsearch(value, root);
    }

    // ATTN
    // - main DRIVER
    public static void main(String args[]) {

        System.out.println("***** FIRST BST ******");

        System.out.println("EMPTY BST HERE:  ");
        BSTRec bst = new BSTRec();
        bst.printContents();

        System.out.println("\nACTUAL BST CONTENTS HERE:  ");
        bst.insert(2);
        bst.insert(1);
        bst.insert(3);
        bst.insert(4);
        bst.printContents();

        System.out.println("\n***** BFS, BST ******");
        bst.bfs();

        System.out.println("\n***** DFS, BST ******");
        bst.dfs();

        System.out.println("\n***** isBST *****");
        // ATTN:  initialize call to RECURSIVE with MAX-MIN Integer value range!
        System.out.println(String.format("Is BST %b", bst.rIsBST(bst.root,  Integer.MIN_VALUE,  Integer.MAX_VALUE)));

    }

}
