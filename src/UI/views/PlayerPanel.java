package ui.views;

import controller.GameController;

import javax.swing.*;

import model.Carte;
import model.Joueur;

import java.awt.*;

public class PlayerPanel extends JPanel {

        private Joueur joueur;
        private GameController controller;

        private JLabel nomLabel;
        private JLabel statutLabel;
        private JLabel defausseLabel;

        public PlayerPanel(
                        Joueur j,
                        GameController c) {

                this.joueur = j;
                this.controller = c;

                this.setPreferredSize(
                                new Dimension(180, 90));

                this.setBorder(
                                BorderFactory.createLineBorder(
                                                Color.BLACK));

                this.setLayout(
                                new GridLayout(3, 1));

                nomLabel = new JLabel(j.getNom());

                statutLabel = new JLabel();

                defausseLabel = new JLabel();

                this.add(nomLabel);
                this.add(statutLabel);
                this.add(defausseLabel);

                updateFromModel();
        }

        public void updateFromModel() {

                if (joueur.isElimine()) {
                        statutLabel.setText(
                                        "Éliminé");

                } else if (joueur.isProtege()) {

                        statutLabel.setText(
                                        "Protégé (Servante)");

                } else {

                        statutLabel.setText(
                                        "En jeu");
                }

                Carte derniere = joueur.getDerniereDefausse();

                if (derniere != null) {

                        defausseLabel.setText(
                                        "Dernière : "
                                                        + derniere.getNom());

                } else {

                        defausseLabel.setText(
                                        "Aucune carte jouée");
                }
        }
}
