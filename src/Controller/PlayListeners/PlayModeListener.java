package Controller.PlayListeners;

import Model.Ball;
import Model.Star;
import Model.iGizmo;
import Model.iModel;
import View.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class PlayModeListener implements ActionListener {


    MainFrame mf;
    String id;

    private Timer timer;
    private iModel gModel;

    public PlayModeListener(MainFrame mainframe, String id) {
        this.id = id;
        this.mf = mainframe;

        gModel = mf.getGameBoard().getGizModel();
        timer = new Timer(50, this);

    }

    @Override
    public final void actionPerformed(final ActionEvent e) {



        if (e.getSource() == timer) {
            gModel.moveBall();
       }  else {
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
                        if(star.isNotRotating()) star.startStarRotation();
                    }
                    for (Ball b : balls) b.setStopped(false);
                    timer.start();
                    mf.getModel().updateActiveTimers(timer);
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

                   for(iGizmo g:stars){
                       Star star = (Star) g;
                       star.stopRotation();
                       star.setTick(true);
                       star.rotate();
                    }


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

                    gModel.loadGame((new File("game.giz")));
                    break;
            }
            mf.setFocusable(true);
            mf.requestFocus();
        }

    }
}
