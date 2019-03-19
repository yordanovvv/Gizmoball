import Model.Triangle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TriangleTests {
    @Test
    public void rotateTriangeTest()
    {
        Triangle t = new Triangle("T", 5, 5);
        t.rotate();
        assertEquals(1, t.getRotationCount());
    }

    @Test
    public void getSetTests()
    {
        Triangle t = new Triangle("T", 5, 5);
        t.setID("T1");
        assertEquals("T1", t.getID());
        assertEquals(0, t.getRotationAngle());
        assertEquals(0,t.getHeight());
        assertEquals(0,t.getWidth());
        t.setYCoord(6);
        assertEquals(6, t.getYCoord());
    }
}
