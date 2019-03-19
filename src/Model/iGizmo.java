package Model;

import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

/*Interface for the creation of all gizmo objects*/
public interface iGizmo {

    //Setters and getters

    String getGizmoType();

    void setID(String id);

    void setXCoord(int x);

    void setYCoord(int y);

    void generateCircles();

    void generateLines();

    void setGizmoConnection(String id);

    void setKeyConnection(String key);

    void rotate();

    int getRotationAngle();

    String getID();


    void setHit(boolean hit);

    Color getColor();

    int getHeight();

    int getWidth();


    int getXCoord();

    int getYCoord();

    boolean getHit();
    ArrayList<Circle> getCircles();

    ArrayList<LineSegment> getLines();

    ArrayList<String> getGizmoConnections();

    ArrayList<String> getKeyConnections();

    boolean removeGizmoConnection(String id);

    void removeKeyConnection();

    void addBall(Ball ball);

    String toString();


    int getRotationCount();

}
