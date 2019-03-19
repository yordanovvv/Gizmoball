package Model;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public abstract class iModel extends Observable {

    boolean displayID=true;

    public abstract void setiGizmo(iGizmo gizmo);

    public abstract HashMap<iGizmo,Character> getKeyTriggers();

    public abstract ArrayList<iGizmo> getAllStars();

    public  abstract HashMap<iGizmo,String> getKeyOrientation();

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

    public abstract boolean checkSpace(int gridX, int gridY, iGizmo g);

    public abstract void wipeSpaces();

    public abstract void saveGame(File file) ;

    public abstract void loadGame(File file) ;

    public void addBall(Ball b) { }

    public abstract void getAudio(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException;


    public boolean displayID(){
        return displayID;
    }

    public void setDisplayID(Boolean display){
        displayID = display;
    }

    public abstract void setSpaces(int gridX, int gridY, boolean val, iGizmo g);



    public abstract boolean[][] getSpaces();

    public abstract iGizmo getGizmoByID(String id);

    public abstract boolean gizmoFound(String id);

    public abstract boolean connectGizmos(String id, String id2);

    public abstract boolean removeConnection(iGizmo gizmo, String id);

    public abstract boolean keyConnectGizmo(String id, String key);



    public abstract ArrayList<Timer> getActiveTimers();
    public abstract void updateActiveTimers(Timer t);

}
