package RECURSE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*

*** USING a DEQUE as opposed to STACK!

# PROBLEM:  Move n stacked disks from Tower SOURCE to Tower DESTINATION
# GIVEN:  3 Towers, SOURCE, DEST, TEMP
# CONSTRAINT:  Discs may only be placed on a larger one below it

# SOLUTION:
# - Discs are labelled from 0 to n-1;
#   where (n-1)th disc is smallest TOP disc,
#   and 0th disc is largest BOTTOM disc!
# - move top stack of all discs except bottom to TEMP
# - move bottom to DEST
# - move top stack from TEMP to DEST

*/
public class TowersOfHanoi {

    Map<String, Stack<Integer>> towers = new HashMap<String, Stack<Integer>>();

    public static final String TOWERA = "TOWER A";
    public static final String TOWERB = "TOWER B";
    public static final String TOWERC = "TOWER C";
    private Integer MAX_NUM_DISCS = null;

    public TowersOfHanoi(int maxNumDiscs){
        MAX_NUM_DISCS = maxNumDiscs;
        // load first tower with all discs
        Stack<Integer> fullStack1 = new Stack<Integer>();
        for (int i = 0; i < maxNumDiscs; i++)
            fullStack1.add((maxNumDiscs-i));
        towers.put(TOWERA, fullStack1);
        // create empty second and third towers
        Stack<Integer> emptyStack1 = new Stack<Integer>();        Stack<Integer> emptyStack2 = new Stack<Integer>();
        towers.put(TOWERB, emptyStack1);
        towers.put(TOWERC, emptyStack2);
    }


    public void moveOneDisc(int currentLevel, String source, String dest) {

        // ATTN:  TRACE for ENTRY recursion level!
        System.out.println(String.format("\n-MoveOneDisc Recursion-Level [%d] from %s to %s", currentLevel, source, dest));

        Integer discValue = null;
        Stack<Integer> sourceTower = towers.get(source);
        Stack<Integer> destTower = towers.get(dest);

        // ATTN:  need to check via PEEK, TWO ILLEGAL conditions
        // * Empty Source Tower
        // * Source value to move is BIGGER than top value in Destination, so rule violated!

        if (sourceTower.isEmpty()) {
            new IllegalArgumentException("Unable to move disc from EMPTY Tower!");
        }

        Integer movingValue = sourceTower.peek();
        Integer topDestValue = null;
        if (!destTower.isEmpty() && movingValue > (topDestValue = destTower.peek())) {
            new IllegalArgumentException(String.format("Unable to move disc from %s to %s; since moved value %d is GREATER than DEST TOP value of %d",
                                         source, dest, movingValue, topDestValue));
        }

        discValue = sourceTower.pop();
        destTower.push(discValue);

        System.out.println("STATE AFTER MOVE:  ");
        showTowers();

    }

    public void showTowers() {

        System.out.println("\nSTATE OF TOWERS IS:");

        System.out.println(TOWERA);
        System.out.println(Arrays.toString(towers.get(TOWERA).toArray()));

        System.out.println(TOWERB);
        System.out.println(Arrays.toString(towers.get(TOWERB).toArray()));

        System.out.println(TOWERC);
        System.out.println(Arrays.toString(towers.get(TOWERC).toArray()));

    }

    public void doTheHanoiShuffle() {

        recurseMoves((MAX_NUM_DISCS - 1), TOWERA, TOWERC, TOWERB);

    }

    public void recurseMoves(Integer currentDisc, String source, String dest, String temp) {

        System.out.println(String.format("\n##### ENTRY to RecurseMoves [%d] FROM %s TO %s with TEMP %s", currentDisc, source, dest, temp));

        // System.out.print("STATE at Recurse Entry START");
        // showTowers();

        if (currentDisc == 0) {
            System.out.println("===>> Calling BASE move");
            moveOneDisc(currentDisc, source, dest);
        }
        else {
            // ATTN:  KEY TRICK: move UPPER tower to TEMP, using DEST as buffer!  USAGE of TEMP is FLEXIBLE, and TRACKED on CALLSTACK!
            System.out.println(String.format("\n==>>  Calling FIRST recurseMoves at recursion level [%d]", currentDisc));
            recurseMoves((currentDisc - 1), source, temp, dest);
            // at this point; we move BASE disc directly from source to dest!
            System.out.println(String.format("\n===>> Calling MID move at recursion level [%d]", currentDisc));
            moveOneDisc(currentDisc, source, dest);
            System.out.println(String.format("\n==>>  Calling SECOND recurseMoves at recursion level [%d]", currentDisc));
            // at this point; RESTORE UPPERMOST tower from TEMP to DEST!
            recurseMoves((currentDisc - 1), temp, dest, source);

        }

    }

    public static void main(String[] args) {

        // CASE 1:  1-element
        System.out.println("\n***** CASE 1:  1-element *****\n");
        TowersOfHanoi towers = new TowersOfHanoi(1);
        towers.showTowers();
        towers.moveOneDisc(1, TOWERA, TOWERC);

        // CASE 2:  2-element
        System.out.println("\n***** CASE 2:  2-element *****\n");
        TowersOfHanoi towers2 = new TowersOfHanoi(2);
        towers2.showTowers();
        towers2.moveOneDisc(1, TOWERA, TOWERB);
        towers2.moveOneDisc(1, TOWERA, TOWERC);
        towers2.moveOneDisc(1, TOWERB, TOWERC);

        // CASE 3:  3-element - or 2exp3 - 1 MOVES!
        System.out.println("\n***** CASE 3:  3-element *****\n");
        TowersOfHanoi towers3 = new TowersOfHanoi(3);
        towers3.doTheHanoiShuffle();

        // CASE 4:  4-element - or 2exp4 - 1 MOVES!
        System.out.println("\n***** CASE 4:  4-element *****\n");
        TowersOfHanoi towers4 = new TowersOfHanoi(4);
        towers4.doTheHanoiShuffle();

    }

}
