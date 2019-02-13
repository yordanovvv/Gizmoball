package View;

import Controller.BuildListeners.*;
import Controller.MainFrameListeners.KeyListenerTest;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class BuildMode extends JPanel{

    private JPanel buildPanel;
    private JLabel label_components;
    private JButton button_triangle;
    private JButton button_circle;
    private JButton button_square;
    private JButton button_leftFlipper;
    private JButton button_rightFlipper;
    private JButton button_ball;
    private JButton button_absorber;

    private JPanel controlsPanel;
    private JLabel label_controls;
    private JButton button_connectButton;
    private JButton button_keypressConnect;
    private JButton button_disconnect;
    private JButton button_rotate;
    private JButton button_move;
    private JButton button_delete;
    private JButton button_clear;

    private MainFrame mainFrame;


    public BuildMode(MainFrame mainFrame){
        this.setBackground(new Color(0, 41, 57, 255));
        this.setBorder( BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(new GridLayout(0,1,10,10));

        this.mainFrame = mainFrame;

        this.setFocusable(true); //Needed for keylistener to work

        //TODO : Link this keylistener when doing connections
        //KeyListenerTest kl = new KeyListenerTest("build", mainFrame);
        //mainFrame.getFocusOwner().addKeyListener(kl);
        //mainFrame.addKeyListener(kl);

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
        button_absorber = new JButton("Absorber");
        button_leftFlipper = new JButton("Left flipper");
        button_rightFlipper = new JButton("Right flipper");
        button_ball= new JButton("Ball");

        button_square = utils.addImgToBtn("square.png",button_square, Color.RED);
        button_triangle = utils.addImgToBtn("triangle.png",button_triangle,Color.CYAN);
        button_circle = utils.addImgToBtn("circle.png",button_circle,Color.GREEN);
        button_absorber = utils.addImgToBtn("absorber.png", button_absorber, Color.MAGENTA);
        button_leftFlipper = utils.addImgToBtn("leftFlipper.png",button_leftFlipper,Color.YELLOW);
        button_rightFlipper = utils.addImgToBtn("rightFlipper.png",button_rightFlipper, Color.ORANGE);
        button_ball = utils.addImgToBtn("football.png",button_ball, Color.BLACK);

        container_build.add(button_square);
        container_build.add(button_triangle);
        container_build.add(button_circle);
        container_build.add(button_absorber);
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
        button_keypressConnect = new JButton("Connect Key");

        container_play.add(button_rotate);
        container_play.add(button_connectButton);
        container_play.add(button_disconnect);
        container_play.add(button_delete);
        container_play.add(button_clear);
        container_play.add(button_move);
        container_play.add(button_keypressConnect);


        button_rotate = utils.addImgToBtn("rotate.png",button_rotate, Color.WHITE);
        button_connectButton = utils.addImgToBtn("connected.png",button_connectButton,Color.WHITE);
        button_disconnect = utils.addImgToBtn("disconnected.png",button_disconnect,Color.WHITE);
        button_delete = utils.addImgToBtn("delete.png",button_delete,Color.WHITE);
        button_clear = utils.addImgToBtn("clearAll.png",button_clear,Color.WHITE);
        button_move = utils.addImgToBtn("move.png",button_move,Color.WHITE);
        button_keypressConnect = utils.addImgToBtn("keypress.png", button_keypressConnect, Color.WHITE);

        controlsPanel.add(label_controls,BorderLayout.PAGE_START);
        controlsPanel.add(container_play,BorderLayout.CENTER);

        //------- Craigs Testing -------------

        /*
        AddBallListener addBallListener = new AddBallListener(mainFrame);
        AddSquareGizmoListener addSquareGizmoListener = new AddSquareGizmoListener(mainFrame);
        AddCircleGizmoListener addCircleGizmoListener = new AddCircleGizmoListener(mainFrame);
        AddTriangleGizmoListener addTriangleGizmoListener = new AddTriangleGizmoListener(mainFrame);
        AddAbsorberGizmoListener addAbsorberGizmoListener = new AddAbsorberGizmoListener(mainFrame);
        */

        //Grid Listener only needs to be in BUILDMODE Class, as in PLAYMODE we are not clicking the grid.
        //GridClickListener board_listener = new GridClickListener(mainFrame.getGameBoard(), "square");

        AddButtonGizmoListener ballButListener = new AddButtonGizmoListener("ball", mainFrame.getGameBoard().getListener());
        AddButtonGizmoListener squareButListener = new AddButtonGizmoListener("square", mainFrame.getGameBoard().getListener());
        AddButtonGizmoListener circleButListener = new AddButtonGizmoListener("circle", mainFrame.getGameBoard().getListener());
        AddButtonGizmoListener triangleButListener = new AddButtonGizmoListener("triangle", mainFrame.getGameBoard().getListener());
        AddButtonGizmoListener absorbButListener = new AddButtonGizmoListener("absorber", mainFrame.getGameBoard().getListener());

        AddButtonGizmoListener leftFlipButListener = new AddButtonGizmoListener("leftFlipper", mainFrame.getGameBoard().getListener());
        AddButtonGizmoListener rightFlipButListener = new AddButtonGizmoListener("rightFlipper", mainFrame.getGameBoard().getListener());

        button_square.addMouseListener(squareButListener);
        button_triangle.addMouseListener(triangleButListener);
        button_circle.addMouseListener(circleButListener);
        button_absorber.addMouseListener(absorbButListener);

        button_leftFlipper.addMouseListener(leftFlipButListener);
        button_rightFlipper.addMouseListener(rightFlipButListener);

        button_ball.addMouseListener(ballButListener);

        //mainFrame.getGameBoard().addMouseListener(board_listener);
        //---------------------------------------

        this.add(buildPanel);
        this.add(controlsPanel);

    }
}
