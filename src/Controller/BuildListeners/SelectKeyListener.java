package Controller.BuildListeners;

import Model.iGizmo;
import Model.iModel;
import View.SelectKeyPopup;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SelectKeyListener implements KeyListener {
    private iModel model;
    private iGizmo gizmo;
    private SelectKeyPopup keyView;

    public SelectKeyListener(iModel model, iGizmo gizmo, SelectKeyPopup view) {
        this.model = model;
        this.gizmo = gizmo;
        this.keyView = view;
    }

    @Override
    public void keyTyped(KeyEvent e) {

        System.out.println("typed=" + KeyEvent.getKeyText(e.getKeyCode()));
        String key = KeyEvent.getKeyText(e.getKeyCode()) + "";
        gizmo.removeKeyConnection();
        model.keyConnectGizmo(gizmo.getID(), key);
        keyView.close();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        System.out.println("pressed=" + KeyEvent.getKeyText(e.getKeyCode()));
        String key = KeyEvent.getKeyText(e.getKeyCode()) + "";
        gizmo.removeKeyConnection();
        model.keyConnectGizmo(gizmo.getID(), key);
        System.out.println("connections=" + gizmo.getKeyConnections());
        keyView.close();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("released=" + KeyEvent.getKeyText(e.getKeyCode()));
        String key = KeyEvent.getKeyText(e.getKeyCode()) + "";
        gizmo.removeKeyConnection();
        model.keyConnectGizmo(gizmo.getID(), key);
        keyView.close();
    }
}
