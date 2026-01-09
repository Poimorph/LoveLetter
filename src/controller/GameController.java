package controller;

import ui.LoveLetterUI;
import ui.dialogs.TargetDialog;

import javax.swing.*;

import model.Joueur;
import model.MainJoueur;
import model.Manche;
import model.Partie;

public class GameController {

    private Partie partie;
    private Manche manche;
    private LoveLetterUI ui;

    public GameController(LoveLetterUI ui) {
        this.ui = ui;
    }

    public void nouvellePartie(String[] noms) {
        Partie.resetInstance();
        partie = Partie.getInstance(noms.length);
        java.util.ArrayList<String> nomsListe = new java.util.ArrayList<>();
        for (String n : noms) {
            nomsListe.add(n);
        }
        partie.initialiser(nomsListe);
        partie.demarrerPartie();
        manche = partie.getMancheActuelle();
        ui.refresh();
    }

    public Joueur getJoueurActif() {
        return manche.getJoueurActif();
    }

    public void jouerCarte(Joueur j, int indexMain) {

        // Selon ta logique : récupérer la carte
        var carte = j.getMain().getCarte(indexMain);

        if (carte.getValeur() == 1) { // GARDE
            demanderCibleGarde(j, carte);

        } else {
            manche.jouerCarte(j, carte, null);
            ui.refresh();
        }
    }

    private void demanderCibleGarde(Joueur j, var carte) {
        TargetDialog d = new TargetDialog(manche.getJoueursCiblables(j));
        Joueur cible = d.show();

        if (cible != null) {
            int valeur = JOptionPane.showInputDialog(
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

        manche.passerAuJoueurSuivant();
        Joueur prochain = manche.getJoueurActif();

        showMaskForPlayer(prochain);
    }

    private void showMaskForPlayer(
            Joueur prochain) {

        JPanel mask = new JPanel(
                new BorderLayout());

        JButton ok = new JButton(
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
