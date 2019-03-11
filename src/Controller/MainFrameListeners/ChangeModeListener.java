package Controller.MainFrameListeners;

import Model.*;
import View.BuildMode;
import View.MainFrame;
import View.PlayMode;
import View.iMainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChangeModeListener implements ActionListener {

    private iMainFrame view;
    private iModel model;

    public ChangeModeListener(iMainFrame view, iModel model){
        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("PLAY")){
            view.switchModes(2);
            ArrayList<Ball> balls = model.getBalls();
            for(Ball b : balls) b.setStopped(true);
            for (iGizmo giz:model.getGizmos()) {
                if(giz.getGizmoType().equals("Star")){
                    Star star = (Star) giz;
                    star.stopRotation();
                }
            }
        }else {
            view.switchModes(1);
        }
    }
}
