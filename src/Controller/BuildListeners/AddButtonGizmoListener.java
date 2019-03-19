package Controller.BuildListeners;

import View.iMainFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddButtonGizmoListener implements MouseListener {

    //private final iMainFrame view;
    private String id;
    GridClickListener gcl;

    public AddButtonGizmoListener(String id, GridClickListener g){
        this.id = id;
        this.gcl = g;
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
        gcl.setSelected(id); //Change what button is "selected" for when clicking on the grid.
    }

}
