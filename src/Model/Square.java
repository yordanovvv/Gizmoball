package Model;

import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

public class Square implements iGizmo {


    private String ID;
    private Color colour;
    private int XCoord;
    private int YCoord;
    private int height, width = 1;
    private ArrayList<LineSegment> lines;
    private ArrayList<Circle> circles;
    private ArrayList<String> gizmoConnections;
    private ArrayList<String> keyConnections;
    private int constant = 30;
    public int rotationCount;
    private boolean hit;

    //Constructor for creating squares

    public Square(String id, int XCoord, int YCoord) {
        this.ID = id;
        this.XCoord = XCoord;
        this.YCoord = YCoord;
        this.hit = false;

        lines = new ArrayList<>();
        circles = new ArrayList<>();
        gizmoConnections = new ArrayList<>();
        keyConnections = new ArrayList<>();

        rotationCount = 0;

        generateCircles();
        generateLines();


        colour = getColor();
    }

    @Override
    public void setHit(boolean hit){
        this.hit = hit;
    }

    @Override
    public boolean getHit() {return hit;}

    @Override
    public Color getColor(){
        Color nonHitColor = new Color(170, 10, 21, 255),
                hitColor = new Color(255, 0, 36, 255);

        if(hit) return hitColor;
        else return nonHitColor;
    }

    @Override
    public String getGizmoType() {
        return "Square";
    }

    @Override
    public void setID(String id) {

        this.ID = id;
    }

    @Override
    public void setXCoord(int x) {

        this.XCoord = x;

    }

    @Override
    public void setYCoord(int y) {

        this.YCoord = y;
    }

    @Override
    public void generateCircles() {

        Circle topLeftCorner = new Circle(XCoord* constant, YCoord* constant, 0);
        Circle topRightCorner = new Circle((XCoord + 1)* constant, (YCoord + 1)* constant, 0);
        Circle bottomLeftCorner = new Circle(XCoord* constant, (YCoord + 1)* constant, 0);
        Circle bottomRightCorner = new Circle((XCoord + 1)* constant, YCoord* constant, 0);
        circles.add(topLeftCorner);
        circles.add(topRightCorner);
        circles.add(bottomLeftCorner);
        circles.add(bottomRightCorner);


    }

    @Override
    public void generateLines() {

        LineSegment topLine = new LineSegment(XCoord * constant, YCoord * constant, (XCoord + 1) * constant, YCoord* constant);
        LineSegment bottomLine = new LineSegment(XCoord* constant, YCoord* constant, XCoord * constant, (YCoord + 1)* constant);
        LineSegment leftLine = new LineSegment(XCoord* constant, (YCoord + 1)* constant, (XCoord + 1)* constant, (YCoord + 1)* constant);
        LineSegment rightLine = new LineSegment((XCoord + 1)* constant, YCoord* constant, (XCoord + 1)* constant, (YCoord + 1)* constant);
        lines.add(topLine);
        lines.add(bottomLine);
        lines.add(leftLine);
        lines.add(rightLine);


    }

    @Override
    public void setGizmoConnection(String id) {

        gizmoConnections.add(id);
    }

    @Override
    public void setKeyConnection(String key) {

    }

    @Override
    public void rotate() {
//TODO guess this will be empty as square looks same rotated
    }

    @Override
    public int getRotationAngle() {
        return 0;
    }

    @Override
    public String getID() {
        return ID;
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

    }

    @Override
    public void addBall(Ball ball) {

    }


    public int getRotationCount() {
        return rotationCount;
    }


    @Override
    public String toString() {
        return getGizmoType() + " " + getID() + " " + getXCoord() + " " + getYCoord();
    }
}
