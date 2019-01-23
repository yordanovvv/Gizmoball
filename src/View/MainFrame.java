package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static void main(String[] args) {
        MainFrame view = new MainFrame();
    }

    private JPanel upperMenu;
        private JMenuBar optionsMenu;
            JMenu fileMenu;
                JMenuItem saveItem, loadItem, quitItem;

    private JPanel componentBoard;
        private JPanel switchBoard;
            private JButton button_switchState;

        private JPanel gamestateBoard;


    private JPanel gameContainer;
        private JPanel gameBoard;
        private JPanel physicsBoard;
                private JLabel label_Gravity;
                private JLabel label_Friction;
                private JLabel label_BallPosition;
                private JLabel label_Velocity;

                private JLabel output_Gravity;
                private JLabel output_Friction;
                private JLabel output_BallPosition;
                private JLabel output_Velocity;



    private final int  WIDTH = 900, HEIGHT = 600;

    public MainFrame(){

        this.setResizable(false);
        this.setTitle("GROUP 11 - Gizmonball");
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       setUP();

       this.setVisible(true);

    }

    private void setUP(){
        Utils util = new Utils();

        //--------------------------------------------------------
        //                     INIT COMPONENTS
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        //--------------------------------------------------------
        //                     OPTIONS BOXES
        BorderLayout layout_upperMenu = new BorderLayout();

        upperMenu = new JPanel();
        upperMenu.setSize(WIDTH,100);
        upperMenu.setLayout(layout_upperMenu);
        upperMenu.setBackground(new Color(7, 7, 44, 160));

        optionsMenu = new JMenuBar();
        fileMenu = new JMenu("Options");

        optionsMenu.setBackground(null);
        fileMenu.setBackground(null);

        saveItem  = new JMenuItem("Save");
        loadItem = new JMenuItem("Load");
        quitItem = new JMenuItem("Quit");

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
        gameContainer.setPreferredSize(new Dimension(500, 500));
        gameBoard = new GameBoard();

        physicsBoard = new JPanel();

        //--------------------------------------------------------
        //                    physicsBoard

        label_BallPosition = new JLabel("Ball Position");
        label_Friction = new JLabel("Friction");
        label_Gravity = new JLabel("Gravity");
        label_Velocity = new JLabel("Velocity");

        label_BallPosition = util.editLabel(label_BallPosition,12,Color.WHITE);
        label_Friction = util.editLabel(label_Friction,12,Color.WHITE);
        label_Gravity = util.editLabel(label_Gravity,12,Color.WHITE);
        label_Velocity = util.editLabel(label_Velocity,12,Color.WHITE);

        output_BallPosition = new JLabel("41.2");
        output_Friction = new JLabel("26");
        output_Gravity = new JLabel("54");
        output_Velocity = new JLabel("10");

        Color output_color = new Color(223, 223, 223, 154);
        output_BallPosition = util.editLabel(output_BallPosition,11,output_color);
        output_Friction = util.editLabel(output_Friction,11,output_color);
        output_Gravity = util.editLabel(output_Gravity,11,output_color);
        output_Velocity = util.editLabel(output_Velocity,11,output_color);

        GridLayout layout_physicsComponent = new GridLayout(2,2,20,20);

        physicsBoard.setLayout(layout_physicsComponent);
        physicsBoard.setBackground(new Color(3, 3, 14, 160));

        physicsBoard.setPreferredSize(new Dimension(WIDTH - 500, HEIGHT - 500));
        physicsBoard.add(label_BallPosition);
        physicsBoard.add(output_BallPosition);

        physicsBoard.add(label_Friction);
        physicsBoard.add(output_Friction);

        physicsBoard.add(label_Gravity);
        physicsBoard.add(output_Gravity);

        physicsBoard.add(label_Velocity);
        physicsBoard.add(output_Velocity);

        physicsBoard.setSize(500,100);

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
        componentBoard.setSize(400,HEIGHT);
        componentBoard.setBackground(Color.WHITE);

        switchBoard = new JPanel();
        BorderLayout layout_switchBoard = new BorderLayout();
        componentBoard.setLayout(layout_switchBoard);

        button_switchState = new JButton("Switch State");//switch.png
        //
        button_switchState = util.addImgToBtn("switch.png", button_switchState);

        componentBoard.add(button_switchState, BorderLayout.PAGE_START);


        //--------------------------------------------------------
        //                    gamestateBoard

        gamestateBoard = new BuildMode();

        componentBoard.add(gamestateBoard,BorderLayout.CENTER);
        this.add(componentBoard,BorderLayout.LINE_START);


    }
}
