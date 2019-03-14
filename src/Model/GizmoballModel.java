package Model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GizmoballModel extends iModel {

    private ArrayList<Ball> balls;
    //private Ball ball;
    private ArrayList<iGizmo> gizmos;
    private Wall walls;
    private iGizmo absorber;
    private iGizmo star;
    private ArrayList<Character> keys;
    private ArrayList<iGizmo> flippers;
    private boolean absorberCollision = false, starCollision = false, starShotOut = false;
    private iGizmo collisionGizmo = null;
    private boolean wallCollision = false;
    private double gravity = 25; //25L/sec^2

    public GizmoballModel() {

        balls = new ArrayList<>();
        flippers = new ArrayList<>();
        keys = new ArrayList<>();
        gizmos = new ArrayList<iGizmo>();

        balls.add(new Ball("B1", 8, 5,  7.5, 7.5)); //2.5 = 50L/sec if moveTime is 0.05 (20 ticks/sec)
        //balls.add(new Ball("B2", 6, 7,  7.5, 7.5));

        walls = new Wall(0, 0, 20, 20);

        absorber = new Absorber("A1", 0, 18, 20, 20);
        star = new Star("init_star",0,0);
        gizmos.add(absorber);

        Star star = new Star("St1",5,5);
        gizmos.add(star);
       /* RightFlipper rightFlipper = new RightFlipper("R1", 6, 7);
        gizmos.add(rightFlipper);

        LeftFlipper leftFlipper = new LeftFlipper("L1", 8, 7);
        gizmos.add(leftFlipper);
        flippers.add(rightFlipper);
        flippers.add(leftFlipper);

        keys.add('r');
        keys.add('t');*/

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
    public ArrayList<Character> getKeys(){
        ArrayList<Character> newList = new ArrayList<>();
        for(iGizmo gizmo : gizmos) {
            if(gizmo.getKeyConnections() != null) {
                for(String key : gizmo.getKeyConnections()) {
                    if(key != null) {
                        newList.add(key.charAt(0));
                    }
                }
            }

        }
        keys = newList;
        return keys;
    }

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
                    }
                  /*  else if(starCollision == true && starShotOut == false){
                        playSound(collisionGizmo);
                        ball = moveBallForTime(ball, tuc + moveTime);
                        star.addBall(ball);
                        ball.setExactX((((Star) star).getmiddleXCoord())*30);
                        ball.setExactY((((Star) star).getmiddleYCoord())*30);
                        ball.setStopped(true);
                        ball.setVelo(new Vect(0,0));

                        ball= ((Star) star).shootBallOut();

                        ball.calculateSpeed(tuc + moveTime);

                        starCollision = false;
                        starShotOut = true;

                    }*/
                    else { //collision

                        ball = moveBallForTime(ball, tuc); //collision in time tuc
                        ball.setVelo(cd.getVelo());

                        if(collisionGizmo != null && wallCollision == false)
                        {
                            ball.calculateSpeed(tuc);

                            collisionGizmo.setHit(!collisionGizmo.getHit());
                            if(collisionGizmo.getGizmoConnections()!=null) {
                                if (!collisionGizmo.getGizmoType().equals("Star"))
                                    checkConnections(collisionGizmo, ball);
                            }

                            System.out.println(ball.getSpeed());
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

    boolean[][] spaces = new boolean[20][20];

    public void setSpaces(int gridX, int gridY, boolean val, iGizmo g){
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
                spaces[gridX+1][gridY] = val;
                spaces[gridX+1][gridY+1] = val;
                break;
            default:
                spaces[gridX][gridY] = val; //this might break it?
        }




        spaces[gridX][gridY] = val;
    }

    public boolean checkSpace(int gridX, int gridY){
        return spaces[gridX][gridY];
        /*if (spaces[gridX][gridY]){
            return true;
        }
        return false;


        /*


        for (iGizmo g : getGizmos()){

        //Check area of right flipper
        if (g.getGizmoType() == "RightFlipper"){
            if ((g.getXCoord()==gridX && g.getYCoord()==gridY)
                    || (g.getXCoord()==gridX+1 && g.getYCoord()==gridY)
                    || (g.getXCoord()==gridX && g.getYCoord()==gridY-1)
                    || (g.getXCoord()==gridX+1 && g.getYCoord()==gridY-1)){
                return false;
            }
        }
        //Check area of left flipper
        if (g.getGizmoType() == "LeftFlipper"){
            if ((g.getXCoord()==gridX && g.getYCoord()==gridY)
                    || (g.getXCoord()==gridX-1 && g.getYCoord()==gridY)
                    || (g.getXCoord()==gridX && g.getYCoord()==gridY-1)
                    || (g.getXCoord()==gridX-1 && g.getYCoord()==gridY-1)){
                return false;
            }
        }
        //Check area of the absorber
        if (g.getGizmoType() == "Absorber"){
            if (gridX>=g.getXCoord() && gridX<=g.getXCoord()+g.getWidth()
                    && (gridY>=g.getYCoord() && gridY<=g.getYCoord()+g.getHeight())){
                return false;
            }
        }
        //Check area of the star
        if (g.getGizmoType() == "Star"){
            if ((g.getXCoord() == gridX && g.getYCoord()==gridY)
                    || (g.getXCoord() == gridX-1 && g.getYCoord()==gridY)
                    || (g.getXCoord() == gridX && g.getYCoord()==gridY-1)
                    || (g.getXCoord() == gridX-1 && g.getYCoord()==gridY-1)){
                return false;
            }
        }
        //Check for any other 1 tile sized gizmo
        if (g.getXCoord() == gridX && g.getYCoord()==gridY){
            //return false;
        }

        }*/
        //return true;
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
        //iterating through  gizmos
        // (i.e. lines and circles that gizmos are composed of) to find tuc
        for (iGizmo gizmo : gizmos) {
            ArrayList<LineSegment> lineSegs = gizmo.getLines();
            ArrayList<Circle> circls = gizmo.getCircles();

            if (lineSegs.size() > 0) {
                for (LineSegment ls : lineSegs) {

                    if (gizmo.getGizmoType().equals("Star")) {//star collisions

                        System.out.println("star");
                        absorberCollision = false;
                        wallCollision = false;
                        starCollision = true;
                        Star star = (Star) gizmo;

                        double time = Geometry.timeUntilRotatingWallCollision(
                                ls,
                                star.getCenter(),
                                Math.toRadians(star.getRotationAngle()+18)/ 1000,
                                ballCircle,
                                ballVelocity);

                        if (time < shortestTime) {
                            shortestTime = time;
                            System.out.println("star line collision");
                            newVelo = Geometry.reflectRotatingWall(
                                    ls,
                                    star.getCenter(),
                                    Math.toRadians(star.getRotationAngle()+18)/ 1000,
                                    ballCircle,
                                    ballVelocity,1);
                       }

                        if (starShotOut != true) {
                               /* for (LineSegment feeder : star.getFeederLines()) {
                                    if (ls.equals(feeder)) {
                                        starCollision = true;
                                        this.star = star;
                                    }
                                }*/
                        }

                    } else {
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
                                newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
                        }
                    }
                }
            }
            if (circls.size() > 0) {
                for (Circle c : circls) {
                    if (gizmo.getGizmoType().equals("Star")) { //star collisions
                        absorberCollision = false;
                        wallCollision = false;
                        starCollision = true;
                        Star star = (Star) gizmo;
                        double time = Geometry.timeUntilRotatingCircleCollision(
                                c,
                                star.getCenter(),
                                Math.toRadians(star.getRotationAngle()+18)/ 1000,
                                ballCircle,
                                ballVelocity);

                        if (time < shortestTime) {
                            shortestTime = time;
                            newVelo = Geometry.reflectRotatingCircle(
                                    c,
                                    star.getCenter(),
                                    Math.toRadians(star.getRotationAngle()+18)/ 1000,
                                    ballCircle,
                                    ballVelocity,1);
                        }
                    } else {
                        timeC = Geometry.timeUntilCircleCollision(c, ballCircle, ballVelocity);
                        if (timeC < shortestTime) {
                            shortestTime = timeC; //we are hitting a circle
                            absorberCollision = false;
                            starCollision = false;
                            starShotOut = false;
                            collisionGizmo = gizmo;
                            newVelo = Geometry.reflectCircle(c.getCenter(), ball.getCircle().getCenter(), ball.getVelo(), 1.0);
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
                        Absorber abs = new Absorber(inputStream[1], Integer.parseInt(inputStream[2]), Integer.parseInt(inputStream[3]), Integer.parseInt(inputStream[4]), Integer.parseInt(inputStream[5]));
                        gizmos.add(abs);
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
            return true;
        }
        return false;
    }

    public void keyConnectGizmo(String id, String key) {
        if (gizmoFound(id)) {
            //get the gizmo
            iGizmo gizmo = getGizmoByID(id);
            //set the key connection
            gizmo.setKeyConnection(key);
        }
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
        if (!keys.isEmpty()) {
            for (int i = 0; i < gizmo.getKeyConnections().size(); i++) {
                if (keys.get(i).equals(gizmo.getKeyConnections().get(i).charAt(0))) {
                    //get the type of gizmo
                    String gizmoType = gizmo.getGizmoType();
                    gizmo.setHit(true);

                    if (gizmoType.equals("LeftFlipper") || gizmoType.equals("RightFlipper")) {
                        //activate left flipper, need direction
                        try {
                            //we are mimicking someone pressing the key
                            //yes, it's cheeky
                            Robot robot = new Robot();
                            System.out.println(ball.getSpeed());
                            if(ball.getSpeed()>0){
                                robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(keys.get(i)));
                                robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(keys.get(i)));
                            }
                        } catch (AWTException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    //TODO need to decide if player can remove all connections or specific ones

    public void removeConnections(String id){


    }

    public void removeKeyConnections(String id){

    }

}
