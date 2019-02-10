package View;

import Model.iGizmo;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class GameBoard extends JPanel implements Observer , iGameBoard{

    private final int WIDTH = 600, HEIGTH = 600;
    private LinkedList<iGizmo> components;

    public GameBoard() {
        components = new LinkedList<>();
        init();
        //model.addObserver(this) todo
    }

   public void paintComponent (Graphics g) {
        super.paintComponent(g);
    }

    private void init(){
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(WIDTH,HEIGTH));
        this.setBackground(Color.BLACK);
    }

    //--------------------------------------------------------
    //                    paint components

    private void paintCircle(Graphics g,int x, int y){
        g.setColor(new Color(80, 170, 44, 255));
        g.fillOval(x,y,30,30);
    }

    private void paintBall(Graphics g,int x, int y){
        g.setColor(new Color(255, 251, 255, 255));
        g.fillOval(x,y,30,30);
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

    private void paintAbsorber(Graphics g, int x, int y, int w1,int w2){
        g.setColor(new Color(170, 71, 144, 255));
        g.fillRect(x,y,w1,w2);
    }
    private void paintSquare(Graphics g,int x, int y){
        g.setColor(new Color(170, 10, 21, 255));
        g.fillRect(x,y,30,30);
    }

    //--------------------------------------------------------
    //                    observer
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
    //--------------------------------------------------------
    //                    interface stuff
    @Override
    public void paintGrid(Graphics g) {
        g.setColor(new Color(118, 170, 170, 255));
        for (int i = 0; i <= 600; i=i+(30)) {
            g.drawLine(i,0,i,600);
        }
        for (int i = 0; i <=600; i=i+(30)) {
            g.drawLine(0,i,600,i);
        }
    }

    @Override
    public void removeGrid(Graphics g) {
        g.clearRect(0,0,this.HEIGTH,this.WIDTH);
    }

    @Override
    public void updateGizmos(iGizmo cmp) {
        for (int i = 0; i < components.size(); i++) {
            if(components.get(i).getID() == cmp.getID()){
                components.remove(i);
                components.add(i,cmp);
            }
        }
    }

    @Override
    public void addGizmos(iGizmo cmp) {
        components.add(cmp);
    }

    @Override
    public void removeGizmos(iGizmo cmp) {
        for (int i = 0; i < components.size(); i++) {
            if(components.get(i).getID() == cmp.getID()){
                components.remove(i);
            }
        }
    }

    //--------------------------------------------------------
    //                    end of interface stuff

}


/*g.fillRect(0,0,600,600);


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


        g.setColor(new Color(255, 251, 255, 255));
        g.fillOval(450,200,20,20);*/
                /*
                ball sticks to the wall for a couple of ticks
                this is not needed for the actual gizmo.

                moses wants the plans updates,
                he wants to flesh out the progress report
                so what each person is goign to be doing
                */



       /* g.setColor(new Color(170, 10, 21, 255));
        g.fillRect(180,30,30,30);


        g.setColor(new Color(118, 170, 170, 255));

        for (int i = 0; i <= 600; i=i+(30)) {
            g.drawLine(i,0,i,600);
        }
        for (int i = 0; i <=600; i=i+(30)) {
            g.drawLine(0,i,600,i);
        }*/
