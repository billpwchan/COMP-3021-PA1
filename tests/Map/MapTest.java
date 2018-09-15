package Map;

import Exceptions.InvalidMapException;
import Exceptions.InvalidNumberOfPlayersException;
import Exceptions.UnknownElementException;
import Map.Occupant.Crate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {
    private Map m;

    private int rows = 5;
    private int cols = 6;
    private char[][] goodMap = {
            {'#', '#', '#', '#', '#', '#'},
            {'#', 'c', '.', '.', '.', '#'},
            {'.', '@', '.', 'a', 'b', '#'},
            {'#', '.', 'C', 'A', 'B', '#'},
            {'#', '#', '#', '#', '#', '#'},
    };
    private char[][] badMap = {
            {'#', '#', '#', '#', '#', '#'},
            {'#', 'c', '.', '.', '.', '#'},
            {'.', '.', '.', 'a', 'b', '#'},
            {'#', '.', 'C', 'A', 'B', '#'},
            {'#', '#', '#', '#', '#', '#'},
    };

    @BeforeEach
    void setUp() throws InvalidMapException {
        m = new Map();
        m.initialize(rows, cols, goodMap);
    }

    @Test
    void moveCrateTest() {
        m.movePlayer(Map.Direction.UP);
        m.movePlayer(Map.Direction.RIGHT);
        m.movePlayer(Map.Direction.RIGHT);
        m.movePlayer(Map.Direction.UP);
        m.movePlayer(Map.Direction.RIGHT);
        m.movePlayer(Map.Direction.RIGHT);
        m.movePlayer(Map.Direction.DOWN);
        m.movePlayer(Map.Direction.LEFT);
        int count = 0;
        for (var destTile : m.getDestTiles()) {
            if (destTile.getOccupant().isPresent()) count++;
        }
        assertEquals(1, count);
    }

    @Test
    void nullPlayerFound() throws InvalidMapException {
        m = new Map();
        assertThrows(InvalidNumberOfPlayersException.class, () -> m.initialize(rows, cols, badMap));

    }

    @Test
    void getDestTiles() {
        assertEquals(2, m.getDestTiles().stream().filter(x -> "AB".contains("" + x.getRepresentation())).count());
    }

    @Test
    void getCrates() {
        assertEquals(2, m.getCrates().stream().filter(x -> "ab".contains("" + x.getRepresentation())).count());
    }

    @Test
    void getCells() {
        assertEquals(5, m.getCells().length);
        assertEquals(6, m.getCells()[0].length);
    }


}