package Model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;
import java.util.ArrayList;

public class Absorber implements iGizmo {

    private String ID;
    private Color colour;
    private int XCoord;
    private int YCoord;
    private int XCoord2;
    private int YCoord2;
    private int height, width;
    private ArrayList<LineSegment> lines;
    private ArrayList<Circle> circles;
    private ArrayList<String> gizmoConnections;
    private ArrayList<String> keyConnections;
    private ArrayList<Ball> balls;
    private final int constant = 30;
    private boolean hit;
    //Constructor for creating absorber
    public Absorber(String id, int XCoord, int YCoord, int XCoord2, int YCoord2) {
        this.ID = id;
        this.XCoord = XCoord;
        this.YCoord = YCoord;
        this.XCoord2 = XCoord2;
        this.YCoord2 = YCoord2;
        this.hit = false;

        lines = new ArrayList<>();
        circles = new ArrayList<>();
        gizmoConnections = new ArrayList<>();
        keyConnections = new ArrayList<>();


        //Lines and circles make up the shape
        generateCircles();
        generateLines();


        height = YCoord2 - YCoord;
        width = XCoord2 - XCoord;

        //Absorber may have many balls
        balls = new ArrayList<Ball>();
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public Color getColor(){
        return new Color(170, 71, 144, 255);
    }

    public Ball activateAbsorber() {
        if (!balls.isEmpty()) {


            Ball fireBall = balls.remove(0);
            Vect shootUp = new Vect(0, -1275);//new Vect(new Angle(0, -180), 50*30);
            fireBall.setVelo(shootUp);
            fireBall.setStopped(false);

            return fireBall;
        }
        return null;
    }

    @Override
    public String getGizmoType () {
        return "Absorber";
    }

    @Override
    public String toString () {
        return getGizmoType() + " " + getID() + " " + getXCoord() + " " + getYCoord() + " " + getXCoord2() + " " + getYCoord2();
    }

    @Override
    public int getRotationCount() {
        return 0;
    }

    @Override
    public void setID (String id){
        this.ID = id;
    }

    @Override
    public void setXCoord (int x){
        this.XCoord = x;
    }

    @Override
    public void setYCoord ( int y){
        this.YCoord = y;
    }

    @Override
    public void setHit(boolean hit){
        this.hit = hit;
    }

    public void setXcoord2 ( int x2){
        this.XCoord2 = x2;
    }

    public void setYCoord2 ( int y2){
        this.YCoord2 = y2;
    }

    @Override
    public void generateCircles () {
        Circle topLeftCorner = new Circle(XCoord*constant, YCoord*constant, 0);
        Circle topRightCorner = new Circle(XCoord2*constant, YCoord2*constant, 0);
        Circle bottomLeftCorner = new Circle(XCoord*constant, YCoord2*constant, 0);
        Circle bottomRightCorner = new Circle(XCoord2*constant, YCoord*constant, 0);
        circles.add(topLeftCorner);
        circles.add(topRightCorner);
        circles.add(bottomLeftCorner);
        circles.add(bottomRightCorner);

    }

    @Override
    public void generateLines () {

        LineSegment topLine = new LineSegment(XCoord * constant, YCoord * constant, XCoord2 * constant, YCoord * constant);
        LineSegment bottomLine = new LineSegment(XCoord * constant, YCoord2 * constant, XCoord2 * constant, YCoord2 * constant);
        LineSegment leftLine = new LineSegment(XCoord * constant, YCoord * constant, XCoord * constant, YCoord2 * constant);
        LineSegment rightLine = new LineSegment(XCoord2 * constant, YCoord * constant, XCoord2 * constant, YCoord2 * constant);

        lines.add(topLine);
        lines.add(bottomLine);
        lines.add(leftLine);
        lines.add(rightLine);
    }

    @Override
    public void setGizmoConnection (String id){
        if(!gizmoConnections.contains(id)) {
            gizmoConnections.add(id);
        }
    }

    @Override
    public void setKeyConnection (String key){
        if(!keyConnections.contains(key)) {
            keyConnections.add(key);
        }
    }

    @Override
    public void rotate () {
        //empty as absorbers cant rotate}
    }


    @Override
    public int getRotationAngle () {
        return 0;
    }

    @Override
    public String getID () {
        return ID;
    }

    @Override
    public int getXCoord () {
        return XCoord;
    }

    @Override
    public int getYCoord () {
        return YCoord;
    }

    @Override
    public boolean getHit() {
        return hit;
    }

    public int getXCoord2 () {
        return XCoord2;
    }

    public int getYCoord2 () {
        return YCoord2;
    }


    public int getHeight () {
        return height;
    }

    @Override
    public int getWidth () {
        return width;
    }

    @Override
    public ArrayList<Circle> getCircles () {
        return circles;
    }

    @Override
    public ArrayList<LineSegment> getLines () {
        return lines;
    }

    @Override
    public ArrayList<String> getGizmoConnections () {
        return gizmoConnections;
    }

    @Override
    public ArrayList<String> getKeyConnections () {
        return keyConnections;
    }

    @Override
    public boolean removeGizmoConnection (String id){
        return gizmoConnections.remove(id);
    }

    @Override
    public void removeKeyConnection () {}

    @Override
    public void addBall(Ball b) {
        balls.add(b);
    }
}