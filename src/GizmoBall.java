import Model.*;
import View.MainFrame;

import javax.swing.*;

public class GizmoBall {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        iModel model = new GizmoballModel();
        MainFrame view = new MainFrame(model);
        model.addObserver(view.getGameBoard());

//        This code is to populate the model with different gizmos to test saving and loading

//        model.addGizmo(new Square("s1", 2, 3));
//        model.addGizmo(new Absorber("a1", 10, 3, 12, 8));
//        model.addGizmo(new GizmoCircle("c1", 4, 5, 1));
//        model.addGizmo(new Triangle("t1", 4, 12));
//        model.addGizmo(new RightFlipper("RF1", 6, 6));
//        model.addGizmo(new LeftFlipper("LF1", 8, 8));
//
//        model.saveGame();
//        model.loadGame();
    }
}
