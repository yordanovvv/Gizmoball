package Controller.BuildListeners;

import Model.*;
import View.ComponentPopup;
import View.GameBoard;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridClickListener implements MouseListener {

    private String id;
    private String selected;

    GameBoard board;
    //private MainFrame mf;

    private final int GRID_SIZE = 30;

    int gridX;
    int gridY;
    double rad = 0.5 ;

    iModel m;

    public GridClickListener(GameBoard board, String selected){
        this.selected = selected;
        this.board = board;

        m = board.getGizModel();
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

        gridX = e.getX()/GRID_SIZE;
        gridY = e.getY()/GRID_SIZE;

        boolean canPlace = checkSpace();
        if (gridX>=20 || gridY>=20){
            canPlace = false;
        }

        System.out.println(gridX + " | " + gridY);

        iGizmo giz;

        //TODO : Change id thingy to get maximum id instead of counting ids
        //Prevents bugs!

        int idNo=1;

        if (canPlace) {
            switch (selected) {
                case "ball":
                    //Would prefer this here, but can also have it when BALL button clicked - C
                    //Hope adding ball works fine like that -L
                    ComponentPopup compPop = new ComponentPopup(); //Not doing anything with this yet,  don't have multiple ball support

                    for (Ball b : m.getBalls()) {
                            idNo++;
                    }
                    double ballXVelo = compPop.get_X_Velocity();
                    double ballYVelo = compPop.get_Y_Velocity();

                    Ball b = new Ball("B" + idNo, gridX, gridY, ballXVelo, ballYVelo); //TODO get velocity from the pop up
                    m.addBall(b);
                    break;
                case "circle":
                    //Used to get next unique idNumber
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("GizmoCircle")) {
                            idNo++;
                        }
                    }
                    //TODO : Update this when GizmoCircle is defined.
                    giz = new GizmoCircle("C" + idNo, gridX, gridY, rad); //GizmoCircle has not yet been defined
                    m.addGizmo(giz);
                    break;
                case "square":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("Square")) {
                            idNo++;
                        }
                    }
                    giz = new Square("S" + idNo, gridX, gridY);
                    m.addGizmo(giz);
                    break;
                case "triangle":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("Triangle")) {
                            idNo++;
                        }
                    }
                    giz = new Triangle("T" + idNo, gridX, gridY);
                    m.addGizmo(giz);
                    break;
                case "rightFlipper":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("RightFlipper")) {
                            idNo++;
                        }
                    }
                    giz = new RightFlipper("RF" + idNo, gridX, gridY);
                    m.addGizmo(giz);
                    break;
                case "leftFlipper":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("LeftFlipper")) {
                            idNo++;
                        }
                    }
                    giz = new LeftFlipper("LF" + idNo, gridX, gridY);
                    m.addGizmo(giz);
                    break;

                //---------------------------------



            }
        } else {
            //TODO : Move this code into a seperate class???
            switch (selected) {
                case "rotate":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getXCoord() == gridX && g.getYCoord() == gridY) {
                            if (g.getGizmoType() == "Triangle") {
                                g.rotate();
                            }
                        }
                    }
                    break;
                case "connect":
                    break;
                case "disconnect":
                    break;
                case "delete":
                    iGizmo removeGimzo = null;
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getXCoord() == gridX && g.getYCoord() == gridY) {
                            System.out.println("clicked on a thing");
                            removeGimzo = g;
                        }
                    }
                    m.removeGizmo(removeGimzo);
                    break;
                case "move":
                    break;
                case "connectKey":
                    break;
            }
        }

    }

    //TODO : Move this to the model ????
    public boolean checkSpace(){
        for (iGizmo g : m.getGizmos()){
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
            //Check for any other 1 tile sized gizmo
            if (g.getXCoord() == gridX && g.getYCoord()==gridY){
              return false;
            }
        }
        return true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int start_gridX = gridX;
        int start_gridY = gridY;

        int end_gridX = e.getX()/GRID_SIZE;
        int end_gridY = e.getY()/GRID_SIZE;

       if (selected=="absorber"){
            System.out.println("Absorber start : " + start_gridX + " | " + start_gridY);
            System.out.println("Absorber end : " + end_gridX + " | " + end_gridY);
            //create absorber here!
            //TODO : Check overlap
            //Functionality for more than one absorber??
            //if (m.getAbsorber()==null) {
                Absorber abs = new Absorber("A", start_gridX, start_gridY, end_gridX+1, end_gridY+1);
                m.addGizmo(abs);
            //} else {
            //    System.out.println("Absorber already exists");
            //}
        }


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public String getSelected(){
        return selected;
    }

    public void setSelected(String s){
        this.selected = s;
    }

}
