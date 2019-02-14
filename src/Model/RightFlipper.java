package Model;

import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

public class RightFlipper implements iGizmo {
    private int rotationAngle = 0;
    private String ID;

    private Color colour;
    private int XCoord;
    private int YCoord;
    private int height, width;
    private ArrayList<LineSegment> lines;
    private ArrayList<Circle> circles;
    private ArrayList<String> gizmoConnections;
    private ArrayList<String> keyConnections;
    private final double constant = 30;
    private boolean down = false;

    public RightFlipper(String id, int x, int y){
        this.ID = id;
        this.XCoord = x;
        this.YCoord = y;

        height = 2;
        width = 2;

        lines = new ArrayList<>();
        circles = new ArrayList<>();
        gizmoConnections = new ArrayList<>();
        keyConnections = new ArrayList<>();

        generateCircles();
        generateLines();
    }



    @Override
    public String getGizmoType() {
        return "RightFlipper";
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
       // the radius is in pixels as we are graphing it to the screen.
        Circle bigTopCircle = new Circle((XCoord+.5)*constant + 15,(YCoord)*constant,7.5);
        Circle bigBottomCircle = new Circle((XCoord+.5)*constant,(YCoord+.2)*constant+56,5);
        Circle topLeft;
        Circle topRight;
        Circle bottomLeft;
        Circle bottomRight;
        circles.add(bigTopCircle);
        circles.add(bigBottomCircle);
    }

    @Override
    public void generateLines() {

        LineSegment leftLine = new LineSegment((XCoord+.5)*constant,(YCoord + .166)*constant,(XCoord + 1) * constant,(YCoord + .166)*constant);
        LineSegment rightLine = new LineSegment((XCoord+.93)*constant,(YCoord + 1.86)*constant,(YCoord + .53)*constant,(YCoord + 1.86)*constant);

        //lines.add(leftLine);
       // lines.add(rightLine);
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
        int rotation = 5;
        if(rotationAngle==90 && down == false)
            down = true;
        else if(rotationAngle == 0)
            down = false;
        else if (down == true)
            rotation = -5;


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
}
