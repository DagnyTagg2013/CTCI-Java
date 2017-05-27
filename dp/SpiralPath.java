package dp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 11/16/16
 *
 * PROBLEM:
 * - find spiralling path from CORNER of grid into center
 *
 * ALGO:
 *
 * - TOP LHS is coordinate (0,0), moving DOWN TO RIGHT for X and Y directions!
 * - INDEX 0 to (MAX-LEN - 1)
 *
 * - CURRENT ASSUMPTIONS:  current point, current direction
 * - BOUNDARIES TO CHANGE ASSUMPTIONS:  grid boundaries, already-visited points
 *
 * - ADAPTIVE, AGILE approach
 *
 * - CHAIN RESULTs of one directional check into the INPUT for the NEXT iterative check!
 *
 * - CASE 1:  move (ONE) SPOT in current Direction
 *            - throw EXCEPTION if BOUNDARIES HIT
 *            - return UPDATED current state-changed otherwise
 *
 * - CASE 2:  move (MULTIPLE) SPOTs based on Case 1
 *            - WHILE loop, breaks on EXCEPTION, and returns PRIOR KNOWN UPDATED current state!
 *
 * - CASE 3:  change Direction based in BOUNDARIES HIT from CASE 2
 *
 * - CASE 4:  (MULTIPLE) move along line, then change direction
 *            - WHILE loop, ENDs on testing visited is COMPLETED
 *            - each STEP is DIRECTION change DECISION-POINT!
 *
 * - CONCLUSION:
 *            - use VISITED to CACHE results and SAVE on redundant computations
 *            - factor out ACTION vs DECISION boundaries
 *            - encapsulate with progessive LOOPs for readability and minimizing complexity of a recursive call!
 *            - use EXCEPTIONs to BREAK out of loops on BOUNDARY conditions, but retain PRIOR state on STACK!
 *            - each function is to progress from VALID current state to NEXT state (which maybe invalid)
 *            - pass data on STACK instead of state variables on class instance!
 *
 */
public class SpiralPath {


    // TODO:  can be configured via ctor input also
    private static int MAX_DIMENSION;
    private static int MAX_INDEX;
    private static int MAX_POINTS;

    // ATTN:  track multiple states for direction!
    public enum Direction {
        LEFT, RIGHT, UP, DOWN, STOP
    }

    public SpiralPath(int gridDim) {
        this.MAX_DIMENSION = gridDim;
        this.MAX_INDEX = gridDim - 1;
        this.MAX_POINTS = gridDim * gridDim;
    }

    public Point moveOneSpotInCurrentDirection(int x, int y, Direction direction, List<Point> visited) {

        // NOW move along adjusted direction prior to rec
        switch (direction) {
            case UP:
                y = y - 1;
                break;

            case DOWN:
                y = y + 1;
                break;

            case LEFT:
                x = x - 1;
                break;

            case RIGHT:
                x = x + 1;
                break;
        }

        // ONE: if BOUNDARY REACHED, throw exception!
        // OR return NULL!
        // TWO: if VISITED REACHED, ALSO throw exception!
        Point movedPoint = new Point(x, y);
        if ((x < 0) || (x > MAX_INDEX) || (y < 0) || (y > MAX_INDEX) || visited.contains(movedPoint)) {
            throw new IndexOutOfBoundsException("Out of Grid Bounds");
            //return null;
        }

        // OTHERWISE; COMPLETE moving the point; SAVE it as VISITED
        visited.add(movedPoint);
        System.out.println(String.format("Moved point to NEW spot:  (%d, %d)", x, y));

        return movedPoint;
    }

    Point multipleMovesOneDirection(Point currentPoint, Direction currentDirection, List<Point> visited) {

        try {
            // NOTE:  ENDLESS LOOP; BROKEN only by EXCEPTION!
            while (true) {
                currentPoint = moveOneSpotInCurrentDirection(currentPoint.x, currentPoint.y, currentDirection, visited);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(String.format("MULTIPLE MOVES in Direction %s; STOPPED at point (%d, %d)", currentDirection, currentPoint.x, currentPoint.y));
            // e.printStackTrace();
            return currentPoint;
        }

    }

    /*
        ATTENTION:
            - Hit Visited on this direction OR hit GRID BOUNDARY;
              now CHANGE direction according to spiral INWARD
     */
    public static Direction spiralInDirection(Direction currentDirection) {

        System.out.println(String.format("CURRENT DIRECTION is %s", currentDirection));


        Direction newDirection = Direction.STOP;

        // ATTENTION:  remember BREAK!
        switch (currentDirection) {
            case UP:
                newDirection = Direction.LEFT;
                break;
            case DOWN:
                newDirection = Direction.RIGHT;
                break;
            case LEFT:
                newDirection = Direction.DOWN;
                break;
            case RIGHT:
                newDirection = Direction.UP;
                break;
        }

        System.out.println(String.format("SPIRALLING DIRECTION to %s", newDirection));


        return newDirection;
    }



    public static void main(String[] args) {

        SpiralPath driver = new SpiralPath(3);

        /*

        // TEST CASE 1: move in given direction ONE spot until GRID BOUNDARY is reached,
        //              SAVE visited  point
        //              EXCEPTION on boundary!
        System.out.println("***** TEST 1");
        Point currentPoint = new Point(2, 0);
        try {
            List<Point> visited = new ArrayList<Point>();
            currentPoint = driver.moveInCurrentDirection(currentPoint.x, currentPoint.y, Direction.LEFT, visited);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("TEST CASE 1:  STOP at point (%d, %d)", currentPoint.x, currentPoint.y));

        // TEST CASE 2: LOOP to KEEP MOVING in given direction ONE spot until GRID BOUNDARY is reached
        System.out.println("***** TEST 2");
        currentPoint = new Point(2, 0);
        List<Point> visited = new ArrayList<Point>();
        Direction currentDirection = Direction.LEFT;
        try {
            currentPoint = driver.multipleMovesOneDirection(currentPoint, currentDirection, visited);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("TEST CASE 2:  STOPPED at point (%d, %d)", currentPoint.x, currentPoint.y));

        // TEST CASE 3: SWITCH DIRECTION given IndexOutOfBoundsException
        System.out.println("***** TEST 3");
        currentDirection = Direction.LEFT;
        currentDirection = driver.spiralInDirection(currentDirection);
        System.out.println(String.format("TEST CASE 3:  SPIRALLED DIRECTION to %s", currentDirection));

        */

        // TEST CASE 4: LOOP to KEEP MOVING in NEW direction; BUT STOP when visited is FULL!
        System.out.println("***** TEST 4");
        Point currentPoint = new Point(2, 0);
        List<Point> visited = new ArrayList<Point>();
        // ATTENTION!  Need to initialize VISITED list with FIRST POINT!
        visited.add(currentPoint);
        Direction currentDirection = Direction.LEFT;
        while (visited.size() != driver.MAX_POINTS) {
                currentPoint = driver.multipleMovesOneDirection(currentPoint, currentDirection, visited);
                // change direction once END of tracking down ONE direction!
                currentDirection = driver.spiralInDirection(currentDirection);
        }
        System.out.println(String.format("TEST CASE 4:  FINALIZED PATH IS %s", visited));

    }
}
