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

        this.setSize(600, 300);
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        JLabel nbJoueursLabel = new JLabel("Nombre de joueurs (2-6) :");
        JTextField nbJoueursField = new JTextField();

        inputPanel.add(nbJoueursLabel);
        inputPanel.add(nbJoueursField);

        // JTextField[] nomFields = new JTextField[6];
        // for (int i = 0; i < 6; i++) {
        //     JLabel nomLabel = new JLabel("Nom du joueur " + (i + 1) + " :");
        //     JTextField nomField = new JTextField();
        //     nomFields[i] = nomField;
        //     inputPanel.add(nomLabel);
        //     inputPanel.add(nomField);
        // }

        JButton startButton = new JButton("Valider");
        startButton.addActionListener(e -> {
            try {
                nbJoueurs = Integer.parseInt(nbJoueursField.getText());
                // if (nbJoueurs < 2 || nbJoueurs > 6) {
                //     throw new NumberFormatException();
                // }
                // nomsJoueurs = new ArrayList<>();
                // for (int i = 0; i < nbJoueurs; i++) {
                //     String nom = nomFields[i].getText().trim();
                //     if (nom.isEmpty()) {
                //         JOptionPane.showMessageDialog(
                //             this,
                //             "Veuillez entrer un nom pour chaque joueur.",
                //             "Erreur",
                //             JOptionPane.ERROR_MESSAGE);
                //         return;
                //     }
                //    nomsJoueurs.add(nom);
                //}
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
