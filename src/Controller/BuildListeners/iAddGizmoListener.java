package Controller.BuildListeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface iAddGizmoListener extends MouseListener {

    public void mousePressed(MouseEvent e);

    public void mouseReleased(MouseEvent e);

    public void mouseEntered(MouseEvent e);

    public void mouseExited(MouseEvent e);

    public void mouseClicked(MouseEvent e);

}
