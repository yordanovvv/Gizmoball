package Model;

import physics.LineSegment;
;
import java.util.ArrayList;

public class Wall {

    private int XCoord;
    private int YCoord;
    private int XCoord2;
    private int YCoord2;
    private final double constant = 30-.4;

    public Wall(int XCoord,int YCoord, int XCoord2, int YCoord2){
        this.XCoord=XCoord;
        this.YCoord=YCoord;
        this.XCoord2=XCoord2;
        this.YCoord2=YCoord2;

    }

    public ArrayList<LineSegment> drawWalls(){
        ArrayList<LineSegment> walls = new ArrayList<>();
        LineSegment bottomWall= new LineSegment(XCoord * constant,YCoord2* constant,XCoord2* constant,YCoord2* constant);
        LineSegment topWall= new LineSegment(XCoord* constant,YCoord* constant, XCoord2* constant,YCoord* constant);
        LineSegment leftWall= new LineSegment(XCoord* constant, YCoord* constant, XCoord* constant, YCoord2* constant);
        LineSegment rightWall= new LineSegment(XCoord2* constant, YCoord* constant, XCoord2* constant, YCoord2* constant);

        walls.add(topWall);
        walls.add(bottomWall);
        walls.add(leftWall);
        walls.add(rightWall);

        return walls;
    }
}
