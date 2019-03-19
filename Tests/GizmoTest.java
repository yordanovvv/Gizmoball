import Model.Ball;
import org.junit.Before;

import java.awt.*;
import java.util.ArrayList;
import Model.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
        circle = new GizmoCircle("C1", 3, 5);
        triangle = new Triangle("T1", 4, 7);
        square = new Square("S1", 20, 9);
        rightFlipper = new RightFlipper("RF1", 8, 10);
        leftFlipper = new LeftFlipper("LF1", 15, 8);
        absorber = new Absorber("A1", 0, 18, 20, 20);
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
        assertEquals(18, absorber.getYCoord());
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

        assertEquals((long) model.getBallSpeed(ball), (long) speed);

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
        iGizmo circle2 = new GizmoCircle("C2", 4, 9);
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

        assertFalse(model.keyConnectGizmo("Rf55", "s"));

        assertTrue(model.getKeyTriggers().containsKey(flipper2));


    }

    @Test
    public void getStars() {
        ArrayList<iGizmo> testStars = new ArrayList<>();
        testStars.add(star);


        assertEquals(testStars, model.getAllStars());
    }

    @Test
    public void setiGizmo() {

        iGizmo circle3 = new GizmoCircle("C1", 3, 5);

        assertEquals(circle3.getID(), circle.getID());
        model.setiGizmo(circle3);

    }

    @Test
    public void removeConnetions() {

        iGizmo circle5 = new GizmoCircle("C5", 3, 4);
        iGizmo square5 = new Square("S5", 6, 7);

        model.addGizmo(circle5);
        model.addGizmo(square5);

        circle5.setGizmoConnection("S5");
        assertTrue(model.removeConnection(circle5, "S5"));
    }



    @Test
    public void moveBall() {
        GizmoballModel m = new GizmoballModel();
        Ball test1 = new Ball("test1", 1, 0, 0, 7);
        test1.setStopped(false);
        m.addBall(test1);
        m.moveBall();

        assertEquals(12.375, m.getBalls().get(1).getExactY(), 3);
        assertEquals(30.0, m.getBalls().get(1).getExactX(), 3);
    }

    @Test
    public void moveBallWithCollisions() {
        GizmoballModel m = new GizmoballModel();
        Ball test1 = new Ball("test1", 1, 0, 0, 7);
        test1.setStopped(false);
        m.addBall(test1);


        iGizmo c1 = new GizmoCircle("C1", 2, 0);
        iGizmo c2 = new GizmoCircle("c2", 2, 1);
        iGizmo c3 = new GizmoCircle("c3", 2, 2);
        iGizmo s1 = new Square("s1", 0, 2);
        iGizmo t1 = new Triangle("t1", 1, 2);
        m.addGizmo(c1);
        m.addGizmo(c2);
        m.addGizmo(c3);
        m.addGizmo(t1);
        m.addGizmo(s1);

        for (int i = 0; i < 5; i++) {
            m.moveBall();
        }

        assertEquals(34.875, m.getBalls().get(1).getExactY(), 3);
        assertEquals(30.0, m.getBalls().get(1).getExactX(), 3);
    }

    @Test
    public void moveBallWithAbsorber() {
        GizmoballModel m = new GizmoballModel();
        Ball test1 = new Ball("test1", 1, 0, 0, 7);
        test1.setStopped(false);
        m.addBall(test1);

        Absorber a1 = new Absorber("a1", 0, 2, 5, 5);
        m.addGizmo(a1);

        for (int i = 0; i < 5; i++) {
            m.moveBall();
        }

        assertEquals(1, m.getAbsorber().getBalls().size());
        assertEquals(135, m.getBalls().get(1).getExactY(), 3);
        assertEquals(135, m.getBalls().get(1).getExactX(), 3);

    }


    @Test
    public void moveBallForTime() {
        GizmoballModel m = new GizmoballModel();
        Ball test1 = new Ball("test1", 1, 0, 0, 7);
        m.addBall(test1);

        test1 = m.moveBallForTime(test1, 1);

        assertEquals(210, test1.getExactY(), 3);
        assertEquals(30, test1.getExactX(), 3);
    }


    @Test
    public void moveBallStarCollisions() {
        GizmoballModel m = new GizmoballModel();
        Ball test1 = new Ball("test1", 1, 0, 0, 7);
        test1.setStopped(false);
        m.addBall(test1);

        Star a1 = new Star("star", 1, 2);
        m.addGizmo(a1);

        for (int i = 0; i < 5; i++) {
            m.moveBall();
        }

        assertEquals(72.92, m.getBalls().get(1).getExactY(), 3);
        assertEquals(30, m.getBalls().get(1).getExactX(), 3);

    }

    @Test
    public void ballSettersTest()
    {
        Ball b = new Ball("b",1,0,0,7);
        b.setID("c");
        assertTrue(b.getID().equals("c"));

        b.setRadius(0.4);
        assertTrue(b.getRadius() == 0.4);

    }


    @Test
    public void wipeSpaces() {
        GizmoballModel m = new GizmoballModel();
        m.setSpaces(4, 4, true, new Star("test", 4, 4),null);
        boolean[][] testSpaces = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                testSpaces[i][j] = false;
            }
        }
        m.wipeSpaces();
        assertTrue(Arrays.deepEquals(m.getSpaces(), testSpaces));
    }

    @Test
    public void setSpaces_star() {
        GizmoballModel m = new GizmoballModel();
        m.setSpaces(4, 4, true, new Star("test", 4, 4),null);

        boolean[][] testSpaces = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                testSpaces[i][j] = false;
            }
        }
        for (int i = 0; i < 4; i++) {
            testSpaces[3 + i][3] = true;
            testSpaces[3 + i][4] = true;
            testSpaces[3 + i][5] = true;
            testSpaces[3 + i][6] = true;
        }

        assertTrue(Arrays.deepEquals(m.getSpaces(), testSpaces));
    }

    @Test
    public void setSpaces_square() {
        GizmoballModel m = new GizmoballModel();
        m.setSpaces(4, 4, true, new Square("test", 4, 4),null);

        boolean[][] testSpaces = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                testSpaces[i][j] = false;
            }
        }
        testSpaces[4][4] = true;

        assertTrue(Arrays.deepEquals(m.getSpaces(), testSpaces));
    }

    @Test
    public void setSpaces_circle() {
        GizmoballModel m = new GizmoballModel();
        m.setSpaces(4, 4, true, new GizmoCircle("test", 4, 4),null);

        boolean[][] testSpaces = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                testSpaces[i][j] = false;
            }
        }
        testSpaces[4][4] = true;

        assertTrue(Arrays.deepEquals(m.getSpaces(), testSpaces));
    }

    @Test
    public void setSpaces_triangle() {
        GizmoballModel m = new GizmoballModel();
        m.setSpaces(4, 4, true, new Triangle("test", 4, 4),null);

        boolean[][] testSpaces = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                testSpaces[i][j] = false;
            }
        }
        testSpaces[4][4] = true;

        assertTrue(Arrays.deepEquals(m.getSpaces(), testSpaces));
    }

    @Test
    public void setSpaces_LeftFlipper() {
        iModel m = new GizmoballModel();
        m.setSpaces(4, 4, true, new LeftFlipper("test", 4, 4),null);

        boolean[][] testSpaces = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                testSpaces[i][j] = false;
            }
        }
        testSpaces[4][4] = true;
        testSpaces[4][5] = true;
        testSpaces[5][4] = true;
        testSpaces[5][5] = true;


        assertTrue(Arrays.deepEquals(m.getSpaces(), testSpaces));
    }

    @Test
    public void setSpaces_RightFlipper() {
        iModel m = new GizmoballModel();
        m.setSpaces(4, 4, true, new RightFlipper("test", 4, 4),null);

        boolean[][] testSpaces = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                testSpaces[i][j] = false;
            }
        }
        testSpaces[4][4] = true;
        testSpaces[4][5] = true;
        testSpaces[3][4] = true;
        testSpaces[3][5] = true;


        assertTrue(Arrays.deepEquals(m.getSpaces(), testSpaces));
    }

    @Test
    public void setSpaces_Absorber() {
        iModel m = new GizmoballModel();
        m.setSpaces(4, 4, true, new Absorber("test", 4, 4, 6, 6),null);

        boolean[][] testSpaces = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                testSpaces[i][j] = false;
            }
        }

        testSpaces[4][4] = true;
        testSpaces[4][5] = true;
        testSpaces[5][4] = true;
        testSpaces[5][5] = true;


        assertTrue(Arrays.deepEquals(m.getSpaces(), testSpaces));
    }

    @Test
    public void checkSpaces_star() {
        iModel m = new GizmoballModel();
        Star test = new Star("test", 4, 4);
        assertTrue(m.checkSpace(4, 4, star));
        m.setSpaces(4, 4, true, test,null);
        assertFalse(m.checkSpace(4, 4, star));
    }

    @Test
    public void checkSpaces_flippers() {
        iModel m = new GizmoballModel();
        LeftFlipper lf = new LeftFlipper("test1", 4, 4);
        assertTrue(m.checkSpace(4, 4, lf));
        m.setSpaces(4, 4, true, lf,null);

        assertFalse(m.checkSpace(4, 4, lf));

        iModel m1 = new GizmoballModel();
        RightFlipper rf = new RightFlipper("test1", 4, 4);
        assertTrue(m1.checkSpace(4, 4, rf));
        m1.setSpaces(4, 4, true, rf,null);
        assertFalse(m1.checkSpace(4, 4, rf));
    }

    @Test
    public void checkSpaces_square() {
        iModel m = new GizmoballModel();
        Square s = new Square("test1", 5, 4);
        assertTrue(m.checkSpace(5, 4, s));
        m.setSpaces(5, 4, true, s,null);
        assertFalse(m.checkSpace(5, 4, s));
    }

    @Test
    public void checkSpaces_triangle() {
        iModel m = new GizmoballModel();
        Triangle s = new Triangle("test1", 5, 4);
        assertTrue(m.checkSpace(5, 4, s));
        m.setSpaces(5, 4, true, s,null);
        assertFalse(m.checkSpace(5, 4, s));
    }

    @Test
    public void checkSpaces_circle() {
        iModel m = new GizmoballModel();
        GizmoCircle s = new GizmoCircle("test1", 5, 4);
        assertTrue(m.checkSpace(5, 4, s));
        m.setSpaces(5, 4, true, s,null);
        assertFalse(m.checkSpace(5, 4, s));
    }

    @Test
    public void checkSpaces_absorber() {
        iModel m = new GizmoballModel();
        Absorber s = new Absorber("test1", 4, 4, 6, 6);
        assertTrue(m.checkSpace(4, 4, s));
        m.setSpaces(5, 4, true, s,null);
        assertFalse(m.checkSpace(4, 4, s));

        assertFalse(m.checkSpace(19, 19, new Absorber("A!", 19, 19, 22, 22)));
    }

    @Test
    public void collisionDetails() {

    }

    @Test(expected = IOException.class)
    public void getAudio_non_existant() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        iModel m = new GizmoballModel();
        m.getAudio("res/clips/jum.wav");
    }

    @Test
    public void saveGame() {

    }

    @Test
    public void loadGame() {

    }

    @Test
    public void checkConnections() {
        iModel m = new GizmoballModel();
        Ball b = new Ball("b1", 1, 2, 0, 20);
        Triangle t = new Triangle("t1", 1, 3);
        Triangle t2 = new Triangle("t2", 5, 5);


        m.addGizmo(t);
        m.addGizmo(t2);
        m.addBall(b);

        m.connectGizmos("t1","t2");
        m.connectGizmos("t1","t3");
        m.getBalls().get(1).setStopped(false);

        m.moveBall();

        assertTrue(m.getGizmoByID("t1").getHit());
        assertTrue(m.getGizmoByID("t2").getHit());
    }

    @Test
    public void checkKeyConnections(){
        GizmoballModel m = new GizmoballModel();
        RightFlipper rf  = new RightFlipper("RF1",1,2);
        Ball b = new Ball("b1", 1, 2, 0, 20);

        m.addBall(b);
        m.addGizmo(rf);

        assertTrue(m.keyConnectGizmo("RF1","f"));
        assertFalse(m.keyConnectGizmo("RFF1","f"));

        assertTrue(m.getKeyTriggers().get(rf) == 'f');

        m.checkKeyConnections(rf,b);
        assertFalse(((RightFlipper) m.getGizmoByID("RF1")).isMoving());

    }


    //TODO need to test equals method for gizmos and fix absorber test
/*
    @Test
    public void equals(){
        iGizmo triangle5= new Triangle("T5",4,5);
        iGizmo triangle6= new Triangle("T5",4,5);

        assertEquals(triangle5,triangle6);

    }*/
}
