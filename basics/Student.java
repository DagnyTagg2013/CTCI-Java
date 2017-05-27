package basics;

/*
    ATTN RECALL:  BASLINE METHODs to implement for Collections
    - ctor (COPY items)
    - copy ctor (to allow Collections deep copy)
    - hashCode() (to allow Collections Hash Map)
    - equals() (to allow DEEP .equals() comparison vs SHALLOW)

 */



// ALT + ENTER for auto-imports!
import java.util.ArrayList;
import java.util.List;

// ATTN:  do INHERITANCE here
// ATTN:  EXTENDs prior to IMPLEMENTs!
public class Student extends Person {

    private ArrayList<Integer> scores;

    // ATTN:  StackOverflow:  JavaWorld does-java-pass-by-reference-or-pass-by-value
    //        - Java manipulates objects 'by reference';
    //          but it passes object references to methods 'by value'
    //          SO within function, COPIES of references of args are used
    //          so SWAP function will NEVER WORK in Java!
    public Student(int id, String first, String last, List<Integer> scores) {

        // ATTN:  call SUPERCLASS ctor!
        super(id, first, last);

        // ATTN:  Java how-to-clone-arraylist-and-also-clone-its-contents
        // ATTN:  the way this works is it assumes copy-ctor exists on each ELEMENT type!
        this.scores = new ArrayList<Integer>(scores.size());
        for (Integer aScore : scores) {
            this.scores.add(new Integer(aScore));
        }

    }


    // TODO:  COPY CTOR with DEEP COPIES!
    public Student(Student otherStudent) {

        super(otherStudent.id, otherStudent.firstName, otherStudent.lastName);
        this.scores = new ArrayList<Integer>(scores.size());
    }

    // TODO:  HASHCODE calling PARENT
    public int hashCode()  {
        return 42;
    }


    // TODO:  EQUALS calling PARENT
    @Override public boolean equals(Object rhs) {

        // TEST SELF!
        if (rhs == this)
            return true;

        // TEST instance type!
        if (!(rhs instanceof Student)) {
            return false;
        }

        return false;

    }


}

