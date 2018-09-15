package Map.Occupant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrateTest {

    @Test
    void getRepresentation() {
        Crate p = new Crate(0, 0, 'd');
        assertEquals('d', p.getRepresentation());
        assertEquals(0, p.getR());
        assertEquals(0, p.getC());
    }
}