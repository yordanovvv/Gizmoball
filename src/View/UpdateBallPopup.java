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

public class UpdateBallPopup {

    JFrame pop_upFrame;
    JLabel description;

    JLabel label_xVelocity;
    JTextField xVelocity;

    JLabel label_yVelocity;
    JTextField yVelocity;

    JButton button_done, button_cancel, button_delete;

    iModel m;
    int gridX;
    int gridY;

    JLabel label_comboBall;
    private JComboBox<String> combo_ball;
    private ArrayList<Ball> balls;


    public UpdateBallPopup(iModel m){

        this.m = m;

        combo_ball = new JComboBox<>();
        balls = m.getBalls();
        for (Ball b : balls){
            combo_ball.addItem("Ball " + b.getID());
        }

        Utils utils = new Utils();
        Color bg_color = (new Color(0, 41, 57, 255));

        pop_upFrame = new JFrame();
        pop_upFrame.setLayout(new BorderLayout());
        pop_upFrame.setPreferredSize(new Dimension(400,200));
        pop_upFrame.setTitle("Ball Settings");


        pop_upFrame.setBackground(bg_color);

        JPanel itsMakingMeDoThis = new JPanel(new BorderLayout());
        itsMakingMeDoThis.setBackground(bg_color);
        description = new JLabel("Please select the X & Y ball velocity.");
        description = utils.editLabel(description,12,Color.WHITE);
        description.setBackground(bg_color);

        itsMakingMeDoThis.add(description,BorderLayout.CENTER);

        pop_upFrame.add(itsMakingMeDoThis,BorderLayout.PAGE_START);

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0,2,10,10));
        container.setBackground(bg_color);

        label_comboBall = new JLabel("Select Ball :");
        label_comboBall = utils.editLabel(label_comboBall,12,Color.WHITE);

        label_xVelocity = new JLabel("X Velocity");
        label_xVelocity = utils.editLabel(label_xVelocity,12,Color.WHITE);

        xVelocity = new JTextField();
        xVelocity.setEditable(true);

        label_yVelocity = new JLabel("Y Velocity");
        label_yVelocity = utils.editLabel(label_yVelocity,12,Color.WHITE);

        yVelocity = new JTextField();
        xVelocity.setEditable(true);

        container.add(label_comboBall);
        container.add(combo_ball);

        container.add(label_xVelocity);
        container.add(xVelocity);

        container.add(label_yVelocity);
        container.add(yVelocity);

        pop_upFrame.add(container, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(0,2,10,10));
        buttonContainer.setBackground(bg_color);

        button_done = new JButton("Update Selected Ball");
        button_cancel = new JButton("Cancel");
        button_delete = new JButton("Delete Ball");

        button_done.addActionListener(new tempActionListener());
        button_cancel.addActionListener(new tempActionListener());
        button_delete.addActionListener(new tempActionListener());

        buttonContainer.add(button_done);
        buttonContainer.add(button_delete);
        buttonContainer.add(button_cancel);

        if (m.getBalls().size() == 0){
            button_done.setEnabled(false);
            button_delete.setEnabled(false);
        }

        pop_upFrame.add(buttonContainer, BorderLayout.PAGE_END);

        pop_upFrame.setVisible(true);
        pop_upFrame.pack();

    }

    public double xVelo = 0.0;
    public double yVelo = 0.0;

    public double get_X_Velocity(){
        return xVelo;
    }
    public double get_Y_Velocity(){
        return yVelo;
    }


    class tempActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals("Update Selected Ball")) {
                try {
                    xVelo = Double.parseDouble(xVelocity.getText());
                    yVelo = Double.parseDouble(yVelocity.getText());

                    Ball selectedBall = balls.get(combo_ball.getSelectedIndex());
                    selectedBall.setVelo(new Vect(xVelo, yVelo));
                } catch (NumberFormatException nfe) {
                    System.out.println("Must enter text, cancelling addition");
                    JOptionPane.showMessageDialog(null, "Enter numerical value.", "Error", JOptionPane.ERROR_MESSAGE);return;
                }
            } else if (e.getActionCommand().equals("Delete Ball")){
                Ball selectedBall = balls.get(combo_ball.getSelectedIndex());
                m.getBalls().remove(selectedBall);
            }
            //selectedball

            pop_upFrame.setVisible(false);
            pop_upFrame.dispose();

        }
    }
}
