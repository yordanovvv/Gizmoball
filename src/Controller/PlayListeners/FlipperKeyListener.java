package Controller.PlayListeners;

import Model.GizmoballModel;
import Model.iGizmo;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlipperKeyListener implements KeyListener {

    String rot;
    GizmoballModel model;
    ArrayList<Character> keys;
    ArrayList<iGizmo>  flipper;
    boolean isStopped;
    private int counter ;
    Timer timer1;


    //todo fix me. I did a basic, do they move together (KIND OF THING). - Nell
    public FlipperKeyListener(String rot, GizmoballModel model, ArrayList<Character> key, ArrayList<iGizmo> flipper){
        this.rot = rot;
        this.model = model;
        this.keys = key;
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
        for (int index = 0; index < keys.size(); index++) {
            char key = keys.get(index);
            if (e.getKeyChar() == key) {
                triggerFlipper("UP",index);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        for (int index = 0; index < keys.size(); index++) {
            char key = keys.get(index);
            if (e.getKeyChar() == key) {
                triggerFlipper("DOWN",index);
            }
            index++;
        }
    }

    public void setIsStopped(boolean isStopped){
        this.isStopped = isStopped;
    }


    public void triggerFlipper(String direction, int index){
        if(!isStopped) {
            if (direction.equals("UP") && flipper.get(index).getRotationAngle() == 0) {
                counter = 0;
                moveFlipper(index);
            } else if (direction.equals("DOWN") && flipper.get(index).getRotationAngle() == 90) {
                counter = 0;
                moveFlipper(index);
            } else if (direction.equals("TICK")) {
                tickFlipper(index);
            } else if(direction.equals("RESET")) {
              //undefined
            }
            }
        }



    private void tickFlipper(int index){
        flipper.get(index).rotate();
        model.setiGizmo(flipper.get(index));
        model.hasChanged();
        model.notifyObservers();
    }


    private void moveFlipper(int index) {

        final Timer timer = new Timer(30 , e -> {
            tickFlipper(index);
            Timer t = (Timer) e.getSource();
            if (counter == 4) t.stop();
            counter++;
        });
        timer.start();

    }
}



