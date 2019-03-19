import Model.*;
import org.junit.Before;
import org.junit.Test;

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

        iGizmo circle3 = new GizmoCircle("C1", 3, 5, 2);

        assertEquals(circle3.getID(), circle.getID());
        model.setiGizmo(circle3);

    }

    @Test
    public void removeConnetions() {

        iGizmo circle5 = new GizmoCircle("C5", 3, 4, 2);
        iGizmo square5 = new Square("S5", 6, 7);

        model.addGizmo(circle5);
        model.addGizmo(square5);

        circle5.setGizmoConnection("S5");
        assertTrue(model.removeConnection(circle5, "S5"));
    }



    @Test
    public void moveBall() {
        GizmoballModel m = new GizmoballModel();
        Ball test1 = new Ball("test1",1,0,0,7);
        test1.setStopped(false);
        m.addBall(test1);
        m.moveBall();

        assertEquals(12.375,m.getBalls().get(1).getExactY(),3);
        assertEquals(30.0,m.getBalls().get(1).getExactX(),3);
    }

    @Test
    public void moveBallWithCollisions(){
        GizmoballModel m = new GizmoballModel();
        Ball test1 = new Ball("test1",1,0,0,7);
        test1.setStopped(false);
        m.addBall(test1);


        iGizmo c1 = new GizmoCircle("C1", 2, 0,2);
        iGizmo c2 = new GizmoCircle("c2", 2, 1,2);
        iGizmo c3 = new GizmoCircle("c3", 2, 2,2);
        iGizmo s1 = new Square("s1",0,2);
        iGizmo t1 = new Triangle("t1",1,2);
        m.addGizmo(c1);
        m.addGizmo(c2);
        m.addGizmo(c3);
        m.addGizmo(t1);
        m.addGizmo(s1);

        for (int i = 0; i < 5; i++) {
            m.moveBall();
        }

        assertEquals(34.875,m.getBalls().get(1).getExactY(),3);
        assertEquals(30.0,m.getBalls().get(1).getExactX(),3);
    }

    @Test
    public void moveBallWithAbsorber(){
        GizmoballModel m = new GizmoballModel();
        Ball test1 = new Ball("test1",1,0,0,7);
        test1.setStopped(false);
        m.addBall(test1);

        Absorber a1 = new Absorber("a1",0,2,5,5);
        m.addGizmo(a1);

        for (int i = 0; i < 5; i++) {
            m.moveBall();
        }

        assertEquals(1,m.getAbsorber().getBalls().size());
        assertEquals(585.0,m.getBalls().get(1).getExactY(),3);
        assertEquals(585.0,m.getBalls().get(1).getExactX(),3);

    }


    @Test
    public void moveBallForTime() {
        GizmoballModel m = new GizmoballModel();
        Ball test1 = new Ball("test1",1,0,0,7);
        m.addBall(test1);

        test1 =m.moveBallForTime(test1,1);

        assertEquals(210,test1.getExactY(),3);
        assertEquals(30,test1.getExactX(),3);
    }

    @Test
    public void moveStarForTime() {

    }

    @Test
    public void wipeSpaces() {

    }

    @Test
    public void setSpaces(){

    }

    @Test
    public void checkSpaces(){

    }

    @Test
    public void collisionDetails(){

    }

    @Test
    public void getAudio(){

    }

    @Test
    public void saveGame(){

    }

    @Test
    public void loadGame(){

    }

    @Test
    public void checkConnections(){

    }

    @Test
    public void checkKeyConnections(){

    }


    //TODO need to test equals method for gizmos and fix absorber test
/*
    @Test
    public void equals(){
        iGizmo triangle5= new Triangle("T5",4,5);
        iGizmo triangle6= new Triangle("T5",4,5);

        assertEquals(triangle5,triangle6);

    }


     //this doesnt work??
    /*
    @Test
    public void getAbsorber(){
        Absorber test = model.getAbsorber();
        assertEquals(test,absorber);
    }


    */


}
