package model.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.*;

import java.util.ArrayList;

public class DeckTest {

    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void testInitialiser() {
        deck.initialiser();
        assertEquals(21, deck.getNombreCartesRestantes());
    }

    @Test
    public void testPreparerPourManche2Joueurs() {
        deck.preparerPourManche(2);

        // 21 cartes (carte cachée et cartes visibles désactivées pour le moment)
        assertEquals(21, deck.getNombreCartesRestantes());
    }

    @Test
    public void testPreparerPourManche4Joueurs() {
        deck.preparerPourManche(4);

        // 21 cartes (carte cachée désactivée pour le moment)
        assertEquals(21, deck.getNombreCartesRestantes());
    }

    @Test
    public void testPiocher() {
        deck.initialiser();

        Carte carte = deck.piocher();
        assertNotNull(carte);
        assertEquals(20, deck.getNombreCartesRestantes());
    }

    @Test
    public void testPiocherDeckVide() {
        Carte carte = deck.piocher();
        assertNull(carte);
    }

    @Test
    public void testPiocherMultiple() {
        deck.initialiser();

        ArrayList<Carte> cartes = deck.piocherMultiple(5);

        assertEquals(5, cartes.size());
        assertEquals(16, deck.getNombreCartesRestantes());
    }

    // Tests désactivés car carte cachée commentée pour le moment
    // @Test
    // public void testCacherCarte() {
    //     deck.initialiser();
    //
    //     deck.cacherCarte();
    //
    //     assertNotNull(deck.getCarteCachee());
    //     assertEquals(20, deck.getNombreCartesRestantes());
    // }

    // @Test
    // public void testPiocherCarteCachee() {
    //     deck.initialiser();
    //     deck.cacherCarte();
    //
    //     Carte cachee = deck.piocherCarteCachee();
    //
    //     assertNotNull(cachee);
    //     assertNull(deck.getCarteCachee());
    // }

    @Test
    public void testAjouterDansDefausse() {
        deck.initialiser();
        Carte carte = deck.piocher();

        deck.ajouterDansDefausse(carte);

        assertEquals(1, deck.getNombreCartesDefausse());
    }

    @Test
    public void testRemettreEnDessous() {
        deck.initialiser();
        Carte carte = deck.piocher();
        int nbCartes = deck.getNombreCartesRestantes();

        deck.remettreEnDessous(carte);

        assertEquals(nbCartes + 1, deck.getNombreCartesRestantes());
    }

    @Test
    public void testRemettreEnDessus() {
        deck.initialiser();
        Carte carte = deck.piocher();

        deck.remettreEnDessus(carte);

        Carte cartePiochee = deck.piocher();
        assertEquals(carte, cartePiochee);
    }

    @Test
    public void testEstVide() {
        assertTrue(deck.estVide());

        deck.initialiser();
        assertFalse(deck.estVide());
    }

    @Test
    public void testContientType() {
        deck.initialiser();

        assertTrue(deck.contientType(TypeCarte.EXAM));

        // Vider le deck
        while (!deck.estVide()) {
            deck.piocher();
        }

        assertFalse(deck.contientType(TypeCarte.EXAM));
    }

    @Test
    public void testCompterType() {
        deck.initialiser();

        assertEquals(6, deck.compterType(TypeCarte.EXAM));
        assertEquals(1, deck.compterType(TypeCarte.GESTIONNAIRE_SEE));
        assertEquals(2, deck.compterType(TypeCarte.JURY));
    }

    @Test
    public void testDistribuer() {
        deck.initialiser();

        ArrayList<Joueur> joueurs = new ArrayList<>();
        joueurs.add(new Joueur("Alice", new MainJoueur()));
        joueurs.add(new Joueur("Bob", new MainJoueur()));

        deck.distribuer(joueurs);

        assertEquals(1, joueurs.get(0).getMain().getNombreCartes());
        assertEquals(1, joueurs.get(1).getMain().getNombreCartes());
        assertEquals(19, deck.getNombreCartesRestantes());
    }

    @Test
    public void testDistribuerMultiple() {
        deck.initialiser();

        ArrayList<Joueur> joueurs = new ArrayList<>();
        joueurs.add(new Joueur("Alice", new MainJoueur()));
        joueurs.add(new Joueur("Bob", new MainJoueur()));

        deck.distribuer(joueurs, 2);

        assertEquals(2, joueurs.get(0).getMain().getNombreCartes());
        assertEquals(2, joueurs.get(1).getMain().getNombreCartes());
        assertEquals(17, deck.getNombreCartesRestantes());
    }

    @Test
    public void testMelanger() {
        deck.initialiser();
        ArrayList<Carte> avant = new ArrayList<>(deck.getCartes());

        deck.melanger();
        ArrayList<Carte> apres = deck.getCartes();

        // Les deux listes doivent avoir la même taille
        assertEquals(avant.size(), apres.size());
    }

    @Test
    public void testGetCartesPubliques() {
        deck.initialiser();
        deck.preparerPourManche(2);

        Carte carte = deck.piocher();
        deck.ajouterDansDefausse(carte);

        ArrayList<Carte> cartesPubliques = deck.getCartesPubliques();

        // 1 défausse (cartes visibles désactivées pour le moment)
        assertEquals(1, cartesPubliques.size());
    }
}