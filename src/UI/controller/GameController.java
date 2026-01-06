package ui.controller;

import modele.Partie;
import modele.Manche;
import modele.Joueur;
import ui.LoveLetterUI;
import ui.dialogs.TargetDialog;

import javax.swing.*;

public class GameController {

    private Partie partie;
    private Manche manche;
    private LoveLetterUI ui;

    public GameController(LoveLetterUI ui) {
        this.ui = ui;
    }

    public void nouvellePartie(String[] noms) {
        partie = new Partie();
        for (String n : noms) {
            partie.ajouterJoueur(new Joueur(n));
        }
        manche = partie.demarrerManche();
        ui.refresh();
    }

    public Joueur getJoueurActif() {
        return manche.getJoueurActif();
    }

    public void jouerCarte(Joueur j, int indexMain) {

        // Selon ta logique : récupérer la carte
        var carte = j.getMain().get(indexMain);

        if (carte.getValeur() == 1) { // GARDE
            demanderCibleGarde(j, carte);

        } else {
            manche.jouerCarte(j, carte, null);
            ui.refresh();
        }
    }

    private void demanderCibleGarde(Joueur j, var carte) {
        TargetDialog d = new TargetDialog(manche.getAutresJoueurs(j));
        Joueur cible = d.show();

        if (cible != null) {
            int valeur =
                JOptionPane.showInputDialog(
                    ui,
                    "Devine une valeur (2-8)");

            manche.jouerCarte(j, carte, cible, valeur);
            ui.refresh();
        }
    }

    public Manche getManche() {
        return manche;
    }

    // ----- À AJOUTER DANS GameController -----

public void passerTourLocal() {

    if (manche == null)
        return;

    Joueur prochain =
        manche.tourSuivant();

    showMaskForPlayer(prochain);
}

private void showMaskForPlayer(
    Joueur prochain) {

    JPanel mask =
        new JPanel(
            new BorderLayout());

    JButton ok =
        new JButton(
          "Passer au joueur : "
          + prochain.getNom());

    mask.add(
        ok,
        BorderLayout.CENTER);

    ok.addActionListener(e -> {
        // remettre le jeu
        ui.setContentPane(
            ui.getPlateau());

        ui.refresh();
    });

    ui.setContentPane(mask);
}

}
