package Model;

import physics.Circle;
import physics.LineSegment;
;
import java.awt.*;
import java.util.ArrayList;

public class Wall{

    private int XCoord;
    private int YCoord;
    private int XCoord2;
    private int YCoord2;
    private final double constant = 30;
    private boolean hit = false;


    private LineSegment bottomWall, topWall, leftWall, rightWall;
    ArrayList<LineSegment> walls = new ArrayList<>();
    ArrayList<String> connections = new ArrayList<>();


    public Wall(int XCoord, int YCoord, int XCoord2, int YCoord2) {
        this.XCoord = XCoord;
        this.YCoord = YCoord;
        this.XCoord2 = XCoord2;
        this.YCoord2 = YCoord2;

        setUpWalls();
    }

    public ArrayList<LineSegment> getWalls() {
        return walls;
    }

    private void setUpWalls() {
        bottomWall = new LineSegment(XCoord * constant, YCoord2 * constant, XCoord2 * constant, YCoord2 * constant);
        topWall = new LineSegment(XCoord * constant, YCoord * constant, XCoord2 * constant, YCoord * constant);
        leftWall = new LineSegment(XCoord * constant, YCoord * constant, XCoord * constant, YCoord2 * constant);
        rightWall = new LineSegment(XCoord2 * constant, YCoord * constant, XCoord2 * constant, YCoord2 * constant);

        walls.add(topWall);
        walls.add(bottomWall);
        walls.add(leftWall);
        walls.add(rightWall);
    }

    public int getXCoord() {
        return XCoord;
    }

    public int getYCoord() {
        return YCoord;
    }

    public int getXCoord2() {
        return XCoord2;
    }

    public int getYCoord2() {
        return YCoord2;
    }

    public String getGizmoType() {
        return "Wall";
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean getHit()
    {
        return hit;
    }

    public void addConnection(String id){
        connections.add(id);
    }
    public ArrayList<String> getConnections(){
            return connections;
    }
}
