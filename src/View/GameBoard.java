package View;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GameBoard extends JPanel implements Observer {

    private final int WIDTH = 500, HEIGTH = 500;
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
                g.setColor(new Color(165, 237, 237, 255));
                g.drawLine(0,0,500,500);

                //paintGrid(g);
                this.setBackground(Color.BLACK);
            }
        };
        graphics.setBackground(Color.BLACK);
        graphics.setPreferredSize(new Dimension(WIDTH,HEIGTH));

        //this.add(graphics,BorderLayout.CENTER);
    }


    private void paintGrid( Graphics g){
        g.setColor(new Color(165, 237, 237, 255));
        g.setColor(Color.WHITE);
        /*
       double seg_size = 500/25;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGTH; j++) {
                g.drawLine(0, 0 , (int) seg_size*i, (int) seg_size*j);
            }

        }
        */
      g.drawLine(0,500,0,500);

    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
