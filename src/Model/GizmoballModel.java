package Model;

import physics.*;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class GizmoballModel extends iModel {

    private ArrayList<Ball> balls;
    //private Ball ball;
    private ArrayList<iGizmo> gizmos;
    private Wall walls;
    private iGizmo absorber;
    private iGizmo star;
 //   private ArrayList<Character> keys;
 //   private ArrayList<iGizmo> flippers;
    private boolean absorberCollision = false, starCollision = false, starShotOut = false;
    private iGizmo collisionGizmo = null;
    private boolean wallCollision = false;
    private double gravity = 25; //25L/sec^2
    boolean[][] spaces = new boolean[20][20];
    HashMap<iGizmo,Character> keyTriggers;
    private int wait = 0;

    public GizmoballModel() {

        balls = new ArrayList<>();
       // flippers = new ArrayList<>();
      //  keys = new ArrayList<>();
        gizmos = new ArrayList<iGizmo>();
        keyTriggers = new HashMap<>();


        //TODO remove all of this, eventually will be empty board
        balls.add(new Ball("B1", 8, 5,  7.5, 7.5)); //2.5 = 50L/sec if moveTime is 0.05 (20 ticks/sec)
        //balls.add(new Ball("B2", 6, 7,  7.5, 7.5));

        walls = new Wall(0, 0, 20, 20);

        absorber = new Absorber("A1", 0, 18, 20, 20);
        star = new Star("init_star",0,0);
        gizmos.add(absorber);
      //  gizmos.add(star);

        //spaces = this.getSpaceGrid();

     //  RightFlipper rightFlipper = new RightFlipper("R1", 5, 10);
     //  gizmos.add(rightFlipper);

      /*  LeftFlipper leftFlipper = new LeftFlipper("L1", 10, 10);
        gizmos.add(leftFlipper);
                keyTriggers.put(leftFlipper,'t');
        */

//         keyTriggers.put(rightFlipper,'r');

    }


    @Override
    public ArrayList<iGizmo> getAllStars(){
        ArrayList<iGizmo> stars = new ArrayList<>();

        for (iGizmo giz:gizmos) {
            if(giz.getGizmoType().equals("Star")){
                stars.add(giz);
            }
        }
        return stars;
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
    public HashMap<iGizmo,Character> getKeyTriggers(){return keyTriggers;}

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
                        //playSound(collisionGizmo);
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
                    }else if(starCollision){
                        System.out.println("--> star collided with ball ");
                        ball = moveBallForTime(ball, tuc);
                        ball.setVelo(cd.getVelo());
                        ball.calculateSpeed(tuc);

                      //  moveStarForTime((Star)star,tuc);

                      /* if(!((Star) star).isRoating())
                            ((Star) star).startStarRotation();*/

                    }
                   /* else if(starCollision == true && starShotOut == ){
                       // playSound(collisionGizmo);
                       // if(wait == 0) {
                            ball = moveBallForTime(ball, tuc + moveTime);
                            star.addBall(ball);
                            ball.setExactX((((Star) star).getmiddleXCoord()) * 30);
                            ball.setExactY((((Star) star).getmiddleYCoord()) * 30);
                            ball.setStopped(true);
                            ball.setVelo(new Vect(0, 0));
                       // }
                      //  else if(wait == 1) {
                            ball = ((Star) star).shootBallOut();

                            ball.calculateSpeed(tuc + moveTime);

                            wait = 0;
                            starCollision = false;
                            starShotOut = true;
                        //}
                        wait++;
                    }*/else { //collision

                        ball = moveBallForTime(ball, tuc); //collision in time tuc
                        ball.setVelo(cd.getVelo());

                        if(collisionGizmo != null && wallCollision == false)
                        {
                            ball.calculateSpeed(tuc);

                            collisionGizmo.setHit(!collisionGizmo.getHit());
                           // System.out.println("checking connecyions");
                            //if(collisionGizmo.getGizmoConnections()!=null) {
                              //  System.out.println("connections not null");
                                if (!collisionGizmo.getGizmoType().equals("Star"))
                                    System.out.println("connections not a star");
                                    checkConnections(collisionGizmo, ball);
                           //}

                            if(ball.getSpeed()!=0) playSound(collisionGizmo);

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


     public Star moveStarForTime(Star s, double time){
        double tuc = time*30*50;
         System.out.println("tuc " +tuc);
         s.spinStarForTime(tuc);
        return s;
    }

    //Clears spaces, avoids NullPointer when clearing spaces
    public void wipeSpaces(){
        for (int i=0; i<20; i++){
            for (int j=0; j<20; j++){
               spaces[i][j] = false;
            }
        }
    }

    public void setSpaces(int gridX, int gridY, boolean val, iGizmo g){
        if(gridX>=20)return;
        if(gridY>=20)return;
        switch(g.getGizmoType()){
            case "Square":
                spaces[gridX][gridY] = val;
                break;
            case "Circle":
                spaces[gridX][gridY] = val;
                break;
            case "Triangle":
                spaces[gridX][gridY] = val;
                break;
            case "RightFlipper":
                spaces[gridX][gridY] = val;
                spaces[gridX][gridY+1] = val;
                spaces[gridX-1][gridY] = val;
                spaces[gridX-1][gridY+1] = val;
                break;
            case "LeftFlipper":
                spaces[gridX][gridY] = val;
                spaces[gridX][gridY+1] = val;
                spaces[gridX+1][gridY] = val;
                spaces[gridX+1][gridY+1] = val;
                break;
            case "Star":
                spaces[gridX][gridY] = val;
                spaces[gridX][gridY+1] = val;
                spaces[gridX][gridY+2] = val;

                spaces[gridX+1][gridY] = val;
                spaces[gridX+2][gridY] = val;

                spaces[gridX+1][gridY+1] = val;
                spaces[gridX+2][gridY+1] = val;
                spaces[gridX+1][gridY+2] = val;
                spaces[gridX+2][gridY+2] = val;

                spaces[gridX-1][gridY-1] = val;
                spaces[gridX-1][gridY] = val;
                spaces[gridX-1][gridY+1] = val;
                spaces[gridX-1][gridY+2] = val;

                spaces[gridX][gridY-1] = val;
                spaces[gridX+1][gridY-1] = val;
                spaces[gridX+2][gridY-1] = val;
                break;
            case "Absorber":
                for (int x=0; x<g.getWidth(); x++){
                    for (int y=0; y<g.getHeight(); y++){
                        spaces[gridX+x][gridY+y] = val;
                    }
                }
                break;
            default:
                spaces[gridX][gridY] = val; //this might break it?
        }
    }

    public boolean[][] getSpaces(){
        return spaces;
    }

    //todo this neeeeeds to be fixed, with regards to movement of gizmos
    public boolean checkSpace(int gridX, int gridY, iGizmo g){
        if(gridX>=20|gridY>=20)return false;
        if (g==null){
            return spaces[gridX][gridY];
        }

        //Spaces true if free, false if not

        switch (g.getGizmoType()){
            case "Circle":
                return spaces[gridX][gridY];
            case "Square":
                return spaces[gridX][gridY];
            case "Triangle":
                return spaces[gridX][gridY];
            case "LeftFlipper":
                boolean  canPlaceLF = true;
                if (spaces[gridX][gridY] || spaces[gridX][gridY+1] || spaces[gridX+1][gridY+1] || spaces[gridX+1][gridY]){
                    canPlaceLF = false;
                }
                return canPlaceLF;
            case "RightFlipper":
                boolean canPlaceRF = true;
                if (spaces[gridX][gridY] || spaces[gridX][gridY+1] || spaces[gridX-1][gridY+1] || spaces[gridX-1][gridY]){
                    canPlaceRF = false;
                }
                return canPlaceRF;
            case "Star":
                boolean canPlaceStar = true;
                if (spaces[gridX][gridY] ||
                        spaces[gridX][gridY+1] ||
                        spaces[gridX][gridY+2] ||
                        spaces[gridX+1][gridY] ||
                        spaces[gridX+2][gridY] ||
                        spaces[gridX+1][gridY+1] ||
                        spaces[gridX+1][gridY+2] ||
                        spaces[gridX+2][gridY+1] ||
                        spaces[gridX+2][gridY+2] ||
                        spaces[gridX-1][gridY-1] ||
                        spaces[gridX-1][gridY] ||
                        spaces[gridX-1][gridY+1] ||
                        spaces[gridX-1][gridY+2] ||
                        spaces[gridX][gridY-1] ||
                        spaces[gridX+1][gridY-1] ||
                        spaces[gridX+2][gridY-1]){
                    canPlaceStar = false;
                }
                return canPlaceStar;
            case "Absorber":
                int posWidth, posHeight;
                boolean absPlace = true;
                posWidth=g.getWidth();
                posHeight=g.getHeight();
                for(int x=0; x<posWidth; x++){
                    for (int y=0; y<posHeight; y++){
                        try {
                        if (spaces[gridX+x][gridY+y]){
                            absPlace=false;
                        }
                        }catch(ArrayIndexOutOfBoundsException aoe){
                            System.out.println("Absorber out of bounds");
                            return false;
                        }
                    }
                }
                return absPlace;
        }
        return spaces[gridX][gridY];
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
        starCollision = false;

        //iterating through walls
        ArrayList<LineSegment> lines = walls.getWalls();

        /*   for(Ball b: balls){
            if(!b.equals(ball)){
                    double  time = Geometry.timeUntilBallBallCollision(ballCircle,ballVelocity,b.getCircle(),b.getVelo());
                    if (timeW < shortestTime) {
                        absorberCollision = false;
                        starCollision = false;
                        starShotOut = false;
                        wallCollision = true;
                        Geometry.VectPair  vectPair = Geometry.reflectBalls(ballCircle.getCenter(),5,ballVelocity,b.getCircle().getCenter(),3,b.getVelo());
                    }

            }
        }*/
        for (LineSegment ls : lines) {

            timeW = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
            if (timeW < shortestTime) {
                shortestTime = timeW; //we are hitting a line segment
                absorberCollision = false;
                starCollision = false;
                starShotOut = false;
                wallCollision = true;
                newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
            }
        }

        for (iGizmo gizmo : gizmos) {
            ArrayList<LineSegment> lineSegs = gizmo.getLines();
            ArrayList<Circle> circls = gizmo.getCircles();
            boolean collision = false;
            double star_rotation_degree =(18*5)+5;
            double time_passed = 0.05;
            if(starShotOut){
                star_rotation_degree=18*20;
            }
            if (circls.size() > 0) {
                for (Circle c : circls) {
                    if (gizmo.getGizmoType().equals("RightFlipper") || gizmo.getGizmoType().equals("LeftFlipper")) {
                        timeC = Geometry.timeUntilRotatingCircleCollision(c, ((RightFlipper) gizmo).getCentre(),
                                ((RightFlipper) gizmo).getAngularVelo(),
                                ballCircle, ballVelocity);
                        if (timeC <= shortestTime) {
                            shortestTime = timeC; //we are hitting a circle
                            absorberCollision = false;
                            collisionGizmo = gizmo;
                            newVelo = Geometry.reflectRotatingCircle(c, ((RightFlipper) gizmo).getCentre(),
                                    ((RightFlipper) gizmo).getAngularVelo(),
                                    ballCircle, ballVelocity, 0.95);
                        }

                    } else if (gizmo.getGizmoType().equals("Star")) {
                        Star star = (Star) gizmo;

                        double angular_velocity = Math.toRadians(star_rotation_degree) * time_passed;
                        double time = Geometry.timeUntilRotatingCircleCollision(
                                c,
                                star.getCenter(),
                                angular_velocity,
                                ballCircle,
                                ballVelocity);

                        if (time <= shortestTime) {
                            absorberCollision = false;
                            wallCollision = false;
                            starCollision = true;
                            shortestTime = time;

                            newVelo = Geometry.reflectRotatingCircle(
                                    c,
                                    star.getCenter(),
                                    angular_velocity,
                                    ballCircle,
                                    ballVelocity, 1);
                        }

                    } else {

                        timeC = Geometry.timeUntilCircleCollision(c, ballCircle, ballVelocity);
                        if (timeC <= shortestTime) {
                            collisionGizmo = gizmo;
                            shortestTime = timeC; //we are hitting a circle
                            absorberCollision = false;
                            starCollision = false;
                            starShotOut = false;
                            collisionGizmo = gizmo;
                            newVelo = Geometry.reflectCircle(c.getCenter(), ball.getCircle().getCenter(), ball.getVelo(), 1);
                        }
                    }
                }
            }

            if (lineSegs.size() > 0) {
                for (LineSegment ls : lineSegs) {
                    if (gizmo.getGizmoType().equals("RightFlipper") && gizmo.getRotationAngle() != 0 && gizmo.getRotationAngle() != 90) {
                        absorberCollision = false;

                        timeL = Geometry.timeUntilRotatingWallCollision(ls,
                                ((RightFlipper) gizmo).getCentre(),
                                ((RightFlipper) gizmo).getAngularVelo(),
                                ballCircle, ballVelocity);
                        if (timeL <= shortestTime) {
                            shortestTime = timeL; //we are hitting a rotating flipper
                            collisionGizmo = gizmo;
                            wallCollision = false; //we found a gizmo that is closer to the ball than a wall
                            newVelo = Geometry.reflectRotatingWall(ls, ((RightFlipper) gizmo).getCentre(),
                                    ((RightFlipper) gizmo).getAngularVelo(),
                                    ballCircle, ballVelocity, 0.95);
                        }

                    }
                    else if (gizmo.getGizmoType().equals("Star")) {
                             Star current_star = (Star) gizmo;
                            // current_star.stopRotation();
                             double angular_velocity = Math.toRadians(star_rotation_degree) * time_passed;
                             double time = Geometry.timeUntilRotatingWallCollision(
                                    ls,
                                    current_star.getCenter(),
                                    angular_velocity,
                                    ballCircle,
                                    ballVelocity);
                            if (time <= shortestTime) {
                                absorberCollision = false;
                                starCollision = true;
                                wallCollision = false;
                                collisionGizmo = gizmo;
                                shortestTime = time;
                                this.star = current_star;
                                newVelo = Geometry.reflectRotatingWall(
                                        ls,
                                        current_star.getCenter(),
                                        angular_velocity,
                                        ballCircle,
                                        ballVelocity, 1.0);
                            }

                            for (LineSegment feeder : current_star.getFeederLines()) {
                                if (ls.equals(feeder)) {
                                    this.starShotOut = true;
                                }
                            }

                    }
                    else {
                        starCollision = false;
                        timeL = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
                        if (timeL < shortestTime) {
                            shortestTime = timeL; //we are hitting a line segment
                            collisionGizmo = gizmo;
                            if (gizmo.getGizmoType().equals("Absorber")) {
                                absorberCollision = true;
                            } else absorberCollision = false;

                            wallCollision = false; //we found a gizmo that is closer to the ball than a wall
                            if (!gizmo.getGizmoType().equals("Star"))
                                newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1);
                        }
                    }
                }
            }
        }


        CollisionDetails cd = new CollisionDetails(shortestTime, newVelo); //possibly add ID of the gizmo it will collide with
        return cd;
    }

    public void getAudio(String path) {
        try {
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(new File(path));
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private void playSound(iGizmo giz){
        switch (giz.getGizmoType()){
            case "Square":
                getAudio("res/clips/jump.wav");
                break;
            case "Circle":
                getAudio("res/clips/jump.wav");
                break;
            case "Triangle":
                getAudio("res/clips/jump.wav");
                break;
            case "RightFlipper":
                if(((RightFlipper) giz).isMoving()){
                    getAudio("res/clips/slap.wav");
                }else {
                    getAudio("res/clips/jump.wav");
                }
                break;
            case "LeftFlipper":
                if(((LeftFlipper) giz).isMoving()){
                    getAudio("res/clips/slap.wav");
                }else {
                    getAudio("res/clips/jump.wav");
                }
                break;
            case "Star":
                getAudio("res/clips/death_ray.wav");
        }
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
    public void saveGame(File file) {
       System.out.println("SAVING GAME\n\n");
        try {
            FileWriter fileWriter = new FileWriter(file);

            //balls
            for(Ball ball: balls){
                fileWriter.write(ball.toString() + "\n");
            }

            //gizmos
            for(iGizmo gizmo : gizmos) {
                fileWriter.write(gizmo.toString() + "\n");
                //rotations
                if(gizmo.getRotationCount() > 0) {
                    for(int i = 0; i < gizmo.getRotationCount(); i++) {
                        fileWriter.write("Rotate " + gizmo.getID() + "\n");
                    }
                }
            }

            //connections
            for(iGizmo gizmo : gizmos) {
                if(gizmo.getGizmoConnections()!=null){
                    if(gizmo.getGizmoConnections().size() > 0) {
                        for(String giz : gizmo.getGizmoConnections()) {
                            fileWriter.write("Connect " + gizmo.getID() +" "+ giz +"\n");
                        }
                    }
                }
            }

            //key connections
            for(iGizmo gizmo : gizmos) {
                if(gizmo.getKeyConnections() != null & gizmo.getKeyConnections().size() > 0) {
                    for(String key : gizmo.getKeyConnections()) {
                        fileWriter.write("KeyConnect key " + key + " up " + gizmo.getID() + "\n");
                        fileWriter.write("KeyConnect key " + key + " down " + gizmo.getID()+ "\n");
                    }
                }
            }
            fileWriter.flush();

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadGame(File file) {
       System.out.println("loading game\n\n");
        gizmos = new ArrayList<>();
        balls = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            String[] inputStream;
            //wipe all previous tiles
            wipeSpaces();
            while((line = bufferedReader.readLine()) != null) {
                inputStream = line.split(" ");
                int gizXCoord =0;
                int gizYCoord =0;
                switch(inputStream[0]) {
                    case "Ball":
                        Ball ball = new Ball(inputStream[1], Double.parseDouble(inputStream[2]), Double.parseDouble(inputStream[3]), Double.parseDouble(inputStream[4]), Double.parseDouble(inputStream[5]));
                        balls.add(ball);
                        break;
                    case "Absorber":
                        gizXCoord = Integer.parseInt(inputStream[2]);
                        gizYCoord = Integer.parseInt(inputStream[3]);
                        Absorber abs = new Absorber(inputStream[1], gizXCoord, gizYCoord, Integer.parseInt(inputStream[4]), Integer.parseInt(inputStream[5]));
                        gizmos.add(abs);
                        setSpaces(gizXCoord, gizYCoord, true, abs);
                        break;
                    case "Square":
                        gizXCoord = Integer.parseInt(inputStream[2]);
                        gizYCoord = Integer.parseInt(inputStream[3]);
                        Square square = new Square(inputStream[1], gizXCoord, gizYCoord);
                        gizmos.add(square);
                        setSpaces(gizXCoord, gizYCoord, true, square);
                        break;
                    case "Circle":
                        gizXCoord = Integer.parseInt(inputStream[2]);
                        gizYCoord = Integer.parseInt(inputStream[3]);
                        GizmoCircle circle = new GizmoCircle(inputStream[1], gizXCoord, gizYCoord, Double.parseDouble(inputStream[4]));
                        gizmos.add(circle);
                        setSpaces(gizXCoord, gizYCoord, true, circle);
                        break;
                    case "Triangle":
                        gizXCoord = Integer.parseInt(inputStream[2]);
                        gizYCoord = Integer.parseInt(inputStream[3]);
                        Triangle triangle = new Triangle(inputStream[1], gizXCoord, gizYCoord);
                        gizmos.add(triangle);
                        setSpaces(gizXCoord, gizYCoord, true, triangle);
                        break;
                    case "RightFlipper":
                        gizXCoord = Integer.parseInt(inputStream[2]);
                        gizYCoord = Integer.parseInt(inputStream[3]);
                        RightFlipper rightFlipper = new RightFlipper(inputStream[1], gizXCoord, gizYCoord);
                        gizmos.add(rightFlipper);
                        setSpaces(gizXCoord, gizYCoord, true, rightFlipper);
                        break;
                    case "LeftFlipper":
                        gizXCoord = Integer.parseInt(inputStream[2]);
                        gizYCoord = Integer.parseInt(inputStream[3]);
                        LeftFlipper leftFlipper = new LeftFlipper(inputStream[1], gizXCoord, gizYCoord);
                        gizmos.add(leftFlipper);
                        setSpaces(gizXCoord, gizYCoord, true, leftFlipper);
                        break;
                    case "Star":
                        gizXCoord = Integer.parseInt(inputStream[2]);
                        gizYCoord = Integer.parseInt(inputStream[3]);
                        Star star = new Star(inputStream[1], gizXCoord, gizYCoord);
                        gizmos.add(star);
                        setSpaces(gizXCoord, gizYCoord, true, star);
                        break;
                    case "Rotate":
                        for(iGizmo gizmo : gizmos) {
                            if(gizmo.getID().equals(inputStream[1])) {
                                gizmo.rotate();
                            }
                        }
                        break;
                    case "Connect":
                            iGizmo giz1 = null, giz2 = null;
                            for (iGizmo gizmo : gizmos) {
                                if (gizmo.getID().equals(inputStream[1])) {
                                    giz1 = gizmo;
                                }
                                if (gizmo.getID().equals(inputStream[2])) {
                                    giz2 = gizmo;
                                }
                            }
                            this.connectGizmos(giz1.getID(), giz2.getID());

                        break;
                    case "KeyConnect":
                        if(inputStream.length >= 5) {
                            String key = inputStream[2],
                                    id = inputStream[4];
                            keyConnectGizmo(id, key);
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

        //spaces = getSpaceGrid();

        this.hasChanged();
        this.notifyObservers();
    }

    //helper method to find if gizmo is in array
    public boolean gizmoFound(String id) {
        for (iGizmo gizmo : gizmos) {

            if (gizmo.getID().equals(id)) {
                return true;
            }

        }
        return false;
    }


    //helper method to get gizmo by id, needed for connections
    public iGizmo getGizmoByID(String id) {

        if (gizmoFound(id)) {
            for (iGizmo gizmo : gizmos) {
                if (gizmo.getID().equals(id)) {
                    return gizmo;
                }
            }
        }
        return null;
    }

    public boolean connectGizmos(String id, String id2) {
        //checking gizmos are actually on board
        if (gizmoFound(id) && gizmoFound(id2)) {
            //actually getting the gizmo to add connection to
            iGizmo gizmo1 = getGizmoByID(id);

            //adding gizmo2 to gizmo1 list of connections
            gizmo1.setGizmoConnection(id2);

            int index = 0;
            for (iGizmo giz:gizmos) {
                if(giz.getID().equals(id)){
                    gizmos.set(index,gizmo1);
                    return true;
                }
                 index ++;
            }
        }
        return false;
    }

    public boolean keyConnectGizmo(String id, String key) {
        if (gizmoFound(id)) {
            //get the gizmo
            iGizmo gizmo = getGizmoByID(id);

            if(!gizmo.getGizmoConnections().contains(key))
                //set the key connection
                gizmo.setKeyConnection(key.toLowerCase());
                keyTriggers.put(gizmo, (key.toLowerCase()).charAt(0));
                return true;
            }
        return false;
    }

    public void checkConnections(iGizmo gizmo, Ball ball) {
        //get the collided gizmos id
        if (!collisionGizmo.getID().equals("")) {
            //get the triggers
            if(gizmo.getGizmoConnections()==null) return;
            for (int i = 0; i < gizmo.getGizmoConnections().size(); i++) {
                //set hit to true i.e activate colour
                getGizmoByID(gizmo.getGizmoConnections().get(i)).setHit(true);
                checkKeyConnections(getGizmoByID(gizmo.getGizmoConnections().get(i)),ball);
            }
        }
    }

    public void checkKeyConnections(iGizmo gizmo, Ball ball) {
        //only check if key press list isn't empty
        if (gizmo.getGizmoType().equals("LeftFlipper") || gizmo.getGizmoType().equals("RightFlipper")) {
            if (!keyTriggers.isEmpty()) {

                if(keyTriggers.containsKey(gizmo)) {

                    char keyChar = keyTriggers.get(gizmo);
                    try {
                        Robot robot = new Robot();
                        if (ball.getSpeed() > 0) {
                            System.out.println(keyChar);
                            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(keyChar));
                            robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(keyChar));
                        }
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



    public boolean removeConnection(iGizmo gizmo, String id){
        return gizmo.removeGizmoConnection(id);
    }



}
