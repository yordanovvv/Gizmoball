package View;

import static java.lang.Math.toIntExact;

import Controller.BuildListeners.GridClickListener;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameBoard extends JPanel implements Observer{

    private final int WIDTH = 600, HEIGTH = 600;
    private String mode;
    iModel m;
    ArrayList<Ball> b;
    GridClickListener gameBoardListener;

    public GameBoard(String mode, iModel m) {
        this.m = m;
        b = m.getBalls();
        this.mode = mode;
        init();
    }

    //TODO Check if this is in right place -C
    //TODO Continue this
    public iModel getGizModel(){
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
        Color c;
        for(iGizmo gizmo : m.getGizmos()){
            x = gizmo.getXCoord() * 30;
            y = gizmo.getYCoord() * 30;
            c = gizmo.getColor();
            switch (gizmo.getGizmoType()){
                case "Circle":
                    paintCircle(g2, x,y,c);
                    break;
                case "Square":
                    paintSquare(g2,x,y,c);
                    break;
                case "Triangle":
                    paintTriangle(g2,x,y,gizmo.getRotationAngle(),c);
                    break;
                case "Absorber":
                    paintAbsorber(g2,x,y,gizmo.getWidth()*30,gizmo.getHeight()*30,c);
                    break;
                case "LeftFlipper":
                    paintLeftFlipper(g2,x,y,gizmo.getRotationAngle(),c);
                    break;
                case "RightFlipper":
                    paintRightFlipper(g2,x,y,gizmo.getRotationAngle(),c);
                    break;
                case"Star":
                    paintStar(g,x,y,gizmo.getRotationAngle(),c);
                    break;
            }
        }

        if(mode.equals("BUILD")){
            paintGrid(g2);
        }
        //todo please check this
       ArrayList<Ball> balls = m.getBalls();
       // paintBall(g2,toIntExact(Math.round(b.getExactX())),toIntExact(Math.round(b.getExactY())));
       //paintBall(g2,b.getExactX(),b.getExactY());

       for (Ball b : balls)
       {
           int p = (int) (b.getExactX() - b.getRadius());
           int q = (int) (b.getExactY() - b.getRadius());
           int width = (int) (2 * b.getRadius());
           paintBall(g,p, q, width);
       }

       repaint();
    }

    //--------------------------------------------------------
    //                    PAINTING COMPONENTS
    private void paintCircle(Graphics g,int x, int y,Color c){
        g.setColor(c);
        g.fillOval(x,y,30,30);
    }

    private void paintBall(Graphics g,int x, int y, int width){
        g.setColor(new Color(255, 251, 255, 255));
        //g.fillOval(x-8,y-8, 16,16);
        g.fillOval(x, y, width, width);
    }

    private void paintTriangle(Graphics g,int x, int y, int angle,Color c){
        Graphics2D g2 =(Graphics2D) g.create();

        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2.getTransform();
        transform.rotate(Math.toRadians(angle), x + (30/2), y + (30/2));
        g2.transform(transform);


        int xPoly[] = {x, (x+30),x, (x+30), (x+30), (x+30)};
        int yPoly[] = {y, (y+30),y, y     , y      ,(y+30)};

        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        g2.setColor(c);
        g2.fillPolygon(poly);

        g2.setTransform(old);
        g2.dispose();
    }


    private void paintStar(Graphics g,int x, int y, int angle,Color c){
        Graphics2D g2 =(Graphics2D) g.create();

        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2.getTransform();
        transform.rotate(Math.toRadians(angle), x + 30, y + 30);
        g2.transform(transform);
        g2.setColor(new Color(114, 57, 187, 255));

        /*
        A stars points are in a polygon:

                           (1)
                     (2)         (5)

                       (3)    (4)
            from the points, we can draw lines from:
            (1)-  (3),(3) - (5), (5) - (2), (2) - (4), (4) - (1)
         */

        int polyPoint1_x = x+30, polyPoint1_y = y;
        int polyPoint2_x = x, polyPoint2_y = y+22;
        int polyPoint3_x = x+12, polyPoint3_y = y+56;
        int polyPoint4_x =x+48, polyPoint4_y = y+56;
        int polyPoint5_x = x+58, polyPoint5_y = y+22;

        int[] px = {polyPoint1_x,polyPoint3_x,polyPoint5_x,polyPoint2_x,polyPoint4_x,polyPoint1_x};
        int[] py = {polyPoint1_y,polyPoint3_y,polyPoint5_y,polyPoint2_y,polyPoint4_y,polyPoint1_y};
        Polygon poly1 = new Polygon(px, py, px.length);
        g2.fillPolygon(poly1);

        g2.setColor(new Color(226, 219, 49, 255));
        g2.drawLine(polyPoint1_x,polyPoint1_y,polyPoint5_x-20,polyPoint2_y-1);
        g2.drawLine(polyPoint5_x-20,polyPoint2_y-1,polyPoint5_x,polyPoint5_y);

        g2.setTransform(old);
        g2.dispose();
    }
    private void paintLeftFlipper(Graphics g,int x, int y, int angle,Color c){
        Graphics2D g2 =(Graphics2D) g.create();


        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2.getTransform();
        transform.rotate(Math.toRadians(angle), x + 8, y + 7);
        g2.transform(transform);

        g2.setColor(new Color(170, 169, 50, 255));
        int[] px = {x,x+15 ,x+13 ,x+1};
        int[] py = {y+5,y+5  ,y+56 ,y+56};
        Polygon poly1 = new Polygon(px, py, px.length);
        g2.fillPolygon(poly1);
        g2.fillOval(x,y,15,15);
        g2.fillOval(x+1,y+48,12,12);
        g2.setColor(Color.BLACK);
        g2.fillOval(x+5,y+6,5,5);


        g2.setTransform(old);
        g2.dispose();
    }

    private void paintRightFlipper(Graphics g,int x, int y, int angle,Color c){
        Graphics2D g2 =(Graphics2D) g.create();

        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2.getTransform();
        transform.rotate(Math.toRadians(angle), x + (30/2) + 8, y + 6);
        g2.transform(transform);

        g2.setColor(c);

        int[] pxFR = {x+15,x+15+15 ,x+13+15 ,x+1+15};
        int[] pyFR = {y+5 ,y+5     ,y+56    ,y+56};
        Polygon polyFR= new Polygon(pxFR, pyFR, pyFR.length);
        g2.fillPolygon(polyFR);

        g2.fillOval(x+15,y,15,15);
        g2.fillOval(x+1+15,y+48,12,12);
        g2.setColor(Color.BLACK);
        g2.fillOval(x+5+15,y+6,5,5);

        g2.setTransform(old);
        g2.dispose();
    }

    private void paintAbsorber(Graphics g, int x, int y, int width,int height,Color c){
        g.setColor(c);
        g.fillRect(x,y,width,height);
    }
    private void paintSquare(Graphics g,int x, int y,Color c){
        g.setColor(c);
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