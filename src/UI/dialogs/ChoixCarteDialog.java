package ui.dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChoixCarteDialog extends JDialog {

    private static final Color BUTTON_COLOR = new Color(139, 69, 19);
    private static final Color BUTTON_HOVER = new Color(160, 82, 45);
    private static final Color BACKGROUND = new Color(245, 245, 220);

    private int indexChoisi = -1;

    public ChoixCarteDialog(Window parent, String titre, ArrayList<String> options) {
        super(parent, titre, ModalityType.APPLICATION_MODAL);

        setSize(350, Math.min(100 + options.size() * 50, 400));
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND);

        // Titre
        JLabel titleLabel = new JLabel(titre, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Panneau des boutons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(BACKGROUND);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            JButton btn = createStyledButton(option, i);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonsPanel.add(btn);
            buttonsPanel.add(Box.createVerticalStrut(8));
        }

        JScrollPane scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, int index) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setBackground(BUTTON_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(300, 40));
        btn.setPreferredSize(new Dimension(280, 35));

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
            indexChoisi = index;
            dispose();
        });

        return btn;
    }

    public int afficher() {
        setVisible(true);
        return indexChoisi;
    }
}