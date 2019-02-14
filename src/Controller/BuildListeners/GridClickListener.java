package Controller.BuildListeners;

import Model.*;
import View.ComponentPopup;
import View.GameBoard;
import View.MainFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridClickListener implements MouseListener {

    private String id;
    private String selected;

    GameBoard board;
    //private MainFrame mf;

    int gridX;
    int gridY;

    private final int GRID_SIZE = 30;

    GizmoballModel m;

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
        //System.out.println("Mouse clicked at : " + e.getX() + " | " + e.getY());
        //System.out.println("Currently selected : " + selected);

        gridX = e.getX()/GRID_SIZE;
        gridY = e.getY()/GRID_SIZE;

        System.out.println("Placing a " +  selected + " at grid position " + gridX + " | " + gridY);

        iGizmo giz;

        //TODO ; Check gizmo position for overlapping gizmos
        int idNo=1;
        switch(selected){
            case "ball":
                //Would prefer this here, but can also have it when BALL button clicked - C
                ComponentPopup compPop = new ComponentPopup(); //Not doing anything with this yet,  don't have multiple ball support
                break;
            case "circle":
                //Used to get next unique idNumber
                for (iGizmo g : m.getGizmos()){
                    if (g.getGizmoType().equals("Circle")){
                        idNo++;
                    }
                }
                //TODO : Update this when Circle is defined.
                giz = new Circle(); //Circle has not yet been defined
                m.addGizmo(giz);
                break;
            case "square":
                for (iGizmo g : m.getGizmos()){
                    if (g.getGizmoType().equals("Square")){
                        idNo++;
                    }
                }
                giz = new Square("S"+idNo, gridX, gridY);
                m.addGizmo(giz);
                break;
            case "triangle":
                for (iGizmo g : m.getGizmos()){
                    if (g.getGizmoType().equals("Triangle")){
                        idNo++;
                    }
                }
                giz = new Triangle("T"+idNo,gridX, gridY);
                m.addGizmo(giz);
                break;
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
            if (m.getAbsorber()==null) {
                Absorber abs = new Absorber("A", start_gridX, start_gridY, end_gridX+1, end_gridY+1);
                m.addGizmo(abs);
            } else {
                System.out.println("Absorber already exists");
            }
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
