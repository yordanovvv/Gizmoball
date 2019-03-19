package Model;

import physics.Angle;
import physics.Circle;
import physics.Geometry;
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
    private boolean down = false, hit;


    public LeftFlipper(String id, int x, int y){
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
        return new Color(0, 0, 0, 255);
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

    public void setColour(Color colour) {
            this.colour = colour;
    }

    @Override
    public void generateCircles() {

        //todo fix me
        Circle bigTopCircle = new Circle(XCoord*constant + 7,YCoord*constant+ 8,8);
        Circle bigBottomCircle = new Circle(XCoord*constant + 6.9,(YCoord+2)*constant - 6,7);
        Circle topLeft  = new Circle(XCoord*constant + 17, YCoord*constant + 8,0);
        Circle topRight  = new Circle(XCoord * constant + 10,(YCoord + 2)*constant + 6,0);
        Circle bottomLeft = new Circle((XCoord)*constant + 3,YCoord*constant -8,0);
        Circle bottomRight = new Circle(XCoord * constant - 1,(YCoord + 2)*constant + 6,0);

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
        LineSegment rightL = new LineSegment((XCoord)*constant + 7 + 8 + 2,YCoord*constant +8,XCoord * constant + 1 + 7 + 2,(YCoord + 2)*constant + 6);
        LineSegment leftL = new LineSegment((XCoord)*constant + 7 - 8 + 4,YCoord*constant -8,XCoord * constant + 1 - 7 + 5,(YCoord + 2)*constant + 6);

         lines.add(rightL);
         lines.add(leftL);
    }

    @Override
    public void setGizmoConnection(String id) {
        if(!gizmoConnections.contains(id)) {
            gizmoConnections.add(id);
        }
    }

    @Override
    public void setKeyConnection(String key) {
        if(!keyConnections.contains(key)) {
            keyConnections.add(key);
        }
    }

    private void updateLinePositions(){
       int rotationDegree = -18;
        if(rotationAngle == -90) rotationDegree = -18;
        else if(rotationAngle ==  0) rotationDegree = 18;
        else if(down) rotationDegree = -rotationDegree;

        Circle  center = circles.get(0);
        for (int i = 0; i < lines.size(); i++) {
            Angle rotation = new Angle(Math.toRadians(rotationDegree));
            LineSegment currentLine = lines.get(i);
            lines.set(i, Geometry.rotateAround(currentLine,center.getCenter(),rotation));
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
        if(rotationAngle==-72 && down == false)
            down = true;
        else if(rotationAngle == 0)
            down = false;
        else if (down == true)
            rotation = -rotation;

        rotationAngle-=rotation;
        updateLinePositions();
    }

    public boolean isMoving(){
        return (rotationAngle!=0 && rotationAngle!=-90);
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
