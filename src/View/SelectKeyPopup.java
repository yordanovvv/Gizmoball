package View;

import Controller.BuildListeners.SelectKeyListener;
import Model.iGizmo;
import Model.iModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SelectKeyPopup {

    private JFrame frame;
    private JLabel description;

    private JLabel connectLabel;
    private iModel model;
    private iGizmo gizmo;

    public SelectKeyPopup(iModel model, iGizmo gizmo) {
        this.model = model;
        this.gizmo = gizmo;

        Utils utils = new Utils();
        Color bg_color = (new Color(0, 41, 57, 255));

        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(500, 200));
        frame.setTitle("Key Bind for " + gizmo.getID());
        frame.setBackground(bg_color);

        JPanel itsMakingMeDoThis = new JPanel(new BorderLayout());
        itsMakingMeDoThis.setBackground(bg_color);

        description = new JLabel("Please select a key bind for " + gizmo.getGizmoType() + "[" + gizmo.getID()+ "]....");
        description = utils.editLabel(description, 12, Color.WHITE);
        description.setBackground(bg_color);

        description.setFocusable(true);
        description.addKeyListener(new SelectKeyListener(model, gizmo, this));

        itsMakingMeDoThis.add(description,BorderLayout.CENTER);

        frame.add(itsMakingMeDoThis,BorderLayout.PAGE_START);

        frame.setVisible(true);
        frame.pack();
    }

    public void close() {
        frame.setVisible(false);
        frame.dispose();
    }
}
