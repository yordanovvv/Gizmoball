package Model;

import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

public class LeftFlipper implements iGizmo {
    private String ID;

    private Color colour;
    private int XCoord;
    private int YCoord;
    private int height, width;
    private ArrayList<LineSegment> lines;
    private ArrayList<Circle> circles;
    private ArrayList<String> gizmoConnections;
    private ArrayList<String> keyConnections;
    private int rotationCount;
    private final double constant = 30;

    private int rotationAngle = 0;
    private boolean down = false;


    public LeftFlipper(String id, int x, int y){
        this.ID = id;
        this.XCoord = x;
        this.YCoord = y;

        height = 2;
        width = 2;

        lines = new ArrayList<>();
        circles = new ArrayList<>();
        gizmoConnections = new ArrayList<>();
        keyConnections = new ArrayList<>();
        rotationCount = 0;

        generateCircles();
        generateLines();
    }



    @Override
    public String getGizmoType() {
        return "LeftFlipper";
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

        //todo fix me
        Circle bigTopCircle = new Circle(XCoord*constant + 20.91,YCoord*constant +7.51,7);
        Circle bigBottomCircle = new Circle(XCoord*constant + 20,YCoord*constant +7.5+48,6);
        Circle topLeft;
        Circle topRight;
        Circle bottomLeft;
        Circle bottomRight;
        circles.add(bigTopCircle);
        circles.add(bigBottomCircle);
    }

    @Override
    public void generateLines() {
        //todo fix me
        LineSegment rightL = new LineSegment((XCoord+.9)*constant,YCoord*constant +7.51,(XCoord + .9) * constant,YCoord*constant +7.5+48);
        LineSegment leftL = new LineSegment((XCoord+.516)*constant,YCoord*constant +7.51,(YCoord + .6)*constant,YCoord*constant +7.5+48);

        // lines.add(rightL);
        //lines.add(leftL);
    }

    @Override
    public void setGizmoConnection(String id) {
        gizmoConnections.add(id);
    }

    @Override
    public void setKeyConnection(String key, String keyboard, String action) {

    }

    @Override
    public void rotate() {
        int rotation = -18;
        if(rotationAngle==-72 && down == false)
            down = true;
        else if(rotationAngle == 0)
            down = false;
        else if (down == true)
            rotation = -rotation;


        rotationAngle+=rotation;
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

    public int getRotationCount() {
        return rotationCount;
    }

    @Override
    public String toString() {
        return getGizmoType() + " " + getID() + " " + getXCoord() + " " + getYCoord();
    }
}
