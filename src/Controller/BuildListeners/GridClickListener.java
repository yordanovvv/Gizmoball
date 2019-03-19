package Controller.BuildListeners;

import Model.*;
import View.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridClickListener implements MouseListener {

    private String id;
    private String selected;
    private iGizmo moveGizmo;

    GameBoard board;

    private final int GRID_SIZE = 30;

    int gridX;
    int gridY;

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

        boolean canPlace;
        if (gridX>=20 || gridY>=20){
            canPlace = false;
        } else {
            canPlace = !m.checkSpace(gridX, gridY, null);
        }

        iGizmo giz = null;

        //Variables to get maximum id of gizmo, avoids bugs
        int idNo=1;
        int bigId=0;

        if (canPlace) {
            switch (selected) {
                case "ball":
                    PlaceBallPopup compPop = new PlaceBallPopup(m, gridX, gridY);
                    break;
                case "circle":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("Circle")) {
                            bigId = Integer.parseInt(g.getID().substring(1));
                            System.out.println(bigId);
                            if (idNo > bigId){
                                bigId = idNo;
                            }
                        }
                    }
                    giz = new GizmoCircle("C" + (bigId+1), gridX, gridY);
                    if (m.checkSpace(gridX, gridY, giz)){   //must be !checkspace bc checkspace returns false is free
                        m.setSpaces(gridX, gridY, true, giz);
                        m.addGizmo(giz);
                    }
                    break;
                case "square":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("Square")) {
                            bigId = Integer.parseInt(g.getID().substring(1));
                            System.out.println(bigId);
                            if (idNo > bigId){
                                bigId = idNo;
                            }
                        }
                    }
                    giz = new Square("S" + (bigId+1), gridX, gridY);
                    if (m.checkSpace(gridX, gridY, giz)){
                        m.setSpaces(gridX, gridY, true, giz);
                        m.addGizmo(giz);
                    }
                    break;
                case "triangle":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("Triangle")) {
                                bigId = Integer.parseInt(g.getID().substring(1));
                                System.out.println(bigId);
                                if (idNo > bigId){
                                    bigId = idNo;
                                }
                        }
                    }
                    giz = new Triangle("T" + (bigId+1), gridX, gridY);
                    if (m.checkSpace(gridX, gridY, giz)){
                        m.setSpaces(gridX, gridY, true, giz);
                        m.addGizmo(giz);
                    }
                    break;
                case "rightFlipper":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("RightFlipper")) {
                                bigId = Integer.parseInt(g.getID().substring(2));
                                System.out.println(bigId);
                                if (idNo > bigId){
                                    bigId = idNo;
                                }
                        }
                    }
                    giz = new RightFlipper("RF" + (bigId+1), gridX, gridY);
                    if (gridX-1>=0 && gridY+1<20) {
                        if (m.checkSpace(gridX, gridY, giz)) {
                            m.setSpaces(gridX, gridY, true, giz);
                            m.addGizmo(giz);
                            SelectKeyPopup keyPopup = new SelectKeyPopup(m, giz);
                        }
                    }
                    break;
                case "leftFlipper":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("LeftFlipper")) {
                                bigId = Integer.parseInt(g.getID().substring(2));
                                System.out.println(bigId);
                                if (idNo > bigId){
                                    bigId = idNo;
                                }
                        }
                    }
                    giz = new LeftFlipper("LF" + (bigId+1), gridX, gridY);
                    if (gridX+1<20 && gridY+1<20) {
                        if (m.checkSpace(gridX, gridY, giz)) {
                            m.setSpaces(gridX, gridY, true, giz);
                            m.addGizmo(giz);
                            SelectKeyPopup keyPopup = new SelectKeyPopup(m, giz);
                        }
                    }
                    break;
                case "star":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getGizmoType().equals("Star")) {
                                bigId = Integer.parseInt(g.getID().substring(2));
                                System.out.println(bigId);
                                if (idNo > bigId){
                                    bigId = idNo;
                                }
                        }
                    }
                    giz = new Star("ST" + (bigId+1), gridX, gridY);
                    if(gridX+2<20 && gridY+2<20 && gridX-1>=0 && gridY-1>=0) {
                        if (m.checkSpace(gridX, gridY, giz)) {
                            m.setSpaces(gridX, gridY, true, giz);
                            m.addGizmo(giz);
                        }
                    }
                    break;
                case "move":
                    if(moveGizmo != null) {
                        System.out.println();
                        if (m.checkSpace(gridX, gridY, moveGizmo)) {
                                //clear spaces gizmo was at
                                m.setSpaces(moveGizmo.getXCoord(), moveGizmo.getYCoord(), false, moveGizmo);
                                //move gizmo
                                moveGizmo.setXCoord(gridX);
                                moveGizmo.setYCoord(gridY);
                                //set spaces of new position
                                m.setSpaces(gridX, gridY, true, moveGizmo);
                                moveGizmo = null;
                        }
                    }
                    break;
                case "connectKey":

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
                        if(g.getGizmoType() == "RightFlipper"){
                            if ((((RightFlipper)g).getRotatedXCoord() == gridX &&((RightFlipper)g).getRotatedYCoord() == gridY)) {
                                if(m.checkRotatedFlipperSpace(g.getXCoord(), g.getYCoord(),g)) {
                                    m.setSpaces(g.getXCoord(), g.getYCoord(), false, g);
                                    ((RightFlipper) g).rotateFlipper();
                                    m.setiGizmo(g);
                                    m.setSpaces(g.getXCoord(), g.getYCoord(), true, g);
                                    ((RightFlipper)g).setRotated(false);
                                    m.setiGizmo(g);
                                }
                            }

                        }else if(g.getGizmoType() == "LeftFlipper"){
                            if (((LeftFlipper)g).getRotatedXCoord() == gridX &&((LeftFlipper)g).getRotatedYCoord() == gridY) {
                                if(m.checkRotatedFlipperSpace(g.getXCoord(), g.getYCoord(),g)) {
                                        m.setSpaces(g.getXCoord(), g.getYCoord(), false, g);
                                        ((LeftFlipper) g).rotateFlipper();
                                        m.setSpaces(g.getXCoord(), g.getYCoord(), true, g);
                                }
                            }
                        }else{
                            if (g.getXCoord() == gridX && g.getYCoord() == gridY) {
                                if (g.getGizmoType() == "Triangle") {
                                    g.rotate();
                                }
                            }
                        }
                    }
                    break;
                case "delete":
                    iGizmo removeGimzo = null;
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getXCoord() == gridX && g.getYCoord() == gridY) {
                            removeGimzo = g;
                            m.setSpaces(gridX, gridY, false, g);
                        }
                    }
                    m.removeGizmo(removeGimzo);
                    break;
                case "move":
                    for (iGizmo g : m.getGizmos()) {
                        if (g.getXCoord() == gridX && g.getYCoord() == gridY) {
                            moveGizmo = g;
                            System.out.println("- : " + g.getID());
                        }
                    }
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

        if (end_gridX>=20 || end_gridX < 0) return;
        if (end_gridY>=20 || end_gridY < 0) return;
        /*
       if (selected=="absorber"){
            //System.out.println("Absorber start : " + start_gridX + " | " + start_gridY);
            //System.out.println("Absorber end : " + end_gridX + " | " + end_gridY);
            if (start_gridX>end_gridX){
                int temp = start_gridX;
                start_gridX = end_gridX;
                end_gridX = temp;
            }
            if (start_gridY>end_gridY){
                int temp = start_gridY;
                start_gridY = end_gridY;
                end_gridY = temp;
            }
            //create absorber here!
           Absorber abs = new Absorber("A", start_gridX, start_gridY, end_gridX+1, end_gridY+1);
           boolean absPlace = true;
           int posWidth=abs.getWidth(), posHeight=abs.getHeight();
           for(int x=0; x<posWidth; x++){
               for (int y=0; y<posHeight; y++){
                   if (m.checkSpace(start_gridX +x, start_gridY+y)){
                       absPlace = false;
                   }
               }
           }
           if(absPlace){
               m.addGizmo(abs);
               m.setSpaces(start_gridX, start_gridY,  true, abs);
           }
        }
        */
        if (start_gridX>end_gridX){
            int temp = start_gridX;
            start_gridX = end_gridX;
            end_gridX = temp;
        }
        if (start_gridY>end_gridY){
            int temp = start_gridY;
            start_gridY = end_gridY;
            end_gridY = temp;
        }

        int posWidth, posHeight;

        switch (selected){
            case "absorber":
                Absorber abs = new Absorber("A", start_gridX, start_gridY, end_gridX+1, end_gridY+1);
                if (m.checkSpace(start_gridX, start_gridY, abs)){
                    m.addGizmo(abs);
                    m.setSpaces(start_gridX, start_gridY,  true, abs);
                }
                break;
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
