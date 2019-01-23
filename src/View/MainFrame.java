package View;

import javax.swing.*;

public class MainFrame extends JFrame {

    private final int  WIDTH = 500, HEIGHT = 200;

    public MainFrame(){

        this.setResizable(false);
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /*

        this is how to make button invisable and fancy
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
