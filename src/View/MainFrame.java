package View;
import Controller.MainFrameListeners.ChangeModeListener;
import Controller.MainFrameListeners.ControlListener;
import Model.iModel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

public class MainFrame extends JFrame implements iMainFrame, Observer {

    private JPanel upperMenu;
    private JMenuBar optionsMenu;
    private JMenu fileMenu;
    private JMenuItem saveItem, loadItem, quitItem;

    private JPanel componentBoard;
    private JPanel switchBoard;
    private JButton button_switchState;
    private JLabel label_sameState;

    private JPanel gamestateBoard;

    private JPanel gameContainer;
    //private JPanel gameBoard;

    private GameBoard gameBoard;

    private JPanel physicsBoard;
    private JLabel label_Gravity;
    private JLabel label_Friction;
    private JLabel label_BallPosition;
    private JLabel label_Velocity;

    private JTextField output_Gravity;
    public JTextField output_Friction;
    private JTextField output_BallPosition;
    public JTextField output_Velocity;

    private iModel model;

    private final int WIDTH = 1000, HEIGHT = 900;

    public MainFrame(iModel model) {

        this.model = model;
        this.pack();
       // this.setResizable(false);
        this.setTitle("GROUP 11 - Gizmoball");
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.setFocusable(true); //Needed for keylistener to work

        setUP();

        this.setVisible(true);

    }

    public iModel getModel(){
        return model;
    }

