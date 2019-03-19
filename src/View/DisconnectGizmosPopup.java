package View;

import Model.iGizmo;
import Model.iModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisconnectGizmosPopup {
    private JFrame frame;
    private JLabel description, disconnectLabel;
    private JComboBox<String> connectionsComboBox;
    private JButton button_remove, button_cancel;

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

        container.add(disconnectLabel);

        connectionsComboBox = new JComboBox<>();
        button_remove = new JButton("Remove");
        button_cancel = new JButton("Close");

        for(iGizmo gizmo : model.getGizmos()) {
            if(gizmo.getGizmoConnections() != null ) {
                //TODO: Loop through connections and fix text
                for(String gizmoID : gizmo.getGizmoConnections()) {
                    iGizmo connectionGizmo = model.getGizmoByID(gizmoID);
                    String s1 = gizmo.getGizmoType() + "[" + gizmo.getID() + "]: x = " + gizmo.getXCoord() + "; y = " + gizmo.getYCoord();

                    String s2 = connectionGizmo.getGizmoType() + "[" + connectionGizmo.getID() + "]: x = " + connectionGizmo.getXCoord() + "; y = " + connectionGizmo.getYCoord();
                    connectionsComboBox.addItem(s1 + " --> " + s2);
                }
            }
        }

        if(connectionsComboBox.getItemCount() == 0) {
            button_remove.setEnabled(false);
        }

        container.add(connectionsComboBox);

        frame.add(container, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(0, 2, 10, 10));
        buttonContainer.setBackground(bg_color);


        //need to add action listeners to these?^^
        button_remove.setActionCommand("Remove");
        button_remove.addActionListener(new SmallDisconnectActionListener());
        button_cancel.setActionCommand("Close");
        button_cancel.addActionListener(new SmallDisconnectActionListener());

        buttonContainer.add(button_remove);
        buttonContainer.add(button_cancel);


        frame.add(buttonContainer, BorderLayout.PAGE_END);

        frame.setVisible(true);
        frame.pack();

    }

    private class SmallDisconnectActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()) {
                case "Remove":
                    String message = connectionsComboBox.getItemAt(connectionsComboBox.getSelectedIndex());
                    int index1 = message.indexOf("[");
                    int index2 = message.indexOf("]");
                    String id1 = message.substring(index1+1, index2);
                    String id2 = message.substring(message.indexOf("[", index1+1) + 1 , message.indexOf("]", index2+1));
                    System.out.println("id1=" + id1);
                    System.out.println("id2=" + id2);
                    iGizmo gizmo1 = model.getGizmoByID(id1);
                    if(model.removeConnection(gizmo1, id2)) {
                        JOptionPane.showMessageDialog(null, "Gizmo " + id1 + " and " + id2 + " disconnected successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "There is a problem disconnecting the selected gizmos. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                case "Close":
                    frame.setVisible(false);
                    frame.dispose();
                    break;
            }

        }
    }
}
