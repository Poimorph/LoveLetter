package ui.dialogs;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InitPartieDialog extends JDialog {
    int nbJoueurs;

    public InitPartieDialog(Window parent) {
        super(parent,
              "Initialisation de la partie",
              ModalityType.APPLICATION_MODAL);

        this.setSize(300, 300);
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        JLabel nbJoueursLabel = new JLabel("Nombre de joueurs (2-6) :");
        JTextField nbJoueursField = new JTextField();

        inputPanel.add(nbJoueursLabel);
        inputPanel.add(nbJoueursField);

        JButton startButton = new JButton("Valider");
        startButton.addActionListener(e -> {
            try {
                nbJoueurs = Integer.parseInt(nbJoueursField.getText());
                if (nbJoueurs < 2 || nbJoueurs > 6) {
                    throw new NumberFormatException();
                }
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Veuillez entrer un nombre valide de joueurs (2-6).",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        this.add(inputPanel, BorderLayout.CENTER);
        this.add(startButton, BorderLayout.SOUTH);
    }
    public int getNbJoueurs() {
        return nbJoueurs;
    }
    public void afficher() {
        this.setVisible(true);
    }




}
