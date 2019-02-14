package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

import Controller.PlayListeners.FlipperKeyListener;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class GizmoballModel extends Observable{

    private Ball ball;
    private ArrayList<iGizmo> gizmos;
    private Wall walls;
    //TODO private List<Flipper>;
    private iGizmo absorber;
    private iGizmo t;
    private iGizmo t2;
    private boolean absorberCollision = false;

    public GizmoballModel()
    {
        //position of ball (25,25) in pixels, Velocity (100,100) pixels per tick
        ball = new Ball(280, 303, 150, 150);
        gizmos = new ArrayList<iGizmo>();
        walls = new Wall(0, 0, 20, 20);
        //walls = new Wall(-10, -10, 575, 575);

        absorber = new Absorber("A1",0,19,20,20);
        gizmos.add(absorber);

        RightFlipper rightFlipper = new RightFlipper("R1",10,10);
        gizmos.add(rightFlipper);

        FlipperKeyListener rightFlipListener = new FlipperKeyListener("right", this, 'r', rightFlipper);//remove this in the long run


        t = new Triangle("T1",13, 14);
        Triangle  t2 = new Triangle("T2",12, 13);
        Triangle t3 = new Triangle("T3",14, 13);
        Triangle t4 = new Triangle("T4",13, 12);
        Triangle t5 = new Triangle("T5",12, 12);
        Triangle t6 = new Triangle("T6",14, 14);

        /*
        Square s1 = new Square("S1",8,8);
        Square s2 = new Square("S1",9,8);
        Square s3 = new Square("S1",10,8);
        Square s4 = new Square("S1",11,8);
        Square s5 = new Square("S1",8,9);
        Square s6 = new Square("S1",8,10);
        Square s7 = new Square("S1",8,11);
        Square s8 = new Square("S1",8,12);
        Square s9 = new Square("S1",10,12);
        Square s10 = new Square("S1",11,12);
        Square s11 = new Square("S1",8,12);
        Square s12 = new Square("S1",9,12);
        Square s13 = new Square("S1",10,12);
        Square s14 = new Square("S1",11,12);
        Square s15 = new Square("S1",12,12);
        Square s16 = new Square("S1",12,8);
        Square s17 = new Square("S1",12,9);
        Square s18 = new Square("S1",12,10);
        Square s19 = new Square("S1",12,11);
        Square s20 = new Square("S1",9,9);

        gizmos.add(s1);
        gizmos.add(s2);
        gizmos.add(s3);
        gizmos.add(s4);
        gizmos.add(s5);
        gizmos.add(s6);
        gizmos.add(s7);
        gizmos.add(s8);
        gizmos.add(s9);
        gizmos.add(s10);
        gizmos.add(s11);
        gizmos.add(s12);
        gizmos.add(s13);
        gizmos.add(s14);
        gizmos.add(s15);
        gizmos.add(s16);
        gizmos.add(s17);
        gizmos.add(s18);
        gizmos.add(s19);
        //gizmos.add(s20);
        */

       /* gizmos.add(t);
        gizmos.add(t2);
        gizmos.add(t3);
        gizmos.add(t4);
        gizmos.add(t5);
        gizmos.add(t6);
*/
    }


    public void moveBall()
    {
        double moveTime = 0.06; //20 times per second

        if (ball != null && !ball.isStopped())
        {
            CollisionDetails cd = timeUntilCollision();
            double tuc = cd.getTuc();
            if (tuc > moveTime) //no collision
            {
                ball = moveBallForTime(ball, moveTime);
            } else {
                if(absorberCollision == true) //collision with an absorber
                {
                    ball = moveBallForTime(ball, tuc + moveTime);
                    absorber.addBall(ball);
                    ball.setStopped(true);
                    //newVelo = new Vect(0,0);
                    //absorber.eatBall or sth
                    //System.out.println("absorber!!!");

                }
                ball = moveBallForTime(ball, tuc); //collision in time tuc
                ball.setVelo(cd.getVelo()); //velocity after the collision
            }

        }
        this.setChanged(); //notify observers, redraw updated view
        this.notifyObservers();

    }

    public Ball moveBallForTime(Ball ball, double time)
    {
        double xVel = ball.getVelo().x();
        double yVel = ball.getVelo().y();
        double newX = ball.getExactX() + (xVel*time);
        double newY = ball.getExactY() + (yVel*time);

        ball.setExactX(newX);
        ball.setExactY(newY);

        return ball;
    }

    private CollisionDetails timeUntilCollision()
    {
        //finding time until collision
        //if collision occurs, finding the new velo
        Circle ballCircle = ball.getCircle();
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = new Vect(0,0);

        //find shortest time
        double shortestTime = Double.MAX_VALUE;
        double timeC = 0.0; //time until collision with a circle
        double timeL = 0.0; //time until collision with a line
        double timeW = 0.0; //                            wall


        //iterating through walls
        ArrayList<LineSegment> lines = walls.getWalls();
        for (LineSegment ls : lines) {

            timeW = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
            if (timeW < shortestTime) {
                shortestTime = timeW; //we are hitting a line segment
                absorberCollision =false;
                newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
            }
        }

        //iterating through  gizmos
        // (i.e. lines and circles that gizmos are composed of) to find tuc
        for (iGizmo gizmo : gizmos)
        {
            ArrayList <LineSegment> lineSegs = gizmo.getLines();
            ArrayList <Circle> circls = gizmo.getCircles();

            if (lineSegs.size()>0)
            {
                for (LineSegment ls : lineSegs)
                {
                    timeL = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
                    if (timeL < shortestTime)
                    {
                        shortestTime = timeL; //we are hitting a line segment
                        if (gizmo.getGizmoType().equals("Absorber"))
                        {
                            absorberCollision = true;
                        }
                        else absorberCollision = false;
                        newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
                    }
                }
            }


            if (circls.size()>0)
            {
                for (Circle c : circls)
                {
                    timeC = Geometry.timeUntilCircleCollision(c, ballCircle, ballVelocity);
                    if (timeC < shortestTime)
                    {
                        shortestTime = timeC; //we are hitting a circle
                        absorberCollision = false;
                        newVelo = Geometry.reflectCircle(c.getCenter(), ball.getCircle().getCenter(), ball.getVelo(),1.0);
                    }
                }
            }
        }


        CollisionDetails cd = new CollisionDetails(shortestTime, newVelo);
        return cd;
    }

    //TODO these need to be done
    private void addBall(Ball ball)
    {

    }

    public void addGizmo(iGizmo gizmo){
        gizmos.add(gizmo);
    }

    //craig testing stuff
    public void removeGizmo(iGizmo gizmo){gizmos.remove(gizmo);}

    //todo fix this. I have done this in order to make the view work -N

    public Ball getBall(){
        return ball;
    }

    public ArrayList<iGizmo> getGizmos(){
        return gizmos;
    }

    public Wall getWalls()
    {
        return walls;
    }

    public void setBallSpeed(int x, int y)
    {
        Vect v = new Vect(x, y);
        ball.setVelo(v);
    }

    public Absorber getAbsorber()
    {
        return (Absorber) absorber;
    }

    public void saveGame() {
//        FileOutputStream fileOut;
//        try {
//            fileOut = new FileOutputStream("temp.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(ball);
//            out.close();
//            fileOut.close();
//            System.out.println("Serialized data is saved in temp.ser");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
        try {
            FileWriter fileWriter = new FileWriter("game.giz");
            fileWriter.write(ball.toString());
            fileWriter.flush();

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadGame() {
//        Ball ball;
//
//        try {
//            FileInputStream fileIn = new FileInputStream("temp.ser");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            ball = (Ball) in.readObject();
//            System.out.println("After Deserialization");
//            System.out.println(ball);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("game.giz"));
            String line;
            String[] array = new String[10];
            while((line = bufferedReader.readLine()) != null) {
                array = line.split(" ");
                switch(array[0]) {
                    case "Ball":
                        ball = new Ball(Double.parseDouble(array[2]), Double.parseDouble(array[3]), Double.parseDouble(array[4]), Double.parseDouble(array[5]));
                        System.out.println(ball);
                        break;
                    default:
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
