import Model.Absorber;
import Model.Ball;
import Model.GizmoCircle;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbsorberTests {
    @Test
    public void fireAbsorberTest()
    {
        Absorber a = new Absorber("A", 4, 5, 10, 8);
        Ball b = new Ball("B", 4, 1, 0, 4);
        a.addBall(b);
        assertEquals(1, a.getBalls().size(), 0);
        a.activateAbsorber();
        assertEquals(-1000, b.getVelo().y(), 1);
        assertEquals(0, a.getBalls().size(), 0);
    }

    @Test
    public void getttersNsettersTest()
    {
        Absorber a = new Absorber("A", 4, 5, 10, 8);
        a.setID("a1");
        assertEquals("a1", a.getID());

        a.setKeyConnection("a");
        assertEquals("a", a.getKeyConnections().get(0));
        a.setKeyConnection("b");
        assertTrue(a.getKeyConnections().size()>1);
        a.removeKeyConnection();
        //assertTrue(a.getKeyConnections().size()==0);f

        GizmoCircle c = new GizmoCircle("C",19, 19);
        GizmoCircle c1 = new GizmoCircle("C1",18, 19);
        a.setGizmoConnection("C");
        a.setGizmoConnection("C1");
        assertTrue(a.getGizmoConnections().size()==2);
        a.removeGizmoConnection("C1");
        assertTrue(a.getGizmoConnections().size()==1);

        assertFalse(a.getHit());
        assertTrue(a.getRotationCount()==0 && a.getRotationAngle()==0);

        a.setXCoord(5);
        assertEquals(5, a.getXCoord());
        a.rotate();
        a.setYCoord(6);
        assertEquals(6, a.getYCoord());
    }
}
