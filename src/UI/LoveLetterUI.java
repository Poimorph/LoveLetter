package ui;

import ui.views.PlateauPanel;
import ui.controller.GameController;

import javax.swing.*;

public class LoveLetterUI extends JFrame {

    private PlateauPanel plateau;
    private GameController controller;

    public LoveLetterUI() {
        this.setTitle("Love Letter");
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        controller = new GameController(this);
        plateau = new PlateauPanel(controller);

        this.setContentPane(plateau);
    }

    public void refresh() {
        plateau.updateFromModel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoveLetterUI().setVisible(true);
        });
    }
}
