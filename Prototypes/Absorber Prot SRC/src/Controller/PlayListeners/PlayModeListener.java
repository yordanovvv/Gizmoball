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

        timer.start();
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {

        if (e.getSource() == timer) {
            gModel.moveBall();
            mf.setFocusable(true);
            mf.requestFocus();
        } else if (e.getSource() == statisticsTimer) {
        }

        }

}
