package ui.dialogs;

import model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TargetDialog extends JDialog {

    private static final Color BUTTON_COLOR = new Color(40, 70, 45);
    private static final Color BUTTON_HOVER = new Color(180, 100, 40);
    private static final Color BACKGROUND = new Color(245, 245, 220);

    private Joueur selected;

    public TargetDialog(Window parent, ArrayList<Joueur> cibles) {
        super(parent, "Choisir une cible", ModalityType.APPLICATION_MODAL);

        setSize(320, Math.min(150 + cibles.size() * 45, 350));
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND);

        // Titre
        JLabel titleLabel = new JLabel("Choisissez un joueur à cibler", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Panneau des joueurs
        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        playersPanel.setBackground(BACKGROUND);
        playersPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        for (Joueur j : cibles) {
            JButton btn = createPlayerButton(j);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            playersPanel.add(btn);
            playersPanel.add(Box.createVerticalStrut(8));
        }

        JScrollPane scrollPane = new JScrollPane(playersPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createPlayerButton(Joueur joueur) {
        JButton btn = new JButton(joueur.getNom());
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(BUTTON_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(250, 40));
        btn.setPreferredSize(new Dimension(230, 38));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(BUTTON_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(BUTTON_COLOR);
            }
        });

        btn.addActionListener(e -> {
            selected = joueur;
            dispose();
        });

        return btn;
    }

    public Joueur afficher() {
        setVisible(true);
        return selected;
    }

    public Joueur showDialog() {
        return afficher();
    }
}