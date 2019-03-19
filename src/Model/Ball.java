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
    private double speed;
    private double friction;


    //fixed size. so size 1 for pixel
    public Ball(String id, double x, double y, double xv, double yv) {
        this.ID = id;
        xpos = x;
        ypos = y;
        velocity = new Vect(xv, yv);
        radius = 0.3;
        stopped = true;
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
        return new Circle((xpos)*constant, (ypos)*constant, radius*constant);
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
        return "Ball " + getID() + " " + Math.round(getExactX()/30*100)/100 + " " + Math.round(getExactY()/30*100)/100+ " " + Math.round(getVelo().x()/30)/100 + " " + Math.round(getVelo().y()/30)/100;
    }

    public void applyFriction(double mu1, double mu2)
    {
        //double mu = 0.025; //per second
        //double mu2 = 0.025; //per L
        double delta_t = 0.05;
        Vect Vnew  = this.getVelo().times(1 - (mu1 * delta_t) - (mu2 * abs(this.getVelo().length()) * delta_t/30));
        friction = Vnew.length();
        this.setVelo(Vnew);
    }


    public double getFriction()
    {
        return friction;
    }

    public void applyGravity(double gravity, double time)
    {
        Vect velocity = this.getVelo();
        Vect grav = new Vect (0, gravity*constant*time);
        this.setVelo(this.getVelo().plus(grav));
    }


    public double calculateSpeed(double time)
    {
        double timeInSecs = 1/time; //ticks per second
        Vect velocity = this.getVelo();
        double x = velocity.x();
        double y = velocity.y();

        //speed = sqrt(x*x + y*y)/timeInSecs;
        //speed = velocity.length()/30;

        speed = velocity.length()/timeInSecs;
        return speed;
    }

    public double getSpeed()
    {
        calculateSpeed(0.05);
        return speed;
    }
}