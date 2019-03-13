package Controller.BuildListeners;

import Model.iModel;
import View.ConnectGizmosPopup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectListener implements ActionListener {

    private iModel model;

    public ConnectListener(iModel model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "connect":
                ConnectGizmosPopup connectpopUp= new ConnectGizmosPopup(model);
                break;
        }
    }
}
