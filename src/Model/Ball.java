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
    private int ID;
    private boolean stopped;

    public Ball(double x, double y, double xv, double yv)
    {
        xpos = x;
        ypos = y;
        colour = Color.BLUE;
        velocity = new Vect(xv, yv);
        radius = 10;
        stopped = false;
    }

    public Vect getVelo()
    {
        return velocity;
    }

    public void setVelo(Vect v)
    {
        velocity = v;
    }

    public double getRadius()
    {
        return radius;
    }

    public Circle getCircle()
    {
        return new Circle(xpos, ypos, radius);
    }

    public int getID(){
        return  ID;
    }

    public void setID(int id)
    {
        ID = id;
    }



}
