import Model.GizmoballModel;
import View.MainFrame;

public class GizmoBall {
    public static void main(String[] args) {
        GizmoballModel model = new GizmoballModel();
        MainFrame view = new MainFrame(model);
        model.addObserver(view.getGameBoard());
        model.saveGame();
        model.loadGame();
    }
}
