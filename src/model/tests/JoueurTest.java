package model.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.*;
import model.cartes.*;

public class JoueurTest {

    private Joueur joueur;
    private MainJoueur main;

    @Before
    public void setUp() {
        main = new MainJoueur();
        joueur = new Joueur("Alice", main);
    }

    @Test
    public void testConstructeur() {
        assertEquals("Alice", joueur.getNom());
        assertEquals(0, joueur.getPionsFaveur());
        assertFalse(joueur.isElimine());
        assertFalse(joueur.isProtege());
    }

    @Test
    public void testRecevoirCarte() {
        Carte carte = new Exam();
        joueur.recevoirCarte(carte);
        assertEquals(1, joueur.getMain().getNombreCartes());
    }

    @Test
    public void testDefausserMain() {
        Carte carte = new Exam();
        joueur.recevoirCarte(carte);

        Carte carteDefaussee = joueur.defausserMain();

        assertNotNull(carteDefaussee);
        assertTrue(joueur.getMain().estVide());
        assertEquals(1, joueur.getCartesJouees().size());
    }

    @Test
    public void testACarteEnMain() {
        Carte exam = new Exam();
        joueur.recevoirCarte(exam);

        assertTrue(joueur.aCarteEnMain(TypeCarte.EXAM));
        assertFalse(joueur.aCarteEnMain(TypeCarte.GESTIONNAIRE_SEE));
    }

    @Test
    public void testGetValeurMain() {
        Carte gestionnaire = new GestionnaireSEE();
        joueur.recevoirCarte(gestionnaire);

        assertEquals(8, joueur.getValeurMain());
    }

    @Test
    public void testSommeCartesJouees() {
        joueur.getCartesJouees().add(new Exam());
        joueur.getCartesJouees().add(new TuteurPedagogique());

        assertEquals(3, joueur.getSommeCartesJouees()); // 1 + 2
    }

    @Test
    public void testAJoueB2() {
        assertFalse(joueur.aJoueB2());

        joueur.getCartesJouees().add(new B2Anglais());
        assertTrue(joueur.aJoueB2());
    }

    @Test
    public void testDoitJouerLA() {
        // Sans LA, pas de contrainte
        joueur.recevoirCarte(new Exam());
        assertFalse(joueur.doitJouerLA());

        // LA seul, pas de contrainte
        joueur.reinitialiser();
        joueur.recevoirCarte(new LearningAgreement());
        assertFalse(joueur.doitJouerLA());

        // LA + Directeur, contrainte active
        joueur.reinitialiser();
        joueur.recevoirCarte(new LearningAgreement());
        joueur.recevoirCarte(new Directeur());
        assertTrue(joueur.doitJouerLA());

        // LA + Bug Informatique, contrainte active
        joueur.reinitialiser();
        joueur.recevoirCarte(new LearningAgreement());
        joueur.recevoirCarte(new BugInformatique());
        assertTrue(joueur.doitJouerLA());
    }

    @Test
    public void testEliminer() {
        joueur.recevoirCarte(new Exam());

        joueur.eliminer();

        assertTrue(joueur.isElimine());
        assertFalse(joueur.isProtege());
        assertTrue(joueur.getMain().estVide());
    }

    @Test
    public void testProtection() {
        joueur.activerProtection();
        assertTrue(joueur.isProtege());

        joueur.leverProtection();
        assertFalse(joueur.isProtege());
    }

    @Test
    public void testAjouterPion() {
        assertEquals(0, joueur.getPionsFaveur());

        joueur.ajouterPion();
        assertEquals(1, joueur.getPionsFaveur());

        joueur.ajouterPions(3);
        assertEquals(4, joueur.getPionsFaveur());
    }

    @Test
    public void testReinitialiser() {
        joueur.recevoirCarte(new Exam());
        joueur.activerProtection();
        joueur.ajouterPion();

        joueur.reinitialiser();

        assertTrue(joueur.getMain().estVide());
        assertTrue(joueur.getCartesJouees().isEmpty());
        assertFalse(joueur.isProtege());
        assertFalse(joueur.isElimine());
        assertEquals(1, joueur.getPionsFaveur()); // Pions conservés
    }

    @Test
    public void testReinitialiserComplet() {
        joueur.recevoirCarte(new Exam());
        joueur.ajouterPion();

        joueur.reinitialiserComplet();

        assertTrue(joueur.getMain().estVide());
        assertTrue(joueur.getCartesJouees().isEmpty());
        assertEquals(0, joueur.getPionsFaveur());
    }

    @Test
    public void testEchangerMain() {
        Joueur bob = new Joueur("Bob", new MainJoueur());

        Carte exam = new Exam();
        Carte tuteur = new TuteurPedagogique();

        joueur.recevoirCarte(exam);
        bob.recevoirCarte(tuteur);

        joueur.echangerMain(bob);

        assertTrue(joueur.aCarteEnMain(TypeCarte.TUTEUR_PEDAGOGIQUE));
        assertTrue(bob.aCarteEnMain(TypeCarte.EXAM));
    }

    @Test
    public void testRemplacerMain() {
        Carte exam = new Exam();
        Carte tuteur = new TuteurPedagogique();

        joueur.recevoirCarte(exam);

        Carte ancienneCarte = joueur.remplacerMain(tuteur);

        assertEquals(exam, ancienneCarte);
        assertTrue(joueur.aCarteEnMain(TypeCarte.TUTEUR_PEDAGOGIQUE));
    }

    @Test
    public void testEstCiblable() {
        assertTrue(joueur.estCiblable());

        joueur.activerProtection();
        assertFalse(joueur.estCiblable());

        joueur.leverProtection();
        joueur.eliminer();
        assertFalse(joueur.estCiblable());
    }

    @Test
    public void testEstEnJeu() {
        assertTrue(joueur.estEnJeu());

        joueur.eliminer();
        assertFalse(joueur.estEnJeu());
    }

    @Test
    public void testAMainComplete() {
        assertFalse(joueur.aMainComplete());

        joueur.recevoirCarte(new Exam());
        assertFalse(joueur.aMainComplete());

        joueur.recevoirCarte(new TuteurPedagogique());
        assertTrue(joueur.aMainComplete());
    }

    @Test
    public void testEquals() {
        Joueur alice2 = new Joueur("Alice", new MainJoueur());
        Joueur bob = new Joueur("Bob", new MainJoueur());

        assertEquals(joueur, alice2);
        assertNotEquals(joueur, bob);
    }
}
