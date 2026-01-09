package ui.views;

import controller.GameController;
import model.Carte;
import model.Joueur;

import javax.swing.*;
import java.awt.*;

public class HandPanel extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(40, 70, 45);

    private GameController controller;
    private JLabel joueurLabel;
    private JPanel cardsPanel;

    public HandPanel(GameController c) {
        this.controller = c;
        setLayout(new BorderLayout(10, 5));
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(0, 220));

        // Label du joueur actif
        joueurLabel = new JLabel("En attente d'une partie...");
        joueurLabel.setFont(new Font("Arial", Font.BOLD, 14));
        joueurLabel.setForeground(Color.WHITE);
        joueurLabel.setHorizontalAlignment(SwingConstants.CENTER);
        joueurLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Panneau des cartes
        cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        cardsPanel.setOpaque(false);

        add(joueurLabel, BorderLayout.NORTH);
        add(cardsPanel, BorderLayout.CENTER);
    }

    public void updateFromModel() {
        cardsPanel.removeAll();

        Joueur actif = controller.getJoueurActif();

        if (actif == null) {
            joueurLabel.setText("En attente d'une partie...");
            revalidate();
            repaint();
            return;
        }

        joueurLabel.setText("Tour de: " + actif.getNom() + " - Cliquez sur une carte pour la jouer");

        for (int i = 0; i < actif.getMain().getNombreCartes(); i++) {
            Carte carte = actif.getMain().getCarte(i);
            CardButton btn = new CardButton(carte, i, controller);
            cardsPanel.add(btn);
        }

        revalidate();
        repaint();
    }
}