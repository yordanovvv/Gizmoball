package Controller.MainFrameListeners;


import Model.Absorber;
import Model.GizmoballModel;
import Model.iGizmo;
import View.MainFrame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//TODO : Refactor this to absorberListener or something?
public class KeyListenerTest implements KeyListener {

    //TODO Link with connections

    GizmoballModel model;
    char key;
    iGizmo giz;

    public KeyListenerTest(String id, GizmoballModel model, char key, iGizmo giz){
        System.out.println("Init KeyListener for gizmo id : " + id);

        this.model = model;
        this.key = key;
        this.giz = giz;

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key : " + e.getKeyChar() + " pressed.");

        if (e.getKeyChar() == key ){
            System.out.println("Absorber fire!");
            //Trigger the absorber !
            //model.getAbsorber().activateAbsorber();
            model.getBall().setVelo(model.getAbsorber().activateAbsorber());
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    //TODO Link connections (flippers and what not)

}
