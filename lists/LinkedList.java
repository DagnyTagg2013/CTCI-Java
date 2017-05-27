package lists;

public class LinkedList {

    Node head;
    Node end;

    public LinkedList() {

        head = null;
        // ATTN:  do this to stop from having to SCAN each time!
        end = null;

    }

    public void append(int newVal) {

        Node newNode = new Node(newVal);

        if (end == null) {
            head = newNode;
        }
        else {
            end.next = newNode;
        }

        // ATTN: update end NO MATTER WHAT!
        end = newNode;

    }

    public void reverse() {

        // TRICK:  need this to reference PRIOR!
        Node scanPrev = null;

        Node scanCurr = head;
        // ATTN:  need to cache REVERSED end here!
        end = head;

        // TRICK:  need this to point BACK, OR just use scanPrev.next for scanCurr?
        Node scanAhead = null;

        // TODO:  think about doing this with 2 ptrs only!
        while (scanCurr != null) {

            // ATTN:  need to SAVE REMAINDER of list
            scanAhead = scanCurr.next;

            // ATTEN:  here's where you relink to go BACKwards
            scanCurr.next = scanPrev;

            // ATTN:  here just update prev
            scanPrev = scanCurr;
            // ATTN:  here pickup scan on REMAINDER!
            scanCurr = scanAhead;

        }

        // ATTN: need to update reversed head here!
        head = scanPrev;

    }

    public void showContents() {

        // HANDLE EMPTY edge-case, exit early!
        if (head == null) {
            System.out.println("EMPTY List");
            return;
        }

        StringBuilder res = new StringBuilder();
        Node scanPtr = head;
        while (scanPtr != null) {
            System.out.print(scanPtr.value);
            System.out.print(' ');
            // RETARD!  advance ptr!
            scanPtr = scanPtr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

            System.out.println("CORE CASE:  3-element List");

            LinkedList list = new LinkedList();
            list.showContents();

            list.append(1);
            list.showContents();

            list.append(2);
            list.showContents();

            list.append(3);
            list.showContents();

            list.reverse();
            list.showContents();

            System.out.println("\nEDGE CASE:  1-element List");

            LinkedList list2 = new LinkedList();
            list2.showContents();
            list2.append(1);
            list2.showContents();
            list2.reverse();
            list2.showContents();

            System.out.println("\nEDGE CASE:  0-element List");

            LinkedList list3 = new LinkedList();
            list3.showContents();
            list3.reverse();
            list3.showContents();


    }

}
