package modele.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import modele.*;
import modele.cartes.*;

public class MainJoueurTest {

    private MainJoueur main;

    @Before
    public void setUp() {
        main = new MainJoueur();
    }

    @Test
    public void testConstructeur() {
        assertTrue(main.estVide());
        assertEquals(0, main.getNombreCartes());
    }

    @Test
    public void testAjouterCarte() {
        Carte exam = new Exam();

        assertTrue(main.ajouterCarte(exam));
        assertEquals(1, main.getNombreCartes());
        assertFalse(main.estVide());
    }

    @Test
    public void testAjouterCarteMainPleine() {
        main.ajouterCarte(new Exam());
        main.ajouterCarte(new TuteurPedagogique());

        // La main est pleine (max 2 cartes)
        assertFalse(main.ajouterCarte(new Jury()));
        assertEquals(2, main.getNombreCartes());
    }

    @Test
    public void testAjouterCarteNull() {
        assertFalse(main.ajouterCarte(null));
        assertEquals(0, main.getNombreCartes());
    }

    @Test
    public void testRetirerCarte() {
        Carte exam = new Exam();
        main.ajouterCarte(exam);

        Carte retiree = main.retirerCarte(exam);

        assertEquals(exam, retiree);
        assertTrue(main.estVide());
    }

    @Test
    public void testRetirerCarteParIndex() {
        Carte exam = new Exam();
        main.ajouterCarte(exam);

        Carte retiree = main.retirerCarte(0);

        assertEquals(exam, retiree);
        assertTrue(main.estVide());
    }

    @Test
    public void testRetirerCarteIndexInvalide() {
        main.ajouterCarte(new Exam());

        assertNull(main.retirerCarte(-1));
        assertNull(main.retirerCarte(5));
        assertEquals(1, main.getNombreCartes());
    }

    @Test
    public void testRetirerCarteParType() {
        main.ajouterCarte(new Exam());
        main.ajouterCarte(new TuteurPedagogique());

        Carte retiree = main.retirerCarteParType(TypeCarte.EXAM);

        assertNotNull(retiree);
        assertEquals(TypeCarte.EXAM, retiree.getType());
        assertEquals(1, main.getNombreCartes());
    }

    @Test
    public void testRetirerCarteParTypeInexistant() {
        main.ajouterCarte(new Exam());

        Carte retiree = main.retirerCarteParType(TypeCarte.GESTIONNAIRE_SEE);

        assertNull(retiree);
        assertEquals(1, main.getNombreCartes());
    }

    @Test
    public void testGetCarte() {
        Carte exam = new Exam();
        main.ajouterCarte(exam);

        Carte carte = main.getCarte(0);

        assertEquals(exam, carte);
        assertEquals(1, main.getNombreCartes()); // Pas retirée
    }

    @Test
    public void testGetCarteParType() {
        Carte exam = new Exam();
        main.ajouterCarte(exam);

        Carte carte = main.getCarteParType(TypeCarte.EXAM);

        assertEquals(exam, carte);
        assertEquals(1, main.getNombreCartes()); // Pas retirée
    }

    @Test
    public void testGetCartes() {
        Carte exam = new Exam();
        Carte tuteur = new TuteurPedagogique();

        main.ajouterCarte(exam);
        main.ajouterCarte(tuteur);

        ArrayList<Carte> cartes = main.getCartes();

        assertEquals(2, cartes.size());
        assertTrue(cartes.contains(exam));
        assertTrue(cartes.contains(tuteur));

        // Vérifier que c'est une copie
        cartes.clear();
        assertEquals(2, main.getNombreCartes());
    }

    @Test
    public void testVider() {
        main.ajouterCarte(new Exam());
        main.ajouterCarte(new TuteurPedagogique());

        main.vider();

        assertTrue(main.estVide());
        assertEquals(0, main.getNombreCartes());
    }

    @Test
    public void testContientType() {
        main.ajouterCarte(new Exam());

        assertTrue(main.contientType(TypeCarte.EXAM));
        assertFalse(main.contientType(TypeCarte.TUTEUR_PEDAGOGIQUE));
    }

    @Test
    public void testContientValeur() {
        main.ajouterCarte(new Exam()); // Valeur 1

        assertTrue(main.contientValeur(1));
        assertFalse(main.contientValeur(5));
    }

    @Test
    public void testEstComplete() {
        assertFalse(main.estComplete());

        main.ajouterCarte(new Exam());
        assertFalse(main.estComplete());

        main.ajouterCarte(new TuteurPedagogique());
        assertTrue(main.estComplete());
    }

