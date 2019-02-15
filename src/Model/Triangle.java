package Model;

import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

public class Triangle implements iGizmo {

    private String ID;
    private Color colour;
    private int XCoord;
    private int YCoord;
    private int height, width;
    private ArrayList<LineSegment> lines;
    private ArrayList<Circle> circles;
    private ArrayList<String> gizmoConnections;
    private ArrayList<String> keyConnections;
    private final double constant = 30-.73;


    public Triangle(String id, int XCoord, int YCoord) {
        this.ID = id;
        this.XCoord = XCoord;
        this.YCoord = YCoord;


        lines = new ArrayList<>();
        circles = new ArrayList<>();
        gizmoConnections = new ArrayList<>();
        keyConnections = new ArrayList<>();

        generateCircles();
        generateLines();

    }

    @Override
    public String getGizmoType() {
        return "Triangle";
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
    public void setColour(Color colour) {
        this.colour = colour;
    }

    @Override
    public void generateCircles() {

        Circle top = new Circle(XCoord*constant, YCoord*constant, 0);
        Circle right = new Circle((XCoord + 1)*constant, YCoord*constant, 0);
        Circle bottomRight = new Circle((XCoord+1)*constant, (YCoord + 1)*constant, 0);

        circles.add(top);
        circles.add(right);
        circles.add(bottomRight);

    }

    @Override
    public void generateLines() {
        LineSegment line2 = new LineSegment(XCoord*constant, YCoord*constant , (XCoord + 1)*constant, (YCoord + 1)*constant);
        LineSegment line1 = new LineSegment(XCoord*constant, YCoord*constant, (XCoord+1)*constant, (YCoord )*constant);
        LineSegment line3 = new LineSegment((XCoord+1)*constant, (YCoord)*constant, (XCoord + 1)*constant, (YCoord+1)*constant);

        lines.add(line1);
        lines.add(line2);
        lines.add(line3);

    }

    @Override
    public void setGizmoConnection(String id) {
        gizmoConnections.add(id);

    }

    @Override
    public void setKeyConnection(String key, String keyboard, String action) {

    }


    //TODO needs done as triangles can be roated
    @Override
    public void rotate() {

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
    public void removeGizmoConnection(String id) {
        gizmoConnections.remove(id);
    }

    @Override
    public void removeKeyConnection() {

    }

    @Override
    public void addBall(Ball ball) {

    }

    @Override
    public String toString() {
        return getGizmoType() + " " + getID() + " " + getXCoord() + " " + getYCoord();
    }
}
