package Controller.PlayListeners;
import Model.iGizmo;
import Model.iModel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class FlipperKeyListener implements KeyListener {

    private iModel model;
    private Character keys;
    private iGizmo flipper;
    boolean isStopped;
    private Timer timer;
    private Long time;
    private Boolean keypressed;
    private Boolean runningTimer;
    private ThreadPoolExecutor executor ;

    public FlipperKeyListener(iModel model, Character key, iGizmo flipper){
        this.model = model;
        this.keys = key;
        this.flipper = flipper;
        this.isStopped = false;
        this.keypressed = false;
        this.runningTimer =false;
        this.time = Integer.toUnsignedLong(0);
        this.executor =(ThreadPoolExecutor) Executors.newFixedThreadPool(3);

        //timer event for flipper rotation
        this.timer = new Timer(13 , e -> {
            synchronized (this) {
                tickFlipper();
                Timer t = (Timer) e.getSource();
                if (flipper.getRotationAngle() == 90 | flipper.getRotationAngle() == 0 | flipper.getRotationAngle() == -90) {
                    runningTimer = false;
                    t.stop();
                }
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!keypressed) { //if key not pressed
                char key = keys;
                if (e.getKeyChar() == key) { //matches character
                    int finalI = 0;
                    Runnable r = () -> key_press_code(finalI);
                    executor.execute(r);
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keypressed = false;// longer pressed
            char key = keys;
            if (e.getKeyChar() == key) { //keys match
                   int finalI = 0;
                   Runnable r = () -> key_release_code(finalI);
                   executor.execute(r);
            }
    }

    private void key_press_code(int i){
        keypressed = true;
        if(flipper.getRotationAngle()==0){
            time = System.currentTimeMillis();
            triggerFlipper("UP");
        }
    }

    private void  key_release_code(int i){
        long gap = System.currentTimeMillis() - time;
        if(gap <= 250) {  //here we have a fast tap
            Runnable waiting_runnable = () -> {//wait till we have stopped running
                synchronized (time) {
                    int i1 = 0;
                    while (runningTimer) {
                        if (i1 == 500) { //infinite loop protection
                            return;
                        }
                        try {
                            Thread.sleep(13);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        i1++;
                    }
                    //if in the duration of the thread waiting, we have already reached 0, do nothing
                    if (flipper.getRotationAngle() != 0) {
                        triggerFlipper("DOWN");
                    }
                }
            };
            executor.execute(waiting_runnable);
        }else{
            if(flipper.getRotationAngle()!=0) {
                triggerFlipper("DOWN");
            }
        }
    }

    //set the whole thing to be stopped
    public void setIsStopped(boolean isStopped){
        this.isStopped = isStopped;
    }

    //tick
    public void tick(){
        triggerFlipper("TICK");
    }

    //where the flipper is moved
    public synchronized void triggerFlipper(String direction){
        if(!isStopped) {
            if(direction.equals("UP") | direction.equals("DOWN") ) {
                runningTimer = true;
               timer.start();
            }else if (direction.equals("TICK")) {
                tickFlipper();
            }
        }
    }

    //one tick of the flipper
    private synchronized void tickFlipper(){
        flipper.rotate();
        model.setiGizmo(flipper);
        model.hasChanged();
        model.notifyObservers();
    }
}



