import Model.*;
import View.MainFrame;

import javax.swing.*;

public class GizmoBall {
    public static void main(String[] args) {
        iModel model = new GizmoballModel();
        MainFrame view = new MainFrame(model);
        model.addObserver(view.getGameBoard());
    }
}
