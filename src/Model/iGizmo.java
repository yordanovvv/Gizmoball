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

    void setColour(Color colour);

    void generateCircles();

    void generateLines();

    void setGizmoConnection(String id);

    void setKeyConnection(String key, String keyboard, String action);

    void rotate();

    int getRotationAngle();

    String getID();
    // ------------------------
    //todo check that these are okay.They are added for the absorber
    int getHeight();

    int getWidth();
    // ------------------------

    int getXCoord();

    int getYCoord();

    ArrayList<Circle> getCircles();

    ArrayList<LineSegment> getLines();

    ArrayList<String> getGizmoConnections();

    ArrayList<String> getKeyConnections();

    void removeGizmoConnection(String id);

    void removeKeyConnection();


    void addBall(Ball ball);
}
