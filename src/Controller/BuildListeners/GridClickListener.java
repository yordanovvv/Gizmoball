package Controller.BuildListeners;

import Model.Circle;
import Model.GizmoballModel;
import Model.Square;
import Model.iGizmo;
import View.ComponentPopup;
import View.MainFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridClickListener implements MouseListener {

    private String id;
    private String selected;
    //private MainFrame mf;

    int gridX;
    int gridY;

    private final int GRID_SIZE = 30;

    GizmoballModel m;

    public GridClickListener(MainFrame mainFrame, String selected){
        this.selected = selected;
        //this.mf = mainFrame;
        //mainFrame.getGameBoard();
        //mainFrame.getGameBoard().getGizModel();
        m = mainFrame.getGameBoard().getGizModel();
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse clicked at : " + e.getX() + " | " + e.getY());
        System.out.println("Currently selected : " + selected);

        gridX = e.getX()/GRID_SIZE;
        gridY = e.getY()/GRID_SIZE;

        System.out.println("Placing a " +  selected + " at grid position " + gridX + " | " + gridY);

        iGizmo giz;

        switch(selected){
            case "ball":
                //Would prefer this here, but can also have it when BALL button clicked - C
                ComponentPopup compPop = new ComponentPopup();
                break;
            case "circle":
                giz = new Circle();
                m.addGizmo(giz);
                //gizmos.addGizmo(giz, gridX, gridY); //Assumption that .addGizmo does a check etc...
                break;
            case "square":
                giz = new Square();
                break;
            //  |
            //  |
            //  |
            // \/  and so on...
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int start_gridX = gridX;
        int start_gridY = gridY;

        int end_gridX = e.getX()/GRID_SIZE;
        int end_gridY = e.getY()/GRID_SIZE;

        System.out.println("release mouse");
        if (selected=="absorber"){

            System.out.println("Absorber start : " + start_gridX + " | " + start_gridY);
            System.out.println("Absorber end : " + end_gridX + " | " + end_gridY);

            //create absorber here!

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
