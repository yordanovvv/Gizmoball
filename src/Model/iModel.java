package Model;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

public abstract class iModel extends Observable {
    public abstract void setiGizmo(iGizmo gizmo);

    public abstract ArrayList<Character> getKeys();

    public abstract ArrayList<iGizmo> getAllStars();

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

    public abstract void saveGame(File file) ;

    public abstract void loadGame(File file) ;

    public void addBall(Ball b) {
    }
}
