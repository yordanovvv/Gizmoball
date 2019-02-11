package View;

import Model.GizmoballModel;

public interface iMainFrame {

    public void switchModes(int mode);

    public int generatePopUp(String title, String msg);

    public void quit();
}
