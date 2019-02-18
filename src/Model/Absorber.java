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
    private final double constant = 30 - .73;

    //Constructor for creating absorber
    public Absorber(String id, int XCoord, int YCoord, int XCoord2, int YCoord2) {
        this.ID = id;
        this.XCoord = XCoord;
        this.YCoord = YCoord;
        this.XCoord2 = XCoord2;
        this.YCoord2 = YCoord2;


        lines = new ArrayList<>();
        circles = new ArrayList<>();
        gizmoConnections = new ArrayList<>();
        keyConnections = new ArrayList<>();


        //Lines and circles make up the shape
        generateCircles();
        generateLines();

        //TODO not sure about the intergral stuff,ie height/width
        height = YCoord2 - YCoord;
        width = XCoord2 - XCoord;

        //Absorber may have many balls
        balls = new ArrayList<Ball>();
    }

    public ArrayList<Ball> getBalls() {
        return balls;

    }

    //TODO not sure how to link ball and absorber
    public Ball activateAbsorber() {
        if (!balls.isEmpty()) {
            Ball fireBall = balls.get(0);
            Vect shootUp = new Vect(0, -750);
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
    public void setID (String id){

        this.ID = id;
    }

    @Override
    public void setXCoord ( int x){

        this.XCoord = x;

    }

    @Override
    public void setYCoord ( int y){
        this.YCoord = y;

    }

    public void setXcoord2 ( int x2){
        this.XCoord2 = x2;
    }

    public void setYCoord2 ( int y2){
        this.YCoord2 = y2;
    }

    @Override
    public void setColour (Color colour){
        this.colour = colour;

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

        LineSegment topLine = new LineSegment(XCoord * constant, YCoord2 * constant, XCoord2 * constant, YCoord2 * constant);
        LineSegment bottomLine = new LineSegment(XCoord * constant, YCoord * constant, XCoord2 * constant, YCoord * constant);
        LineSegment leftLine = new LineSegment(XCoord * constant, YCoord * constant, XCoord * constant, YCoord2 * constant);
        LineSegment rightLine = new LineSegment(XCoord2 * constant, YCoord * constant, XCoord2 * constant, YCoord2 * constant);
        lines.add(topLine);
        lines.add(bottomLine);
        lines.add(leftLine);
        lines.add(rightLine);
    }


    @Override
    public void setGizmoConnection (String id){

        gizmoConnections.add(id);

    }

    @Override
    public void setKeyConnection (String key, String keyboard, String action){

        keyConnections.add(key + " " + keyboard + " " + action);
    }

    @Override
    public void rotate () {
        //empty as absorbers cant rotate
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

    public Color getColour () {
        return colour;
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
    public void removeGizmoConnection (String id){
        gizmoConnections.remove(id);
    }

    @Override
    public void removeKeyConnection () {}

    @Override
    public void addBall(Ball b) {
        balls.add(b);
    }
}