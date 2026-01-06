package ui.views;

import ui.controller.GameController;
import javax.swing.*;
import java.awt.*;

public class HandPanel extends JPanel {

    private GameController controller;

    public HandPanel(GameController c) {
        this.controller = c;
        this.setLayout(new FlowLayout());
    }

    public void updateFromModel() {

        this.removeAll();

        var actif = controller.getJoueurActif();

        for (int i = 0; i < actif.getMain().size(); i++) {

            var carte = actif.getMain().get(i);

            CardButton btn =
                new CardButton(carte, i, controller);

            this.add(btn);
        }

        this.revalidate();
        this.repaint();
    }
}
