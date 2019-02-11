package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class GizmoballModel extends Observable implements Serializable {

    private Ball ball;
    private List<iGizmo> gizmos;

    //TODO private List<Wall> walls;

    public GizmoballModel()
    {
        //position (25,25) in pixels, Velocity (100,100) pixels per tick
        ball = new Ball(25, 25, 100, 100);
        gizmos = new ArrayList<iGizmo>();

    }

    public void moveBall()
    {
        double moveTime = 0.05; //20 times per second

        if (ball != null && !ball.isStopped())
        {
            CollisionDetails cd = timeUntilCollision();
            double tuc = cd.getTuc();
            if (tuc > moveTime) //no collision
            {
                ball = moveBallForTime(ball, moveTime);
            } else {
                ball = moveBallForTime(ball, tuc); //collision in time tuc
                ball.setVelo(cd.getVelo()); //velocity after the collision
            }

        }
        this.setChanged(); //notify observers, redraw updated view TODO
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
        double timeC = 0.0; //time until collision with a line
        double timeL = 0.0; //time until collision with a circle

        //TODO iterate through walls to see if collision with a wall will occur

        //iterating through  gizmos
        // (i.e. lines and circles that gizmos are composed of) to find tuc
        for (iGizmo gizmo : gizmos)
        {
            ArrayList <LineSegment> lineSegs = gizmo.getLines();
            ArrayList <Circle> circls = gizmo.getCircles();

            //TODO what does gizmo return if there are no circle or line segments e.g. if it is a square bumper or a circle
            //an empty list maybe?
            //so that we can check if (circls.size()>0) then iterate through circls


            for (LineSegment ls : lineSegs)
            {
                timeL = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
                if (timeL < shortestTime)
                {
                    shortestTime = timeL; //we are hitting a line segment
                    newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
                }
            }

            for (Circle c : circls)
            {
                timeC = Geometry.timeUntilCircleCollision(c, ballCircle, ballVelocity);
                if (timeC < shortestTime)
                {
                    shortestTime = timeC; //we are hitting a circle
                    newVelo = Geometry.reflectCircle(c.getCenter(), ball.getCircle().getCenter(), ball.getVelo(),1.0);
                }
            }
        }

        return new CollisionDetails(shortestTime, newVelo);
    }

    //TODO these need to be done
    private void addBall(Ball ball){}

    private void addGizmo(iGizmo gizmo){}

    //todo fix this. I have done this in order to make the view work -N
    public Ball getBall(){
        return ball;
    }

    public List<iGizmo> getGizmos(){
        return gizmos;
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
