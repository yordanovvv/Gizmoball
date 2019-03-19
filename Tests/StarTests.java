import Model.Ball;
import Model.Star;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class StarTests {

    @Test
    public void rotationTest() throws InterruptedException {
        Star s = new Star("Star1",1,1);
        s.startStarRotation();
        assertFalse(s.isNotRotating());
        Thread.sleep(1000);
        s.stopRotation();
        assertTrue(s.isNotRotating());

    }

    @Test
    public void colourChange(){
        Color hit =  new Color(183, 97, 255, 255);
        Color not_hit=  new Color(119, 0, 211, 255);
        Star s = new Star("Star1",1,1);
        assertEquals(not_hit,s.getColor());
        s.setHit(true);
        assertEquals(hit,s.getColor());
    }

    @Test
    public void checkMiddleCoord(){
        Star s = new Star("Star1",1,1);
        assertEquals(2,s.getmiddleXCoord());
        assertEquals(2,s.getmiddleYCoord());
    }

    @Test
    public void fillerTests(){
        Star s = new Star("S1",1,1);
        assertEquals("Star S1 1 1", s.toString());
    }
}
