
import controller.GameController;
import ui.LoveLetterUI;
import model.Partie;

public class Main {
    public static void main(String[] args) {
        LoveLetterUI ui = new LoveLetterUI();

        GameController controller = new GameController(ui);

        ui.setController(controller);

        ui.setVisible(true);

    }
}