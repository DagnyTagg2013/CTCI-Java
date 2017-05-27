package basics;

/**
 * Created by dagnyt on 5/9/17.
 */
public class Person {

    protected String firstName;
    protected String lastName;
    protected Integer id;

    public Person(int id, String first, String last) {
        // ATTN:  Java AUTO-BOXING creates new Object INSTANCE thru ctor
        // Stack Overflow:  Why-do-we-use-autoboxing-and-unboxing-in-java
        // this.id = new Integer(id),
        this.id = id;
        this.firstName = first;
        this.lastName = last;

    }

    // COPY CTOR
    public Person(Person other) {

        this.id = other.id;
        this.firstName = new String(other.firstName);
        this.lastName = new String(other.lastName);

    }

    // OBJ, THIS, TYPE CAST, DEEP
    //ATTN:  EQUALS override on DEFAULT OBJECT BASE CLASS with VTABLE into MULTIPLE INSTANCE TYPES!
    @Override public boolean equals(Object rhs) {

        // TEST SELF!
        if (rhs == this)
            return true;

        // TEST instance type!
        if (!(rhs instanceof Person)) {
            return false;
        }

        // CAST RHS OBJECT to Person, which maybe upcasting for inheritance!
        Person rhp = (Person)rhs;

        // DEEP COMPARISON
        // ATTN: could ALSO put super.equals()
        // ATTN: need EQUALs for DEEP comparison!
        boolean isEquals = (   this.id.equals(rhp.id)
                            &&
                               this.firstName.equals(rhp.id)
                            &&
                               this.lastName.equals(rhp.id));

        return isEquals;

    }

    // ATTN:  MUST - HAVE with INIT, MULTIPLY to PRIME NUMBER and CHAIN results to ADD each element!
    @Override public int hashCode() {
        int result = 17;
        result = 31 * result + this.id;
        result = 31 * result + this.firstName.hashCode();
        result = 31 * result + this.lastName.hashCode();
        return result;
    }

    //hashcode necessary!
    public String toString() {
        return String.format("[%d, %s, %s]", this.id, this.firstName, this.lastName);
    }


    public static void main(String args[]) {

        Person base = new Person(1, "Tina", "Fey");
        /*
        Student sub = new Student();

        // NOTE:  DEFAULT package access for data members
        // sub.y DATA is STATIC bound to SHAPE, or base class reference
        System.out.println(base.y + ", " + sub.y);
        // q.getY() is DYNAMICALLY bound via FUNCTION pointer to SubShape, which then accesses DERIVED class data; which OVERSHADOWs
        // base data of SAME name!
        System.out.println(base.getY() + ", " + sub.getY());
        */
    }

}
