package Controller.BuildListeners;

import Model.Circle;
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

    private final int GRID_SIZE = 30;

    public GridClickListener(MainFrame mainFrame, String selected){
        this.selected = selected;
        //this.mf = mainFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked at : " + e.getX() + " | " + e.getY());
        System.out.println("Currently selected : " + selected);

        int gridX = e.getX()/GRID_SIZE;
        int gridY = e.getY()/GRID_SIZE;

        System.out.println("Placing a " +  selected + " at grid position " + gridX + " | " + gridY);

        iGizmo g;

        switch(selected){
            case "ball":
                //Would prefer this here, but can also have it when BALL button clicked - C
                ComponentPopup compPop = new ComponentPopup();
                break;
            case "circle":
                g = new Circle();
                //gizmos.addGizmo(c, gridX, gridY); //Assumption that .addGizmo does a check etc...
                //Just how do I pass in the gizmoModel here, or do I even need to do that??
                break;
            case "square":
                g = new Square();
                break;
                //  |
                //  |
                //  |
                // \/  and so on...
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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
