package Controller.MainFrameListeners;

import Model.GizmoballModel;
import Model.iModel;
import View.BuildMode;
import View.MainFrame;
import View.PlayMode;
import View.iMainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            model.getBall().setStopped(true);
        }else{
            view.switchModes(1);
        }

    }
}