    @Test
    public void testEchangerAvec() {
        MainJoueur autre = new MainJoueur();

        Carte exam = new Exam();
        Carte tuteur = new TuteurPedagogique();

        main.ajouterCarte(exam);
        autre.ajouterCarte(tuteur);

        main.echangerAvec(autre);

        assertTrue(main.contientType(TypeCarte.TUTEUR_PEDAGOGIQUE));
        assertTrue(autre.contientType(TypeCarte.EXAM));
    }

    @Test
    public void testGetValeurMax() {
        main.ajouterCarte(new Exam()); // Valeur 1
        main.ajouterCarte(new BugInformatique()); // Valeur 5

        assertEquals(5, main.getValeurMax());
    }

    @Test
    public void testGetSommeValeurs() {
        main.ajouterCarte(new Exam()); // Valeur 1
        main.ajouterCarte(new TuteurPedagogique()); // Valeur 2

        assertEquals(3, main.getSommeValeurs());
    }

    @Test
    public void testGetCarteMaxValeur() {
        Carte exam = new Exam(); // Valeur 1
        Carte bug = new BugInformatique(); // Valeur 5

        main.ajouterCarte(exam);
        main.ajouterCarte(bug);

        Carte max = main.getCarteMaxValeur();

        assertEquals(bug, max);
    }

    @Test
    public void testDoitJouerLA() {
        // Sans LA, pas de contrainte
        main.ajouterCarte(new Exam());
        assertFalse(main.doitJouerLA());

        // LA seul, pas de contrainte
        main.vider();
        main.ajouterCarte(new LearningAgreement());
        assertFalse(main.doitJouerLA());

        // LA + Directeur, contrainte active
        main.vider();
        main.ajouterCarte(new LearningAgreement());
        main.ajouterCarte(new Directeur());
        assertTrue(main.doitJouerLA());

        // LA + Bug Informatique, contrainte active
        main.vider();
        main.ajouterCarte(new LearningAgreement());
        main.ajouterCarte(new BugInformatique());
        assertTrue(main.doitJouerLA());
    }

    @Test
    public void testGetIndicesCartesJouables() {
        main.ajouterCarte(new Exam());
        main.ajouterCarte(new TuteurPedagogique());

        ArrayList<Integer> indices = main.getIndicesCartesJouables();

        // Toutes les cartes sont jouables
        assertEquals(2, indices.size());
        assertTrue(indices.contains(0));
        assertTrue(indices.contains(1));
    }

    @Test
    public void testGetIndicesCartesJouablesAvecContrainte() {
        main.ajouterCarte(new LearningAgreement());
        main.ajouterCarte(new Directeur());

        ArrayList<Integer> indices = main.getIndicesCartesJouables();

        // Seule LA est jouable
        assertEquals(1, indices.size());

        Carte carteJouable = main.getCarte(indices.get(0));
        assertEquals(TypeCarte.LA, carteJouable.getType());
    }

    @Test
    public void testGetCartesJouables() {
        Carte exam = new Exam();
        Carte tuteur = new TuteurPedagogique();

        main.ajouterCarte(exam);
        main.ajouterCarte(tuteur);

        ArrayList<Carte> jouables = main.getCartesJouables();

        assertEquals(2, jouables.size());
        assertTrue(jouables.contains(exam));
        assertTrue(jouables.contains(tuteur));
    }

    @Test
    public void testGetCartesJouablesAvecContrainte() {
        Carte la = new LearningAgreement();
        Carte directeur = new Directeur();

        main.ajouterCarte(la);
        main.ajouterCarte(directeur);

        ArrayList<Carte> jouables = main.getCartesJouables();

        // Seule LA est jouable
        assertEquals(1, jouables.size());
        assertEquals(la, jouables.get(0));
    }

    @Test
    public void testAfficher() {
        String affichage = main.afficher();
        assertEquals("Main vide", affichage);

        main.ajouterCarte(new Exam());
        affichage = main.afficher();

        assertTrue(affichage.contains("Exam"));
        assertTrue(affichage.contains("1")); // Valeur
    }

    @Test
    public void testAfficherDetaille() {
        main.ajouterCarte(new Exam());

        String affichage = main.afficherDetaille();

        assertTrue(affichage.contains("VOTRE MAIN"));
        assertTrue(affichage.contains("Exam"));
        assertTrue(affichage.contains("Valeur"));
    }

    @Test
    public void testAfficherDetailleAvecContrainte() {
        main.ajouterCarte(new LearningAgreement());
        main.ajouterCarte(new Directeur());

        String affichage = main.afficherDetaille();

        assertTrue(affichage.contains("CONTRAINTE"));
        assertTrue(affichage.contains("jouer LA"));
    }

    @Test
    public void testAfficherCache() {
        main.ajouterCarte(new Exam());

        String affichage = main.afficherCache();

        assertTrue(affichage.contains("1 carte"));
        assertFalse(affichage.contains("Exam")); // Pas de détails
    }
}