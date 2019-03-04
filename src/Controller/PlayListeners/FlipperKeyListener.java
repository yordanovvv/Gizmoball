package Controller.PlayListeners;

import Model.iGizmo;
import Model.iModel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlipperKeyListener implements KeyListener {

    String rot;
    iModel model;
    ArrayList<Character> keys;
    ArrayList<iGizmo>  flipper;
    boolean isStopped, runningTimer = false,keypressed = false;
    private Timer timer;
    int index = 0;
    long time = 0;

    //todo fix me. I did a basic, do they move together (KIND OF THING). - Nell
    public FlipperKeyListener(String rot, iModel model, ArrayList<Character> key, ArrayList<iGizmo> flipper){
        this.rot = rot;
        this.model = model;
        this.keys = key;
        this.flipper = flipper;
        this.isStopped = false;
        this.keypressed = false;

        this.timer = new Timer(30 , e -> {
            tickFlipper(index);
            Timer t = (Timer) e.getSource();
            if(flipper.get(index).getRotationAngle() == 90 | flipper.get(index).getRotationAngle() == 0 |  flipper.get(index).getRotationAngle() == -90) {
                runningTimer = false;
                t.stop();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!keypressed) {
            for (int i = 0; i < keys.size(); i++) {
                char key = keys.get(i);
                if (e.getKeyChar() == key) {
                    index = i;
                    keypressed=true;
                    time = System.currentTimeMillis();
                    triggerFlipper("UP");
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keypressed=false;
        for (int i = 0; i < keys.size(); i++) {
            char key = keys.get(i);
               if (e.getKeyChar() == key) {
                   long gap = System.currentTimeMillis() - time;
                   System.out.println(gap);
                   index = i;
                   if(gap <= 120 ) {  //here we have a fast tap
                       Runnable waiting_runnable = () -> {//wait till we have stopped running
                           int i1 = 0;
                           while(runningTimer){
                               if(i1 == 500){
                                   return;
                               }
                               try {
                                   Thread.sleep(30);
                                   System.out.println(":)");
                               } catch (InterruptedException e1) {
                                   e1.printStackTrace();
                               }
                               i1++;
                           }

                           if(flipper.get(index).getRotationAngle()!=0){
                               triggerFlipper("DOWN");
                           }
                       };

                       Thread waiting_thread = new Thread(waiting_runnable);
                       waiting_thread.start();

                   }else{
                       triggerFlipper("DOWN");
                   }
                }
            i++;
        }
    }

    public void setIsStopped(boolean isStopped){
        this.isStopped = isStopped;
    }

    public void triggerFlipper(String direction){
        if(!isStopped) {
            if (direction.equals("UP") | direction.equals("DOWN") ) {
                runningTimer = true;
                timer.start();
            } else if (direction.equals("TICK")) {
                tickFlipper(index);
            }
        }
    }

    private void tickFlipper(int index){
        flipper.get(index).rotate();
        model.setiGizmo(flipper.get(index));
        model.hasChanged();
        model.notifyObservers();
    }
}



