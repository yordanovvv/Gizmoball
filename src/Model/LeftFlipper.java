package Model;

import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

public class LeftFlipper implements iGizmo {
    private String id;

    private Color colour;
    private int XCoord;
    private int YCoord;
    private int height, width;
    private ArrayList<LineSegment> lines;
    private ArrayList<Circle> circles;
    private ArrayList<String> gizmoConnections;
    private ArrayList<String> keyConnections;
    private final double constant = 30-.73;

    private int rotationAngle = 0;
    private boolean down = false;


    @Override
    public String getGizmoType() {
        return "LeftFlipper";
    }

    @Override
    public void setID(String id) {

    }

    @Override
    public void setXCoord(int x) {

    }

    @Override
    public void setYCoord(int y) {

    }

    @Override
    public void setColour(Color colour) {

    }

    @Override
    public void generateCircles() {

    }

    @Override
    public void generateLines() {

    }

    @Override
    public void setGizmoConnection(String id) {

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
        return null;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getXCoord() {
        return 0;
    }

    @Override
    public int getYCoord() {
        return 0;
    }

    @Override
    public ArrayList<Circle> getCircles() {
        return null;
    }

    @Override
    public ArrayList<LineSegment> getLines() {
        return null;
    }

    @Override
    public ArrayList<String> getGizmoConnections() {
        return null;
    }

    @Override
    public ArrayList<String> getKeyConnections() {
        return null;
    }

    @Override
    public void removeGizmoConnection(String id) {

    }

    @Override
    public void removeKeyConnection() {

    }

    @Override
    public void addBall(Ball ball) {

    }
}
