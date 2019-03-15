package Controller.BuildListeners;

import View.GameBoard;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DisplayVacancyGridListener implements MouseListener {

    GameBoard board;
    public DisplayVacancyGridListener(GameBoard board){
        this.board = board;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        board.setDisplaySpace(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        board.setDisplaySpace(false);
    }
}
