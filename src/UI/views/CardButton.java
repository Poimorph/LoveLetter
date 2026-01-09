package ui.views;

import javax.swing.JButton;

import controller.GameController;
import model.Carte;

public class CardButton extends JButton {

    public CardButton(
            Carte carte,
            int index,
            GameController c) {

        this.setText(carte.getNom());

        this.addActionListener(e -> c.jouerCarte(
                c.getJoueurActif(),
                index));
    }
}
