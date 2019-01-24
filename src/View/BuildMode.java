package View;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class BuildMode extends JPanel implements Observer {

    private JPanel buildPanel;
    private JLabel label_components;
    private JButton button_triangle;
    private JButton button_circle;
    private JButton button_square;
    private JButton button_leftFlipper;
    private JButton button_rightFlipper;
    private JButton button_ball;

    private JPanel controlsPanel;
    private JLabel label_controls;
    private JButton button_connectButton;
    private JButton button_disconnect;
    private JButton button_rotate;
    private JButton button_move;
    private JButton button_delete;
    private JButton button_clear;




    public BuildMode(){
        Color bg_color = new Color(22, 21, 60, 171);

        this.setBackground(bg_color);
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(new GridLayout(0,1,10,10));


        init();

    }

    private void init() {

        buildPanel = new JPanel(new BorderLayout());

        JPanel container_build = new JPanel(new GridLayout(0,3));
        label_components = new JLabel("Components");

        button_square = new JButton("Square");
        button_triangle = new JButton("Triangle");
        button_circle= new JButton("Circle");
        button_leftFlipper = new JButton("Left flipper");
        button_rightFlipper = new JButton("Right flipper");
        button_ball= new JButton("Ball");

        container_build.add(button_square);
        container_build.add(button_triangle);
        container_build.add(button_circle);
        container_build.add(button_leftFlipper);
        container_build.add(button_rightFlipper);
        container_build.add(button_ball);

        buildPanel.add(label_components,BorderLayout.PAGE_START);
        buildPanel.add(container_build,BorderLayout.CENTER);


        controlsPanel = new JPanel(new BorderLayout());
        JPanel container_play = new JPanel(new GridLayout(0,3));
        label_controls = new JLabel("Controls");

        button_connectButton = new JButton("Connect");
        button_disconnect = new JButton("Disconnect");
        button_rotate = new JButton("Rotate");
        button_move = new JButton("Move");
        button_clear = new JButton("Clear");
        button_delete = new JButton("Delete");

        container_play.add(button_rotate);
        container_play.add(button_connectButton);
        container_play.add(button_disconnect);
        container_play.add(button_delete);
        container_play.add(button_move);
        container_play.add(button_clear);
        container_play.add(button_move);

        controlsPanel.add(label_controls,BorderLayout.PAGE_START);
        controlsPanel.add(container_play,BorderLayout.CENTER);

        this.add(buildPanel);
        this.add(controlsPanel);

    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
