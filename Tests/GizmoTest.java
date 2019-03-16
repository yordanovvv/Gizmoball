import Model.*;
import org.junit.*;

import static org.junit.Assert.*;

public class GizmoTest {
    private iModel model;
    private GizmoCircle circle;
    private Triangle triangle;
    private iGizmo square;
    private iGizmo rightFlipper;
    private iGizmo leftFlipper;
    private iGizmo absorber;
    private iGizmo star;

    //Set up a board in order to test gizmos
    @Before
    public void createBoard() {
        model = new GizmoballModel();
        circle = new GizmoCircle("C1", 3, 5, 2);
        triangle = new Triangle("T1", 4, 7);
        square = new Square("S1", 20, 9);
        rightFlipper = new RightFlipper("RF1", 8, 10);
        leftFlipper = new LeftFlipper("LF1", 15, 8);
        absorber = new Absorber("A1", 0, 1, 20, 1);
        star = new Star("S1", 19, 17);
        model.addGizmo(circle);
        model.addGizmo(triangle);
        model.addGizmo(square);
        model.addGizmo(rightFlipper);
        model.addGizmo(leftFlipper);
        model.addGizmo(absorber);
        model.addGizmo(star);


    }


    @Test
    public void getID() {

        assertEquals("C1", circle.getID());
        assertNotEquals(2, circle.getID());

    }

    @Test
    public void setID() {

        circle.setID("C4");
        assertEquals("C4", circle.getID());

    }

    @Test
    public void getXCoord() {
        assertEquals(3, circle.getXCoord());
        assertEquals(4, triangle.getXCoord());
        assertEquals(20, square.getXCoord());
        assertEquals(8, rightFlipper.getXCoord());
        assertEquals(15, leftFlipper.getXCoord());
        assertEquals(0, absorber.getXCoord());
        assertEquals(19, star.getXCoord());

    }

    @Test
    public void setXCoord() {
        triangle.setXCoord(5);
        assertEquals(5, triangle.getXCoord());
    }

    @Test
    public void getYCoord() {

        assertEquals(5, circle.getYCoord());
        assertEquals(7, triangle.getYCoord());
        assertEquals(9, square.getYCoord());
        assertEquals(10, rightFlipper.getYCoord());
        assertEquals(8, leftFlipper.getYCoord());
        assertEquals(1, absorber.getYCoord());
        assertEquals(17, star.getYCoord());


    }

    @Test
    public void setYCoord() {
        star.setYCoord(18);
        assertEquals(18, star.getYCoord());
    }

    @Test
    public void testAddGizmo() {
        iGizmo square2 = new Square("S2", 14, 3);
        model.addGizmo(square2);


    }

    @Test
    public void toStringTest() {
        String t = "Triangle T1 4 7";
        assertEquals(t, triangle.toString());


    }

    @Test
    public void getGizmoType(){
        assertEquals("Circle", circle.getGizmoType());
    }



   //TODO need to test equals method for gizmos
}
