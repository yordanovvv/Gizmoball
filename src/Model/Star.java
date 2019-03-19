package Model;

import physics.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;


public class Star implements iGizmo{

    private String ID;

    private Color colour;
    private int XCoord;
    private int YCoord;
    private int height, width;
    private ArrayList<Ball> balls;
    private ArrayList<LineSegment> lines;
    private ArrayList<Circle> circles;
    private ArrayList<String> gizmoConnections;
    private ArrayList<String> keyConnections;
    private int rotationCount;
    private final int constant = 30;

    private int rotationAngle = 0;
    private boolean tick = false;
    private boolean down = false, hit, stopped = true;

    private int
            polyPoint1_x ,polyPoint1_y ,
            polyPoint2_x, polyPoint2_y,
            polyPoint3_x,polyPoint3_y ,
            polyPoint4_x, polyPoint4_y ,
            polyPoint5_x, polyPoint5_y;

    public Star(String id, int x, int y){
        this.ID = id;
        this.XCoord = x;
        this.YCoord = y;
        this.hit = false;

        height = 2;
        width = 2;

        balls = new ArrayList<>();
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        gizmoConnections = new ArrayList<>();
        keyConnections = new ArrayList<>();
        rotationCount = 0;

        polyPoint1_x = XCoord*30+30;
        polyPoint1_y =  YCoord*30;
        polyPoint2_x =  XCoord*30;
        polyPoint2_y = YCoord*30+22;
        polyPoint3_x =  XCoord*30+12;
        polyPoint3_y = YCoord*30+56;
        polyPoint4_x = XCoord*30+48;
        polyPoint4_y = YCoord*30+56;
        polyPoint5_x =  XCoord*30+58;
        polyPoint5_y = YCoord*30+22;
        generateCircles();
        generateLines();


    }

    //--------------------------------------------------------
    //              CLASS SPECIFIC METHODS
    public ArrayList<LineSegment> getFeederLines(){
        return new ArrayList<>(Arrays.asList(new LineSegment[] {lines.get(0), lines.get(1)}));
    }

    private synchronized void updateLinePositions(){
        int rotationDegree = 18;

        Vect pivet = new Vect((1 + XCoord)*constant, (1 + YCoord)*constant);
        for (int i = 0; i < lines.size(); i++) {
            Angle rotation = new Angle(Math.toRadians(rotationDegree));
            LineSegment currentLine = lines.get(i);
            lines.set(i, Geometry.rotateAround(currentLine,pivet,rotation));
        }

        for (int i = 0; i < circles.size(); i++) {
            Angle rotation = new Angle(Math.toRadians(rotationDegree));
            Circle currentCircle = circles.get(i);
            circles.set(i, Geometry.rotateAround(currentCircle,pivet,rotation));
        }
    }

    public void stopRotation(){
        stopped = true;
    }

    public Boolean isRoating(){
        return stopped;
    }

    public void startStarRotation(){
        if(tick){
          //  rotate();
        }else {
            if (stopped) spinStar(200);
        }
    }

    public boolean isTick(){
        return tick;
    }

    public void setTick(boolean tick){
        this.tick = tick;
    }

    public void spinStarForTime(double timeTill){
        double formatted_tuc = timeTill*30;
        //rotate();
        long start = System.currentTimeMillis();
        Timer t = new Timer(200, e -> {
            Timer clone = (Timer) e.getSource();
            if(System.currentTimeMillis() - start >= formatted_tuc | stopped)clone.stop();
            rotate();
        });
    }

    private void spinStar(int delay){
        stopped = false;
        Timer t = new Timer(delay, e -> {
            Timer clone = (Timer) e.getSource();
            if(stopped)clone.stop();
            rotate();
        });
        t.start();
    }

    public Ball shootBallOut(){
        //this is where the star rotates for a random amount of times and shoots the ball out ;)
        if (!balls.isEmpty()) {
            final Ball[] fireBall = {balls.remove(0)};

            stopRotation();
            Vect shootUp = new Vect(10, -500);

            stopped = false;
            final int[] counter = {0};
            Consumer<Ball> callback = i-> fireBall[0] = i;

            Timer t = new Timer(50, e -> {
                Ball ball = fireBall[0];
                Timer clone = (Timer) e.getSource();
                if(stopped | counter[0] == 20){
                    clone.stop();
                    ball.setExactX((getXCoord())*30);
                    ball.setExactY((getYCoord())*30);
                    ball.setVelo(shootUp);
                    ball.setStopped(false);
                    callback.accept(ball);
                    stopRotation();
                    startStarRotation();
                }
                rotate();
                counter[0]++;
            });
            t.start();
            return fireBall[0];
            }
       // }
        return null;
    }

    public int getmiddleXCoord(){return (XCoord+1);}

    public int getmiddleYCoord(){return (YCoord+1);}

    public Vect getCenter(){
        return new Vect((1 + XCoord)*constant, (1 + YCoord)*constant);
    }

    //--------------------------------------------------------
    //               INTERFACE METHODS
    @Override
    public String getGizmoType() {
        return "Star";
    }

    @Override
    public void setID(String id) {
        this.ID = id;
    }

    @Override
    public void setXCoord(int x) {
        XCoord = x;
    }

    @Override
    public void setYCoord(int y) {
        YCoord = y;
    }

