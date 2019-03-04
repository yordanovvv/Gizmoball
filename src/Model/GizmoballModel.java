package Model;

import java.io.*;
import java.util.ArrayList;
import physics.*;

public class GizmoballModel extends iModel {

    private ArrayList<Ball> balls;
    //private Ball ball;
    private ArrayList<iGizmo> gizmos;
    private Wall walls;
    private iGizmo absorber;
    private ArrayList<Character> keys;
    private ArrayList<iGizmo> flippers;
    private boolean absorberCollision = false;
    private iGizmo collisionGizmo = null;
    private boolean wallCollision = false;
    private double gravity = 25; //25L/sec^2

    public GizmoballModel() {

        balls = new ArrayList<>();
        balls.add(new Ball("B1", 5, 5,  7.5, 7.5)); //2.5 = 50L/sec if moveTime is 0.05 (20 ticks/sec)
        //balls.add(new Ball("B2", 6, 7,  7.5, 7.5));
        gizmos = new ArrayList<iGizmo>();
        walls = new Wall(0, 0, 20, 20);

        absorber = new Absorber("A1", 0, 18, 20, 20);
        gizmos.add(absorber);
        RightFlipper rightFlipper = new RightFlipper("R1", 6, 7);
        gizmos.add(rightFlipper);

        LeftFlipper leftFlipper = new LeftFlipper("L1", 10, 12);
        gizmos.add(leftFlipper);

        flippers = new ArrayList<>();
        keys = new ArrayList<>();

        flippers.add(leftFlipper);
        flippers.add(rightFlipper);

        keys.add('t');
        keys.add('r');

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

        //reset the hit values
        for (iGizmo g:gizmos) {
            g.setHit(false);
        }

        for (Ball ball : balls)
        {
            if (ball != null && !ball.isStopped()) {

                ball.applyGravity(gravity, moveTime);
                ball.applyFriction();

                CollisionDetails cd = timeUntilCollision(ball);
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

                        if(collisionGizmo != null && wallCollision == false)
                        {
                            collisionGizmo.setHit(!collisionGizmo.getHit());
                            switch (collisionGizmo.getID().charAt(0))
                            {
                                case 'C':
                                    System.out.println("Circle collision " + collisionGizmo.getID());

                                    break;
                                case 'R':
                                    System.out.println("Flipper collision " + collisionGizmo.getID()); break;
                                default: break;
                            }

                        }
                        if(wallCollision == true)
                        {
                            //System.out.println("Wall collision");
                        }

                    }
                }

            }
            this.setChanged(); //notify observers, redraw updated view
            this.notifyObservers();
            collisionGizmo = null;
            wallCollision = false;
        }
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

    private CollisionDetails timeUntilCollision(Ball ball) {

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
        wallCollision = false;

        //iterating through walls
        ArrayList<LineSegment> lines = walls.getWalls();
        for (LineSegment ls : lines) {

            timeW = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
            if (timeW < shortestTime) {
                shortestTime = timeW; //we are hitting a line segment
                absorberCollision = false;
                wallCollision = true;
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
                        collisionGizmo = gizmo;
                        if (gizmo.getGizmoType().equals("Absorber")) {
                            absorberCollision = true;
                        } else absorberCollision = false;
                        wallCollision = false; //we found a gizmo that is closer to the ball than a wall
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
                        collisionGizmo = gizmo;
                        newVelo = Geometry.reflectCircle(c.getCenter(), ball.getCircle().getCenter(), ball.getVelo(), 1.0);
                    }
                }
            }
        }

        CollisionDetails cd = new CollisionDetails(shortestTime, newVelo); //possibly add ID of the gizmo it will collide with
        return cd;
    }

    public void addBall(Ball b) {
        balls.add(b);
    }

    @Override
    public void addGizmo(iGizmo gizmo) {
        gizmos.add(gizmo);
    }

    @Override
    public void removeGizmo(iGizmo gizmo) {
        gizmos.remove(gizmo);
    }

    @Override
    public ArrayList<Ball> getBalls() {
        return balls;
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
    public void setBallSpeed(Ball ball, int x, int y) {
        Vect v = new Vect(x, y);
        ball.setVelo(v);
    }
    @Override
    public double getBallSpeed(Ball b){

        //System.out.println("Speed is" + ball.getSpeed());
        return b.getSpeed();

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

            for(Ball ball: balls){
                fileWriter.write(ball.toString() + "\n");
            }

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
                        Ball ball = new Ball(inputStream[1], Double.parseDouble(inputStream[2]), Double.parseDouble(inputStream[3]), Double.parseDouble(inputStream[4]), Double.parseDouble(inputStream[5]));
                        balls.add(ball);
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
