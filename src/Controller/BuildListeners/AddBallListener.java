package Controller.BuildListeners;

import View.ComponentPopup;
import View.iMainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBallListener implements ActionListener {

    iMainFrame view;


    public AddBallListener(iMainFrame view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ComponentPopup c = new ComponentPopup();

    }
}
