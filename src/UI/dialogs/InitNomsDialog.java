package ui.dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InitNomsDialog extends JDialog {
    ArrayList<String> nomsJoueurs;
    int nbJoueurs;

    public InitNomsDialog(Window parent, int nbJoueurs) {
        super(parent,
              "Initialisation des noms des joueurs",
              ModalityType.APPLICATION_MODAL);

        this.nbJoueurs = nbJoueurs;
        this.setSize(300, 400);
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(nbJoueurs, 2, 5, 5));

        JTextField[] nomFields = new JTextField[nbJoueurs];
        for (int i = 0; i < nbJoueurs; i++) {
            JLabel nomLabel = new JLabel("Nom du joueur " + (i + 1) + " :");
            JTextField nomField = new JTextField();
            nomFields[i] = nomField;
            inputPanel.add(nomLabel);
            inputPanel.add(nomLabel.toString(),nomField);
        }

        JButton startButton = new JButton("Valider");
        startButton.addActionListener(e -> {
            nomsJoueurs = new ArrayList<>();
            for (int i = 0; i < nbJoueurs; i++) {
                String nom = nomFields[i].getText().trim();
                if (nom.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Veuillez entrer un nom pour chaque joueur.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                nomsJoueurs.add(nom);
            }
            dispose();
        });

        this.add(inputPanel, BorderLayout.CENTER);
        this.add(startButton, BorderLayout.SOUTH);
    }
    public ArrayList<String> getNomsJoueurs() {
        return nomsJoueurs;
    }
    public void afficher() {
        this.setVisible(true);
    }

    
}
