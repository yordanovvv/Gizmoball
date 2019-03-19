import Model.GizmoCircle;
import Model.LeftFlipper;
import Model.RightFlipper;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class FlipperTests {
    @Test
    public void gettersAndSettersTest()
    {
        LeftFlipper lf = new LeftFlipper("LF", 3, 3);
        RightFlipper rf = new RightFlipper("rf", 7, 7);
        GizmoCircle c = new GizmoCircle("C",19, 19);

        lf.setHit(true);
        assertTrue(lf.getHit());

        rf.setHit(true);
        assertTrue(rf.getHit());

        assertTrue(lf.getColor().equals(new Color(0, 0, 0, 255)));
        assertTrue(rf.getColor().equals(new Color(170, 169, 50, 255)));

        rf.setID("RF");
        assertTrue(rf.getID().equals("RF"));
        assertEquals("RF", rf.getID());

        lf.setID("LF");
        assertTrue(lf.getID().equals("LF"));
        assertEquals("LF", lf.getID());

        lf.setColour(new Color(170, 169, 50, 255));
        rf.setColour(new Color(0, 0, 0, 255));

        rf.setXCoord(5);
        rf.setYCoord(5);
        assertEquals(5, rf.getXCoord());
        assertEquals(5, rf.getYCoord());

        lf.setXCoord(12);
        lf.setYCoord(12);
        assertEquals(12, lf.getXCoord());
        assertEquals(12, lf.getYCoord());

        rf.setGizmoConnection("LF");
        lf.setGizmoConnection("RF");
        rf.setGizmoConnection("C");
        lf.setGizmoConnection("C");


        assertTrue(rf.getGizmoConnections().size()==2);
        assertTrue(lf.getGizmoConnections().size()==2);

        rf.removeGizmoConnection("C");
        assertTrue(rf.getGizmoConnections().size()==1);

        lf.removeGizmoConnection("C");
        assertTrue(lf.getGizmoConnections().size()==1);

        rf.setKeyConnection("r");
        assertTrue(rf.getKeyConnections().size() ==1);
        lf.removeKeyConnection();
        //TODO
        //assertEquals(0, rf.getKeyConnections().size());

        lf.setKeyConnection("l");
        assertEquals(1, lf.getKeyConnections().size());
        lf.removeKeyConnection();
        //TODO
        //assertEquals(0, rf.getKeyConnections().size());

        assertFalse(rf.isMoving());
        assertFalse(lf.isMoving());

        assertEquals(2,rf.getHeight());
        assertEquals(2,lf.getHeight());
        assertEquals(2,rf.getWidth());
        assertEquals(2,lf.getWidth());

        assertEquals(0, lf.getRotationCount());
        assertEquals(0, rf.getRotationCount());

        assertEquals(6, lf.getCircles().size());
        assertEquals(6, rf.getCircles().size());
        assertEquals(2, lf.getLines().size());
        assertEquals(2, rf.getLines().size());

        assertEquals(lf.getCircles().get(0).getCenter(), lf.getCentre());
        assertEquals(rf.getCircles().get(0).getCenter(), rf.getCentre());

        assertEquals("LeftFlipper LF 12 12",lf.toString());
        assertEquals("RightFlipper RF 5 5",rf.toString());
    }

    @Test
    public void rotationTests()
    {
        LeftFlipper lf = new LeftFlipper("LF", 3, 3);
        RightFlipper rf = new RightFlipper("rf", 7, 7);

        rf.rotate();
        assertEquals(Math.toRadians(18*0.05), rf.getAngularVelo(), 0);

        lf.rotate();
        assertEquals(-Math.toRadians(18*0.05), lf.getAngularVelo(), 0);

        lf.rotate();
        lf.rotate();
        lf.rotate();
        lf.rotate();
        lf.rotate();
        assertEquals(-72, lf.getRotationAngle(), 0);


        rf.rotate();
        rf.rotate();
        rf.rotate();
        rf.rotate();
        assertTrue(rf.getAngle() == 90);
        rf.rotate();
        assertEquals(72,rf.getRotationAngle(), 0);
    }
}
