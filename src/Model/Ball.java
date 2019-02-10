package Model;
import java.awt.Color;
import physics.Circle;
import physics.Vect;

import java.util.Observable;

public class Ball extends Observable {

    private Vect velocity;
    private double radius;
    private double xpos;
    private double ypos;
    private Color colour;
    private String ID;
    private boolean stopped;

    public Ball(double x, double y, double xv, double yv) {
        xpos = x;
        ypos = y;
        colour = new Color(59, 112, 170, 255); //todo remove as not needed
        velocity = new Vect(xv, yv);
        radius = 10;
        stopped = false;
    }

    public Vect getVelo() {
        return velocity;
    }

    public void setVelo(Vect v) {
        velocity = v;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius (double r)
    {
        radius = r;
    }

    public Circle getCircle() {
        return new Circle(xpos, ypos, radius);
    }

    public String getID() { return ID; }

    public boolean isStopped()
    {
        return stopped;
    }

    public void setID(String id) {
        ID = id;
    }

    public double getExactX(){
        return xpos;
    }

    public double getExactY()
    {
        return ypos;
    }

    public void setExactX(double x)
    {
        xpos = x;
    }
    public void setExactY(double y)
    {
        ypos = y;
    }
}
