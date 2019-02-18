package Controller.PlayListeners;

import Model.GizmoballModel;
import Model.iGizmo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FlipperKeyListener implements KeyListener {

    String rot;
    GizmoballModel model;
    char key;
    iGizmo flipper;
    boolean isStopped;

    public FlipperKeyListener(String rot, GizmoballModel model, char key, iGizmo flipper){
        this.rot = rot;
        this.model = model;
        this.key = key;
        this.flipper = flipper;
        this.isStopped = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == key ){
            moveFlipper("UP");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == key) {
            moveFlipper("DOWN");
        }
    }

    public void setIsStopped(boolean isStopped){
        this.isStopped = isStopped;
    }


    public void moveFlipper(String direction){
        if(!isStopped) {
            if (direction.equals("UP") && flipper.getRotationAngle() == 0) {
                moveFlipper();
            } else if (direction.equals("DOWN") && flipper.getRotationAngle() == 90) {
                moveFlipper();
            } else if (direction.equals("TICK")) {
                tickFlipper();
            } else if(direction.equals("RESET")) {
              //undefined
                }
            }
        }

    private void tickFlipper(){
        flipper.rotate();
        model.hasChanged();
        model.notifyObservers();
    }

    private void moveFlipper(){
        for (int i = 0; i <5; i++) {
            tickFlipper();
            try {
                Thread.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