    @Override
    public void generateCircles() {


        Circle poly1 = new Circle(polyPoint1_x ,polyPoint1_y,0);
        Circle poly2 = new Circle(polyPoint2_x,polyPoint2_y,0);
        Circle poly3 = new Circle(polyPoint3_x,polyPoint3_y,0);
        Circle poly4 = new Circle(polyPoint4_x,polyPoint4_y,0);
        Circle poly5 = new Circle(polyPoint5_x,polyPoint5_y,0);

        Circle c1 = new Circle(polyPoint5_x-20,polyPoint2_y,1);
        Circle c2 = new Circle(polyPoint2_x+22,polyPoint2_y,1);
        Circle c3 = new Circle(polyPoint3_x+6,polyPoint3_y-20,1);
        Circle c4 = new Circle(polyPoint4_x-18,polyPoint4_y-12,1);
        Circle c5 = new Circle(polyPoint5_x-16,polyPoint5_y+13,1);
        Circle c6 = new Circle((XCoord+1)*30,(YCoord + 1)*30,15);

        circles.addAll(Arrays.asList(new Circle[]{poly1,poly2,poly3,poly4,poly5,c1,c2,c3,c4,c5,c6}));
    }

    @Override
    public void generateLines() {
        /*

            g2.drawLine(polyPoint1_x,polyPoint1_y,polyPoint5_x-20,polyPoint2_y);
        g2.drawLine(polyPoint5_x-20,polyPoint2_y,polyPoint5_x,polyPoint5_y);
         */
        LineSegment line1 = new LineSegment(polyPoint1_x,polyPoint1_y,polyPoint5_x-20,polyPoint2_y);
        LineSegment line2 = new LineSegment(polyPoint5_x-20,polyPoint2_y,polyPoint5_x,polyPoint5_y);
        LineSegment line3 = new LineSegment(polyPoint1_x,polyPoint1_y,polyPoint2_x+22,polyPoint2_y);
        LineSegment line4 = new LineSegment(polyPoint2_x+22,polyPoint2_y,polyPoint2_x,polyPoint2_y);
        LineSegment line5 = new LineSegment(polyPoint2_x,polyPoint2_y,polyPoint3_x+6,polyPoint3_y-20);
        LineSegment line6 = new LineSegment(polyPoint3_x+6,polyPoint3_y-20,polyPoint3_x,polyPoint3_y);
        LineSegment line7 = new LineSegment(polyPoint3_x,polyPoint3_y,polyPoint4_x-18,polyPoint4_y-12);
        LineSegment line8 = new LineSegment(polyPoint4_x-18,polyPoint4_y-12,polyPoint4_x,polyPoint4_y);
        LineSegment line9 = new LineSegment(polyPoint4_x,polyPoint4_y,polyPoint5_x-16,polyPoint5_y+13);
        LineSegment line10 = new LineSegment(polyPoint5_x-16,polyPoint5_y+13,polyPoint5_x,polyPoint5_y);
        LineSegment line11 = new LineSegment(polyPoint1_x,polyPoint1_y-1,polyPoint3_x,polyPoint3_y);
        LineSegment line12 = new LineSegment(polyPoint3_x,polyPoint3_y+1,polyPoint5_x,polyPoint5_y);//!!
        LineSegment line13 = new LineSegment(polyPoint5_x,polyPoint5_y+1,polyPoint2_x,polyPoint2_y+1);
        LineSegment line14 = new LineSegment(polyPoint2_x,polyPoint2_y,polyPoint4_x,polyPoint4_y);//!!
        LineSegment line15 = new LineSegment(polyPoint4_x,polyPoint4_y,polyPoint1_x,polyPoint1_y);

        lines.addAll(Arrays.asList(new LineSegment[]{line1,line2,line3,line4,line5,line6,line7,line8,line9,line10,line11,line12,line13,line14,line15}));
    }

    @Override
    public void setGizmoConnection(String id) {
        //cannot be connected
    }

    @Override
    public void setKeyConnection(String key) {
        //cannot be connected
    }

    @Override
    public synchronized void rotate() {
        int rotation = 18;
        rotationAngle+=rotation;
        rotationCount++;
        if(rotationAngle==360){rotationAngle=0;rotationCount=0; }

        updateLinePositions();
    }

    @Override
    public int getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void setHit(boolean hit) {
        this.hit = hit;
    }

    @Override
    public Color getColor() {
        if(hit){
            return new Color(183, 97, 255, 255);
        }else {
            return new Color(119, 0, 211, 255);
        }
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getXCoord() {
        return XCoord;
    }

    @Override
    public int getYCoord() {
        return YCoord;
    }

    @Override
    public boolean getHit() {
        return hit;
    }

    @Override
    public ArrayList<Circle> getCircles() {
        return circles;
    }

    @Override
    public ArrayList<LineSegment> getLines() {
        return lines;
    }

    @Override
    public ArrayList<String> getGizmoConnections() {
        return gizmoConnections;
    }

    @Override
    public ArrayList<String> getKeyConnections() {
        return keyConnections;
    }

    @Override
    public boolean removeGizmoConnection(String id) {
        return gizmoConnections.remove(id);
    }

    @Override
    public void removeKeyConnection() {
    //??
    }

    @Override
    public void addBall(Ball b) {
        balls.add(b);
    }

    @Override
    public int getRotationCount() {
        return rotationCount;
    }

    @Override
    public String toString() {
        return getGizmoType() + " " + getID() + " " + getXCoord() + " " + getYCoord();
    }

}
