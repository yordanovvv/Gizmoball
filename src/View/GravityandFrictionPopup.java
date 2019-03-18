package View;

import Model.Ball;
import Model.iGizmo;
import Model.iModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GravityandFrictionPopup {

    private JFrame frame;
    private JLabel description;

    private JLabel gravityLabel;
    private JSlider gravitySlider;

    private JLabel frictionLabel;
    private JSlider frictionSlider;

    private JButton button_done;
    private JButton button_cancel;

    private iModel model;


    public GravityandFrictionPopup(iModel m) {
        this.model = m;
        model.setDisplayID(true);


        Utils utils = new Utils();
        Color bg_color = (new Color(0, 41, 57, 255));

        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(500, 200));
        frame.setTitle("Set gravity and friction.");
        frame.setBackground(bg_color);

        JPanel itsMakingMeDoThis = new JPanel(new BorderLayout());
        itsMakingMeDoThis.setBackground(bg_color);

        description = new JLabel("Please select gravity and friction values.");
        description = utils.editLabel(description, 12, Color.WHITE);
        description.setBackground(bg_color);

        itsMakingMeDoThis.add(description,BorderLayout.CENTER);

        frame.add(itsMakingMeDoThis,BorderLayout.PAGE_START);


        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0,2,10,10));
        container.setBackground(bg_color);

       gravityLabel = new JLabel("Choose gizmo1");
      gravityLabel = utils.editLabel(gravityLabel,12,Color.WHITE);


       frictionLabel = new JLabel("Choose gizmo2");
       frictionLabel = utils.editLabel(frictionLabel,12,Color.WHITE);


        //need to make sliders now, initially set to 5?
        //friction takes in 2 numbers in Ball class, one of them will be fixed, the other can get from slider???
        gravitySlider= new JSlider(0,100,5);
        frictionSlider= new JSlider(0,100,5);



        container.add(gravityLabel);
        container.add(gravitySlider);

        container.add(frictionLabel);
        container.add(frictionSlider);


        frame.add(container, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(0, 2, 10, 10));
        buttonContainer.setBackground(bg_color);

        button_done = new JButton("Done");
        button_cancel = new JButton("Cancel");


        //TODO action listeners for done and cancel

        buttonContainer.add(button_done);
        buttonContainer.add(button_cancel);


        frame.add(buttonContainer, BorderLayout.PAGE_END);

        frame.setVisible(true);
        frame.pack();

    }

}
