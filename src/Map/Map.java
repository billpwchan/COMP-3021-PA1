package Map;

import Exceptions.InvalidMapException;
import Exceptions.InvalidNumberOfPlayersException;
import Exceptions.UnknownElementException;
import Map.Occupant.Crate;
import Map.Occupant.Occupant;
import Map.Occupant.Player;
import Map.Occupiable.DestTile;
import Map.Occupiable.Occupiable;
import Map.Occupiable.Tile;

import java.util.ArrayList;

/**
 * A class holding a the 2D array of cells, representing the world map
 */
public class Map {
    private Cell[][] cells;
    private ArrayList<DestTile> destTiles = new ArrayList<>();
    private ArrayList<Crate> crates = new ArrayList<>();

    private Player player;

    /**
     * This function instantiates and initializes cells, destTiles, crates to the correct map elements (the # char
     * means a wall, @ the player, . is unoccupied Tile, lowercase letter is crate on a Tile,
     * uppercase letter is an unoccupied DestTile).
     *
     * @param rows The number of rows in the map
     * @param cols The number of columns in the map
     * @param rep  The 2d char array read from the map text file
     * @throws InvalidMapException Throw the correct exception when necessary. There should only be 1 player.
     */
    public void initialize(int rows, int cols, char[][] rep) throws InvalidMapException {
        this.reInitializeVariables(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; ++j) {
                switch (rep[i][j]) {
                    case '#': {
                        this.cells[i][j] = new Wall();
                        break;
                    }
                    case '@': {
                        if (this.player == null) {
                            this.player = new Player(i, j);
                            Tile newTile = new Tile();
                            newTile.setOccupant(this.player);
                            this.cells[i][j] = newTile;
                            break;
                        } else {
                            throw new InvalidNumberOfPlayersException(">1 players found!");
                        }
                    }
                    case '.': {
                        this.cells[i][j] = new Tile();
                        break;
                    }
                    default: {
                        Cell newTile;
                        if (Character.isLowerCase(rep[i][j])) { //Case of Crate on a cell.
                            newTile = new Tile();
                            Occupant newOccupant = new Crate(i, j, rep[i][j]);
                            this.crates.add((Crate) newOccupant);
                            ((Tile) newTile).setOccupant(newOccupant);
                            this.cells[i][j] = newTile;
                            break;
                        } else if (Character.isUpperCase(rep[i][j])) {
                            this.cells[i][j] = new DestTile(rep[i][j]);
                            this.destTiles.add((DestTile) this.cells[i][j]);
                            break;
                        }
                        throw new UnknownElementException("Unknown char: " + rep[i][j]);
                    }
                }
            }
        }
        if (this.player == null) {
            throw new InvalidNumberOfPlayersException("0 players found!");
        }
    }

    private void reInitializeVariables(int rows, int cols) {
        this.cells = new Cell[rows][cols];
        this.destTiles = new ArrayList<>();
        this.crates = new ArrayList<>();
        this.player = null;
    }

    public ArrayList<DestTile> getDestTiles() {
        return destTiles;
    }

    public ArrayList<Crate> getCrates() {
        return crates;
    }

    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Attempts to move the player in the specified direction. Note that the player only has the strength to push
     * one crate. It cannot push 2 or more crates simultaneously. The player cannot walk through walls or walk beyond
     * map coordinates.
     *
     * @param d The direction the player wants to move
     * @return Whether the move was successful
     */
    public boolean movePlayer(Direction d) {
        int oriRow = this.player.getR();
        int oriCol = this.player.getC();
        int currentRow = this.player.getR();
        int currentCol = this.player.getC();
        switch (d) {
            case UP: {
                currentRow--;
                break;
            }
            case DOWN: {
                currentRow++;
                break;
            }
            case LEFT: {
                currentCol--;
                break;
            }
            case RIGHT: {
                currentCol++;
                break;
            }
        }
        if (this.isValid(currentRow, currentCol) && this.isOccupiableAndNotOccupiedWithCrate(currentRow, currentCol)) {
            ((Tile) this.cells[oriRow][oriCol]).removeOccupant();
            ((Tile) this.cells[currentRow][currentCol]).setOccupant(this.player);
            this.player.setPos(currentRow, currentCol);
            return true;
        } else if (this.isValid(currentRow, currentCol) && this.cells[currentRow][currentCol] instanceof Occupiable) {
            if (((Tile) this.cells[currentRow][currentCol]).getOccupant().get() instanceof Crate) {
                if (this.moveCrate((Crate) ((Tile) this.cells[currentRow][currentCol]).getOccupant().get(), d)) {
                    ((Tile) this.cells[oriRow][oriCol]).removeOccupant();
                    ((Tile) this.cells[currentRow][currentCol]).setOccupant(this.player);
                    this.player.setPos(currentRow, currentCol);
                    return true;
                }
            }
        }
        return false; // You may also modify this line.
    }

    /**
     * Attempts to move the crate into the specified direction by 1 cell. Will only succeed if the destination
     * implements the occupiable interface and is not currently occupied.
     *
     * @param c The crate to be moved
     * @param d The desired direction to move the crate in
     * @return Whether or not the move was successful
     */
    private boolean moveCrate(Crate c, Direction d) {
        int oriRow = c.getR();
        int oriCol = c.getC();
        int currentRow = c.getR();
        int currentCol = c.getC();
        switch (d) {
            case UP: {
                currentRow--;
                break;
            }
            case DOWN: {
                currentRow++;
                break;
            }
            case LEFT: {
                currentCol--;
                break;
            }
            case RIGHT: {
                currentCol++;
                break;
            }
        }
        if (this.isValid(currentRow, currentCol) && this.isOccupiableAndNotOccupiedWithCrate(currentRow, currentCol)) {
            ((Tile) this.cells[oriRow][oriCol]).removeOccupant();
            ((Tile) this.cells[currentRow][currentCol]).setOccupant(c);
            c.setPos(currentRow, currentCol);
            return true;
        }
        return false; // You may also modify this line.
    }

    private boolean isValid(int r, int c) {
        return (r >= 0 && r < cells.length && c >= 0 && c < cells[0].length);
    }

    /**
     * @param r The row coordinate
     * @param c The column coordinate
     * @return Whether or not the specified location on the grid is a location which implements Occupiable,
     * yet does not currently have a crate in it. Will return false if out of bounds.
     */
    public boolean isOccupiableAndNotOccupiedWithCrate(int r, int c) {
        if (!this.isValid(r, c)) {
            return false;
        }
        if (!(this.cells[r][c] instanceof Occupiable)) {
            return false;
        } else {
            return !(((Occupiable) this.cells[r][c]).getOccupant().isPresent()
                    && ((Occupiable) this.cells[r][c]).getOccupant().get() instanceof Crate);
        }
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
