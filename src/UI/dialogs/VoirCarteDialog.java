package ui.dialogs;

import model.Carte;
import model.Joueur;

import javax.swing.*;
import java.awt.*;

/**
 * Dialogue pour afficher la carte d'un adversaire (effet Tuteur Pédagogique)
 */
public class VoirCarteDialog extends JDialog {

    private static final Color CARD_BACKGROUND = new Color(255, 248, 220);
    private static final Color CARD_BORDER = new Color(139, 69, 19);
    private static final Color VALUE_COLOR = new Color(178, 34, 34);
    private static final Color HEADER_BG = new Color(34, 85, 51);

    public VoirCarteDialog(Window parent, Joueur cible, Carte carte) {
        super(parent, "Carte révélée", ModalityType.APPLICATION_MODAL);
        setSize(350, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 220));

        // En-tête
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(HEADER_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel headerLabel = new JLabel("Carte de " + cible.getNom());
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Carte
        JPanel cardPanel = createCardPanel(carte);
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setBackground(new Color(245, 245, 220));
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        centerWrapper.add(cardPanel);

        add(centerWrapper, BorderLayout.CENTER);

        // Bouton OK
        JButton okButton = new JButton("Compris !");
        okButton.setBackground(HEADER_BG);
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(245, 245, 220));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        bottomPanel.add(okButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createCardPanel(Carte carte) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(160, 200));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER, 3),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        card.setLayout(new BorderLayout(5, 10));

        // Valeur
        JLabel valeurLabel = new JLabel(String.valueOf(carte.getValeur()));
        valeurLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valeurLabel.setForeground(VALUE_COLOR);
        valeurLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Nom
        JLabel nomLabel = new JLabel("<html><center>" + carte.getNom() + "</center></html>");
        nomLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nomLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Description
        JTextArea descArea = new JTextArea(carte.getDescription());
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setFocusable(false);
        descArea.setFont(new Font("Arial", Font.PLAIN, 10));
        descArea.setForeground(Color.DARK_GRAY);

        card.add(valeurLabel, BorderLayout.NORTH);
        card.add(nomLabel, BorderLayout.CENTER);
        card.add(descArea, BorderLayout.SOUTH);

        return card;
    }

    public void afficher() {
        setVisible(true);
    }
}