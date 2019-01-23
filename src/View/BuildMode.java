package View;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class BuildMode extends JPanel implements Observer {

    private JPanel buildPanel;
    private JLabel addGizmo;
    private JButton button_triangle;
    private JButton button_circle;
    private JButton button_square;
    private JButton button_leftFlipper;
    private JButton button_rightFlipper;
    private JButton button_ball;
    private JButton button_connectButton;
    private JButton button_keypress;
    private JButton button_delete;
    private JButton button_rotate;
    private JButton button_move;




    public BuildMode(){

        GridLayout layout_buildMode = new GridLayout(1,1,10,10);

        Color bg_color = new Color(22, 21, 60, 171);

        this.setBackground(bg_color);
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(layout_buildMode);


        init();

    }

    private void init() {

        buildPanel = new JPanel();


        buildPanel.setLayout(new FlowLayout());

        button_square = new JButton("Square");
        button_triangle = new JButton("Triangle");
        button_circle= new JButton("Circle");
        button_leftFlipper = new JButton("Left flipper");
        button_rightFlipper = new JButton("Right flipper");
        button_ball= new JButton("Ball");

        buildPanel.add(button_square);
        buildPanel.add(button_triangle);
        buildPanel.add(button_circle);
        buildPanel.add(button_leftFlipper);
        buildPanel.add(button_rightFlipper);
        buildPanel.add(button_ball);

        this.add(buildPanel);

    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
