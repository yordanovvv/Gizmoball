package Controller.BuildListeners;

import View.iMainFrame;

import java.awt.event.MouseEvent;

public class AddSquareGizmoListener implements iAddGizmoListener{

    private final iMainFrame view;

    public AddSquareGizmoListener(iMainFrame view){
        this.view = view;
    }

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
        System.out.println("Square Gizmo Clicked");
    }
}
