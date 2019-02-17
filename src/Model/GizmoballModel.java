package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

import Controller.PlayListeners.FlipperKeyListener;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class GizmoballModel extends Observable {

    private Ball ball;
    private ArrayList<iGizmo> gizmos;
    private Wall walls;
    //TODO private List<Flipper>;
    private iGizmo absorber;

    private boolean absorberCollision = false;
    private double gravity = 25; //25L/sec^2

    public GizmoballModel() {

        ball = new Ball("B1", 6, 6, 2.5, 2.5); //2.5 = 50L/sec if moveTime is 0.05 (20 ticks/sec)
        //ball = new Ball("B1" ,280, 303, 150, 150);
        gizmos = new ArrayList<iGizmo>();
        walls = new Wall(0, 0, 20, 20);

        absorber = new Absorber("A1", 0, 19, 20, 20);
        gizmos.add(absorber);

        RightFlipper rightFlipper = new RightFlipper("R1", 10, 10);
    //    gizmos.add(rightFlipper);

        FlipperKeyListener rightFlipListener = new FlipperKeyListener("right", this, 'r', rightFlipper);//remove this in the long run
    }


    public void moveBall() {
        double moveTime = 0.05; //20 times per second

        if (ball != null && !ball.isStopped()) {

            ball.setVelo(ball.applyGravity(gravity));

            CollisionDetails cd = timeUntilCollision();
            double tuc = cd.getTuc();
            ball.setVelo(ball.applyFriction(moveTime, tuc));
            if (tuc > moveTime) //no collision
            {
                ball = moveBallForTime(ball, moveTime);
            } else {
                if (absorberCollision == true) //collision with an absorber
                {
                    ball = moveBallForTime(ball, tuc + moveTime*0.75);
                    absorber.addBall(ball);
                    ball.setStopped(true);
                }
                ball = moveBallForTime(ball, tuc); //collision in time tuc
                ball.setVelo(cd.getVelo()); //velocity after the collision
            }

        }
        this.setChanged(); //notify observers, redraw updated view
        this.notifyObservers();
    }

    public Ball moveBallForTime(Ball ball, double time) {
        double xVel = ball.getVelo().x();
        double yVel = ball.getVelo().y();
        double newX =  (ball.getExactX() + (xVel * time));
        double newY =  (ball.getExactY() + (yVel * time));

        ball.setExactX(newX);
        ball.setExactY(newY);

        return ball;
    }

    private CollisionDetails timeUntilCollision() {
        //finding time until collision
        //if collision occurs, finding the new velo
        Circle ballCircle = ball.getCircle();
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = new Vect(0, 0);

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
                absorberCollision = false;
                newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
            }
        }

        //iterating through  gizmos
        // (i.e. lines and circles that gizmos are composed of) to find tuc
        for (iGizmo gizmo : gizmos) {
            ArrayList<LineSegment> lineSegs = gizmo.getLines();
            ArrayList<Circle> circls = gizmo.getCircles();

            if (lineSegs.size() > 0) {
                for (LineSegment ls : lineSegs) {
                    timeL = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
                    if (timeL < shortestTime) {
                        shortestTime = timeL; //we are hitting a line segment
                        if (gizmo.getGizmoType().equals("Absorber")) {
                            absorberCollision = true;
                        } else absorberCollision = false;
                        newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
                    }
                }
            }


            if (circls.size() > 0) {
                for (Circle c : circls) {
                    timeC = Geometry.timeUntilCircleCollision(c, ballCircle, ballVelocity);
                    if (timeC < shortestTime) {
                        shortestTime = timeC; //we are hitting a circle
                        absorberCollision = false;
                        newVelo = Geometry.reflectCircle(c.getCenter(), ball.getCircle().getCenter(), ball.getVelo(), 1.0);
                    }
                }
            }
        }


        CollisionDetails cd = new CollisionDetails(shortestTime, newVelo);
        return cd;
    }

    //TODO these need to be done
    private void addBall(Ball ball) {

    }

    public void addGizmo(iGizmo gizmo) {
        gizmos.add(gizmo);
    }

    //craig testing stuff
    public void removeGizmo(iGizmo gizmo) {
        gizmos.remove(gizmo);
    }

    //todo fix this. I have done this in order to make the view work -N

    public Ball getBall() {
        return ball;
    }

    public ArrayList<iGizmo> getGizmos() {
        return gizmos;
    }

    public Wall getWalls() {
        return walls;
    }

    public void setBallSpeed(int x, int y) {
        Vect v = new Vect(x, y);
        ball.setVelo(v);
    }

    public Absorber getAbsorber()
    {
        return (Absorber) absorber;
    }

    public void saveGame() {
        System.out.println("SAVING GAME\n\n");
        try {
            FileWriter fileWriter = new FileWriter("game.giz");
            fileWriter.write(ball.toString() + "\n");
            for(iGizmo gizmo : gizmos) {
                fileWriter.write(gizmo.toString() + "\n");
            }
            fileWriter.flush();

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("printing model on save");
        for(iGizmo gizmo : gizmos) {
            System.out.println(gizmo);
        }
    }

    public void loadGame() {
        System.out.println("loading game\n\n");
        gizmos = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("game.giz"));
            String line;
            String[] array;
            while((line = bufferedReader.readLine()) != null) {
                array = line.split(" ");
                switch(array[0]) {
                    case "Ball":
                        ball = new Ball(array[1], Double.parseDouble(array[2]), Double.parseDouble(array[3]), Double.parseDouble(array[4]), Double.parseDouble(array[5]));
                        break;
                    case "Absorber":
                        Absorber abs = new Absorber(array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]), Integer.parseInt(array[4]), Integer.parseInt(array[5]));
                        gizmos.add(abs);
                        break;
                    case "Square":
                        Square square = new Square(array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]));
                        gizmos.add(square);
                        break;
                    case "Circle":
                        GizmoCircle circle = new GizmoCircle(array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]), Double.parseDouble(array[4]));
                        gizmos.add(circle);
                        break;
                    case "Triangle":
                        Triangle triangle = new Triangle(array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]));
                        gizmos.add(triangle);
                        break;
                    case "RightFlipper":
                        RightFlipper rightFlipper = new RightFlipper(array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]));
                        gizmos.add(rightFlipper);
                        break;
                    case "LeftFlipper":
                        LeftFlipper leftFlipper = new LeftFlipper(array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]));
                        gizmos.add(leftFlipper);
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

        System.out.println("printing model on load");
        for(iGizmo gizmo : gizmos) {
            System.out.println(gizmo);
        }
    }
}
