package modele.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import modele.*;
import modele.cartes.*;

public class MancheTest {

    private Manche manche;
    private ArrayList<Joueur> joueurs;

    @Before
    public void setUp() {
        joueurs = new ArrayList<>();
        joueurs.add(new Joueur("Alice", new MainJoueur()));
        joueurs.add(new Joueur("Bob", new MainJoueur()));
        joueurs.add(new Joueur("Charlie", new MainJoueur()));

        manche = new Manche(joueurs, joueurs.get(0)); // Alice commence
    }

    @Test
    public void testConstructeur() {
        assertNotNull(manche);
        assertEquals(3, manche.getTousLesJoueurs().size());
        assertFalse(manche.isTerminee());
    }

    @Test
    public void testConstructeurAvecPremierJoueur() {
        Joueur premierJoueur = joueurs.get(1); // Bob
        Manche manche2 = new Manche(joueurs, premierJoueur);

        assertEquals(premierJoueur, manche2.getJoueurActif());
    }

    @Test
    public void testInitialiser() {
        manche.initialiser();

        // Chaque joueur doit avoir 1 carte
        for (Joueur joueur : joueurs) {
            assertEquals(1, joueur.getMain().getNombreCartes());
        }

        // Le deck doit être préparé
        assertNotNull(manche.getDeck());
        assertFalse(manche.getDeck().estVide());
    }

    @Test
    public void testPasserAuJoueurSuivant() {
        manche.initialiser();
        Joueur premier = manche.getJoueurActif();

        manche.passerAuJoueurSuivant();
        Joueur deuxieme = manche.getJoueurActif();

        assertNotEquals(premier, deuxieme);
    }

    @Test
    public void testEliminerJoueur() {
        manche.initialiser();
        Joueur joueur = joueurs.get(0);

        manche.eliminerJoueur(joueur);

        assertTrue(joueur.isElimine());
    }

    @Test
    public void testMarquerB2Joue() {
        manche.initialiser();
        Joueur joueur = joueurs.get(0);

        manche.marquerB2Joue(joueur);

        assertTrue(manche.getJoueursAyantJoueB2().contains(joueur));
    }

    @Test
    public void testGetJoueursCiblables() {
        manche.initialiser();
        Joueur joueurActif = joueurs.get(0);

        ArrayList<Joueur> ciblables = manche.getJoueursCiblables(joueurActif);

        // Les 2 autres joueurs doivent être ciblables
        assertEquals(2, ciblables.size());
        assertFalse(ciblables.contains(joueurActif));
    }

    @Test
    public void testGetJoueursCiblablesAvecProtection() {
        manche.initialiser();
        Joueur joueurActif = joueurs.get(0);
        Joueur protege = joueurs.get(1);

        protege.activerProtection();

        ArrayList<Joueur> ciblables = manche.getJoueursCiblables(joueurActif);

        // Seul 1 joueur doit être ciblable (le protégé est exclu)
        assertEquals(1, ciblables.size());
        assertFalse(ciblables.contains(protege));
    }

    @Test
    public void testGetJoueursCiblablesAvecSoiMeme() {
        manche.initialiser();
        Joueur joueurActif = joueurs.get(0);

        ArrayList<Joueur> ciblables = manche.getJoueursCiblablesAvecSoiMeme(joueurActif);

        // Les 3 joueurs doivent être ciblables
        assertEquals(3, ciblables.size());
        assertTrue(ciblables.contains(joueurActif));
    }

    @Test
    public void testVerifierFinManche_UnSeulJoueurRestant() {
        manche.initialiser();

        // Éliminer 2 joueurs
        manche.eliminerJoueur(joueurs.get(0));
        manche.eliminerJoueur(joueurs.get(1));

        assertTrue(manche.verifierFinManche());
    }

    @Test
    public void testVerifierFinManche_DeckVide() {
        manche.initialiser();

        // Vider le deck
        while (!manche.getDeck().estVide()) {
            manche.getDeck().piocher();
        }

        assertTrue(manche.verifierFinManche());
    }

    @Test
    public void testDeterminerVainqueurs_UnSeulJoueur() {
        manche.initialiser();

        manche.eliminerJoueur(joueurs.get(0));
        manche.eliminerJoueur(joueurs.get(1));

        ArrayList<Joueur> vainqueurs = manche.determinerVainqueurs();

        assertEquals(1, vainqueurs.size());
        assertEquals(joueurs.get(2), vainqueurs.get(0));
    }

