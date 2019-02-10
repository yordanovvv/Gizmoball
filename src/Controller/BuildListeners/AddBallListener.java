package Controller.BuildListeners;

import View.ComponentPopup;
import View.iMainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class AddBallListener implements iAddGizmoListener{

    iMainFrame view;

    //TODO Remove this and all the other multiple billions of redundant listeners - C

    public AddBallListener(iMainFrame view){
        this.view = view;
    }

    /*
    public void actionPerformed(ActionEvent e) {
        ComponentPopup c = new ComponentPopup();
    }
    */

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked on ball!");
        ComponentPopup c = new ComponentPopup();
    }
}
