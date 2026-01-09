package ui.views;

import javax.swing.*;
import java.awt.*;

import controller.GameController;
import model.Carte;

public class CardButton extends JPanel {

    private static final Color CARD_BACKGROUND = new Color(255, 248, 220);
    private static final Color CARD_BORDER = new Color(139, 69, 19);
    private static final Color VALUE_COLOR = new Color(178, 34, 34);
    private static final Color HOVER_BACKGROUND = new Color(255, 235, 180);

    private Carte carte;
    private int index;
    private GameController controller;
    private boolean hovered = false;

    public CardButton(Carte carte, int index, GameController c) {
        this.carte = carte;
        this.index = index;
        this.controller = c;

        setPreferredSize(new Dimension(140, 180));
        setBackground(CARD_BACKGROUND);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER, 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setLayout(new BorderLayout(5, 5));

        // Panneau du haut : valeur et nom
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel valeurLabel = new JLabel(String.valueOf(carte.getValeur()));
        valeurLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valeurLabel.setForeground(VALUE_COLOR);
        topPanel.add(valeurLabel, BorderLayout.WEST);

        // Panneau central : nom de la carte
        JLabel nomLabel = new JLabel("<html><center>" + carte.getNom() + "</center></html>");
        nomLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nomLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panneau du bas : description
        JTextArea descArea = new JTextArea(carte.getDescription());
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setFocusable(false);
        descArea.setFont(new Font("Arial", Font.PLAIN, 9));
        descArea.setForeground(Color.DARK_GRAY);

        add(topPanel, BorderLayout.NORTH);
        add(nomLabel, BorderLayout.CENTER);
        add(descArea, BorderLayout.SOUTH);

        // Gestion du clic
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                controller.jouerCarte(controller.getJoueurActif(), index);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                hovered = true;
                setBackground(HOVER_BACKGROUND);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hovered = false;
                setBackground(CARD_BACKGROUND);
            }
        });
    }
}