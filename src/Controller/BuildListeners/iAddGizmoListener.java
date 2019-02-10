package Controller.BuildListeners;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface iAddGizmoListener extends MouseListener {

    //TODO Delete this, is redundant - just use MouseListener, is the same, - C
    //TODO Delete all the other redundant listeners too.

    public void mousePressed(MouseEvent e);

    public void mouseReleased(MouseEvent e);

    public void mouseEntered(MouseEvent e);

    public void mouseExited(MouseEvent e);

    public void mouseClicked(MouseEvent e);

}
