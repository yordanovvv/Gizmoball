package Controller.BuildListeners;

import Model.iModel;
import View.UpdateBallPopup;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UpdateBallVeloListener implements MouseListener {

    iModel m;

    public UpdateBallVeloListener(iModel m){
        this.m = m;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        UpdateBallPopup pop = new UpdateBallPopup(m);
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
