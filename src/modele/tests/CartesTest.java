package modele.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import modele.*;
import modele.cartes.*;

/**
 * Tests pour les effets des cartes
 */
public class CartesTest {

    private Manche manche;
    private ArrayList<Joueur> joueurs;
    private Joueur alice, bob, charlie;

    @Before
    public void setUp() {
        alice = new Joueur("Alice", new MainJoueur());
        bob = new Joueur("Bob", new MainJoueur());
        charlie = new Joueur("Charlie", new MainJoueur());

        joueurs = new ArrayList<>();
        joueurs.add(alice);
        joueurs.add(bob);
        joueurs.add(charlie);

        manche = new Manche(joueurs, joueurs.get(0)); // Alice commence
        manche.initialiser();
    }

    // ==================== EXAM (Garde) ====================

    @Test
    public void testExam_DevinettCorrect() {
        bob.reinitialiser();
        bob.recevoirCarte(new TuteurPedagogique());

        Exam exam = new Exam();
        ActionJoueur action = new ActionJoueur(alice, exam, bob, TypeCarte.TUTEUR_PEDAGOGIQUE);

        exam.appliquerEffet(action, manche);

        assertTrue(bob.isElimine());
    }

    @Test
    public void testExam_DevinettIncorrect() {
        bob.reinitialiser();
        bob.recevoirCarte(new TuteurPedagogique());

        Exam exam = new Exam();
        ActionJoueur action = new ActionJoueur(alice, exam, bob, TypeCarte.JURY);

        exam.appliquerEffet(action, manche);

        assertFalse(bob.isElimine());
    }

    @Test
    public void testExam_NecessiteCible() {
        Exam exam = new Exam();
        assertTrue(exam.necessiteCible());
        assertFalse(exam.peutCiblerSoiMeme());
    }

    // ==================== TUTEUR PÉDAGOGIQUE (Prêtre) ====================

    @Test
    public void testTuteurPedagogique_VoirMain() {
        bob.reinitialiser();
        bob.recevoirCarte(new Exam());

        TuteurPedagogique tuteur = new TuteurPedagogique();
        ActionJoueur action = new ActionJoueur(alice, tuteur, bob, null);

        // Devrait juste afficher la carte, pas d'effet sur le jeu
        tuteur.appliquerEffet(action, manche);

        assertFalse(bob.isElimine());
        assertEquals(1, bob.getMain().getNombreCartes());
    }

    @Test
    public void testTuteurPedagogique_NecessiteCible() {
        TuteurPedagogique tuteur = new TuteurPedagogique();
        assertTrue(tuteur.necessiteCible());
        assertFalse(tuteur.peutCiblerSoiMeme());
    }

    // ==================== JURY (Baron) ====================

    @Test
    public void testJury_ComparaisonGagnante() {
        alice.reinitialiser();
        bob.reinitialiser();

        alice.recevoirCarte(new BugInformatique()); // Valeur 5
        bob.recevoirCarte(new Exam()); // Valeur 1

        Jury jury = new Jury();
        ActionJoueur action = new ActionJoueur(alice, jury, bob, null);

        jury.appliquerEffet(action, manche);

        assertTrue(bob.isElimine());
        assertFalse(alice.isElimine());
    }

    @Test
    public void testJury_ComparaisonPerdante() {
        alice.reinitialiser();
        bob.reinitialiser();

        alice.recevoirCarte(new Exam()); // Valeur 1
        bob.recevoirCarte(new BugInformatique()); // Valeur 5

        Jury jury = new Jury();
        ActionJoueur action = new ActionJoueur(alice, jury, bob, null);

        jury.appliquerEffet(action, manche);

        assertTrue(alice.isElimine());
        assertFalse(bob.isElimine());
    }

    @Test
    public void testJury_ComparaisonEgalite() {
        alice.reinitialiser();
        bob.reinitialiser();

        alice.recevoirCarte(new Exam());
        bob.recevoirCarte(new Exam());

        Jury jury = new Jury();
        ActionJoueur action = new ActionJoueur(alice, jury, bob, null);

        jury.appliquerEffet(action, manche);

        assertFalse(alice.isElimine());
        assertFalse(bob.isElimine());
    }

    @Test
    public void testJury_NecessiteCible() {
        Jury jury = new Jury();
        assertTrue(jury.necessiteCible());
        assertFalse(jury.peutCiblerSoiMeme());
    }

    // ==================== RÈGLEMENT DES ÉTUDES (Servante) ====================

    @Test
    public void testReglementDesEtudes_ActiverProtection() {
        ReglementDesEtudes rde = new ReglementDesEtudes();
        ActionJoueur action = new ActionJoueur(alice, rde, null, null);

        rde.appliquerEffet(action, manche);

        assertTrue(alice.isProtege());
    }

    @Test
    public void testReglementDesEtudes_PasDeCible() {
        ReglementDesEtudes rde = new ReglementDesEtudes();
        assertFalse(rde.necessiteCible());
    }

