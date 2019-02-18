package Controller.MainFrameListeners;

import View.MainFrame;
import View.iMainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlListener implements ActionListener {

    MainFrame view;

    public ControlListener(MainFrame view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCMD =  e.getActionCommand();
        //todo check that the file loaders actually follow MVC
        if(actionCMD.equals("Save")){
            /*JFrame f = new JFrame();
            JFileChooser fileLoader = new JFileChooser();
            fileLoader.setDialogTitle("Save Gizmo");

            String filename = "";

            int rVal = fileLoader.showSaveDialog(f);
            if (rVal == JFileChooser.APPROVE_OPTION) {

                //todo file saving for controller

                view.getModel().saveGame();

            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
               //todo cancel file loading
            }*/view.getModel().saveGame();
        }else if(actionCMD.equals("Quit")){
            int choice = view.generatePopUp("Quit?","Are you sure you want to quit?");
            if(choice == 0){
                view.quit();
            }
        }else if(actionCMD.equals("Load")){
           /* JFrame f = new JFrame();
            JFileChooser fileLoader = new JFileChooser();
            fileLoader.setDialogTitle("Load Gizmo");

            String filename = "";

            int rVal = fileLoader.showOpenDialog(f);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                //todo file loading for controller

                view.getModel().loadGame();
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                //todo cancel file loading
            }*/
            view.getModel().loadGame();
        }

    }
}
