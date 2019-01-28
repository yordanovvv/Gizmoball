package Controller.MainFrameListeners;

import View.iMainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlListener implements ActionListener {

    iMainFrame mainFrame;

    public ControlListener(iMainFrame mainFrame){
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCMD =  e.getActionCommand();

        if(actionCMD.equals("Save")){
            JFrame f = new JFrame();
            JFileChooser fileLoader = new JFileChooser();
            fileLoader.setDialogTitle("Save Gizmo");

            String filename = "";

            int rVal = fileLoader.showSaveDialog(f);
            if (rVal == JFileChooser.APPROVE_OPTION) {

                //todo file saving for controller
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
               //todo cancel file loading
            }
        }else if(actionCMD.equals("Quit")){
            int choice = mainFrame.generatePopUp("Quit?","Are you sure you want to quit?");
            if(choice == 0){
                mainFrame.quit();
            }
        }else if(actionCMD.equals("Load")){
            JFrame f = new JFrame();
            JFileChooser fileLoader = new JFileChooser();
            fileLoader.setDialogTitle("Load Gizmo");

            String filename = "";

            int rVal = fileLoader.showOpenDialog(f);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                //todo file loading for controller
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                //todo cancel file loading
            }
        }

    }
}
