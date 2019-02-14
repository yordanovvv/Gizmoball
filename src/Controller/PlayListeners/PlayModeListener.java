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
    private GizmoballModel gModel;

    public PlayModeListener(MainFrame mainframe, String id){
        this.id = id;
        this.mf = mainframe;

        //mf.getGameBoard();

        gModel = mf.getGameBoard().getGizModel();
        timer = new Timer(50, this);


    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer){
            gModel.moveBall();
        } else {
            //System.out.println(e.getActionCommand());
            switch (e.getActionCommand()) {
                case "Play":
                    gModel.getBall().setStopped(false);
                    timer.restart();
                    break;
                case "Pause":
                    gModel.getBall().setStopped(true);
                    timer.stop(); //FIXME : Make timer actually stop....
                    break;
                case "Tick":
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
