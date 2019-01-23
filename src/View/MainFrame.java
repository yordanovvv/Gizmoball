package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

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
            private JLabel stateName;
            private JButton switchState;

        private JPanel gamestateBoard;

    private JPanel gameContainer;
        private JPanel gameBoard;
        private JPanel physicsBoard;



    private final int  WIDTH = 900, HEIGHT = 800;

    public MainFrame(){

        this.setResizable(false);
        this.setTitle("GROUP 11 - Gizmonball");
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       setUP();

       this.setVisible(true);

    }

    private void setUP(){
        //--------------------------------------------------------
        //                     INIT COMPONENTS
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        //--------------------------------------------------------
        //                     OPTIONS BOXES
        upperMenu = new JPanel();
        upperMenu.setSize(WIDTH,100);
        BorderLayout layout_upperMenu = new BorderLayout();
        upperMenu.setLayout(layout_upperMenu);
        upperMenu.setBackground(Color.WHITE);


        String[] menuOptions = new String[] {"Load", "Save","Quit"};
        optionsMenu = new JMenuBar();
        fileMenu = new JMenu("Options");
        fileMenu.setSize(100,50);

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

        gameBoard = new GameBoard();
        physicsBoard = new JPanel();

        gameContainer.add(gameBoard, BorderLayout.CENTER);
        gameContainer.add(physicsBoard, BorderLayout.PAGE_END);

        this.add(gameContainer, BorderLayout.CENTER);

        //--------------------------------------------------------
        //                     COMPONENT BOX
        componentBoard = new JPanel();
        componentBoard.setSize(400,HEIGHT);
        componentBoard.setBackground(Color.WHITE);

        switchBoard = new JPanel();
        GridLayout layout_switchBoard = new GridLayout(0,2);
        switchBoard.setLayout(layout_gameContainer);

        stateName = new JLabel("Play Mode");
        stateName.setFont(new Font("Monaco", Font.PLAIN, 20));
        switchState = new JButton("Switch State");
        switchState = addImgToBtn("/switch.png",switchState);

        componentBoard.add(stateName);
        componentBoard.add(switchState);


        this.add(componentBoard,BorderLayout.LINE_START);


    }


    private JButton addImgToBtn(String path,JButton button){
        JButton play = new JButton("Play");
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setBorder(null);
        button.setContentAreaFilled( false );

        return button;
    }
    /*

        this is how to make button invisible and fancy
        JButton play = new JButton("Play");
        try {
            Image img = ImageIO.read(getClass().getResource("play.png"));
            img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            play.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        play.setVerticalTextPosition(SwingConstants.BOTTOM);
        play.setHorizontalTextPosition(SwingConstants.CENTER);
        play.setBorder(null);
        play.setContentAreaFilled( false );


     */


}
