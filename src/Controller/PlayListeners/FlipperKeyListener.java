package Controller.PlayListeners;

import Model.GizmoballModel;
import Model.iGizmo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FlipperKeyListener implements KeyListener {

    String rot;
    GizmoballModel model;
    char key;
    iGizmo flipper;
    boolean isStopped;
    private int counter ;
    Timer timer1;

    public FlipperKeyListener(String rot, GizmoballModel model, char key, iGizmo flipper){
        this.rot = rot;
        this.model = model;
        this.key = key;
        this.flipper = flipper;
        this.isStopped = false;

        counter = 0;

        //Stuff for flipper animation
     /*   ActionListener actListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                triggerFlipper();
            }
        };
        this.timer1=new Timer(50,actListener);
        timer1.start();*/

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == key ){
            triggerFlipper("UP");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == key) {
            triggerFlipper("DOWN");
        }
    }

    public void setIsStopped(boolean isStopped){
        this.isStopped = isStopped;
    }


    public void triggerFlipper(String direction){
        if(!isStopped) {
            if (direction.equals("UP") && flipper.getRotationAngle() == 0) {
                counter = 0;
                moveFlipper();
            } else if (direction.equals("DOWN") && flipper.getRotationAngle() == 90) {
                counter = 0;
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


    private void moveFlipper() {

        //todo check my math, i am dead -Nells
        final Timer timer = new Timer(50 , e -> {
            tickFlipper();
            Timer t = (Timer) e.getSource();
            if (counter == 4) t.stop();
            counter++;
        });
        timer.start();

    }
}



