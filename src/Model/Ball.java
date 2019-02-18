package Model;

import physics.Circle;
import physics.Vect;

import java.util.Observable;
import static java.lang.Math.abs;

public class Ball extends Observable {

    private Vect velocity;
    private double radius;
    private double xpos;
    private double ypos;
    private String ID;
    private boolean stopped;
    private double constant = 30;

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
        Vect v = new Vect(velocity.x()*constant, velocity.y()*constant);
        return v;
    }

    public void setVelo(Vect v) {
        Vect w = new Vect(v.x()/constant, v.y()/constant);
        velocity = w;
    }

    public double getRadius() {
        return radius*30;
    }

    public void setRadius (double r)
    {
        radius = r/constant;
    }

    public Circle getCircle() {
        return new Circle((xpos+radius)*constant, (ypos+radius)*constant, radius*constant);
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
        return xpos*constant;
    }

    public double getExactY()
    {
        return ypos*constant;
    }

    public void setExactX(double x)
    {
        xpos = x/constant;
    }
    public void setExactY(double y)
    {
        ypos = y/constant;
    }

    @Override
    public String toString() {
        return "Ball " + getID() + " " + getExactX() + " " + getExactY() + " " + getVelo().x() + " " + getVelo().y();
    }

    public Vect applyFriction()
    {
        double mu = 0.025; //per second
        double mu2 = 0.025; //per L
        double delta_t = 0.0009; //what is a sufficiently small delta_t?

        Vect Vold = this.getVelo();
        Vect absVold = new Vect (abs(Vold.x()), abs(Vold.y()));
        double x = Vold.x() * (1 - (mu * delta_t) - (mu2 * absVold.x() * delta_t));
        double y = Vold.y() * (1 - (mu * delta_t) - (mu2 * absVold.y() * delta_t));

        Vect Vnew = new Vect(x,y);

        return Vnew;
    }

    public Vect applyGravity(double g)
    {
        Vect velocity = this.getVelo();

        double x = velocity.x();// + g; gravity only acts in the y direction
        double y = velocity.y()+g;

        velocity = new Vect(x,y);
        return velocity;
    }
}