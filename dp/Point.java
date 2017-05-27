/**
 * Created by daphneeng on 11/8/16.
 */

package dp;

// TODO: refactor as INDEPENDENT Point class!
class Point {

    public int x = 0;
    public int y = 0;

    //constructor
    public Point(int a, int b) {
        x = a;
        y = b;
    }

    // OBJ, THIS, TYPE CAST, DEEP
    //ATTN:  EQUALS override on DEFAULT OBJECT BASE CLASS with VTABLE into MULTIPLE INSTANCE TYPES!
    @Override public boolean equals(Object rhs) {

        // TEST SELF!
        if (rhs == this)
            return true;

        // TEST instance type!
        if (!(rhs instanceof Point)) {
            return false;
        }

        // CAST RHS OBJECT to POINT, which maybe upcasting for inheritance!
        Point rhp = (Point)rhs;

        // DEEP COMPARISON
        // ATTN: could ALSO put super.equals()
        boolean isEquals = ((this.x == rhp.x) && (this.y == rhp.y));

        return isEquals;

    }

    // ATTN:  MUST - HAVE with INIT, MULTIPLY to PRIME NUMBER and CHAIN results to ADD each element!
    @Override public int hashCode() {
        int result = 17;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    //hashcode necessary!
    public String toString() {
        return String.format("[%d, %d]", x, y);
    }

}
