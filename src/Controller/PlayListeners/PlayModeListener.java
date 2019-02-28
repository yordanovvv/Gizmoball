package Controller.PlayListeners;

import Model.Ball;
import Model.GizmoballModel;
import Model.iModel;
import View.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            switch (e.getActionCommand()) {
                case "Play":
                    //gModel.getRightFlipListener().setIsStopped(false);

                    for (Ball b : balls) b.setStopped(false);
                    timer.restart();
                    statisticsTimer.start();
                    break;
                case "Pause":
                    //gModel.getRightFlipListener().setIsStopped(true);
                    for (Ball b : balls) b.setStopped(true);
                    timer.stop();
                    break;
                case "Tick":
                    //gModel.getRightFlipListener().setIsStopped(false);
                    //gModel.getRightFlipListener().moveFlipper("TICK");

                    for (Ball b : balls)
                    {
                        b.setStopped(false);
                        gModel.moveBall();
                        b.setStopped(true);
                    }

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
