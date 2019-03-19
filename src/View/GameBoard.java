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
    private boolean displaySpace =true;
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
        if(displaySpace) {
            boolean[][] grid = m.getSpaces();
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    boolean isFree = grid[i][j];
                    if (!isFree) {
                        g.setColor(new Color(74, 99, 37, 188));
                    } else {
                        g.setColor(new Color(81, 14, 22, 171));
                    }
                    g.fillRect(i * 30, j * 30, 30, 30);
                }
            }
        }


        Graphics2D g2 = (Graphics2D) g;
        int x , y;
        Color c;
        for(iGizmo gizmo : m.getGizmos()){
            x = gizmo.getXCoord() * 30;
            y = gizmo.getYCoord() * 30;
            c = gizmo.getColor();
            Boolean displayID = m.displayID();
            switch (gizmo.getGizmoType()){
                case "Circle":
                    paintCircle(g2, x,y,c,gizmo.getID(),displayID);
                    break;
                case "Square":
                    paintSquare(g2,x,y,c,gizmo.getID(),displayID);
                    break;
                case "Triangle":
                    paintTriangle(g2,x,y,gizmo.getRotationAngle(),c,gizmo.getID(),displayID);
                    break;
                case "Absorber":
                    paintAbsorber(g2,x,y,gizmo.getWidth()*30,gizmo.getHeight()*30,c,gizmo.getID(),displayID);
                    break;
                case "LeftFlipper":
                    paintLeftFlipper(g2,x,y,gizmo.getRotationAngle(),c,gizmo.getID(),displayID);
                    break;
                case "RightFlipper":
                    paintRightFlipper(g2,x,y,gizmo.getRotationAngle(),7.5, 6, c,gizmo.getID(),displayID);
                    break;
                case"Star":
                    paintStar(g,x,y,gizmo.getRotationAngle(),c,gizmo.getID(),displayID);
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

       Wall w = m.getWalls();
       w.getXCoord();
       g.setColor(new Color(0, 246, 255, 255));
       g.drawLine(w.getXCoord()*30,w.getYCoord()*30,w.getXCoord2()*30,w.getYCoord()*30);
       g.drawLine(w.getXCoord()*30,w.getYCoord()*30,w.getXCoord()*30,w.getYCoord2()*30);
       g.drawLine(w.getXCoord2()*30,w.getYCoord2()*30,w.getXCoord2()*30,w.getYCoord()*30);
       g.drawLine(w.getXCoord2()*30,w.getYCoord2()*30,w.getXCoord()*30,w.getYCoord2()*30);
       repaint();
    }

    //--------------------------------------------------------
    //                    PAINTING COMPONENTS
    private void paintCircle(Graphics g,int x, int y,Color c,String id,Boolean displayID){
        g.setColor(c);

        g.fillOval(x,y,30,30);

        if(displayID) {
            g.setColor(Color.WHITE);
            g.drawString(id.toUpperCase(), x + 5, y + 21);
        }
    }

    private void paintBall(Graphics g,int x, int y, int width){
        g.setColor(new Color(255, 251, 255, 255));
        //g.fillOval(x-8,y-8, 16,16);
        g.fillOval(x, y, width, width);
    }

    private void paintTriangle(Graphics g,int x, int y, int angle,Color c,String id,Boolean displayID){
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

        if(displayID) {
            g2.setColor(Color.WHITE);
            g2.drawString(id.toUpperCase(), x + 5, y + 16);
        }

        g2.setTransform(old);
        g2.dispose();
    }


    private void paintStar(Graphics g,int x, int y, int angle,Color c,String id,Boolean displayID){
        Graphics2D g2 =(Graphics2D) g.create();

        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2.getTransform();
        transform.rotate(Math.toRadians(angle), x + 30, y + 30);
        g2.transform(transform);
        g2.setColor(c);

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

        g2.setColor(c);
        g2.fillOval(x + 18, y + 18,25,25);
       /* g2.drawLine(polyPoint1_x,polyPoint1_y,polyPoint5_x-20,polyPoint2_y);
        g2.drawLine(polyPoint5_x-20,polyPoint2_y,polyPoint5_x,polyPoint5_y);
        g2.fillOval(x + 18, y + 18,25,25);
//        g2.setColor(new Color(52, 9, 14));

        g2.drawLine(polyPoint1_x,polyPoint1_y,polyPoint2_x+22,polyPoint2_y);
        g2.drawLine(polyPoint2_x+22,polyPoint2_y,polyPoint2_x,polyPoint2_y);

        g2.drawLine(polyPoint2_x,polyPoint2_y,polyPoint3_x+6,polyPoint3_y-20);

        g2.drawLine(polyPoint3_x+6,polyPoint3_y-20,polyPoint3_x,polyPoint3_y);
        g2.drawLine(polyPoint3_x,polyPoint3_y,polyPoint4_x-18,polyPoint4_y-12);

        g2.drawLine(polyPoint4_x-18,polyPoint4_y-12,polyPoint4_x,polyPoint4_y);
        g2.drawLine(polyPoint4_x,polyPoint4_y,polyPoint5_x-16,polyPoint5_y+13);

        g2.drawLine(polyPoint5_x-16,polyPoint5_y+13,polyPoint5_x,polyPoint5_y);
*/
        g2.setColor(new Color(74, 0, 129,2));
        g2.drawPolygon(poly1);

        if(displayID) {
            g2.setColor(Color.WHITE);
            g2.drawString(id.toUpperCase(), x + 20, y + 30 + 6);
        }

        g2.setTransform(old);
        g2.dispose();
    }
    private void paintLeftFlipper(Graphics g,int x, int y, int angle,Color c,String id,Boolean displayID){
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

        if(displayID) {
            g2.setColor(Color.WHITE);
            g2.drawString(id.toUpperCase(), x, y + 30);
        }

        g2.setTransform(old);
        g2.dispose();
    }

    private void paintRightFlipper(Graphics g,int x, int y, int angle, double bigR, double smallR, Color c, String id,Boolean displayID){
        Graphics2D g2 =(Graphics2D) g.create();

        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2.getTransform();
        //transform.rotate(Math.toRadians(angle), x + (30/2) + 8, y + 6);
        transform.rotate(Math.toRadians(angle), x + 30 - bigR, y + smallR);
        g2.transform(transform);

        g2.setColor(c);

        int[] pxFR = {x + 30, (int)(x + (30 - 2*bigR)), (int) (x + (30 - 2*smallR)), x+30};
        int[] pyFR = {(int) (y + bigR), (int) (y + bigR),(int) (y + 2*30 - smallR), (int)(y+2*30-smallR)};
        Polygon polyFR= new Polygon(pxFR, pyFR, pyFR.length);
        g2.fillPolygon(polyFR);

        g2.fillOval((int) (x+30 - 2*bigR), (int) (y),15,15);
        g2.fillOval((int) (x + 30 - 2*smallR),(int) (y+2*30 - 2*smallR),12,12);
        g2.setColor(Color.BLACK);
        g2.fillOval(x+5+15,y+6,5,5);

       /* int[] pxFR = {x+15,x+15+15 ,x+13+15 ,x+1+15};
        int[] pyFR = {y+5 ,y+5     ,y+56    ,y+56};
        Polygon polyFR= new Polygon(pxFR, pyFR, pyFR.length);
        g2.fillPolygon(polyFR);

        g2.fillOval(x+15,y,15,15);
        g2.fillOval(x+1+15,y+48,12,12);
        g2.setColor(Color.BLACK);
        g2.fillOval(x+5+15,y+6,5,5); */

        if(displayID) {
            g2.setColor(Color.WHITE);
            g2.drawString(id.toUpperCase(), x + 15, y + 30);
        }
        g2.setTransform(old);
        g2.dispose();
    }

    private void paintAbsorber(Graphics g, int x, int y, int width,int height,Color c, String id,Boolean displayID){
        g.setColor(c);
        g.fillRect(x,y,width,height);
        if(displayID) {
            g.setColor(Color.WHITE);
            g.drawString(id.toUpperCase(), x + 15, y + 30);
        }
    }
    private void paintSquare(Graphics g,int x, int y,Color c,String id,Boolean displayID){
        g.setColor(c);
        g.fillRect(x,y,30,30);
        if(displayID) {
            g.setColor(Color.WHITE);
            g.drawString(id.toUpperCase(), x + 5, y + 30);
        }
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
        g.setColor(new Color(140, 201, 201, 255));
        for (int i = 0; i <= 600; i=i+(30)) {
            g.drawLine(i,0,i,600);
        }
        for (int i = 0; i <=600; i=i+(30)) {
            g.drawLine(0,i,600,i);
        }
    }

    public void setDisplaySpace(boolean displaySpace) {
        this.displaySpace = displaySpace;
    }
}