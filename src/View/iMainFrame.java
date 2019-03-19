package View;

import Model.GizmoballModel;

public interface iMainFrame {

    public void switchModes(int mode,String list);

    public int generatePopUp(String title, String msg);

    public void quit();

    public void setGridVisability(boolean visability);

}
