package View;

import javax.swing.*;


import Model.iGizmo;
import Model.iModel;

import java.awt.*;
import java.util.ArrayList;

public class ConnectGizmosPopup {

    private JFrame frame;
    private JLabel description;

    private JLabel connectLabel;
    private JComboBox<String> combo_gizmo1;

    private JLabel connectLabel2;
    private JComboBox<String> combo_gizmo2;

    private JButton button_done;
    private JButton button_cancel;

    private iModel model;


    //has an instance of model to access list of gizmos
    public ConnectGizmosPopup(iModel m) {
        this.model = m;


        Utils utils = new Utils();
        Color bg_color = (new Color(0, 41, 57, 255));

        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(500, 200));
        frame.setTitle("Connect gizmos.");
        frame.setBackground(bg_color);

        JPanel itsMakingMeDoThis = new JPanel(new BorderLayout());
        itsMakingMeDoThis.setBackground(bg_color);

        description = new JLabel("Please select two gizmos to connect.");
        description = utils.editLabel(description, 12, Color.WHITE);
        description.setBackground(bg_color);

        itsMakingMeDoThis.add(description,BorderLayout.CENTER);

        frame.add(itsMakingMeDoThis,BorderLayout.PAGE_START);




        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0,2,10,10));
        container.setBackground(bg_color);

        //labels and combo box for choosing 2 gizmos to connect

        connectLabel = new JLabel("Choose gizmo1");
        connectLabel = utils.editLabel(connectLabel,12,Color.WHITE);


        connectLabel2 = new JLabel("Choose gizmo2");
        connectLabel2 = utils.editLabel(connectLabel2,12,Color.WHITE);


        //need to fill combo boxes now
        combo_gizmo1= new JComboBox<>();
        combo_gizmo2= new JComboBox<>();


        //get all gizmos on board
        ArrayList<iGizmo> gizmos = model.getGizmos();

        //get their id and type, so player can know whats what?
            for (iGizmo g : gizmos) {
                combo_gizmo1.addItem("id " + g.getID() + " type " + g.getGizmoType());
            }

        //do the same for combo box 2
        for (iGizmo g : gizmos) {

            combo_gizmo2.addItem("id " + g.getID() + " type " + g.getGizmoType());
        }

        container.add(connectLabel);
        container.add(combo_gizmo1);

        container.add(connectLabel2);
        container.add(combo_gizmo2);

        //done and cancel buttons

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(0, 2, 10, 10));
        buttonContainer.setBackground(bg_color);

        button_done = new JButton("Done");
        button_cancel = new JButton("Cancel");
        //need to add action listeners to these?^^

        buttonContainer.add(button_done);
        buttonContainer.add(button_cancel);


        frame.add(buttonContainer, BorderLayout.PAGE_END);

        frame.setVisible(true);
        frame.pack();


    }
}


