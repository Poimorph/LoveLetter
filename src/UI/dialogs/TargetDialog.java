package ui.dialogs;

import model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TargetDialog extends JDialog {

    private Joueur selected;

    public TargetDialog(
            Window parent,
            ArrayList<Joueur> cibles) {

        super(parent,
              "Choisir une cible",
              ModalityType.APPLICATION_MODAL);

        this.setSize(300, 200);
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());

        JPanel listPanel =
            new JPanel(
                new GridLayout(
                    cibles.size(), 1));

        for (Joueur j : cibles) {

            JButton btn =
                new JButton(j.getNom());

            btn.addActionListener(e -> {
                selected = j;
                dispose();
            });

            listPanel.add(btn);
        }

        JScrollPane scroll =
            new JScrollPane(listPanel);

        this.add(
            new JLabel(
                "Choisissez un joueur :",
                SwingConstants.CENTER),
            BorderLayout.NORTH);

        this.add(scroll, BorderLayout.CENTER);
    }

    public Joueur afficher() {
        this.setVisible(true);
        return selected;
    }

    
     // Affiche le dialogue et retourne
     // le joueur sélectionné ou null
     
    public Joueur showDialog() {
        this.setVisible(true);
        return selected;
    }
}
