package Controller.BuildListeners;

import Model.iModel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClearClickListener implements MouseListener {

    iModel m;

    public ClearClickListener(iModel m){
        this.m = m;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        m.getGizmos().removeAll(m.getGizmos());
        m.wipeSpaces();
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
}
