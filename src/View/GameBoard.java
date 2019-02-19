package View;

import static java.lang.Math.toIntExact;

import Controller.BuildListeners.GridClickListener;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import java.util.Observable;
import java.util.Observer;

public class GameBoard extends JPanel implements Observer{

    private final int WIDTH = 600, HEIGTH = 600;
    private String mode;
    GizmoballModel m;
    Ball b;
    GridClickListener gameBoardListener;
    public GameBoard(String mode, GizmoballModel m) {
        this.m = m;
        b = m.getBall();
        this.mode = mode;
        init();
    }

    //TODO Check if this is in right place -C
    //TODO Continue this
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

        updateListener();
    }

    private void updateListener(){
        gameBoardListener = new GridClickListener(this, "square");
        //clear all previous mouse listeners
        for (MouseListener m : this.getMouseListeners()){
            this.removeMouseListener(m);
        }
        if (mode.equals("BUILD")) {
            this.addMouseListener(gameBoardListener);
        }
    }
    //---- TODO : Check this method is in right place
    public GridClickListener getListener() {
        return gameBoardListener;
    }

    /**
     * Gets the list of gizmos + ball from the Model and paints them
     * @param g inner graphics component
     */
   public void paintComponent (Graphics g) {
        super.paintComponent(g);

        Wall w = m.getWalls();
        w.getXCoord();
        g.setColor(Color.MAGENTA);
        g.drawLine(w.getXCoord()*30,w.getYCoord()*30,w.getXCoord2()*30,w.getYCoord()*30);
        g.drawLine(w.getXCoord()*30,w.getYCoord()*30,w.getXCoord()*30,w.getYCoord2()*30);
        g.drawLine(w.getXCoord2()*30,w.getYCoord2()*30,w.getXCoord2()*30,w.getYCoord()*30);
        g.drawLine(w.getXCoord2()*30,w.getYCoord2()*30,w.getXCoord()*30,w.getYCoord2()*30);

        Graphics2D g2 = (Graphics2D) g;
        int x , y;
        for(iGizmo gizmo : m.getGizmos()){
            x = gizmo.getXCoord() * 30;
            y = gizmo.getYCoord() * 30;
            switch (gizmo.getGizmoType()){
                case "GizmoCircle":
                    paintCircle(g2, x,y);
                    break;
                case "Square":
                    paintSquare(g2,x,y);
                    break;
                case "Triangle":
                    paintTriangle(g2,x,y,gizmo.getRotationAngle());
                    break;
                case "Absorber":
                    paintAbsorber(g2,x,y,gizmo.getWidth()*30,gizmo.getHeight()*30);
                    break;
                case "LeftFlipper":
                    paintLeftFlipper(g2,x,y,gizmo.getRotationAngle());
                    break;
                case "RightFlipper":
                    paintRightFlipper(g2,x,y,gizmo.getRotationAngle());
                    break;
            }
        }

        if(mode.equals("BUILD")){
            paintGrid(g2);
        }
        //todo please check this
       Ball b = m.getBall();
       // paintBall(g2,toIntExact(Math.round(b.getExactX())),toIntExact(Math.round(b.getExactY())));
       //paintBall(g2,b.getExactX(),b.getExactY());

       int p = (int) (b.getExactX() - b.getRadius());
       int q = (int) (b.getExactY() - b.getRadius());
       int width = (int) (2 * b.getRadius());
       paintBall(g,p, q, width);

       repaint();
    }

    //--------------------------------------------------------
    //                    PAINTING COMPONENTS
    private void paintCircle(Graphics g,int x, int y){
        g.setColor(new Color(80, 170, 44, 255));
        g.fillOval(x,y,30,30);
    }

    private void paintBall(Graphics g,int x, int y, int width){
        g.setColor(new Color(255, 251, 255, 255));
        //g.fillOval(x-8,y-8, 16,16);
        g.fillOval(x, y, width, width);
    }

    private void paintTriangle(Graphics g,int x, int y, int angle){
        Graphics2D g2 =(Graphics2D) g.create();

        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2.getTransform();
        transform.rotate(Math.toRadians(angle), x + (30/2), y + (30/2));
        g2.transform(transform);


        int xPoly[] = {x, (x+30),x, (x+30), (x+30), (x+30)};
        int yPoly[] = {y, (y+30),y, y     , y      ,(y+30)};

        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        g2.setColor(new Color(59, 112, 170, 255));
        g2.fillPolygon(poly);

        g2.setTransform(old);
        g2.dispose();
    }

    private void paintLeftFlipper(Graphics g,int x, int y, int angle){
        Graphics2D g2 =(Graphics2D) g.create();


        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2.getTransform();
        transform.rotate(Math.toRadians(angle), x + (30/2) + 5, y + 5);
        g2.transform(transform);

        g2.setColor(new Color(170, 169, 50, 255));
        int[] px = {x,x+15 ,x+13 ,x+1};
        int[] py = {y+5,y+5  ,y+56 ,y+56};
        Polygon poly1 = new Polygon(px, py, px.length);
        g2.fillPolygon(poly1);
        g2.fillOval(x,y,15,15);
        g2.fillOval(x+1,y+48,12,12);
        g2.setColor(new Color(0, 0, 0, 255));
        g2.fillOval(x+5,y+6,5,5);


        g2.setTransform(old);
        g2.dispose();
    }

    private void paintRightFlipper(Graphics g,int x, int y, int angle){
        Graphics2D g2 =(Graphics2D) g.create();

        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2.getTransform();
        transform.rotate(Math.toRadians(angle), x + (30/2) + 5, y + 5);
        g2.transform(transform);

        g2.setColor(new Color(170, 169, 50, 255));

        int[] pxFR = {x+15,x+15+15 ,x+13+15 ,x+1+15};
        int[] pyFR = {y+5 ,y+5     ,y+56    ,y+56};
        Polygon polyFR= new Polygon(pxFR, pyFR, pyFR.length);
        g2.fillPolygon(polyFR);

        g2.fillOval(x+15,y,15,15);
        g2.fillOval(x+1+15,y+48,12,12);
        g2.setColor(new Color(0, 0, 0, 255));
        g2.fillOval(x+5+15,y+6,5,5);

        g2.setTransform(old);
        g2.dispose();
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