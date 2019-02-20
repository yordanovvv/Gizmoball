package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

import Controller.PlayListeners.FlipperKeyListener;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class GizmoballModel extends Observable {

    private ArrayList<iGizmo> gizmos;
    private Wall walls;
    //TODO private List<Flipper>;
    private iGizmo absorber;

    private boolean absorberCollision = false;
    private double gravity = 25; //25L/sec^2

    public GizmoballModel() {

        gizmos = new ArrayList<iGizmo>();
        walls = new Wall(0, 0, 20, 20);

        RightFlipper rightFlipper = new RightFlipper("R1", 10, 10);

        gizmos.add(rightFlipper);

        FlipperKeyListener rightFlipListener = new FlipperKeyListener("right", this, 'r', rightFlipper);//remove this in the long run
    }


    private CollisionDetails timeUntilCollision() {
        //finding time until collision
        //if collision occurs, finding the new velo
        Vect newVelo = new Vect(0, 0);

        //find shortest time
        double shortestTime = Double.MAX_VALUE;
        double timeC = 0.0; //time until collision with a circle
        double timeL = 0.0; //time until collision with a line
        double timeW = 0.0; //                            wall

        CollisionDetails cd = new CollisionDetails(shortestTime, newVelo);
        return cd;
    }

    public ArrayList<iGizmo> getGizmos() {
        return gizmos;
    }

    public Wall getWalls() {
        return walls;
    }

}
