package View;

import Model.iGizmo;
import Model.iModel;

import javax.swing.*;
import java.awt.*;

public class DisconnectGizmosPopup {
    private JFrame frame;
    private JLabel description, disconnectLabel;
    private JComboBox<String> connectionsComboBox;

    private iModel model;

    public DisconnectGizmosPopup(iModel m) {
        model = m;

        Utils utils = new Utils();
        Color bg_color = (new Color(0, 41, 57, 255));

        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(500, 200));
        frame.setTitle("Disonnect gizmos.");
        frame.setBackground(bg_color);

        JPanel itsMakingMeDoThis = new JPanel(new BorderLayout());
        itsMakingMeDoThis.setBackground(bg_color);

        description = new JLabel("Please select a connection to be removed.");
        description = utils.editLabel(description, 12, Color.WHITE);
        description.setBackground(bg_color);

        itsMakingMeDoThis.add(description,BorderLayout.CENTER);

        frame.add(itsMakingMeDoThis,BorderLayout.PAGE_START);

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0,1,10,10));
        container.setBackground(bg_color);

        //labels and combo box for choosing 2 gizmos to connect

        disconnectLabel = new JLabel("All connections");
        disconnectLabel = utils.editLabel(disconnectLabel,12,Color.WHITE);

        connectionsComboBox = new JComboBox<>();

        for(iGizmo gizmo : model.getGizmos()) {
            if(gizmo.getGizmoConnections() != null ) {
                //TODO: Loop through connections and fix text
                connectionsComboBox.addItem(gizmo.getGizmoType() + "[" + gizmo.getID() + "]: x = " + gizmo.getXCoord() + "; y = " + gizmo.getYCoord());
            }
        }

        frame.setVisible(true);
        frame.pack();

    }
}
