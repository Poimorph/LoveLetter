package modele.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import modele.*;

public class PartieTest {

    @Before
    public void setUp() {
        Partie.resetInstance();
    }

    @Test
    public void testGetInstance() {
        Partie partie = Partie.getInstance(3);
        assertNotNull(partie);
        assertEquals(3, partie.getNombreJoueurs());
    }

    @Test
    public void testSingleton() {
        Partie partie1 = Partie.getInstance(3);
        Partie partie2 = Partie.getInstance();

        assertSame(partie1, partie2);
    }

    @Test
    public void testCalculerPionsRequis() {
        assertEquals(6, Partie.getInstance(2).getPionsRequis());
        Partie.resetInstance();
        assertEquals(5, Partie.getInstance(3).getPionsRequis());
        Partie.resetInstance();
        assertEquals(4, Partie.getInstance(4).getPionsRequis());
        Partie.resetInstance();
        assertEquals(3, Partie.getInstance(5).getPionsRequis());
        Partie.resetInstance();
        assertEquals(3, Partie.getInstance(6).getPionsRequis());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculerPionsRequisInvalide() {
        Partie.getInstance(7);
    }

    @Test
    public void testInitialiser() {
        Partie partie = Partie.getInstance(3);

        ArrayList<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");
        noms.add("Charlie");

        partie.initialiser(noms);

        assertEquals(3, partie.getJoueurs().size());
        assertEquals("Alice", partie.getJoueurs().get(0).getNom());
        assertEquals("Bob", partie.getJoueurs().get(1).getNom());
        assertEquals("Charlie", partie.getJoueurs().get(2).getNom());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitialiserNombreIncorrect() {
        Partie partie = Partie.getInstance(3);

        ArrayList<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");

        partie.initialiser(noms);
    }

    @Test
    public void testDemarrerPartie() {
        Partie partie = Partie.getInstance(2);

        ArrayList<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");

        partie.initialiser(noms);
        partie.demarrerPartie();

        assertEquals(EtatPartie.En_Cours, partie.getEtat());
        assertNotNull(partie.getMancheActuelle());
        assertEquals(1, partie.getNumeroManche());
    }

    @Test(expected = IllegalStateException.class)
    public void testDemarrerPartieSansInitialisation() {
        Partie partie = Partie.getInstance(2);
        partie.demarrerPartie();
    }

    @Test
    public void testLancerNouvelleManche() {
        Partie partie = Partie.getInstance(2);

        ArrayList<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");

        partie.initialiser(noms);
        partie.demarrerPartie();

        int numeroManche = partie.getNumeroManche();
        Manche mancheActuelle = partie.getMancheActuelle();

        partie.lancerNouvelleManche();

        assertEquals(numeroManche + 1, partie.getNumeroManche());
        assertNotSame(mancheActuelle, partie.getMancheActuelle());
    }

    @Test
    public void testVerifierVictoireFinale() {
        Partie partie = Partie.getInstance(2);

        ArrayList<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");

        partie.initialiser(noms);
        partie.demarrerPartie();

        // Pas de vainqueur au début
        assertNull(partie.verifierVictoireFinale());

        // Donner 6 pions à Alice
        partie.getJoueurs().get(0).ajouterPions(6);

        // Alice doit être vainqueur
        ArrayList<Joueur> vainqueurs = partie.verifierVictoireFinale();
        assertNotNull(vainqueurs);
        assertEquals(1, vainqueurs.size());
        assertEquals("Alice", vainqueurs.get(0).getNom());
    }

    @Test
    public void testVerifierVictorieFinale_Egalite() {
        Partie partie = Partie.getInstance(2);

        ArrayList<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");

        partie.initialiser(noms);
        partie.demarrerPartie();

        // Donner 6 pions aux deux joueurs
        partie.getJoueurs().get(0).ajouterPions(6);
        partie.getJoueurs().get(1).ajouterPions(6);

        // Les deux doivent être vainqueurs
        ArrayList<Joueur> vainqueurs = partie.verifierVictoireFinale();
        assertNotNull(vainqueurs);
        assertEquals(2, vainqueurs.size());
    }

    @Test
    public void testTerminerPartie() {
        Partie partie = Partie.getInstance(2);

        ArrayList<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");

        partie.initialiser(noms);
        partie.demarrerPartie();

        partie.terminerPartie();

        assertEquals(EtatPartie.Terminee, partie.getEtat());
        assertTrue(partie.estTerminee());
        assertNull(partie.getMancheActuelle());
    }

    @Test
    public void testGetScores() {
        Partie partie = Partie.getInstance(2);

        ArrayList<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");

        partie.initialiser(noms);

        partie.getJoueurs().get(0).ajouterPions(3);
        partie.getJoueurs().get(1).ajouterPions(1);

        Map<Joueur, Integer> scores = partie.getScores();

        assertEquals(2, scores.size());
        assertEquals(Integer.valueOf(3), scores.get(partie.getJoueurs().get(0)));
        assertEquals(Integer.valueOf(1), scores.get(partie.getJoueurs().get(1)));
    }

    @Test
    public void testReinitialiser() {
        Partie partie = Partie.getInstance(2);

        ArrayList<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");

        partie.initialiser(noms);
        partie.demarrerPartie();

        partie.reinitialiser();

        assertTrue(partie.getJoueurs().isEmpty());
        assertNull(partie.getMancheActuelle());
        assertEquals(0, partie.getNumeroManche());
        assertEquals(EtatPartie.En_Cours, partie.getEtat());
    }

    @Test
    public void testGetJoueurActif() {
        Partie partie = Partie.getInstance(2);

        ArrayList<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");

        partie.initialiser(noms);
        partie.demarrerPartie();

        Joueur joueurActif = partie.getJoueurActif();

        assertNotNull(joueurActif);
        assertTrue(partie.getJoueurs().contains(joueurActif));
    }

    @Test
    public void testGetStatistiquesFinales() {
        Partie partie = Partie.getInstance(2);

        ArrayList<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");

        partie.initialiser(noms);
        partie.demarrerPartie();

        partie.getJoueurs().get(0).ajouterPions(6);
        partie.terminerPartie();

        String stats = partie.getStatistiquesFinales();

        assertNotNull(stats);
        assertTrue(stats.contains("STATISTIQUES FINALES"));
        assertTrue(stats.contains("Alice"));
        assertTrue(stats.contains("Bob"));
    }
}