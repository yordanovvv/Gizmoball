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

        for (int i=0; i<20; i++){
            for (int j=0; j<20; j++){
                try {
                    m.setSpaces(i, j, false, null);
                }catch(NullPointerException npe){} //this is fine
            }
        }

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
