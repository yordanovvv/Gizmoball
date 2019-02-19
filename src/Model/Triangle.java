package Model;

import physics.Angle;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

public class Triangle implements iGizmo {

    private String ID;
    private Color colour;
    private int XCoord;
    private int YCoord;
    private int height, width;
    private int rotationCount; //rotation count is set to 0 initially
    private ArrayList<LineSegment> lines;
    private ArrayList<Circle> circles;
    private ArrayList<String> gizmoConnections;
    private ArrayList<String> keyConnections;
    private final double constant = 30;
    private int rotationAngle;


    public Triangle(String id, int XCoord, int YCoord) {
        this.ID = id;
        this.XCoord = XCoord;
        this.YCoord = YCoord;


        lines = new ArrayList<>();
        circles = new ArrayList<>();
        gizmoConnections = new ArrayList<>();
        keyConnections = new ArrayList<>();

        rotationCount = 0;

        generateCircles();
        generateLines();

    }

    @Override
    public String getGizmoType() {
        return "Triangle";
    }

    @Override
    public void setID(String id) {
        this.ID = id;

    }

    @Override
    public void setXCoord(int x) {
        this.XCoord = x;

    }

    @Override
    public void setYCoord(int y) {
        this.YCoord = y;

    }

    @Override
    public void setColour(Color colour) {
        this.colour = colour;
    }

    @Override
    public void generateCircles() {

        Circle top = new Circle(XCoord*constant, YCoord*constant, 0);
        Circle right = new Circle((XCoord + 1)*constant, YCoord*constant, 0);
        Circle bottomRight = new Circle((XCoord+1)*constant, (YCoord + 1)*constant, 0);

        circles.add(top);
        circles.add(right);
        circles.add(bottomRight);

    }

    @Override
    public void generateLines() {
        LineSegment line1 = new LineSegment(XCoord*constant, YCoord*constant, (XCoord+1)*constant, (YCoord )*constant);
        LineSegment line2 = new LineSegment(XCoord*constant, YCoord*constant , (XCoord + 1)*constant, (YCoord + 1)*constant);
        LineSegment line3 = new LineSegment((XCoord+1)*constant, (YCoord)*constant, (XCoord + 1)*constant, (YCoord+1)*constant);

        lines.add(line1);
        lines.add(line2);
        lines.add(line3);

    }

    @Override
    public void setGizmoConnection(String id) {
        gizmoConnections.add(id);

    }

    @Override
    public void setKeyConnection(String key, String keyboard, String action) {

    }


    //TODO needs done as triangles can be rotated
    @Override
    public void rotate() {
        int rotationDegree = 90;
        rotationCount++;
        if(rotationCount > 3) rotationCount = 0;

        System.out.println(rotationCount);
        rotationAngle  = rotationDegree * rotationCount;

        Circle  center = new Circle((XCoord), (YCoord),.5*constant); //make a circle that fills that set square and get its center

        for (int i = 0; i < lines.size(); i++) {
            Angle rotation = new Angle(Math.toRadians(rotationAngle));
            LineSegment currentLine = lines.get(i);
            lines.set(i,Geometry.rotateAround(currentLine,center.getCenter(),rotation));
        }


       /* for (int i = 0; i < circles.size(); i++) {
            Angle rotation = new Angle(rotationAngle);
            Circle currentCircle = circles.get(i);
            //circles.set(i, Geometry.rotateAround(currentCircle,center.getCenter(),rotation));
           // System.out.println(circles.get(i).getCenter().x());
            //System.out.println(circles.get(i).getCenter().y());
        }
*/

        //we need to clear the arraylists as we are redrawing
       /* lines.clear();
        circles.clear();

        if (rotationCount == 3) {
            //set it back to the start
            rotationCount=0;

        } else if (rotationCount == 0) {

            //if rotation is 0, we are back to start, so lines same as very start aka what we call in constructor???

            LineSegment line1 = new LineSegment(XCoord*constant, YCoord*constant, (XCoord+1)*constant, (YCoord )*constant);
            LineSegment line2 = new LineSegment(XCoord*constant, YCoord*constant , (XCoord + 1)*constant, (YCoord + 1)*constant);
            LineSegment line3 = new LineSegment((XCoord+1)*constant, (YCoord)*constant, (XCoord + 1)*constant, (YCoord+1)*constant);

            lines.add(line1);
            lines.add(line2);
            lines.add(line3);

            rotationCount++;

        } else if (rotationCount == 1) {

            LineSegment line1 = new LineSegment(XCoord*constant, YCoord*constant, (XCoord+1)*constant, (YCoord )*constant);
            LineSegment line2 = new LineSegment(XCoord*constant, YCoord*constant , (XCoord + 1)*constant, (YCoord + 1)*constant);
            LineSegment line3 = new LineSegment((XCoord)*constant, (YCoord+1)*constant, (XCoord+1)*constant, (YCoord)*constant);

            lines.add(line1);
            lines.add(line2);
            lines.add(line3);


            rotationCount++;

        } else if (rotationCount == 2) {

            rotationCount++;

        } else {
            rotationCount = 3;
        }

        System.out.println(rotationCount);

*/
        //rotationAngle = 45*rotationCount;
    }

    @Override
    public int getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getXCoord() {
        return XCoord;
    }

    @Override
    public int getYCoord() {
        return YCoord;
    }


    @Override
    public ArrayList<Circle> getCircles() {
        return circles;
    }

    @Override
    public ArrayList<LineSegment> getLines() {
        return lines;
    }

    @Override
    public ArrayList<String> getGizmoConnections() {
        return gizmoConnections;
    }

    @Override
    public ArrayList<String> getKeyConnections() {
        return keyConnections;
    }

    @Override
    public void removeGizmoConnection(String id) {
        gizmoConnections.remove(id);
    }

    @Override
    public void removeKeyConnection() {

    }

    @Override
    public void addBall(Ball ball) {

    }


    @Override
    public String toString() {
        return getGizmoType() + " " + getID() + " " + getXCoord() + " " + getYCoord();
    }

    public int getRotationCount() {
        return rotationCount;
    }
}
