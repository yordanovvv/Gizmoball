package View;

import Model.iGizmo;

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


                g.setColor(new Color(80, 170, 44, 255));
                g.fillOval(0,0,30,30);
                g.fillOval(0,30,30,30);

                g.setColor(new Color(170, 71, 144, 255));
                g.fillRect(0,540,600,60);

                int xPoly[] = {120, (120+30),120, (120+30), (120+30), (120+30)};
                int yPoly[] = {120, (120+30),120, 120     , 120      ,(120+30)};

                Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
                g.setColor(new Color(59, 112, 170, 255));
                g.fillPolygon(poly);

                g.setColor(new Color(170, 169, 50, 255));
                int x = 180,y = 180;

                int[] px = {x,x+15 ,x+13 ,x+1};
                int[] py = {y+5,y+5  ,y+56 ,y+56};
                Polygon poly1 = new Polygon(px, py, px.length);
                g.fillPolygon(poly1);
                g.fillOval(x,y,15,15);
                g.fillOval(x+1,y+48,12,12);
                g.setColor(new Color(0, 0, 0, 255));
                g.fillOval(x+5,y+6,5,5);



                /*
                ball sticks to the wall for a couple of ticks
                this is not needed for the actual gizmo.

                moses wants the plans updates,
                he wants to flesh out the progress report
                so what each person is goign to be doing
                */

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

    public void paintcmp(int x,int y, String cmp){
        double x_coord = x*30;
        double y_coord = y*30;
    }

    public void addCmp(Graphics g,String type, int x, int y, int w1,int w2){
        switch (type){
            case "C":
                g.setColor(new Color(80, 170, 44, 255));
                g.fillOval(x,y,30,30);
                break;
            case "T":

                int xPoly[] = {x, (x+30),x, (x+30), (x+30), (x+30)};
                int yPoly[] = {y, (y+30),y, y     , y      ,(y+30)};

                Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
                g.setColor(new Color(59, 112, 170, 255));
                g.fillPolygon(poly);
                break;

            case "F L":
                g.setColor(new Color(170, 169, 50, 255));
                int[] px = {x,x+15 ,x+13 ,x+1};
                int[] py = {y+5,y+5  ,y+56 ,y+56};
                Polygon poly1 = new Polygon(px, py, px.length);
                g.fillPolygon(poly1);
                g.fillOval(x,y,15,15);
                g.fillOval(x+1,y+48,12,12);
                g.setColor(new Color(0, 0, 0, 255));
                g.fillOval(x+5,y+6,5,5);
                break;

            case "F R":
                g.setColor(new Color(170, 131, 41, 255));
                int[] pxFR = {x+15,x+15+15 ,x+13+15 ,x+1+15};
                int[] pyFR = {y+5,y+5  ,y+56 ,y+56};
                Polygon polyFR= new Polygon(pxFR, pyFR, pyFR.length);
                g.fillPolygon(polyFR);
                g.fillOval(x+15,y,15,15);
                g.fillOval(x+1+15,y+48,12,12);
                g.setColor(new Color(0, 0, 0, 255));
                g.fillOval(x+5+15,y+6,5,5);
                break;
            case "A":
                g.setColor(new Color(170, 71, 144, 255));
                g.fillRect(x,y,w1,w2);
                break;
        }

    }

    public void paintGrid(Graphics g){
        for (int i = 0; i <= 600; i=i+(30)) {
            g.drawLine(i,0,i,600);
        }
        for (int i = 0; i <=600; i=i+(30)) {
            g.drawLine(0,i,600,i);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
