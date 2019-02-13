package View;

import static java.lang.Math.toIntExact;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameBoard extends JPanel implements Observer{

    private final int WIDTH = 600, HEIGTH = 600;
    private String mode;
    GizmoballModel m;
    Ball b;
    public GameBoard(String mode, GizmoballModel m) {
        init();
        this.m = m;
        b = m.getBall();
        this.mode = mode;
    }

    //TODO Check if this is in right place -C
    public GizmoballModel getGizModel(){
        return m;
    }

    /**
     * Initialises the game screen
     */
    private void init(){
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(WIDTH,HEIGTH));
        this.setBackground(Color.BLACK);
    }

    /**
     * Gets the list of gizmos + ball from the Model and paints them
     * @param g inner graphics component
     */
   public void paintComponent (Graphics g) {
        super.paintComponent(g);



        Graphics2D g2 = (Graphics2D) g;
        int x , y;
        for(iGizmo gizmo : m.getGizmos()){
            x = gizmo.getXCoord() * 30; //we need scale here so do not remove
            y = gizmo.getYCoord()* 30;
            switch (gizmo.getGizmoType()){
                case "Circle":
                    paintCircle(g2, x,y);
                    break;
                case "Square":
                    paintSquare(g2,x,y);
                    break;
                case "Triangle":
                    paintTriangle(g2,x,y);
                    break;

                case "Absorber":
                    paintAbsorber(g2,x,y,gizmo.getWidth()*30,gizmo.getHeight()*30);
                    break;

                case "LeftFlipper":
                    paintLeftFlipper(g2,x,y);
                    break;
                case "RightFlipper":
                    paintRightFlipper(g2,x,y);
                    break;
            }
        }

        if(mode.equals("BUILD")){
            paintGrid(g2);
        }
        //todo please check this
       Ball b = m.getBall();
        int xBall = toIntExact(Math.round(b.getExactX()));//*30;
        int yBall = toIntExact(Math.round(b.getExactY()));//*30;
       paintBall(g2,xBall,yBall);
       //Absorber a = (Absorber) m.getAbsorber();
       //paintAbsorber(g2,a.getXCoord2(), a.getYCoord2(), a.getXCoord(), a.getYCoord());

       repaint();
    }

    //--------------------------------------------------------
    //                    PAINTING COMPONENTS
    private void paintCircle(Graphics g,int x, int y){
        g.setColor(new Color(80, 170, 44, 255));
        g.fillOval(x,y,30,30);
    }

    private void paintBall(Graphics g,int x, int y){
        g.setColor(new Color(255, 251, 255, 255));
        g.fillOval(x,y,16,16);
    }

    private void paintTriangle(Graphics g,int x, int y){
        int xPoly[] = {x, (x+30),x, (x+30), (x+30), (x+30)};
        int yPoly[] = {y, (y+30),y, y     , y      ,(y+30)};

        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        g.setColor(new Color(59, 112, 170, 255));
        g.fillPolygon(poly);
    }

    private void paintLeftFlipper(Graphics g,int x, int y){
        g.setColor(new Color(170, 169, 50, 255));
        int[] px = {x,x+15 ,x+13 ,x+1};
        int[] py = {y+5,y+5  ,y+56 ,y+56};
        Polygon poly1 = new Polygon(px, py, px.length);
        g.fillPolygon(poly1);
        g.fillOval(x,y,15,15);
        g.fillOval(x+1,y+48,12,12);
        g.setColor(new Color(0, 0, 0, 255));
        g.fillOval(x+5,y+6,5,5);
    }

    private void paintRightFlipper(Graphics g,int x, int y){
        g.setColor(new Color(170, 169, 50, 255));
        int[] pxFR = {x+15,x+15+15 ,x+13+15 ,x+1+15};
        int[] pyFR = {y+5,y+5  ,y+56 ,y+56};
        Polygon polyFR= new Polygon(pxFR, pyFR, pyFR.length);
        g.fillPolygon(polyFR);
        g.fillOval(x+15,y,15,15);
        g.fillOval(x+1+15,y+48,12,12);
        g.setColor(new Color(0, 0, 0, 255));
        g.fillOval(x+5+15,y+6,5,5);
    }

    private void paintAbsorber(Graphics g, int x, int y, int width,int height){
        g.setColor(new Color(170, 71, 144, 255));
        g.fillRect(x,y,width,height);
    }
    private void paintSquare(Graphics g,int x, int y){
        g.setColor(new Color(170, 10, 21, 255));
        g.fillRect(x,y,30,30);
    }

    //--------------------------------------------------------
    //                     OBSERVER
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
    //--------------------------------------------------------
    //                    GRID CONTROLS
    public void paintGrid(Graphics g) {
        g.setColor(new Color(118, 170, 170, 255));
        for (int i = 0; i <= 600; i=i+(30)) {
            g.drawLine(i,0,i,600);
        }
        for (int i = 0; i <=600; i=i+(30)) {
            g.drawLine(0,i,600,i);
        }
    }
}