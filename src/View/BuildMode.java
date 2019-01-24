package View;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
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
        this.setBackground(new Color(0, 41, 57, 255));
        this.setBorder( BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(new GridLayout(0,1,10,10));

        init();

    }

    private void init() {

        Color panel_colour = new Color(0, 55, 77, 255);
        Utils utils = new Utils();

        //--------------------------------------------------------
        //                    BUILD PANEL


        buildPanel = new JPanel(new BorderLayout());
        buildPanel.setBackground(panel_colour);
        JPanel container_build = new JPanel(new GridLayout(0,3));
        container_build.setBackground(panel_colour);
        label_components = new JLabel("Components");
        label_components = utils.editLabel(label_components,15,Color.WHITE);

        button_square = new JButton("Square");
        button_triangle = new JButton("Triangle");
        button_circle= new JButton("Circle");
        button_leftFlipper = new JButton("Left flipper");
        button_rightFlipper = new JButton("Right flipper");
        button_ball= new JButton("Ball");


        button_square = utils.addImgToBtn("switch.png",button_square, Color.RED);
        button_triangle = utils.addImgToBtn("switch.png",button_triangle,Color.BLUE);
        button_circle = utils.addImgToBtn("switch.png",button_circle,Color.GREEN);
        button_leftFlipper = utils.addImgToBtn("switch.png",button_leftFlipper,Color.YELLOW);
        button_rightFlipper = utils.addImgToBtn("switch.png",button_rightFlipper, Color.ORANGE);
        button_ball = utils.addImgToBtn("switch.png",button_ball,Color.PINK);
        button_circle = utils.addImgToBtn("switch.png",button_circle,Color.WHITE);

        container_build.add(button_square);
        container_build.add(button_triangle);
        container_build.add(button_circle);
        container_build.add(button_leftFlipper);
        container_build.add(button_rightFlipper);
        container_build.add(button_ball);

        buildPanel.add(label_components,BorderLayout.PAGE_START);
        buildPanel.add(container_build,BorderLayout.CENTER);


        //--------------------------------------------------------
        //                    CONTROL PANEL

        controlsPanel = new JPanel(new BorderLayout());
        controlsPanel.setBackground(panel_colour);
        JPanel container_play = new JPanel(new GridLayout(0,3));
        container_play.setBackground(panel_colour);

        label_controls = new JLabel("Controls");
        label_controls = utils.editLabel(label_controls,15,Color.WHITE);

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


        button_rotate = utils.addImgToBtn("switch.png",button_rotate, Color.WHITE);
        button_connectButton = utils.addImgToBtn("switch.png",button_connectButton,Color.WHITE);
        button_disconnect = utils.addImgToBtn("switch.png",button_disconnect,Color.WHITE);
        button_delete = utils.addImgToBtn("switch.png",button_delete,Color.WHITE);
        button_move = utils.addImgToBtn("switch.png",button_move, Color.WHITE);
        button_clear = utils.addImgToBtn("switch.png",button_clear,Color.WHITE);
        button_move = utils.addImgToBtn("switch.png",button_move,Color.WHITE);

        controlsPanel.add(label_controls,BorderLayout.PAGE_START);
        controlsPanel.add(container_play,BorderLayout.CENTER);

        this.add(buildPanel);
        this.add(controlsPanel);

    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
