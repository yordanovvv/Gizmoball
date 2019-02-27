package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

import Controller.PlayListeners.FlipperKeyListener;
import physics.*;

public class GizmoballModel extends iModel {

    private Ball ball;
    private ArrayList<iGizmo> gizmos;
    private Wall walls;
    //TODO private List<Flipper>;
    private iGizmo absorber;
    private ArrayList<Character> keys;//todo remove!!
    private ArrayList<iGizmo> flippers;
    private boolean absorberCollision = false;
    private double gravity = 25; //25L/sec^2

    public GizmoballModel() {

        ball = new Ball("B1", 5, 5,  7.5, 7.5); //2.5 = 50L/sec if moveTime is 0.05 (20 ticks/sec)
        //  ball.setVelo(new Vect(new Angle(200, 200), 8*30));

        gizmos = new ArrayList<iGizmo>();
        walls = new Wall(0, 0, 20, 20);

        absorber = new Absorber("A1", 0, 18, 20, 20);
        gizmos.add(absorber);
        //absorber.addBall(ball);
        //ball.setStopped(true);

        RightFlipper rightFlipper = new RightFlipper("R1", 6, 7);
        gizmos.add(rightFlipper);

        /*LeftFlipper leftFlipper = new LeftFlipper("L1", 10, 12);
        gizmos.add(leftFlipper);*/

        flippers = new ArrayList<>();
        keys = new ArrayList<>();

        flippers.add(rightFlipper);
        //flippers.add(leftFlipper);

        keys.add('r');
       // keys.add('t');

        FlipperKeyListener flipperListener = new FlipperKeyListener("right", this, keys, flippers);//remove this in the long run


        //FlipperKeyListener leftFlipListener = new FlipperKeyListener("left", this, 't', leftFlipper);//remove this in the long run
    }

    @Override
    public void setiGizmo(iGizmo gizmo){
        int i = 0;
        for (iGizmo giz:gizmos) {
            if(giz.getID().equals(gizmo.getID())) gizmos.set(i,gizmo);
            i++;
        }
    }

    @Override
    public ArrayList<Character> getKeys(){return keys;}

    @Override
    public ArrayList<iGizmo> getFlippers(){return flippers;}

    @Override
    public void moveBall() {
        double moveTime = 0.05; //20 times per second

        if (ball != null && !ball.isStopped()) {

            ball.applyGravity(gravity, moveTime);
            ball.applyFriction();

            CollisionDetails cd = timeUntilCollision();

            double tuc = cd.getTuc();

            if (tuc > moveTime) //no collision
            {
                ball = moveBallForTime(ball, moveTime);
            } else {
                if (absorberCollision == true && ball.getVelo().y()>0) //collision with an absorber
                {
                    ball = moveBallForTime(ball, tuc + moveTime);
                    absorber.addBall(ball);
                    ball.setExactX((((Absorber)absorber).getXCoord2()-.5)*30);
                    ball.setExactY((((Absorber)absorber).getYCoord2()-.5)*30);
                    ball.setStopped(true);
                    ball.setVelo(new Vect(0,0));
                    absorberCollision = false;
                }
                else if(absorberCollision == true && ball.getVelo().y()<0) //ball is moving up so ignore absorber line
                {
                    ball = moveBallForTime(ball, moveTime);
                }
                else { //collision

                    ball = moveBallForTime(ball, tuc); //collision in time tuc


                    ball.setVelo(cd.getVelo());

                }
            }

        }
        this.setChanged(); //notify observers, redraw updated view
        this.notifyObservers();
    }

    @Override
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
        //if collision occurs, finding the new velocity
        Circle ballCircle = ball.getCircle();
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = new Vect(0, 0);

        //find shortest time
        double shortestTime = Double.MAX_VALUE;
        double timeC = 0.0; //time until collision with a circle
        double timeL = 0.0; //time until collision with a line
        double timeW = 0.0; //                            wall

        absorberCollision = false;

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

    @Override
    public void addGizmo(iGizmo gizmo) {
        gizmos.add(gizmo);
    }

    @Override
    public void removeGizmo(iGizmo gizmo) {
        gizmos.remove(gizmo);
    }

    //todo fix this. I have done this in order to make the view work -N
    @Override
    public Ball getBall() {
        return ball;
    }
    @Override
    public ArrayList<iGizmo> getGizmos() {
        return gizmos;
    }
    @Override
    public Wall getWalls() {
        return walls;
    }
    @Override
    public void setBallSpeed(int x, int y) {
        Vect v = new Vect(x, y);
        ball.setVelo(v);
    }
    @Override
    public double getBallSpeed(){

        System.out.println("Speed is" + ball.getSpeed());
        return ball.getSpeed();

    }
    @Override
    public Absorber getAbsorber() {
        return (Absorber) absorber;
    }
    @Override
    public void saveGame() {
        System.out.println("SAVING GAME\n\n");
        try {
            FileWriter fileWriter = new FileWriter("game.giz");
            fileWriter.write(ball.toString() + "\n");

            for(iGizmo gizmo : gizmos) {
                fileWriter.write(gizmo.toString() + "\n");
                if(gizmo.getRotationCount() > 0) {
                    for(int i = 0; i < gizmo.getRotationCount(); i++) {
                        fileWriter.write("Rotate " + gizmo.getID() + "\n");
                    }
                }
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
    @Override
    public void loadGame() {
        System.out.println("loading game\n\n");
        gizmos = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("game.giz"));
            String line;
            String[] inputStream;
            while((line = bufferedReader.readLine()) != null) {
                inputStream = line.split(" ");
                switch(inputStream[0]) {
                    case "Ball":
                        ball = new Ball(inputStream[1], Double.parseDouble(inputStream[2]), Double.parseDouble(inputStream[3]), Double.parseDouble(inputStream[4]), Double.parseDouble(inputStream[5]));
                        break;
                    case "Absorber":
                        Absorber abs = new Absorber(inputStream[1], Integer.parseInt(inputStream[2]), Integer.parseInt(inputStream[3]), Integer.parseInt(inputStream[4]), Integer.parseInt(inputStream[5]));
                        gizmos.add(abs);
                        break;
                    case "Square":
                        Square square = new Square(inputStream[1], Integer.parseInt(inputStream[2]), Integer.parseInt(inputStream[3]));
                        gizmos.add(square);
                        break;
                    case "Circle":
                        GizmoCircle circle = new GizmoCircle(inputStream[1], Integer.parseInt(inputStream[2]), Integer.parseInt(inputStream[3]), Double.parseDouble(inputStream[4]));
                        gizmos.add(circle);
                        break;
                    case "Triangle":
                        Triangle triangle = new Triangle(inputStream[1], Integer.parseInt(inputStream[2]), Integer.parseInt(inputStream[3]));
                        gizmos.add(triangle);
                        break;
                    case "RightFlipper":
                        RightFlipper rightFlipper = new RightFlipper(inputStream[1], Integer.parseInt(inputStream[2]), Integer.parseInt(inputStream[3]));
                        gizmos.add(rightFlipper);
                        break;
                    case "LeftFlipper":
                        LeftFlipper leftFlipper = new LeftFlipper(inputStream[1], Integer.parseInt(inputStream[2]), Integer.parseInt(inputStream[3]));
                        gizmos.add(leftFlipper);
                        break;
                    case "Rotate":
                        for(iGizmo gizmo : gizmos) {
                            if(gizmo.getID().equals(inputStream[1])) {
                                gizmo.rotate();
                            }
                        }
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

        this.hasChanged();
        this.notifyObservers();
    }
}
