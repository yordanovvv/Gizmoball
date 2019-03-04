package Controller.PlayListeners;
import Model.iGizmo;
import Model.iModel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FlipperKeyListener implements KeyListener {

    private String rot;
    private iModel model;
    private  ArrayList<Character> keys;
    private ArrayList<iGizmo>  flipper;
    boolean isStopped;
    private Timer timer;
    private int index = 0;
    private ArrayList<Long> time;
    private ArrayList<Boolean> keypressed;
    private ArrayList<Boolean> runningTimer;

    public FlipperKeyListener(String rot, iModel model, ArrayList<Character> key, ArrayList<iGizmo> flipper){
        this.rot = rot;
        this.model = model;
        this.keys = key;
        this.flipper = flipper;
        this.isStopped = false;
        this.keypressed = new ArrayList<>();
        this.runningTimer = new ArrayList<>();
        this.time = new ArrayList<>();

        for (int i = 0; i < keys.size(); i++) {//populate
            time.add(Integer.toUnsignedLong(0));
            runningTimer.add(false);
            keypressed.add(false);
        }

        //timer event for flipper rotation
        this.timer = new Timer(30 , e -> {
            synchronized (this) {
                tickFlipper(index);
                Timer t = (Timer) e.getSource();
                if (flipper.get(index).getRotationAngle() == 90 | flipper.get(index).getRotationAngle() == 0 | flipper.get(index).getRotationAngle() == -90) {
                    runningTimer.set(index, false);
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
        if(!keypressed.get(index)) { //if key not pressed
            for (int i = 0; i < keys.size(); i++) {
                char key = keys.get(i);
                if (e.getKeyChar() == key) { //matches character
                    int finalI = i;
                    Runnable r = () -> key_press_code(finalI);
                    Thread t = new Thread(r);
                    t.start();

                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keypressed.set(index,false); //key no longer pressed
        for (int i = 0; i < keys.size(); i++) {
            char key = keys.get(i);
               if (e.getKeyChar() == key) { //keys match
                   int finalI = i;
                   Runnable r = () -> key_release_code(finalI);
                   Thread t = new Thread(r);
                   t.start();
                }
            i++;
        }
    }

    private synchronized void key_press_code(int i){
        index = i;
        keypressed.set(index,true);
        if(flipper.get(index).getRotationAngle()==0){
            time.set(index,System.currentTimeMillis());
            triggerFlipper("UP");
        }
    }

    private synchronized void  key_release_code(int i){
        long gap = System.currentTimeMillis() - time.get(index);
        index = i;
        if(gap <= 250) {  //here we have a fast tap
            Runnable waiting_runnable = () -> {//wait till we have stopped running
                synchronized (time) {
                    int i1 = 0;
                    while (runningTimer.get(index)) {
                        if (i1 == 500) { //infinite loop protection
                            return;
                        }
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        i1++;
                    }
                    //if in the duration of the thread waiting, we have already reached 0, do nothing
                    if (flipper.get(index).getRotationAngle() != 0) {
                        triggerFlipper("DOWN");
                    }
                }
            };

            Thread waiting_thread = new Thread(waiting_runnable);
            waiting_thread.start();

        }else{
            if(flipper.get(index).getRotationAngle()!=0) {
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
            if (direction.equals("UP") | direction.equals("DOWN") ) {
                runningTimer.set(index,true);
                Timer newT = timer;
                newT.start();
            } else if (direction.equals("TICK")) {
                tickFlipper(index);
            }
        }
    }

    //one tick of the flipper
    private synchronized void tickFlipper(int index){
        flipper.get(index).rotate();
        model.setiGizmo(flipper.get(index));
        model.hasChanged();
        model.notifyObservers();
    }
}



