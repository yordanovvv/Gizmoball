import Model.GizmoballModel;
import View.GameBoard;
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
        GizmoballModel model = new GizmoballModel();
        MainFrame view = new MainFrame(model);
        model.addObserver(view.getGameBoard());
        model.saveGame();
        model.loadGame();
    }
}
