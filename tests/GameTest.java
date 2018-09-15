import Exceptions.InvalidMapException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game g;

    @BeforeEach
    void setUp() throws InvalidMapException {
        g = new Game();
        g.loadMap("tests/goodmap.txt");
    }

    @Test
    void loadMapFailure() throws InvalidMapException {
        g.loadMap("");
        g.loadMap("asdlfkjasdlkfj"); //should not throw exception
        assertThrows(InvalidMapException.class, () -> g.loadMap("tests/badmap.txt"));
    }

    @Test
    void loadMapFailure_InvalidChar() {
        assertThrows(InvalidMapException.class, () -> g.loadMap("tests/badmap2.txt"));
    }

    @Test
    void displayTest() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        g.display();
        assertTrue(true, "##########\n" +
                "#@...a..A#\n" +
                "#b.......#\n" +
                "#B.......#\n" +
                "##########".equals(outContent.toString()));
        System.setOut(originalOut);
    }

    @Test
    void makeMove() throws InvalidMapException {
        g.makeMove('s');
        g.makeMove('r');

        //Already re-initialized.
        g.makeMove('w');
        g.makeMove('s');
        g.makeMove('a');
        g.makeMove('d');
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        g.display();
        assertTrue(true, "##########\n" +
                "#....a..A#\n" +
                "#.@......#\n" +
                "#b.......#\n" +
                "##########".equals(outContent.toString()));
        assertFalse(g.isDeadlocked());
        System.setOut(originalOut);
    }

    @Test
    void isWinFailed() {
        assertFalse(g.isWin());
    }

    @Test
    void isWinSuccess() throws InvalidMapException {
        g.loadMap("tests/successmap.txt");
        assertTrue(g.isWin());
    }

    @Test
    void reinitializeFailed() throws InvalidMapException {
//        g.loadMap("tests/badmap.txt");
//        assertThrows(InvalidMapException.class, () -> g.loadMap("tests/badmap.txt"));
//        g.makeMove('r');
    }

    @Test
    void invalidMove() throws InvalidMapException {
        assertFalse(g.makeMove('t'));
    }

    @Test
    void isDeadLocked() throws InvalidMapException {
        g.loadMap("tests/deadlockMap.txt");
        assertTrue(g.isDeadlocked());
    }
}