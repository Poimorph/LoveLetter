package controller;

import ui.LoveLetterUI;
import ui.dialogs.ChoixCarteDialog;
import ui.dialogs.InitPartieDialog;
import ui.dialogs.TargetDialog;

import java.util.ArrayList;
import javax.swing.*;

import model.ActionJoueur;
import model.Carte;
import model.CarteFactory;
import model.Joueur;
import model.MainJoueur;
import model.Manche;
import model.Partie;

import java.awt.*;

public class GameController {

    private Partie partie;
    private Manche manche;
    private LoveLetterUI ui;

    public GameController(LoveLetterUI ui) {
        this.ui = ui;
        InitPartieDialog dialog =
            new InitPartieDialog(ui);
        dialog.afficher();
        ArrayList<String> noms = dialog.getNomsJoueurs();
        initialiserPartie(noms);
        boucleJeu();
        ui.refresh();
    }

    public void boucleJeu(){
        while (!partie.estTerminee()) {
            manche = partie.getMancheActuelle();
            while (!manche.isTerminee()) {
                Joueur actif = manche.getJoueurActif();
                ChoixCarteDialog choixCarteDialog =
                    new ChoixCarteDialog(
                        ui,
                        "Choisir une carte à jouer, "
                                + actif.getNom(),
                        actif.getMain().getNomsCartesJouables());
                int indexChoisi = choixCarteDialog.afficher();
                jouerCarte(actif, indexChoisi);
                manche.passerAuJoueurSuivant();
                ui.refresh();
            }
            partie.lancerNouvelleManche();
        }
    }

    public void initialiserPartie(ArrayList<String> noms) {
        partie = Partie.getInstance(noms.size());
        partie.initialiser(noms);
        partie.demarrerPartie();
        manche = partie.getMancheActuelle();
        ui.refresh();
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

        var carte = j.getMain().getCarte(indexMain);

        if (carte.getValeur() == 1) { // GARDE
            demanderCibleGarde(j, carte);

        } else {
            ActionJoueur action = new ActionJoueur(
                                            j,
                                            carte,
                                            null,
                                            null);
            manche.jouerTour(action);
            ui.refresh();
        }
    }

    private void demanderCibleGarde(Joueur j, Carte carte) {
        TargetDialog d = new TargetDialog(ui, manche.getTousLesJoueurs());
        Joueur cible = d.afficher();

        if (cible != null) {
            String userInputValue = JOptionPane.showInputDialog(
                    ui,
                    "Devine une valeur (0-9)");

            Carte carteDevinee = CarteFactory.creerCarte(Integer.parseInt(userInputValue));
            ActionJoueur action = new ActionJoueur(
                                            j,
                                            carte,
                                            cible,
                                            carteDevinee.getType());
            manche.jouerTour(action);
            ui.refresh();
        }
    }

    public Manche getManche() {
        return manche;
    }



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
