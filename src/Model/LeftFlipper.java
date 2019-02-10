package Model;

import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

public class LeftFlipper implements iGizmo {

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

    }

    @Override
    public int getRotationAngle() {
        return 0;
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
}
