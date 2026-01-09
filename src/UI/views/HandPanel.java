package ui.views;

import controller.GameController;
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

        for (int i = 0; i < actif.getMain().getNombreCartes(); i++) {

            var carte = actif.getMain().getCarte(i);

            CardButton btn = new CardButton(carte, i, controller);

            this.add(btn);
        }

        this.revalidate();
        this.repaint();
    }
}
