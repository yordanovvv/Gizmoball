package View;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {

    private final int WIDTH = 500, HEIGTH = 500;

    public GameBoard(){
        init();
    }


    public void init(){

        this.setPreferredSize(new Dimension(WIDTH,HEIGTH));
        this.setMaximumSize(new Dimension(WIDTH,HEIGTH));
        this.setMinimumSize(new Dimension(WIDTH,HEIGTH));

        this.setBackground(Color.BLACK);

    }


}
