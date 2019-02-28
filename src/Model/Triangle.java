package Model;

import physics.Angle;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

public class Triangle implements iGizmo {

    private String ID;
    private Color colour;
    private int XCoord;
    private int YCoord;
    private int height, width;
    private int rotationCount; //rotation count is set to 0 initially
    private ArrayList<LineSegment> lines;
    private ArrayList<Circle> circles;
    private ArrayList<String> gizmoConnections;
    private ArrayList<String> keyConnections;
    private final double constant = 30;
    private int rotationAngle;
    private boolean hit;


    public Triangle(String id, int XCoord, int YCoord) {
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

    }

    @Override
    public void setHit(boolean hit){
        this.hit = hit;
    }

    @Override
    public Color getColor(){
        Color nonHitColor =  new Color(59, 112, 170, 255),
                hitColor = new Color(3, 148, 255, 255);
        if(hit) return hitColor;
        else return nonHitColor;
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
        LineSegment line1 = new LineSegment(XCoord*constant, YCoord*constant, (XCoord+1)*constant, (YCoord )*constant);
        LineSegment line2 = new LineSegment(XCoord*constant, YCoord*constant , (XCoord + 1)*constant, (YCoord + 1)*constant);
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

    @Override
    public void rotate() {
        int rotationDegree = 90;
        rotationCount++;
        if(rotationCount > 4) rotationCount = 1;

        rotationAngle  = rotationDegree * rotationCount;
        //ransform.rotate(Math.toRadians(angle), x + (30/2), y + (30/2));
        Circle  center = new Circle((XCoord*constant+15), (YCoord*constant+15),0); //make a circle that fills that set square and get its center

        if(rotationCount!=0) {
            System.out.println(center.getCenter().x());
            System.out.println(center.getCenter().y());
            for (int i = 0; i < lines.size(); i++) {
                Angle rotation = new Angle(Math.toRadians(rotationDegree));
                LineSegment currentLine = lines.get(i);
                LineSegment l = Geometry.rotateAround(currentLine, center.getCenter(), rotation);
                lines.set(i, l);
            }

            for (int i = 0; i < circles.size(); i++) {
                Angle rotation = new Angle(Math.toRadians(rotationDegree));
                Circle currentCircle = circles.get(i);
                circles.set(i, Geometry.rotateAround(currentCircle, center.getCenter(), rotation));
            }
        }else{

        }
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


    @Override
    public String toString() {
        return getGizmoType() + " " + getID() + " " + getXCoord() + " " + getYCoord();
    }

    public int getRotationCount() {
        return rotationCount;
    }
}
