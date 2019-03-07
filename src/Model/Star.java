package Model;

import physics.Angle;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Star implements iGizmo{

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
    private boolean down = false, hit, stopped = false;

    public Star(String id, int x, int y){
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
    public String getGizmoType() {
        return "Star";
    }

    @Override
    public void setID(String id) {
        this.ID = id;
    }

    @Override
    public void setXCoord(int x) {
        XCoord = x;
    }

    @Override
    public void setYCoord(int y) {
        YCoord = y;
    }

    @Override
    public void generateCircles() {
        int x = XCoord * 30;
        int y = YCoord * 30 ;

        int polyPoint1_x = x+30, polyPoint1_y = y;
        int polyPoint2_x = x, polyPoint2_y = y+22;
        int polyPoint3_x = x+12, polyPoint3_y = y+56;
        int polyPoint4_x =x+48, polyPoint4_y = y+56;
        int polyPoint5_x = x+58, polyPoint5_y = y+22;


        Circle poly1 = new Circle(polyPoint1_x,polyPoint1_y,1);
        Circle poly2 = new Circle(polyPoint2_x,polyPoint2_y,0);
        Circle poly3 = new Circle(polyPoint3_x,polyPoint3_y,0);
        Circle poly4 = new Circle(polyPoint4_x,polyPoint4_y,0);
        Circle poly5 = new Circle(polyPoint5_x,polyPoint5_y,1);

        circles.addAll(Arrays.asList(new Circle[]{poly1,poly2,poly3,poly4,poly5}));
    }

    @Override
    public void generateLines() {
        int x = XCoord * 30;
        int y = YCoord * 30 ;

        int polyPoint1_x = x+30, polyPoint1_y = y;
        int polyPoint2_x = x, polyPoint2_y = y+22;
        int polyPoint3_x = x+12, polyPoint3_y = y+56;
        int polyPoint4_x =x+48, polyPoint4_y = y+56;
        int polyPoint5_x = x+58, polyPoint5_y = y+22;

        LineSegment line1 = new LineSegment(polyPoint1_x,polyPoint1_y,polyPoint3_x,polyPoint3_y);
        LineSegment line2 = new LineSegment(polyPoint3_x,polyPoint3_y,polyPoint5_x,polyPoint5_y);
        LineSegment line3 = new LineSegment(polyPoint5_x,polyPoint5_y,polyPoint2_x,polyPoint2_y);
        LineSegment line4 = new LineSegment(polyPoint2_x,polyPoint2_y,polyPoint4_x,polyPoint4_y);
        LineSegment line5 = new LineSegment(polyPoint4_x,polyPoint4_y,polyPoint1_x,polyPoint1_y);

        lines.addAll(Arrays.asList(new LineSegment[]{line1,line2,line3,line4,line5}));
    }

    @Override
    public void setGizmoConnection(String id) {
        //cannot be connected
    }

    @Override
    public void setKeyConnection(String key) {
        //cannot be connected
    }

    private void updateLinePositions(){
        int rotationDegree = 18;

        Circle  center =new Circle((XCoord+1)*constant,(YCoord+1)*constant,0);
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

    public void stopRotation(){
        stopped = true;
    }

    public Boolean isRoating(){
        return stopped;
    }

    public void startStarRotation(){
        stopped = false;
        Timer t = new Timer(100, e -> {
            Timer clone = (Timer) e.getSource();
            if(stopped)clone.stop();
            rotate();
        });
        t.start();

    }

    @Override
    public void rotate() {
        int rotation = 18;
        rotationAngle+=rotation;
        rotationCount++;
        if(rotationAngle==360){rotationAngle=0;rotationCount=0; }


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
    public void setHit(boolean hit) {
        this.hit = hit;
    }

    @Override
    public Color getColor() {
        if(hit){
            return new Color(114, 57, 187, 255);
        }else {
            return new Color(143, 101, 187, 255);
        }
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
    //??
    }

    @Override
    public void removeKeyConnection() {
    //??
    }

    @Override
    public void addBall(Ball ball) {
        //not applicable
    }

    @Override
    public int getRotationCount() {
        return rotationCount;
    }

    @Override
    public String toString() {
        return getGizmoType() + " " + getID() + " " + getXCoord() + " " + getYCoord();
    }

}
