package Model;

import physics.*;

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
    private int rotationCount;
    private boolean down = false;
    private boolean hit;

    public RightFlipper(String id, int x, int y){
        this.ID = id;
        this.XCoord = x;
        this.YCoord = y;
        this.hit = false;

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
    public void setHit(boolean hit){
        this.hit = hit;
    }

    @Override
    public Color getColor(){
        return new Color(170, 169, 50, 255);
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

    public void setColour(Color colour) {
        this.colour = colour;
    }

    @Override
    public void generateCircles() {
        //todo fix me
        //XCoord*constant + 20.9
       // the radius is in pixels as we are graphing it to the screen.

        Circle bigTopCircle = new Circle(XCoord*constant + 22.6,YCoord*constant + 7.5,7.4);
        Circle bigBottomCircle = new Circle(XCoord*constant + 22.6,YCoord*constant +7.51+47.5,6);
        Circle topLeft = new Circle(XCoord*constant + 30,YCoord*constant +7,0);
        Circle topRight = new Circle((XCoord + .9) * constant,YCoord*constant +7.5+48,0);
        Circle bottomLeft = new Circle(XCoord*constant + 17.5,YCoord*constant +7,0);
        Circle bottomRight = new Circle((XCoord) * constant + 15.8,YCoord*constant +7.5+48,0);

        circles.add(bigTopCircle);
        circles.add(bigBottomCircle);
        circles.add(topLeft);
        circles.add(topRight);
        circles.add(bottomLeft);
        circles.add(bottomRight);

    }

    @Override
    public void generateLines() {

        //todo fix me
        LineSegment rightL = new LineSegment(XCoord*constant + 30 ,YCoord*constant +7,(XCoord + .9) * constant   ,YCoord*constant +8+48);
        LineSegment leftL = new LineSegment(XCoord*constant + 17.5,YCoord*constant +7,(XCoord) * constant + 15.8 ,YCoord*constant +8+48);

        System.out.println();
        lines.add(rightL);
        lines.add(leftL);
    }

    @Override
    public void setGizmoConnection(String id) {
        gizmoConnections.add(id);
    }

    @Override
    public void setKeyConnection(String key) {

    }

    private void updateLinePositions(){
        int rotationDegree = 18;
        if(rotationAngle == 90) rotationDegree = +18;
        else if(rotationAngle ==  0) rotationDegree = -18;
        else if(down) rotationDegree = -rotationDegree;

        Circle  center = circles.get(0);
        for (int i = 0; i < lines.size(); i++) {
            Angle rotation = new Angle(Math.toRadians(rotationDegree));
            LineSegment currentLine = lines.get(i);
            lines.set(i,Geometry.rotateAround(currentLine,center.getCenter(),rotation));
        }

        for (int i = 0; i < circles.size(); i++) {
            Angle rotation = new Angle(Math.toRadians(rotationDegree));
            Circle currentCircle = circles.get(i);
            circles.set(i, Geometry.rotateAround(currentCircle,center.getCenter(),rotation));
        }
    }

    @Override
    public void rotate() {
        int rotation = 18;
        if(rotationAngle==72 && down == false)
            down = true;
        else if(rotationAngle == 0)
            down = false;
        else if (down == true)
            rotation = -rotation;
        rotationAngle+=rotation;
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
    public void removeGizmoConnection(String id) {
        gizmoConnections.remove(id);
    }

    @Override
    public void removeKeyConnection() {

    }

    @Override
    public void addBall(Ball ball) {

    }

    public boolean isMoving(){
        return (rotationAngle!=0 && rotationAngle!=90);
    }

    public int getRotationCount() {
        return rotationCount;
    }

    @Override
    public String toString() {
        return getGizmoType() + " " + getID() + " " + getXCoord() + " " + getYCoord();
    }
}
