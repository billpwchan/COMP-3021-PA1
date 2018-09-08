
import Exceptions.InvalidMapException;
import Exceptions.UnknownElementException;
import Map.*;
import Map.Occupant.Crate;
import Map.Occupiable.DestTile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Holds the necessary components for running the game.
 */
public class Game {

    private Map m;
    private int numRows;
    private int numCols;
    private char[][] rep;

    /**
     * Loads and reads the map line by line, instantiates and initializes Map m.
     * Print out the number of rows, then number of cols (on two separate lines).
     *
     * @param filename the map text filename
     * @throws InvalidMapException
     */
    public void loadMap(String filename) throws InvalidMapException {
        //TODO
        if (filename.equals("")) {
            return;
        }     //Possible to throw exception
        try {
            var mapFile = new File(filename);
            Scanner mapScanner = new Scanner(mapFile);
            this.numRows = mapScanner.nextInt();
            this.numCols = mapScanner.nextInt();
            this.rep = new char[this.numRows][this.numCols];
            mapScanner.nextLine();  //NewLine character Skip Line.
            System.out.println("" + this.numRows);
            //Iterate through the map. Should consider the case
            //map dimension is inconsistent to specified numRows/Cols
            for (var i = 0; i < this.numRows; i++) {
                String rowString = mapScanner.nextLine();
//                System.out.println(rowString);
                for (var j = 0; j < this.numCols; j++) {
                    this.rep[i][j] = rowString.charAt(j);
                }
            }
            //Assign it to the map variable
            this.m = new Map();
            this.m.initialize(this.numRows, this.numCols, this.rep);
            mapScanner.close();
        } catch (InvalidMapException ex) {
            throw ex;
        } catch (FileNotFoundException ex) {
            var exceptionCaught = ex;
            exceptionCaught.printStackTrace();
        }
    }

    /**
     * Can be done using functional concepts.
     *
     * @return Whether or not the win condition has been satisfied
     */
    public boolean isWin() {
        //TODO
        return false; // You may also modify this line.
    }

    /**
     * When no crates can be moved but the game is not won, then deadlock has occurred.
     *
     * @return Whether deadlock has occurred
     */
    public boolean isDeadlocked() {
        //TODO
        return false; // You may also modify this line.
    }

    /**
     * Print the map to console
     */
    public void display() {
        //TODO

    }

    /**
     * @param c The char corresponding to a move from the user
     *          w: up
     *          a: left
     *          s: down
     *          d: right
     *          r: reload map (resets any progress made so far)
     * @return Whether or not the move was successful
     */
    public boolean makeMove(char c) {
        //TODO
        return false; // You may also modify this line.
    }


}
