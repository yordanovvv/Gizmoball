package View;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GameBoard extends JPanel implements Observer {

    private final int WIDTH = 600, HEIGTH = 600;
    private JPanel graphics;

    public GameBoard(){
        init();
    }


    public void init(){
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(WIDTH,HEIGTH));

        this.setBackground(Color.BLACK);

        graphics = new JPanel() {
            public void paintComponent(Graphics g) {
                g.fillRect(0,0,600,600);
                g.setColor(new Color(118, 170, 170, 255));
                for (int i = 0; i <= 600; i=i+(30)) {
                    g.drawLine(i,0,i,600);
                }
                for (int i = 0; i <=600; i=i+(30)) {
                    g.drawLine(0,i,600,i);
                }
            }
        };
        graphics.setBackground(Color.BLACK);
        graphics.setPreferredSize(new Dimension(WIDTH,HEIGTH));

        this.add(graphics,BorderLayout.CENTER);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
