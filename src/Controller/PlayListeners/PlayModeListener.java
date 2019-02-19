package Controller.PlayListeners;

import Model.GizmoballModel;
import View.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayModeListener implements ActionListener {


    MainFrame mf;
    String id;

    private Timer timer;
    private Timer statisticsTimer;
    private GizmoballModel gModel;

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

           double s= gModel.getBallSpeed();
            String speed = Double.toString(s);
            mf.output_Velocity.setText(speed);


        } else {
            //System.out.println(e.getActionCommand());
            switch (e.getActionCommand()) {

                case "Play":
                    //gModel.getRightFlipListener().setIsStopped(false);
                    gModel.getBall().setStopped(false);
                    timer.restart();
                    statisticsTimer.start();
                    break;
                case "Pause":
                    //gModel.getRightFlipListener().setIsStopped(true);
                    gModel.getBall().setStopped(true);
                    timer.stop();
                    break;
                case "Tick":
                    //gModel.getRightFlipListener().setIsStopped(false);
                    //gModel.getRightFlipListener().moveFlipper("TICK");

                    gModel.getBall().setStopped(false);
                    gModel.moveBall();
                    gModel.getBall().setStopped(true);
                    break;
                case "Restart":
                    //Reload file?
                    break;
            }
            mf.setFocusable(true);
            mf.requestFocus();
        }
    }
}
