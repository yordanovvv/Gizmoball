import Model.Square;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SquareTests {
    @Test
    public void tests()
    {
        Square s = new Square("S1", 5, 5);
        assertFalse(s.getHit());
        s.setID("S1");
        assertEquals("S1", s.getID());
        s.setXCoord(7);
        s.setYCoord(7);
        assertEquals(7, s.getXCoord());
        assertEquals(7, s.getYCoord());
        assertEquals(0, s.getRotationAngle());
        assertEquals(0, s.getHeight());
        assertEquals(1, s.getWidth());
        s.rotate();
        assertTrue(s.getRotationCount()==0);
        assertEquals(0, s.getRotationAngle());

    }
}
