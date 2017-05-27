package lists;

public class Node {

        // ATTN:  Public for expediency!
        public Integer value;
        public Node next;

        public Node(int initVal) {
                this.value = initVal;
                this.next = null;
        }
}
