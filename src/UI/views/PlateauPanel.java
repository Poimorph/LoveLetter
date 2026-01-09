package ui.views;

import controller.GameController;

import javax.swing.*;

import model.Joueur;
import model.Manche;

import java.awt.*;

public class PlateauPanel extends JPanel {

    private GameController controller;

    private JPanel playersZone;
    private JPanel handZone;
    private JPanel publicZone;

    private JButton nouvellePartieBtn;

    public PlateauPanel(GameController c) {

        this.controller = c;
        this.setLayout(new BorderLayout());

        // --- Zone des joueurs ---
        playersZone = new JPanel();
        playersZone.setLayout(
                new FlowLayout(FlowLayout.CENTER, 20, 10));

        // --- Zone main privée ---
        handZone = new HandPanel(controller);

        // --- Zone publique ---
        publicZone = new JPanel();
        publicZone.setLayout(new BorderLayout());

        nouvellePartieBtn = new JButton("Nouvelle Partie");

        nouvellePartieBtn.addActionListener(e -> demanderJoueurs());

        this.add(playersZone, BorderLayout.NORTH);
        this.add(handZone, BorderLayout.SOUTH);
        this.add(publicZone, BorderLayout.CENTER);
        this.add(nouvellePartieBtn, BorderLayout.WEST);
    }

    private void demanderJoueurs() {

        String saisie = JOptionPane.showInputDialog(
                this,
                "Noms des joueurs séparés par des virgules");

        if (saisie == null || saisie.isBlank())
            return;

        String[] noms = saisie.split(",");

        for (int i = 0; i < noms.length; i++) {
            noms[i] = noms[i].trim();
        }

        controller.nouvellePartie(noms);
    }

    public void updateFromModel() {

        playersZone.removeAll();

        Manche m = controller.getManche();

        if (m == null)
            return;

        for (Joueur j : m.getTousLesJoueurs()) {

            PlayerPanel pj = new PlayerPanel(j, controller);

            playersZone.add(pj);
        }

        ((HandPanel) handZone)
                .updateFromModel();

        updatePublic(m);

        this.revalidate();
        this.repaint();
    }

    private void updatePublic(Manche m) {

        publicZone.removeAll();

        JTextArea historique = new JTextArea(
                m.getHistoriqueTexte());

        historique.setEditable(false);

        publicZone.add(
                new JScrollPane(historique));
    }
}