    // ==================== BUG INFORMATIQUE (Prince) ====================

    @Test
    public void testBugInformatique_DefausserEtPiocher() {
        bob.reinitialiser();
        bob.recevoirCarte(new Exam());

        BugInformatique bug = new BugInformatique();
        ActionJoueur action = new ActionJoueur(alice, bug, bob, null);

        bug.appliquerEffet(action, manche);

        // Bob doit avoir pioché une nouvelle carte
        assertEquals(1, bob.getMain().getNombreCartes());
    }

    @Test
    public void testBugInformatique_DefausserGestionnaireSEE() {
        bob.reinitialiser();
        bob.recevoirCarte(new GestionnaireSEE());

        BugInformatique bug = new BugInformatique();
        ActionJoueur action = new ActionJoueur(alice, bug, bob, null);

        bug.appliquerEffet(action, manche);

        // Bob doit être éliminé car il a défaussé Gestionnaire SEE
        assertTrue(bob.isElimine());
    }

    @Test
    public void testBugInformatique_PeutCiblerSoiMeme() {
        BugInformatique bug = new BugInformatique();
        assertTrue(bug.necessiteCible());
        assertTrue(bug.peutCiblerSoiMeme());
    }

    // ==================== GESTIONNAIRE SEE (Princesse) ====================

    @Test
    public void testGestionnaireSEE_Elimination() {
        GestionnaireSEE gestionnaire = new GestionnaireSEE();
        ActionJoueur action = new ActionJoueur(alice, gestionnaire, null, null);

        gestionnaire.appliquerEffet(action, manche);

        assertTrue(alice.isElimine());
    }

    @Test
    public void testGestionnaireSEE_PasDeCible() {
        GestionnaireSEE gestionnaire = new GestionnaireSEE();
        assertFalse(gestionnaire.necessiteCible());
    }

    // ==================== B2 ANGLAIS (Espionne) ====================

    @Test
    public void testB2Anglais_MarquerJoueur() {
        B2Anglais b2 = new B2Anglais();
        ActionJoueur action = new ActionJoueur(alice, b2, null, null);

        b2.appliquerEffet(action, manche);

        assertTrue(manche.getJoueursAyantJoueB2().contains(alice));
    }

    @Test
    public void testB2Anglais_PasDeCible() {
        B2Anglais b2 = new B2Anglais();
        assertFalse(b2.necessiteCible());
    }

    // ==================== LEARNING AGREEMENT (Comtesse) ====================

    @Test
    public void testLearningAgreement_PasEffet() {
        LearningAgreement la = new LearningAgreement();
        ActionJoueur action = new ActionJoueur(alice, la, null, null);

        // LA n'a pas d'effet direct
        la.appliquerEffet(action, manche);

        assertFalse(alice.isElimine());
    }

    @Test
    public void testLearningAgreement_PasDeCible() {
        LearningAgreement la = new LearningAgreement();
        assertFalse(la.necessiteCible());
    }

    // ==================== DIRECTEUR (Roi) ====================

    @Test
    public void testDirecteur_EchangerMain() {
        alice.reinitialiser();
        bob.reinitialiser();

        Carte exam = new Exam();
        Carte tuteur = new TuteurPedagogique();

        alice.recevoirCarte(exam);
        bob.recevoirCarte(tuteur);

        Directeur directeur = new Directeur();
        ActionJoueur action = new ActionJoueur(alice, directeur, bob, null);

        directeur.appliquerEffet(action, manche);

        // Les mains doivent être échangées
        assertTrue(alice.aCarteEnMain(TypeCarte.TUTEUR_PEDAGOGIQUE));
        assertTrue(bob.aCarteEnMain(TypeCarte.EXAM));
    }

    @Test
    public void testDirecteur_NecessiteCible() {
        Directeur directeur = new Directeur();
        assertTrue(directeur.necessiteCible());
        assertFalse(directeur.peutCiblerSoiMeme());
    }

    // ==================== TESTS DE COMPARAISON ====================

    @Test
    public void testCompareTo() {
        Carte exam = new Exam(); // Valeur 1
        Carte tuteur = new TuteurPedagogique(); // Valeur 2
        Carte gestionnaire = new GestionnaireSEE(); // Valeur 8

        assertTrue(exam.compareTo(tuteur) < 0);
        assertTrue(gestionnaire.compareTo(tuteur) > 0);
        assertEquals(0, exam.compareTo(new Exam()));
    }

    @Test
    public void testGetters() {
        Exam exam = new Exam();

        assertEquals(TypeCarte.EXAM, exam.getType());
        assertEquals(1, exam.getValeur());
        assertEquals("Exam", exam.getNom());
        assertNotNull(exam.getDescription());
    }

    @Test
    public void testToString() {
        Exam exam = new Exam();
        String str = exam.toString();

        assertTrue(str.contains("Exam"));
        assertTrue(str.contains("1"));
    }
}