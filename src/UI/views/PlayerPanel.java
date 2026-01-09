package ui.views;

import controller.GameController;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import model.Carte;
import model.Joueur;

import java.awt.*;

public class PlayerPanel extends JPanel {

    private static final Color ACTIVE_BACKGROUND = new Color(220, 255, 220);
    private static final Color ELIMINATED_BACKGROUND = new Color(255, 200, 200);
    private static final Color PROTECTED_BACKGROUND = new Color(200, 220, 255);
    private static final Color NORMAL_BACKGROUND = new Color(245, 245, 245);
    private static final Color ACTIVE_BORDER = new Color(34, 139, 34);

    private Joueur joueur;
    private GameController controller;

    private JLabel statutLabel;
    private JLabel pionsLabel;
    private JLabel defausseLabel;

    public PlayerPanel(Joueur j, GameController c) {
        this.joueur = j;
        this.controller = c;

        setPreferredSize(new Dimension(160, 120));
        setLayout(new BorderLayout(5, 5));

        // Titre avec nom du joueur
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                j.getNom(),
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12));
        setBorder(border);

        // Panneau central avec les informations
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Statut
        statutLabel = new JLabel();
        statutLabel.setFont(new Font("Arial", Font.BOLD, 11));
        statutLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Pions de faveur
        pionsLabel = new JLabel();
        pionsLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        pionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Dernière carte jouée
        defausseLabel = new JLabel();
        defausseLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        defausseLabel.setForeground(Color.DARK_GRAY);
        defausseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(statutLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(pionsLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(defausseLabel);

        add(infoPanel, BorderLayout.CENTER);

        updateFromModel();
    }

    public void updateFromModel() {
        // Mise à jour du statut et de la couleur de fond
        if (joueur.isElimine()) {
            statutLabel.setText("Eliminé");
            statutLabel.setForeground(new Color(178, 34, 34));
            setBackground(ELIMINATED_BACKGROUND);
        } else if (joueur.isProtege()) {
            statutLabel.setText("Protégé");
            statutLabel.setForeground(new Color(30, 144, 255));
            setBackground(PROTECTED_BACKGROUND);
        } else {
            statutLabel.setText("En jeu");
            statutLabel.setForeground(new Color(34, 139, 34));
            setBackground(NORMAL_BACKGROUND);
        }

        // Vérifier si c'est le joueur actif
        Joueur actif = controller.getJoueurActif();
        if (actif != null && actif.equals(joueur) && !joueur.isElimine()) {
            setBackground(ACTIVE_BACKGROUND);
            setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(ACTIVE_BORDER, 3),
                    joueur.getNom() + " (Tour)",
                    TitledBorder.CENTER,
                    TitledBorder.TOP,
                    new Font("Arial", Font.BOLD, 12),
                    ACTIVE_BORDER));
        } else {
            setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                    joueur.getNom(),
                    TitledBorder.CENTER,
                    TitledBorder.TOP,
                    new Font("Arial", Font.BOLD, 12)));
        }

        // Pions de faveur
        int pions = joueur.getPionsFaveur();
        pionsLabel.setText("Pions: " + pions);

        // Dernière carte défaussée
        Carte derniere = joueur.getDerniereDefausse();
        if (derniere != null) {
            defausseLabel.setText("Dernière: " + derniere.getNom());
        } else {
            defausseLabel.setText("Aucune carte jouée");
        }
    }
}