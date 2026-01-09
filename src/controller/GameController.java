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

    public GameController() {
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
                showMaskForPlayer(actif);
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
        debuterTour();
    }

    private void debuterTour() {
        if (partie.estTerminee() || manche.isTerminee()) {
            gererFinManche();
            return;
        }
        Joueur actif = manche.getJoueurActif();
        if (manche.getDeck().getNombreCartesRestantes() > 0) {
            actif.getMain().ajouterCarte(manche.getDeck().piocher());
        } else {
            Carte carteJeu = manche.getCarteCachee();
            if (carteJeu != null)
                actif.getMain().ajouterCarte(carteJeu);
        }
        ui.refresh();
        showMaskForPlayer(actif);
    }

    public Joueur getJoueurActif() {
        return manche.getJoueurActif();
    }

    /**
     * Appelée par CardButton quand on clique sur une carte.
     * C'est la suite de la "boucle".
     */
    public void jouerCarte(Joueur joueur, int indexMain) {
        if (!joueur.equals(manche.getJoueurActif())) {
            JOptionPane.showMessageDialog(ui, "Ce n'est pas votre tour !");
            return;
        }
        Carte carte = joueur.getMain().getCarte(indexMain);
        Joueur cible = demanderCibleGarde(joueur, carte);
        Carte carteDevinee = demanderDevinette(joueur, carte);
        ActionJoueur action = new ActionJoueur(
                joueur,
                carte,
                cible,
                (carteDevinee != null) ? carteDevinee.getType() : null);
        if (carte.getValeur() == 6) {
            int choixCarte = demanderChoixCarteAncienEleve(joueur);
            action.setCarteGardeeIndex(choixCarte);
        }
        manche.jouerTour(action);
        verifierEtatJeu();
    }

    /**
     * Vérifie si la manche ou la partie est finie, sinon passe au joueur suivant.
     */
    private void verifierEtatJeu() {
        ui.refresh();

        if (partie.estTerminee()) {
            JOptionPane.showMessageDialog(ui,
                    "La partie est terminée ! Vainqueur : " + partie.getVainqueurFinal().getNom());
            return;
        }

        if (manche.isTerminee()) {
            gererFinManche();
        } else {
            manche.passerAuJoueurSuivant();
            debuterTour();
        }
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

    private void gererFinManche() {
        Joueur vainqueur = manche.determinerVainqueurs().get(0);
        String msg = "Fin de la manche ! Vainqueur : " + (vainqueur != null ? vainqueur.getNom() : "Personne");
        JOptionPane.showMessageDialog(ui, msg);

        partie.lancerNouvelleManche();

        if (!partie.estTerminee()) {
            manche = partie.getMancheActuelle();
            debuterTour();
        } else {
            JOptionPane.showMessageDialog(ui, "Partie Terminée !");
        }
    }

    private int demanderChoixCarteAncienEleve(Joueur j) {
        Carte carteEnMain = j.getMain().getCarte(0);
        ArrayList<Carte> cartesDuDeck = manche.getDeck().regarderPlusieurs(2);
        ArrayList<String> options = new ArrayList<>();

        if (carteEnMain != null) {
            options.add(carteEnMain.getNom() + " (Valeur: " + carteEnMain.getValeur() + ") - En main");
        }
        for (int i = 0; i < cartesDuDeck.size(); i++) {
            Carte c = cartesDuDeck.get(i);
            options.add(c.getNom() + " (Valeur: " + c.getValeur() + ") - Piochée");
        }

        ChoixCarteDialog dialog = new ChoixCarteDialog(
                ui,
                "Ancien Élève - Choisissez la carte à garder",
                options);

        return dialog.afficher();
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
