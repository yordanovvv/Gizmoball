package Controller.MainFrameListeners;

import View.MainFrame;
import View.iMainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ControlListener implements ActionListener {

    MainFrame view;

    public ControlListener(MainFrame view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCMD =  e.getActionCommand();

        if(actionCMD.equals("Save")){
            JFrame f = new JFrame();
            JFileChooser fileLoader = new JFileChooser();
            fileLoader.setDialogTitle("Save Gizmo");
            fileLoader.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileLoader.setFileFilter(new FileNameExtensionFilter("GizmoBall files", "giz"));
            String filename = "",
                    filePath = "";

            int rVal = fileLoader.showSaveDialog(f);
            if (rVal == JFileChooser.APPROVE_OPTION) {


                filename = fileLoader.getSelectedFile().getName();
                filePath = fileLoader.getSelectedFile().getPath();
                System.out.println("filename: "  + filename);
                System.out.println("filePath: " + fileLoader.getSelectedFile().getAbsolutePath());
                if(filePath.endsWith(".giz")) {
                    view.getModel().saveGame(new File(filePath));
                } else {
                    view.getModel().saveGame(new File(filePath + ".giz"));
                }


            }
            if (rVal == JFileChooser.CANCEL_OPTION) {

                return;
            };
        }else if(actionCMD.equals("Quit")){
            int choice = view.generatePopUp("Quit?","Are you sure you want to quit?");
            if(choice == 0){
                view.quit();
            }
        }else if(actionCMD.equals("Load")){
            JFrame f = new JFrame();
            JFileChooser fileLoader = new JFileChooser();
            fileLoader.setDialogTitle("Load Gizmo");
            fileLoader.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileLoader.setFileFilter(new FileNameExtensionFilter("GizmoBall files", "giz"));
            String filename = "", filePath = "";

            int rVal = fileLoader.showOpenDialog(f);
            if (rVal == JFileChooser.APPROVE_OPTION) {

                filePath = fileLoader.getSelectedFile().getPath();
                if(filePath.endsWith(".giz")) {
                    view.getModel().loadGame(new File(filePath));
                    view.getModel().saveGame(new File("game.giz"));
                } else {
                    view.getModel().loadGame(new File(filePath + ".giz"));
                    view.getModel().saveGame(new File("game.giz"));
                }
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {

                return;
            }
        }

    }
}
