package Controller.PlayListeners;

import Model.GizmoballModel;
import Model.iGizmo;
import Model.iModel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class FlipperKeyListener implements KeyListener {

    String rot;
    iModel model;
    ArrayList<Character> keys;
    ArrayList<iGizmo>  flipper;
    boolean isStopped, timerHasStarted = false,keypressed = false;
    private Timer timer;
    int index = 0, estimateTime = 60;
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
            if(flipper.get(index).getRotationAngle() == 90 | flipper.get(index).getRotationAngle() == 0)
                t.stop();
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!keypressed) {
            for (int index = 0; index < keys.size(); index++) {
                char key = keys.get(index);
                if (e.getKeyChar() == key) {
                    this.index = index;
                    keypressed=true;
                    triggerFlipper("UP");
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keypressed=false;
       for (int index = 0; index < keys.size(); index++) {
            char key = keys.get(index);
           if (e.getKeyChar() == key) {
               long gap = System.currentTimeMillis() - time;
               System.out.println(gap);
               if(gap <= 300) {
                   try {
                       Thread.sleep(120*10);
                   } catch (InterruptedException e1) {
                       e1.printStackTrace();
                   }
               }
               this.index = index;
               triggerFlipper("DOWN");
            }
            index++;
        }
    }

    public void setIsStopped(boolean isStopped){
        this.isStopped = isStopped;
    }

    public void triggerFlipper(String direction){
        if(!isStopped) {
            if (direction.equals("UP") | direction.equals("DOWN") ) {
                timerHasStarted = true;
                timer.start();
                timerHasStarted = false;
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



