package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import physics.*;

public class GizmoballModel extends Observable {

    private Ball ball;
    private ArrayList<iGizmo> gizmos;
    private Wall walls;
    //TODO private List<Flipper>;
    private iGizmo absorber;

    private boolean absorberCollision = false;
    private double gravity = 25; //25L/sec^2

    public GizmoballModel() {

        ball = new Ball("B1", 5, 5,  7.5, 7.5); //2.5 = 50L/sec if moveTime is 0.05 (20 ticks/sec)
        //  ball.setVelo(new Vect(new Angle(200, 200), 8*30));

        gizmos = new ArrayList<iGizmo>();
        walls = new Wall(0, 0, 20, 20);

        Square s1 = new Square("S1", 4, 0);
        Square s2 = new Square("S2", 3, 1);
        Square s3 = new Square("S3", 2, 2);
        Square s4 = new Square("S4", 1, 3);
        Square s5 = new Square("S5", 0, 4);

        GizmoCircle c1 = new GizmoCircle("C1", 12, 4, 0.5);
        GizmoCircle c2 = new GizmoCircle("C2", 13, 6, 0.5);
        GizmoCircle c3 = new GizmoCircle("C3", 12, 9, 0.5);
        GizmoCircle c4 = new GizmoCircle("C4", 10, 10, 0.5);
        GizmoCircle c5 = new GizmoCircle("C5", 11, 11, 0.5);
        GizmoCircle c6 = new GizmoCircle("C6", 12, 12, 0.5);
        GizmoCircle c7 = new GizmoCircle("C7", 13, 13, 0.5);

        Triangle t1 = new Triangle("T1", 0, 8);
        t1.rotate();
        t1.rotate();
        t1.rotate();

        Triangle t2 = new Triangle("T2", 5, 8);
        t2.rotate();
        t2.rotate();

        Triangle t3 = new Triangle("T3", 5, 14);
        t3.rotate();

        Triangle t4 = new Triangle("T4", 2, 14);
        t4.rotate();

        Triangle t5 = new Triangle("T5", 4, 11);
        t5.rotate();
        t5.rotate();
        t5.rotate();

        Triangle t6 = new Triangle("T6", 17, 2);

        Triangle t7 = new Triangle("T7", 16, 17);
        t7.rotate();

        Triangle t8 = new Triangle("T8", 11, 12);

        Square s6 = new Square("S6", 17, 17);
        Square s7 = new Square("S7", 17, 18);
        Square s8 = new Square("S8", 16, 18);
        Square s9 = new Square("S9", 15, 18);
        Square s10 = new Square("S10", 14, 18);
        Square s11 = new Square("S11", 17, 16);
        Square s12 = new Square("S12", 17, 15);
        Square s13 = new Square("S13", 17, 1);
        Square s14 = new Square("S14", 18, 1);
        Square s15 = new Square("S15", 18, 2);
        Square s16 = new Square("S16", 18, 3);
        Square s17 = new Square("S17", 4, 10);
        Square s18 = new Square("S18", 3, 10);
        Square s19 = new Square("S19", 3, 11);

        GizmoCircle c8 = new GizmoCircle("C8", 3, 14, 0.5);
        GizmoCircle c9 = new GizmoCircle("C9", 2, 15, 0.5);

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

        gizmos.add(t1);
        gizmos.add(t2);
        gizmos.add(t3);
        gizmos.add(t4);
        gizmos.add(t5);
        gizmos.add(t6);

        gizmos.add(c1);
        gizmos.add(c2);
        gizmos.add(c3);
        gizmos.add(c4);
        gizmos.add(c5);
        gizmos.add(c6);
        gizmos.add(c7);
        gizmos.add(c8);
        gizmos.add(c9);


    }

    int collideSticky = 0; //TODO : Remove this after prototyping
    public void moveBall() throws InterruptedException {
        double moveTime = 0.05; //20 times per second

        if (ball != null && !ball.isStopped()) {

            //System.out.println("Speed: " + ball.calculateSpeed(moveTime));

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
                else {

                    collideSticky ++;

                    //Thread.sleep(1000);

                    ball = moveBallForTime(ball, tuc); //collision in time tuc

                    //5 ticks sticky
                    if (collideSticky > 3){

                        ball.setVelo(cd.getVelo());
                        //
                        collideSticky = 0;
                    }

                    // //velocity after the collision
                }
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

    public double getBallSpeed(){

        System.out.println("Speed is " + ball.getSpeed());
        System.out.println("Velo is " + ball.getVelo());


        return ball.getSpeed();

    }

    public Absorber getAbsorber() {
        return (Absorber) absorber;
    }

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
                    case "GizmoCircle":
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
