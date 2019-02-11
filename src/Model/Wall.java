package Model;

import physics.LineSegment;
;
import java.util.ArrayList;

public class Wall {

    private int XCoord;
    private int YCoord;
    private int XCoord2;
    private int YCoord2;

    public Wall(int XCoord,int YCoord, int XCoord2, int YCoord2){
        this.XCoord=XCoord;
        this.YCoord=YCoord;
        this.XCoord2=XCoord2;
        this.YCoord2=YCoord2;

    }

    public ArrayList<LineSegment> drawWalls(){
        ArrayList<LineSegment> walls = new ArrayList<>();
        LineSegment topWall= new LineSegment(XCoord,YCoord2,XCoord2,YCoord2);
        LineSegment bottomWall= new LineSegment(XCoord,YCoord, XCoord2,YCoord);
        LineSegment leftWall= new LineSegment(XCoord, YCoord, XCoord, YCoord2);
        LineSegment rightWall= new LineSegment(XCoord2, YCoord, XCoord2, YCoord2);

        walls.add(topWall);
        walls.add(bottomWall);
        walls.add(leftWall);
        walls.add(rightWall);

        return walls;
    }
}
