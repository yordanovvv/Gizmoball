package Controller.BuildListeners;

import Model.iModel;
import View.KeyBinds;
import View.UpdateBallPopup;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShowPopupListener implements MouseListener {

    iModel m;
    String popupId;

    public ShowPopupListener(iModel m, String popupId){
        this.m = m;
        this.popupId = popupId;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (popupId.equals("updateBall")){
            UpdateBallPopup pop = new UpdateBallPopup(m);
        } else if (popupId.equals("key binds")){
            KeyBinds gpop = new KeyBinds(m);
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
