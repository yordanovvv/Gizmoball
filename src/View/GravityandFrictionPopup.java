package View;

import Model.Ball;
import Model.iGizmo;
import Model.iModel;
import physics.Vect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    //-
    JLabel label_comboBall;
    private JComboBox<String> combo_ball;
    private ArrayList<Ball> balls;


    public GravityandFrictionPopup(iModel m) {
        this.model = m;
        model.setDisplayID(true);

        combo_ball = new JComboBox<>();
        balls = m.getBalls();
        for (Ball b : balls){
            combo_ball.addItem("Ball " + b.getID());
        }


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

        label_comboBall = new JLabel("Select Ball :");
        label_comboBall = utils.editLabel(label_comboBall,12,Color.WHITE);

        gravityLabel = new JLabel("Set gravity value : ");
        gravityLabel = utils.editLabel(gravityLabel,12,Color.WHITE);

       frictionLabel = new JLabel("Set friction value : ");
       frictionLabel = utils.editLabel(frictionLabel,12,Color.WHITE);


        container.add(label_comboBall);
        container.add(combo_ball);


        //need to make sliders now, initially set to 5?
        //friction takes in 2 numbers in Ball class, one of them will be fixed, the other can get from slider???
        gravitySlider = new JSlider(-50,50,5);
        frictionSlider = new JSlider(-50,50,5);

        gravitySlider.setPaintTicks(true);
        gravitySlider.setPaintLabels(true);
        gravitySlider.setMajorTickSpacing(10);
        gravitySlider.setMinorTickSpacing(5);


        container.add(gravityLabel);
        container.add(gravitySlider);

        frictionSlider.setPaintTicks(true);
        frictionSlider.setPaintLabels(true);
        frictionSlider.setMajorTickSpacing(10);
        frictionSlider.setMinorTickSpacing(5);

        container.add(frictionLabel);
        container.add(frictionSlider);

        frame.add(container, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(0, 2, 10, 10));
        buttonContainer.setBackground(bg_color);

        button_done = new JButton("Done");
        button_cancel = new JButton("Cancel");

        //TODO action listeners for done and cancel

        button_done.addActionListener(new GravityandFrictionPopup.tempActionListener());
        button_cancel.addActionListener(new GravityandFrictionPopup.tempActionListener());

        buttonContainer.add(button_done);
        buttonContainer.add(button_cancel);

        frame.add(buttonContainer, BorderLayout.PAGE_END);

        frame.setVisible(true);
        frame.pack();

    }

    class tempActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Done")){
                System.out.println("updating stuff");

                Ball selectedBall = balls.get(combo_ball.getSelectedIndex());
                selectedBall.applyGravity(gravitySlider.getValue(), 0.05);
                selectedBall.setFriction(0.025, frictionSlider.getValue());

            }
            frame.setVisible(false);
            frame.dispose();

        }
    }

}