    @Test
    public void testDeterminerVainqueurs_PlusHauteValeur() {
        manche.initialiser();

        // Donner des cartes de valeurs différentes
        joueurs.get(0).reinitialiser();
        joueurs.get(1).reinitialiser();
        joueurs.get(2).reinitialiser();

        joueurs.get(0).recevoirCarte(new Exam()); // Valeur 1
        joueurs.get(1).recevoirCarte(new TuteurPedagogique()); // Valeur 2
        joueurs.get(2).recevoirCarte(new BugInformatique()); // Valeur 5

        ArrayList<Joueur> vainqueurs = manche.determinerVainqueurs();

        assertEquals(1, vainqueurs.size());
        assertEquals(joueurs.get(2), vainqueurs.get(0));
    }

    @Test
    public void testDeterminerVainqueurs_Egalite() {
        manche.initialiser();

        // Donner la même carte à 2 joueurs
        joueurs.get(0).reinitialiser();
        joueurs.get(1).reinitialiser();
        joueurs.get(2).reinitialiser();

        joueurs.get(0).recevoirCarte(new BugInformatique()); // Valeur 5
        joueurs.get(1).recevoirCarte(new BugInformatique()); // Valeur 5
        joueurs.get(2).recevoirCarte(new Exam()); // Valeur 1

        ArrayList<Joueur> vainqueurs = manche.determinerVainqueurs();

        // 2 joueurs avec la valeur max
        assertEquals(2, vainqueurs.size());
    }

    @Test
    public void testAttribuerPionsFaveur() {
        manche.initialiser();

        // Éliminer 2 joueurs pour avoir un vainqueur
        manche.eliminerJoueur(joueurs.get(0));
        manche.eliminerJoueur(joueurs.get(1));

        int pionsBob = joueurs.get(2).getPionsFaveur();

        // Forcer la fin de manche et attribuer les pions
        ArrayList<Joueur> vainqueurs = manche.determinerVainqueurs();
        // Simuler l'attribution interne (normalement fait par terminerManche)
        for (Joueur v : vainqueurs) {
            v.ajouterPion();
        }

        assertEquals(pionsBob + 1, joueurs.get(2).getPionsFaveur());
    }

    @Test
    public void testVerifierBonusB2_UnSeulVainqueurAvecB2() {
        manche.initialiser();

        // Éliminer 2 joueurs
        manche.eliminerJoueur(joueurs.get(0));
        manche.eliminerJoueur(joueurs.get(1));

        // Marquer que le vainqueur a joué B2
        manche.marquerB2Joue(joueurs.get(2));

        // Déterminer les vainqueurs et vérifier bonus
        ArrayList<Joueur> vainqueurs = manche.determinerVainqueurs();

        // Bonus devrait être attribué
        Joueur bonusB2 = manche.verifierBonusB2();
        assertNotNull(bonusB2);
        assertEquals(joueurs.get(2), bonusB2);
    }

    @Test
    public void testVerifierBonusB2_PlusieursVainqueursAvecB2() {
        manche.initialiser();

        // Donner la même carte à 2 joueurs
        joueurs.get(0).reinitialiser();
        joueurs.get(1).reinitialiser();
        joueurs.get(2).reinitialiser();

        joueurs.get(0).recevoirCarte(new BugInformatique());
        joueurs.get(1).recevoirCarte(new BugInformatique());
        joueurs.get(2).recevoirCarte(new Exam());

        // Marquer que les 2 vainqueurs ont joué B2
        manche.marquerB2Joue(joueurs.get(0));
        manche.marquerB2Joue(joueurs.get(1));

        // Bonus ne devrait pas être attribué (2 vainqueurs avec B2)
        Joueur bonusB2 = manche.verifierBonusB2();
        assertNull(bonusB2);
    }

    @Test
    public void testGetJoueursActifs() {
        manche.initialiser();

        assertEquals(3, manche.getJoueursActifs().size());

        manche.eliminerJoueur(joueurs.get(0));

        assertEquals(2, manche.getJoueursActifs().size());
    }
}