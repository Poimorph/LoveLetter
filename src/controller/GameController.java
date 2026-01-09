package controller;

import ui.LoveLetterUI;
import ui.dialogs.*;

import java.util.ArrayList;
import javax.swing.*;

import model.ActionJoueur;
import model.Carte;
import model.CarteFactory;
import model.Joueur;
import model.Manche;
import model.Partie;

import java.awt.*;

public class GameController {

    private Partie partie;
    private Manche manche;
    private LoveLetterUI ui;

    public GameController(LoveLetterUI ui) {
        this.ui = ui;
    }

    public void Play() {
        InitPartieDialog dialog = new InitPartieDialog(ui);
        dialog.afficher();
        int nbJoueurs = dialog.getNbJoueurs();
        InitNomsDialog nomsDialog = new InitNomsDialog(ui, nbJoueurs);
        nomsDialog.afficher();
        ArrayList<String> noms = nomsDialog.getNomsJoueurs();
        initialiserPartie(noms);
        boucleJeu();
        ui.refresh();
    }

    public void boucleJeu() {
        while (!partie.estTerminee()) {
            manche = partie.getMancheActuelle();
            while (!manche.isTerminee()) {
                Joueur actif = manche.getJoueurActif();
                actif.getMain().ajouterCarte(manche.getDeck().piocher());
                ChoixCarteDialog choixCarteDialog = new ChoixCarteDialog(
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

        Carte carte = j.getMain().getCarte(indexMain);

        Joueur cible = demanderCibleGarde(j, carte);
        Carte carteDevinee = demanderDevinette(j, carte);

        ActionJoueur action = new ActionJoueur(
                j,
                carte,
                cible,
                (carteDevinee != null) ? carteDevinee.getType() : null);
        manche.jouerTour(action);
        ui.refresh();

    }

    private Joueur demanderCibleGarde(Joueur j, Carte carte) {
        if (!carte.necessiteCible()) {
            return null;
        }
        TargetDialog d = new TargetDialog(ui, manche.getJoueursCiblables(j));
        Joueur cible = d.afficher();
        return cible;
    }

    private Carte demanderDevinette(Joueur j, Carte carte) {
        if (carte.getValeur() == 1) { // GARDE
            GuardValueDialog dialog = new GuardValueDialog(ui);
            Integer userInputValue = dialog.showDialog();
            if (userInputValue == null)
                return null;

            Carte carteDevinee = CarteFactory.creerCarte(userInputValue);

            return carteDevinee;
        }
        return null;
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
