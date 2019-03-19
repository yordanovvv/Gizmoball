package View;

import Model.iGizmo;
import Model.iModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyBinds {
    private JFrame frame;
    private JLabel description, disconnectLabel;
    private JComboBox<String> bindsComboBox;
    private JButton button_change, button_cancel;

    private iModel model;

    public KeyBinds(iModel m) {
        model = m;

        Utils utils = new Utils();
        Color bg_color = (new Color(0, 41, 57, 255));

        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(500, 200));
        frame.setTitle("Key binds");
        frame.setBackground(bg_color);

        JPanel itsMakingMeDoThis = new JPanel(new BorderLayout());
        itsMakingMeDoThis.setBackground(bg_color);

        description = new JLabel("Please select a gizmo's key bind to be changed or added.");
        description = utils.editLabel(description, 12, Color.WHITE);
        description.setBackground(bg_color);

        itsMakingMeDoThis.add(description,BorderLayout.CENTER);

        frame.add(itsMakingMeDoThis,BorderLayout.PAGE_START);

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0,1,10,10));
        container.setBackground(bg_color);

        //labels and combo box for choosing 2 gizmos to connect

        disconnectLabel = new JLabel("All key bind gizmos:");
        disconnectLabel = utils.editLabel(disconnectLabel,12,Color.WHITE);

        container.add(disconnectLabel);

        bindsComboBox = new JComboBox<>();

        button_change = new JButton("Change");
        button_cancel = new JButton("Close");

        updateComboBox();

        container.add(bindsComboBox);

        frame.add(container, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(0, 2, 10, 10));
        buttonContainer.setBackground(bg_color);


        button_change.setActionCommand("Change");
        button_change.addActionListener(new KeyBindsListener());
        button_cancel.setActionCommand("Close");
        button_cancel.addActionListener(new KeyBindsListener());

        buttonContainer.add(button_change);
        buttonContainer.add(button_cancel);


        frame.add(buttonContainer, BorderLayout.PAGE_END);

        frame.setVisible(true);
        frame.pack();
    }

    private void updateComboBox() {
        bindsComboBox.removeAllItems();

        for(iGizmo gizmo : model.getGizmos()) {
            if(gizmo.getKeyConnections() != null && gizmo.getKeyConnections().size() > 0) {
                for(String key : gizmo.getKeyConnections()) {
                    bindsComboBox.addItem(gizmo.getGizmoType() + "[" + gizmo.getID() + "] ===== '" + key + "'");
                }
            }
        }

        if(bindsComboBox.getItemCount() == 0) {
            button_change.setEnabled(false);
        }
    }

    private class KeyBindsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            switch (e.getActionCommand()) {
                case "Change":
                    String message = bindsComboBox.getItemAt(bindsComboBox.getSelectedIndex());
                    String gizmoID = message.substring(message.indexOf("[") + 1, message.indexOf("]"));
                    String key = message.substring(message.indexOf("'") + 1, message.length()-1);
                    System.out.println("gizmoID=" + gizmoID);
                    System.out.println("key=" + key);

                    model.getGizmoByID(gizmoID).removeKeyConnection();
                    SelectKeyPopup selectKeyPopup = new SelectKeyPopup(model, model.getGizmoByID(gizmoID));
                case "Close":
                    frame.setVisible(false);
                    frame.dispose();
                    break;
            }
        }
    }
}
