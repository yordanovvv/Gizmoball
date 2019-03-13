package Controller.BuildListeners;

import Model.*;
import View.ConnectGizmosPopup;
import View.PlaceBallPopup;
import View.GameBoard;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridClickListener implements MouseListener {

    private String id;
    private String selected;
    private iGizmo moveGizmo;

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
        moveGizmo = null;

    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

        gridX = e.getX()/GRID_SIZE;
        gridY = e.getY()/GRID_SIZE;

        boolean canPlace = m.checkSpace(gridX, gridY);
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
                    PlaceBallPopup compPop = new PlaceBallPopup(m, gridX, gridY); //Not doing anything with this yet,  don't have multiple ball support
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
                case "move":
                    if(moveGizmo != null) {
                        moveGizmo.setXCoord(gridX);
                        moveGizmo.setYCoord(gridY);
                        moveGizmo = null;
                    }
                    break;
                    case "star":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("Star")) {
                            idNo++;
                        }
                    }
                    giz = new Star("ST" + idNo, gridX, gridY);
                    m.addGizmo(giz);
                    break;
                default:
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
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getXCoord() == gridX && g.getYCoord() == gridY) {
                            moveGizmo = g;
                        }
                    }
                    break;
                case "connectKey":
                    break;
            }
        }

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
