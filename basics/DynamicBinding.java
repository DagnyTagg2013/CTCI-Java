package basics;

public class DynamicBinding extends Object  {
	
	public static void main(String args[]) {
		        Shape base = new Shape();
                Shape sub = new SubShape();
                // NOTE:  DEFAULT package access for data members
                // sub.y DATA is STATIC bound to SHAPE, or base class reference
                System.out.println(base.y + ", " + sub.y);
                // q.getY() is DYNAMICALLY bound via FUNCTION pointer to SubShape, which then accesses DERIVED class data; which OVERSHADOWs
                // base data of SAME name!
                System.out.println(base.getY() + ", " + sub.getY());
        }
}

class Shape {
     int x = 7, y = 7;
     int getY() { return y; }
}

class SubShape extends Shape {
     int x=5, y=5;
     int getY() { return y; }
}

