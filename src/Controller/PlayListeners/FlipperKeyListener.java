package Controller.PlayListeners;

import Model.GizmoballModel;
import Model.LeftFlipper;
import Model.iGizmo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FlipperKeyListener implements KeyListener {

    String rot;
    GizmoballModel model;
    char key;
    iGizmo flipper;

    public FlipperKeyListener(String rot, GizmoballModel model, char key, iGizmo flipper){
        this.rot = rot;
        this.model = model;
        this.key = key;
        this.flipper = flipper;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == key ){
            System.out.println("Flipper flip!");
            //flipper.flip();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
