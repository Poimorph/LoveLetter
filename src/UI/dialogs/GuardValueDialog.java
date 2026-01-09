package ui.dialogs;

import javax.swing.*;
import java.awt.*;

public class GuardValueDialog extends JDialog {

    private Integer value = null;

    public GuardValueDialog(Window parent) {

        super(parent,
              "Choisir une valeur",
              ModalityType.APPLICATION_MODAL);

        this.setSize(300, 150);
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());

        JPanel buttons =
            new JPanel(
                new GridLayout(2, 4, 5, 5));

        for (int i = 2; i <= 8; i++) {

            int val = i;

            JButton b =
                new JButton(
                    String.valueOf(i));

            b.addActionListener(e -> {
                value = val;
                dispose();
            });

            buttons.add(b);
        }

        this.add(
            new JLabel(
                "Devinez une carte (2 à 8)",
                SwingConstants.CENTER),
            BorderLayout.NORTH);

        this.add(buttons, BorderLayout.CENTER);
    }

    /**
     * Affiche le dialogue et retourne
     * la valeur choisie ou null
     */
    public Integer showDialog() {
        this.setVisible(true);
        return value;
    }
}
