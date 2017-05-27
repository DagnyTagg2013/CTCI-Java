package dp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 11/7/16.
 *
 * PROBLEM:  JAVA Implementation of Dynamic Programming problem
 *           - navigate a grid with certain BLOCKED points!
 *           - ORIGIN at TOP LEFT; END at BOTTOM RIGHT
 *
 * EXTENSION:  can check VISITED/CACHED points to RETURN result immediately if invalid path;
 *             otherwise, ADD to CACHE prior to return!
 *
 * APPROACH:
 *
 * - CHAIN Boolean RESULTS of one directional check into the INPUT for the NEXT iterative check!
 * - RETURN Boolean validation indicator to check whether to STOP BREADTH-FIRST path recursion on current node and descendants!
 *
 * StackOverflow:  Java8 vs Scala
 * - http://kukuruku.co/hub/scala/java-8-vs-scala-the-difference-in-approaches-and-mutual-innovations
 * - https://www.infoq.com/articles/java-8-vs-scala
 * - https://jaxenter.com/the-10-most-annoying-things-about-java-after-using-scala-108012.html
 *
 * StackOverflow:  Building Java from Maven pom.xml, or build.sbt
 * - http://stackoverflow.com/questions/2550441/is-guava-libraries-available-in-maven-repo
 * - http://mvnrepository.com/artifact/com.google.guava
 * - http://mvnrepository.com/repos/central
 * - http://stackoverflow.com/questions/8296280/use-sbt-to-build-pure-java-project
 * - http://eng.wealthfront.com/2015/02/11/pattern-matching-in-java-with-visitor/
 *
 * StackOverflow:  Restoring Maven installation
 * - http://stackoverflow.com/questions/8826881/maven-install-on-mac-os-x
 * - brew install maven
 * - THEN do mvn clean update; in terminal to get updates!
 * - http://stackoverflow.com/questions/29627656/error-package-com-google-common-base-does-not-exist
 * - http://stackoverflow.com/questions/17223536/failed-to-execute-goal-org-apache-maven-pluginsmaven-compiler-plugin2-3-2comp
 * -  rm -rf ~/.m2/repository/org/apache
 *
 * StackOverflow:  IntelliJ Auto-Importing of Class!
 * - ALT + ENTER on MAC!
 * - http://stackoverflow.com/questions/31211842/any-way-or-shortcut-to-auto-import-the-classes-in-intellij-like-in-eclipse
 *
 * - LIST initialization
 * - http://www.leveluplunch.com/java/examples/initialize-list/
 */

// BIG TODO:  Resolve Maven download on Google Guava MVN dependency!
// import com.google.guava.ImmutableMap;

public class JDynamicPathfinder {

    // TODO:  refactor to accept into ctor!
    private static int GRID_DIM = 0;  // NOTE:  0-based origin!
    // TODO:  refactor to accept into ctor, an INDEPENDENT Point class, since needs to support Containment check with Hashcode and Equals!
    // http://stackoverflow.com/questions/5600668/how-can-i-initialize-an-arraylist-with-all-zeroes-in-java
    private List<Point>  blockedPoints;

    public JDynamicPathfinder (int gridDimension, List<Point> blockedPoints) {

        this.GRID_DIM = gridDimension;
        // TODO:  take a local COPY instead!
        this.blockedPoints = blockedPoints;

        System.out.println("Initialized blocked Points are:  ");
        System.out.println(blockedPoints);
    }



    // ATTN:  use RAW coordinates!
    private boolean isFree (int x, int y) {

        // TODO:  fix spurious creation of NEW point!
        Point testPoint = new Point(x,y);
        boolean isFree = !blockedPoints.contains(testPoint);

        return isFree;
    }

    // TODO:  BEST to START with TRUE invariant, then do TRANSITION and TEST prior to adding to VISITED LIST!
    //        OTHERWISE, have to BACKOUT partialPath!
    // ATTN:
    // - input FARTHEST ENDPOINT x coordinate from ORIGIN 0; and y coordinate also!
    // - uses RAW coordinates to check boundaries, but save Point composite to Path!
    // - returns FALSE to BACKUP DEPTH-FIRST RECURSIVE stack on finding Path possibilities!
    // TODO:  note that state is validated, then ADVANCEMENT is validated prior to recursive call!
    //        i.e. VALIDATE at TOP of recursive function; or PRIOR to calling recursive call!
    public boolean getPath(int x, int y, List<Point> partialPath) {

        // ATTN:  FIRST test if point is on BLOCK List!
        if (!isFree(x, y))
        {
            return false;  // LAST point on path is BLOCKED, so NO valid path exists!
        }

        Point p = new Point(x, y);
        partialPath.add(p);

        // ATTN:  boundary degenerate case!
        if (x == 0 && y == 0) {
            return true; // found a path
        }

        boolean success = false;

        // ATTN: MOVE BACKWARDS by FIRST going LEFT, and VALIDATE PRIOR to recursive call!
        // ATTN:  check for index bound PRIOR to decrement!  0 is BOUND, so check for 1 ABOVE!
        if ((x >= 1) && isFree(x - 1, y)) {
            success = getPath(x - 1, y, partialPath);
        }


        // ATTN:  NOW go UP
        // ATTN:  check for index bound PRIOR to decrement!  0 is BOUND, so check for 1 ABOVE!
        // ATTN:  CHAIN prior result, because want to navigate nearly an optimal DIAGONAL path!
        if (!success && (y >= 1) && isFree(x, y - 1)) {
            success = getPath(x, y - 1, partialPath);
        }

        // ATTN:  BACK out element if not useful!
        if (!success) {
            partialPath.remove(p);
        }

        return success;

    }

    public static void main(String[] args) {

        // Prints "Hello, World" to the terminal window.
        System.out.println("Hello, World");


        // ******* TEST 1:  BLOCKED PATH ***********
        // INITIALIZE Blocked Points
        System.out.println("TEST 1");
        List<Point> blockedPoints1 = new ArrayList<Point>();
        blockedPoints1.add(new Point(1,1));
        blockedPoints1.add(new Point(2,2));
        blockedPoints1.add(new Point(3,3));

        JDynamicPathfinder aDynamicPathfinder = new JDynamicPathfinder(3, blockedPoints1);
        List<Point> partialPath = new ArrayList<Point>();
        boolean isFoundPath = aDynamicPathfinder.getPath(JDynamicPathfinder.GRID_DIM, JDynamicPathfinder.GRID_DIM, partialPath);
        if (isFoundPath) {
            System.out.println("FOUND Path is:  ");
            System.out.println(partialPath);
        }
        else {
            System.out.print("Path NOT Found!");
        }

        // ****** TEST 2: VALID PATH ***************
        // INITIALIZE Blocked Points
        System.out.println("\nTEST 2");
        List<Point> blockedPoints2 = new ArrayList<Point>();
        blockedPoints2.add(new Point(2,2));

        JDynamicPathfinder aDynamicPathfinder2 = new JDynamicPathfinder(3, blockedPoints2);
        List<Point> partialPath2 = new ArrayList<Point>();
        boolean isFoundPath2 = aDynamicPathfinder2.getPath(JDynamicPathfinder.GRID_DIM, JDynamicPathfinder.GRID_DIM, partialPath2);
        if (isFoundPath2) {
            System.out.println("FOUND Path is:  ");
            System.out.println(partialPath2);
        }
        else {
            System.out.print("Path NOT Found!");
        }

    } // end main

} // end class




