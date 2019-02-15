package Model;

import physics.Circle;
import physics.Vect;

import java.util.Observable;

public class Ball extends Observable {

    private Vect velocity;
    private double radius;
    private double xpos;
    private double ypos;
    private String ID;
    private boolean stopped;

    //fixed size. so size 1 for pixel
    public Ball(String id, double x, double y, double xv, double yv) {
        this.ID = id;
        xpos = x;
        ypos = y;
        velocity = new Vect(xv, yv);
        radius = 0.3;//todo adjust all of the sizing to L's because spec.
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
        return new Circle((xpos+radius), ypos+radius, radius*30);
    }

    public String getID() { return ID; }

    public boolean isStopped()
    {
        return stopped;
    }

    public void setStopped(boolean value) { stopped = value; }

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

    @Override
    public String toString() {
        return "Ball " + getID() + " " + getExactX() + " " + getExactY() + " " + getVelo().x() + " " + getVelo().y();
    }
}
