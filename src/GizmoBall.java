import Model.*;
import View.MainFrame;

import javax.swing.*;
import java.io.File;

public class GizmoBall {
    public static void main(String[] args) {
        iModel model = new GizmoballModel();
        MainFrame view = new MainFrame(model);
        model.addObserver(view.getGameBoard());
       // model.loadGame(new File("default.giz"));
    }
}
