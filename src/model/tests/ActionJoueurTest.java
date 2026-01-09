package model.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.*;
import model.cartes.*;

public class ActionJoueurTest {

    private Joueur joueur;
    private Joueur cible;
    private Carte carte;
    private TypeCarte carteDevinee;

    @Before
    public void setUp() {
        joueur = new Joueur("Alice", new MainJoueur());
        cible = new Joueur("Bob", new MainJoueur());
        carte = new Exam();
        carteDevinee = TypeCarte.TUTEUR_PEDAGOGIQUE;
    }

    @Test
    public void testConstructeur() {
        ActionJoueur action = new ActionJoueur(joueur, carte, cible, carteDevinee);

        assertEquals(joueur, action.getJoueur());
        assertEquals(carte, action.getCarteJouee());
        assertEquals(cible, action.getCible());
        assertEquals(carteDevinee, action.getCarteDevinee());
    }

    @Test
    public void testConstructeurSansCible() {
        ActionJoueur action = new ActionJoueur(joueur, carte, null, null);

        assertEquals(joueur, action.getJoueur());
        assertEquals(carte, action.getCarteJouee());
        assertNull(action.getCible());
        assertNull(action.getCarteDevinee());
    }

    @Test
    public void testGetJoueur() {
        ActionJoueur action = new ActionJoueur(joueur, carte, cible, carteDevinee);

        assertEquals(joueur, action.getJoueur());
    }

    @Test
    public void testGetCarteJouee() {
        ActionJoueur action = new ActionJoueur(joueur, carte, cible, carteDevinee);

        assertEquals(carte, action.getCarteJouee());
    }

    @Test
    public void testGetCible() {
        ActionJoueur action = new ActionJoueur(joueur, carte, cible, carteDevinee);

        assertEquals(cible, action.getCible());
    }

    @Test
    public void testGetCarteDevinee() {
        ActionJoueur action = new ActionJoueur(joueur, carte, cible, carteDevinee);

        assertEquals(carteDevinee, action.getCarteDevinee());
    }

    @Test
    public void testActionCompletePourExam() {
        // Action typique pour jouer Exam (Garde)
        ActionJoueur action = new ActionJoueur(joueur, carte, cible, TypeCarte.JURY);

        assertNotNull(action.getJoueur());
        assertNotNull(action.getCarteJouee());
        assertNotNull(action.getCible());
        assertNotNull(action.getCarteDevinee());
    }

    @Test
    public void testActionSansCiblePourB2() {
        // Action pour B2 Anglais (pas de cible)
        Carte b2 = new B2Anglais();
        ActionJoueur action = new ActionJoueur(joueur, b2, null, null);

        assertNotNull(action.getJoueur());
        assertNotNull(action.getCarteJouee());
        assertNull(action.getCible());
        assertNull(action.getCarteDevinee());
    }
}