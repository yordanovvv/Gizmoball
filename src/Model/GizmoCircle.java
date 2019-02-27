package Model;

import physics.LineSegment;
import physics.*;

import java.awt.*;
import java.util.ArrayList;

public class GizmoCircle implements iGizmo {

    private String ID;
    private Color colour;
    private int XCoord;
    private int YCoord;
    private double radius;
    private ArrayList<Circle> circles;
    private ArrayList<LineSegment> lines;
    private ArrayList<String> gizmoConnections;
    private ArrayList<String> keyConnections;
    private final double constant = 30;
    private int rotationCount;
    private boolean hit;


    public GizmoCircle(String id, int XCoord, int YCoord, double radius){
        this.ID=id;
        this.XCoord=XCoord;
        this.YCoord=YCoord;
        this.radius=radius;
        this.hit = false;

        gizmoConnections = new ArrayList<>();
        keyConnections = new ArrayList<>();
        rotationCount = 0;

        circles = new ArrayList<>();
        lines=new ArrayList<>();
        generateCircles();

    }

    @Override
    public void setHit(boolean hit){
        this.hit = hit;
    }

    @Override
    public Color getColor(){
        Color hitColor =  new Color(80, 170, 44, 255),
                nonHitColor =  new Color(98, 209, 54, 255);
        if(hit) return hitColor;
        else return nonHitColor;
    }

    @Override
    public String getGizmoType() {
        return "Circle";
    }

    @Override
    public void setID(String id) {

        this.ID=id;
    }

    @Override
    public void setXCoord(int x) {
        this.XCoord=XCoord;

    }

    @Override
    public void setYCoord(int y) {

        this.YCoord=YCoord;
    }
    @Override
    public void generateCircles() {
        Circle circle= new Circle((XCoord + radius)*constant,(YCoord+radius)*constant,radius*constant);
        circles.add(circle);
    }

    @Override
    public void generateLines() {
        //not needed for circle

    }

    @Override
    public void setGizmoConnection(String id) {

    }

    @Override
    public void setKeyConnection(String key, String keyboard, String action) {

    }

    @Override
    public void rotate() {
        rotationCount++;
        if(rotationCount > 3) rotationCount = 0;
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
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
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

    public int getRotationCount() {
        return rotationCount;
    }

    @Override
    public String toString() {
        return getGizmoType() + " " + getID() + " " + getXCoord() + " " + getYCoord() + " " + radius;
    }
}
