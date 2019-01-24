package Controller.MainFrameListeners;

import View.BuildMode;
import View.MainFrame;
import View.PlayMode;
import View.iMainFrame;
import sun.applet.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeModeListener implements ActionListener {

    private iMainFrame mainFrame;

    public ChangeModeListener(iMainFrame mainFrame){
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("PLAY")){
            mainFrame.switchModes(2);
        }else{
            mainFrame.switchModes(1);
        }

    }
}
