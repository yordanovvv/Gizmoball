package Model;

import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

/*Interface for the creation of all gizmo objects*/
public interface iGizmo {

    //Setters and getters

    void setID(String id);

    void setXCoord(int x);

    void setYCoord(int y);

    void setColour(Color colour);

    void generateCircles();

    void generateLines();

    void setGizmoConnection(String id);

    void setKeyConnection(String key, String action);

    void rotate();

    int getRotationAngle();

    String getID();

    int getXCoord();

    int getYCoord();

    ArrayList<LineSegment> getCircles();

    ArrayList<LineSegment> getLines();

    int getGizmoConnection();

    int getKeyConnection();

    void removeGizmoConnection();

    void removeKeyConnection();


}
