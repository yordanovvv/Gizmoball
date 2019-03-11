package View;

import Controller.BuildListeners.AddButtonGizmoListener;
import Controller.BuildListeners.ClearClickListener;
import Controller.BuildListeners.GridClickListener;

import javax.swing.*;
import java.awt.*;

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
    private JButton button_star;

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
        button_circle= new JButton("GizmoCircle");
        button_absorber = new JButton("Absorber");
        button_leftFlipper = new JButton("Left flipper");
        button_rightFlipper = new JButton("Right flipper");
        button_ball= new JButton("Ball");
        button_star = new JButton("Star");

        button_square = utils.addImgToBtn("square.png",button_square, Color.RED);
        button_triangle = utils.addImgToBtn("triangle.png",button_triangle,Color.CYAN);
        button_circle = utils.addImgToBtn("circle.png",button_circle,Color.GREEN);
        button_absorber = utils.addImgToBtn("absorber.png", button_absorber, Color.MAGENTA);
        button_leftFlipper = utils.addImgToBtn("leftFlipper.png",button_leftFlipper,Color.YELLOW);
        button_rightFlipper = utils.addImgToBtn("rightFlipper.png",button_rightFlipper, Color.ORANGE);
        button_ball = utils.addImgToBtn("football.png",button_ball, Color.BLACK);
        button_star =  utils.addImgToBtn("star.png",button_star, new Color(102, 16, 193, 255));

        container_build.add(button_square);
        container_build.add(button_triangle);
        container_build.add(button_circle);
        container_build.add(button_absorber);
        container_build.add(button_leftFlipper);
        container_build.add(button_rightFlipper);
        container_build.add(button_ball);
        container_build.add(button_star);


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

        GridClickListener lis = mainFrame.getGameBoard().getListener();

        //TODO Refactor ADDBUTTONGIZMOLISTENER
        AddButtonGizmoListener ballButListener = new AddButtonGizmoListener("ball", lis);
        AddButtonGizmoListener squareButListener = new AddButtonGizmoListener("square", lis);
        AddButtonGizmoListener circleButListener = new AddButtonGizmoListener("circle", lis);
        AddButtonGizmoListener triangleButListener = new AddButtonGizmoListener("triangle", lis);
        AddButtonGizmoListener absorbButListener = new AddButtonGizmoListener("absorber", lis);
        AddButtonGizmoListener starButListener = new AddButtonGizmoListener("star", lis);

        AddButtonGizmoListener leftFlipButListener = new AddButtonGizmoListener("leftFlipper", lis);
        AddButtonGizmoListener rightFlipButListener = new AddButtonGizmoListener("rightFlipper", lis);

        button_square.addMouseListener(squareButListener);
        button_triangle.addMouseListener(triangleButListener);
        button_circle.addMouseListener(circleButListener);
        button_absorber.addMouseListener(absorbButListener);
        button_star.addMouseListener(starButListener);

        button_leftFlipper.addMouseListener(leftFlipButListener);
        button_rightFlipper.addMouseListener(rightFlipButListener);

        button_ball.addMouseListener(ballButListener);

        //-------------
        //controls
        AddButtonGizmoListener rotateListener = new AddButtonGizmoListener("rotate", lis);
        AddButtonGizmoListener connectListener = new AddButtonGizmoListener("connect", lis);
        AddButtonGizmoListener disconnectListener = new AddButtonGizmoListener("disconnect", lis);
        AddButtonGizmoListener deleteListener = new AddButtonGizmoListener("delete", lis);
        AddButtonGizmoListener moveListener = new AddButtonGizmoListener("move", lis);
        AddButtonGizmoListener connectKeyListener = new AddButtonGizmoListener("connectKey", lis);

        ClearClickListener clearClickListener = new ClearClickListener(mainFrame.getModel());

        button_rotate.addMouseListener(rotateListener);
        button_delete.addMouseListener(deleteListener);
        button_connectButton.addMouseListener(connectListener);
        button_disconnect.addMouseListener(disconnectListener);
        button_clear.addMouseListener(clearClickListener);
        button_move.addMouseListener(moveListener);
        button_keypressConnect.addMouseListener(connectKeyListener);

        //mainFrame.getGameBoard().addMouseListener(board_listener);
        //---------------------------------------

        this.add(buildPanel);
        this.add(controlsPanel);

    }
}
