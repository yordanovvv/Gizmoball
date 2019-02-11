package Controller.MainFrameListeners;

import View.BuildMode;
import View.MainFrame;
import View.PlayMode;
import View.iMainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeModeListener implements ActionListener {

    private iMainFrame view;

    public ChangeModeListener(iMainFrame view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("PLAY")){
            view.switchModes(2);
        }else{
            view.switchModes(1);
        }

    }
}