    private void setUP() {
        Utils util = new Utils();

        //--------------------------------------------------------
        //                     INIT COMPONENTS

        //--------------------------------------------------------
        //                     OPTIONS BOXES
        BorderLayout layout_upperMenu = new BorderLayout();
        ControlListener menuListener = new ControlListener(this);

        upperMenu = new JPanel();
        upperMenu.setSize(WIDTH, 100);
        upperMenu.setLayout(layout_upperMenu);
        upperMenu.setBackground(Color.WHITE);

        optionsMenu = new JMenuBar();
        optionsMenu.setOpaque(true);
        optionsMenu.setBackground(Color.WHITE);

        fileMenu = new JMenu("Options");
        fileMenu.setOpaque(true);
        fileMenu.setBackground(Color.WHITE);

        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(menuListener);
        saveItem.setActionCommand("Save");

        loadItem = new JMenuItem("Load");
        loadItem.addActionListener(menuListener);
        loadItem.setActionCommand("Load");

        quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(menuListener);
        quitItem.setActionCommand("Quit");

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(quitItem);

        optionsMenu.add(fileMenu);

        upperMenu.add(optionsMenu, BorderLayout.LINE_START);
        this.add(upperMenu, BorderLayout.PAGE_START);

        //--------------------------------------------------------
        //                     GAME BOX

        gameContainer = new JPanel();

        BorderLayout layout_gameContainer = new BorderLayout();
        gameContainer.setLayout(layout_gameContainer);
        int size = 700;
        gameContainer.setPreferredSize(new Dimension(size, size));
        gameContainer.setMaximumSize(new Dimension(size,size));
        gameContainer.setMinimumSize(new Dimension(size,size));
        gameBoard = new GameBoard("BUILD",model);

        physicsBoard = new JPanel();

        //--------------------------------------------------------
        //                    physicsBoard

        label_BallPosition = new JLabel("Ball Position");
        label_Friction = new JLabel("Friction");
        label_Gravity = new JLabel("Gravity");
        label_Velocity = new JLabel("Velocity");

        label_BallPosition = util.editLabel(label_BallPosition, 12, Color.BLACK);
        label_Friction = util.editLabel(label_Friction, 12, Color.BLACK);
        label_Gravity = util.editLabel(label_Gravity, 12, Color.BLACK);
        label_Velocity = util.editLabel(label_Velocity, 12, Color.BLACK);

        output_BallPosition = new JTextField("");
        output_Friction = new JTextField("");
        output_Gravity = new JTextField("");
        output_Velocity = new JTextField("");

        output_BallPosition = util.styleTextField(output_BallPosition, 11);
        output_Friction = util.styleTextField(output_Friction, 11);
        output_Gravity = util.styleTextField(output_Gravity, 11);
        output_Velocity = util.styleTextField(output_Velocity, 11);

        GridLayout layout_physicsComponent = new GridLayout(2, 2, 20, 20);

        physicsBoard.setLayout(layout_physicsComponent);
        physicsBoard.setBackground(Color.WHITE);

        physicsBoard.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                new CompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 41, 57, 255), 6, true),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                )
        ));

        physicsBoard.setMaximumSize(new Dimension(WIDTH - 700, HEIGHT - 700));
        physicsBoard.setMinimumSize(new Dimension(WIDTH - 700, HEIGHT - 700));
        physicsBoard.add(label_BallPosition);
        physicsBoard.add(output_BallPosition);

        physicsBoard.add(label_Friction);
        physicsBoard.add(output_Friction);

        physicsBoard.add(label_Gravity);
        physicsBoard.add(output_Gravity);

        physicsBoard.add(label_Velocity);
        physicsBoard.add(output_Velocity);

        //--------------------------------------------------------
        //                    gameContainer

        gameContainer.add(gameBoard, BorderLayout.CENTER);
        gameContainer.add(physicsBoard, BorderLayout.PAGE_END);



        this.add(gameContainer, BorderLayout.CENTER);

        //------------------------------------------------------------------
        //                     COMPONENT BOX

        //--------------------------------------------------------
        //                    componentBoard

        componentBoard = new JPanel();
        componentBoard.setSize(400, HEIGHT);

        switchBoard = new JPanel(new FlowLayout(FlowLayout.LEFT));
        switchBoard.setBackground((new Color(0, 41, 57, 255)));

        label_sameState = new JLabel("   GAME STATE");
        label_sameState = util.editLabel(label_sameState, 20, Color.WHITE);
        //label_sameState.setHorizontalAlignment(Label.CENTER);

        BorderLayout layout_switchBoard = new BorderLayout();
        componentBoard.setLayout(layout_switchBoard);

        button_switchState = new JButton("Switch State");//switch.png
        button_switchState = util.addImgToBtn("switch.png", button_switchState, Color.WHITE);
        button_switchState.addActionListener(new ChangeModeListener(this,model));
        button_switchState.setFocusable(false);

        switchBoard.add(button_switchState);
        switchBoard.add(label_sameState);

        componentBoard.add(switchBoard, BorderLayout.PAGE_START);


        //--------------------------------------------------------
        //                    gamestateBoard

        gamestateBoard = new BuildMode(this);

        componentBoard.add(gamestateBoard, BorderLayout.CENTER);
        this.add(componentBoard, BorderLayout.LINE_START);

    }

    @Override
    public void switchModes(int mode) {

        //Clear all the keylisteners
        for (KeyListener kl : this.getKeyListeners()) {
            this.removeKeyListener(kl);
        }

        this.invalidate();
        if (mode == 1) {
            label_sameState.setText("   GAME STATE");
            button_switchState.setActionCommand("PLAY");
            componentBoard.remove(gamestateBoard);
            gamestateBoard = new PlayMode(this);
            componentBoard.add(gamestateBoard, BorderLayout.CENTER);

            gameContainer.remove(gameBoard);
            gameBoard = new GameBoard("PLAY",model);
            gameContainer.add(gameBoard, BorderLayout.CENTER);

        } else {
            label_sameState.setText("  BUILD STATE");
            button_switchState.setActionCommand("BUILD");
            gameContainer.remove(gameBoard);
            gameBoard = new GameBoard("BUILD",model);
            gameContainer.add(gameBoard, BorderLayout.CENTER);

            componentBoard.remove(gamestateBoard);
            gamestateBoard = new BuildMode(this);
            componentBoard.add(gamestateBoard, BorderLayout.CENTER);
        }

        gameBoard.revalidate();
        gameBoard.repaint();

        this.validate();
        this.repaint();
    }

    //Currently using this to pass the "gameBoard" into the buildMode class, so I can add the appropriate listener. -C
    public GameBoard getGameBoard() {
        return gameBoard;
    }
    //-------

    @Override
    public int generatePopUp(String title, String msg) {
        JFrame frame = new JFrame();
        String[] options = {"Yes", "No"};
        return JOptionPane.showOptionDialog(frame,
                msg,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
    }

    @Override
    public void quit() {
        this.setVisible(false);
        System.exit(0);
    }


    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}
