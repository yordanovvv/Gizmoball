package Controller.PlayListeners;

import Model.*;
import View.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlayModeListener implements ActionListener {


    MainFrame mf;
    String id;

    private Timer timer;
    private Timer statisticsTimer;
    private iModel gModel;

    public PlayModeListener(MainFrame mainframe, String id) {
        this.id = id;
        this.mf = mainframe;

        gModel = mf.getGameBoard().getGizModel();
        timer = new Timer(50, this);
        statisticsTimer = new Timer(50, this);


    }

    @Override
    public final void actionPerformed(final ActionEvent e) {

        if (e.getSource() == timer) {
            gModel.moveBall();
       } else if (e.getSource() == statisticsTimer) {

            //TODO display balls speed ?
          // double s = gModel.getBallSpeed(Ball b);
          // String speed = Double.toString(s);
          // mf.output_Velocity.setText(speed);


        } else {
            //System.out.println(e.getActionCommand());
            ArrayList<Ball> balls = gModel.getBalls();
            ArrayList<iGizmo> stars = gModel.getAllStars();
            ArrayList<iGizmo> flippers = gModel.getGizmos();
            switch (e.getActionCommand()) {
                case "Play":
                    //gModel.getRightFlipListener().setIsStopped(false);
                   for(iGizmo g:stars){
                        Star star = (Star) g;
                        star.setTick(false);
                        if(star.isRoating()) star.startStarRotation();
                    }
                    for (Ball b : balls) b.setStopped(false);
                    timer.restart();
                    statisticsTimer.start();
                    break;
                case "Pause":
                    //gModel.getRightFlipListener().setIsStopped(true);
                    for(iGizmo g:stars){
                        Star star = (Star) g;
                        star.setTick(false);
                        star.stopRotation();
                    }

                    for (Ball b : balls) b.setStopped(true);
                    timer.stop();
                    break;
                case "Tick":
                    //gModel.getRightFlipListener().setIsStopped(false);
                    //gModel.getRightFlipListener().moveFlipper("TICK");
                   for(iGizmo g:stars){
                        /*Star star = (Star) g;
                        star.stopRotation();
                        star.rotate();
                        star.stopRotation();*/
                       Star star = (Star) g;
                       star.stopRotation();
                       star.setTick(true);
                       star.rotate();
                    }

                   //TODO probably remove since ticking like that is independent of key press
                    for(iGizmo g:flippers){
                       if(g.getGizmoType().equals("RightFlipper"))
                       {
                           g.rotate();
                       }
                    }
                    for (Ball b : balls)
                    {
                        b.setStopped(false);
                        gModel.moveBall();
                        b.setStopped(true);
                    }

                    break;
                case "Restart":
                    //Reload file?
                    gModel.loadGame((new File("game.giz")));
                    break;
            }
            mf.setFocusable(true);
            mf.requestFocus();
        }
    }
}
