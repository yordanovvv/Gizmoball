package Model;

import java.util.ArrayList;
import java.util.List;

public class GizmoballModel {

    Ball ball = new Ball(0,0,0,0);
    List<iGizmo> gizmos = new ArrayList<>();

    private void addBall(Ball ball){}

    private void addGizmo(iGizmo gizmo){}

    private List<iGizmo> getGizmos(){
        return null;
    }

    //TODO : Switch return type to CollisionDetails
    private void timeUNtilCollision(){
        //return null;
    }

}
