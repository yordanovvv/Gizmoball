import Model.*;
import com.sun.source.tree.AssertTree;
import org.junit.*;

import java.util.ArrayList;

import static java.lang.StrictMath.sqrt;
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
    private Ball ball;
    private Wall walls;
    private ArrayList<Ball> balls;

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
        walls = new Wall(0, 0, 20, 20);
        ball = new Ball("B1", 18, 6, 3, 2);
        balls = new ArrayList<>();
        model.addGizmo(circle);
        model.addGizmo(triangle);
        model.addGizmo(square);
        model.addGizmo(rightFlipper);
        model.addGizmo(leftFlipper);
        model.addGizmo(absorber);
        model.addGizmo(star);
        model.addBall(ball);


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
    public void getGizmoType() {
        assertEquals("Circle", circle.getGizmoType());
    }

    @Test
    public void removeGizmo() {
        model.removeGizmo(triangle);
        assertFalse(model.getGizmos().contains(triangle));

    }

    @Test
    public void getBalls() {
        assertTrue(model.getBalls().contains(ball));
    }

    @Test
    public void getWalls() {

        //check all are same

        assertEquals(walls.getXCoord(), model.getWalls().getXCoord());
        assertEquals(walls.getXCoord2(), model.getWalls().getXCoord2());
        assertEquals(walls.getYCoord(), model.getWalls().getYCoord());
        assertEquals(walls.getYCoord2(), model.getWalls().getYCoord2());
    }

    @Test
    public void addBall() {
        balls.add(ball);
        assertTrue(balls.contains(ball));
    }

    @Test
    public void setBallSpeed() {
        model.setBallSpeed(ball, 6, 4);
        double speed = sqrt(36 + 16) * 0.05;

        assertEquals((long) ball.getSpeed(), (long) speed, 0);

    }

    @Test
    public void gizmoFound() {
        iGizmo triangle3 = new Triangle("T3", 4, 3);
        model.addGizmo(triangle3);
        assertTrue(model.gizmoFound("T3"));
        assertFalse(model.gizmoFound("T4"));
    }

    @Test
    public void getGizmoByID() {
        iGizmo circle2 = new GizmoCircle("C2", 4, 9, 2);
        model.addGizmo(circle2);
        assertEquals(model.getGizmoByID("C2"), circle2);
        assertNull(model.getGizmoByID("C10"));
    }

    @Test
    public void connections() {
        iGizmo square5 = new Square("S5", 6, 8);
        iGizmo square6 = new Square("S6", 7, 8);

        model.addGizmo(square5);
        model.addGizmo(square6);


        model.connectGizmos("S5", "S6");

        assertTrue(model.gizmoFound("S5") & model.gizmoFound("S6"));

        assertTrue(square5.getGizmoConnections().contains("S6"));
        assertFalse(model.connectGizmos("S5", "S10"));
    }


    @Test
    public void keyConnections() {

        iGizmo flipper2 = new RightFlipper("RF2", 5, 7);


        model.addGizmo(flipper2);

        model.keyConnectGizmo("RF2", "r");

        assertTrue(model.getKeyTriggers().containsKey(flipper2));


    }


    //TODO need to test equals method for gizmos
}
