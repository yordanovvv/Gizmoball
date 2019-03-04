package Model;

import java.util.ArrayList;
import java.util.Observable;

public abstract class iModel extends Observable {
    public abstract void setiGizmo(iGizmo gizmo);

    public abstract ArrayList<Character> getKeys();

    public abstract ArrayList<iGizmo> getFlippers();

    public abstract void moveBall() ;

    public abstract Ball moveBallForTime(Ball ball, double time) ;

    public abstract void addGizmo(iGizmo gizmo);

    //craig testing stuff
    public abstract void removeGizmo(iGizmo gizmo);

    public abstract ArrayList<Ball> getBalls();

    public abstract ArrayList<iGizmo> getGizmos();

    public abstract Wall getWalls();

    public abstract void setBallSpeed(Ball b, int x, int y) ;

    public abstract double getBallSpeed(Ball b);

    public abstract Absorber getAbsorber();

    public abstract void saveGame() ;

    public abstract void loadGame() ;

    public void addBall(Ball b) {
    }
}
