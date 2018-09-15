package Map.Occupiable;

import Map.Occupant.Crate;
import Map.Occupant.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DestTileTest {
    private DestTile d;
    private Crate correctCrate;
    private Crate wrongCrate;

    @BeforeEach
    void setUp() {
        d = new DestTile('A');
        correctCrate = new Crate(0, 0, 'a');
        wrongCrate = new Crate(0, 0, 'b');
    }

    @Test
    void isCompletedSuccess() {
        d.setOccupant(correctCrate);
        assertTrue(d.isCompleted());
    }

    @Test
    void isCompletedFailed() {
        d.setOccupant(wrongCrate);
        assertFalse(d.isCompleted());

        Player player = new Player(0, 0);
        d.setOccupant(player);
        assertFalse(d.isCompleted());
    }

    @Test
    void getRepresentationTest() {
        d.setOccupant(correctCrate);
        assertEquals('a', d.getRepresentation());
    }

}