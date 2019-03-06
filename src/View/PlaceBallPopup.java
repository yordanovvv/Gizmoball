package View;

import Model.Ball;
import Model.iModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaceBallPopup {

    JFrame pop_upFrame;
    JLabel description;

    JLabel label_xVelocity;
    JTextField xVelocity;

    JLabel label_yVelocity;
    JTextField yVelocity;

    JButton button_done, button_cancel;

    iModel m;
    int gridX;
    int gridY;

    public PlaceBallPopup(iModel m, int gridX, int gridY){

        this.m = m;
        this.gridX = gridX;
        this.gridY = gridY;

        Utils utils = new Utils();
        Color bg_color = (new Color(0, 41, 57, 255));

        pop_upFrame = new JFrame();
        pop_upFrame.setLayout(new BorderLayout());
        pop_upFrame.setPreferredSize(new Dimension(400,150));
        pop_upFrame.setTitle("Add a Ball.");


        pop_upFrame.setBackground(bg_color);

        JPanel itsMakingMeDoThis = new JPanel(new BorderLayout());
        itsMakingMeDoThis.setBackground(bg_color); //todo fix the colouring - remove this panel to see issue

        description = new JLabel("Please select the X & Y ball velocity.");
        description = utils.editLabel(description,12,Color.WHITE);
        description.setBackground(bg_color);

        itsMakingMeDoThis.add(description,BorderLayout.CENTER);

        pop_upFrame.add(itsMakingMeDoThis,BorderLayout.PAGE_START);

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0,2,10,10));
        container.setBackground(bg_color);

        label_xVelocity = new JLabel("X Velocity");
        label_xVelocity = utils.editLabel(label_xVelocity,12,Color.WHITE);

        xVelocity = new JTextField();
        xVelocity.setEditable(true);

        label_yVelocity = new JLabel("Y Velocity");
        label_yVelocity = utils.editLabel(label_yVelocity,12,Color.WHITE);

        yVelocity = new JTextField();
        xVelocity.setEditable(true);

        container.add(label_xVelocity);
        container.add(xVelocity);

        container.add(label_yVelocity);
        container.add(yVelocity);

        pop_upFrame.add(container, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(0,2,10,10));
        buttonContainer.setBackground(bg_color);

        button_done = new JButton("Done");
        button_cancel = new JButton("Cancel");

        button_done.addActionListener(new tempActionListener());
        button_cancel.addActionListener(new tempActionListener());

        buttonContainer.add(button_done);
        buttonContainer.add(button_cancel);

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

            try {
                xVelo = Double.parseDouble(xVelocity.getText());
                yVelo = Double.parseDouble(yVelocity.getText());
            } catch (NumberFormatException nfe){
                System.out.println("Must enter text, cancelling addition");
                pop_upFrame.setVisible(false);
                pop_upFrame.dispose();
                return;
            }

            //todo : make this do that thing i mentioned in another class - c
            int idNo = 0;
            for (Ball b: m.getBalls()){
                idNo ++;
            }

           if (e.getActionCommand().equals("Done")){
                Ball b = new Ball("B"+idNo, gridX, gridY, xVelo, yVelo);
                m.addBall(b);
            }

            pop_upFrame.setVisible(false);
            pop_upFrame.dispose();

        }
    }
}
