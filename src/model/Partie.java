package model;

import java.util.*;

public class Partie {
    private static Partie instance;

    private ArrayList<Joueur> joueurs;
    private Manche mancheActuelle;
    private int numeroManche;
    private EtatPartie etat;
    private int nombreJoueurs;
    private int pionsRequis;
    private ArrayList<Manche> manches;
    private Joueur vainqueurFinal;

    private Partie(int nbJoueurs) {
        this.nombreJoueurs = nbJoueurs;
        this.joueurs = new ArrayList<>();
        this.numeroManche = 0;
        this.etat = EtatPartie.En_Cours;
        this.pionsRequis = calculerPionsRequis(nbJoueurs);
        this.manches = new ArrayList<>();
        this.vainqueurFinal = null;
    }

    public static Partie getInstance(int nbJoueurs) {
        if (instance == null) {
            instance = new Partie(nbJoueurs);
        }
        return instance;
    }

    /**
     * Permet de récupérer l'instance existante sans paramètre
     */
    public static Partie getInstance() {
        return instance;
    }

    /**
     * Réinitialise le singleton (pour démarrer une nouvelle partie)
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Calcule le nombre de pions requis pour gagner selon le nombre de joueurs
     */
    private int calculerPionsRequis(int nbJoueurs) {
        switch (nbJoueurs) {
            case 2:
                return 6;
            case 3:
                return 5;
            case 4:
                return 4;
            case 5:
            case 6:
                return 3;
            default:
                throw new IllegalArgumentException(
                        "Nombre de joueurs invalide : " + nbJoueurs + " (doit être entre 2 et 6)");
        }
    }

    /**
     * Initialise la partie avec les noms des joueurs
     */
    public void initialiser(ArrayList<String> nomsJoueurs) {
        if (nomsJoueurs.size() != nombreJoueurs) {
            throw new IllegalArgumentException("Le nombre de noms ne correspond pas au nombre de joueurs");
        }
        joueurs.clear();
        for (String nom : nomsJoueurs) {
            joueurs.add(new Joueur(nom, new MainJoueur()));
        }
    }

    /**
     * Démarre la partie en lançant la première manche
     */
    public void demarrerPartie() {
        if (joueurs.isEmpty()) {
            throw new IllegalStateException("La partie n'a pas été initialisée avec des joueurs");
        }
        etat = EtatPartie.En_Cours;
        numeroManche = 0;
        lancerNouvelleManche();
    }

    /**
     * Lance une nouvelle manche
     */
    public void lancerNouvelleManche() {
        numeroManche++;

        // Réinitialiser l'état des joueurs pour la nouvelle manche
        for (Joueur joueur : joueurs) {
            joueur.reinitialiser();
        }

        // Déterminer le premier joueur
        Joueur premierJoueur = determinerPremierJoueur();

        // Créer et démarrer la nouvelle manche
        mancheActuelle = new Manche(joueurs, premierJoueur);
        mancheActuelle.initialiser();
        manches.add(mancheActuelle);
        for (Joueur j : joueurs) {
            // Distribuer une carte à chaque joueur
            // j.getMain().ajouterCarte(mancheActuelle.getDeck().piocher());
        }
    }

    /**
     * Détermine le premier joueur de la manche
     * (vainqueur de la manche précédente ou aléatoire si première manche)
     */
    private Joueur determinerPremierJoueur() {
        if (manches.isEmpty()) {
            // Première manche : joueur aléatoire
            Random random = new Random();
            return joueurs.get(random.nextInt(joueurs.size()));
        } else {
            // Manches suivantes : le vainqueur de la manche précédente
            Manche manchePrecedente = manches.get(manches.size() - 1);
            ArrayList<Joueur> vainqueurs = manchePrecedente.getVainqueurs();
            if (vainqueurs != null && !vainqueurs.isEmpty()) {
                return vainqueurs.get(0);
            }
            return joueurs.get(0);
        }
    }

    /**
     * Retourne le joueur dont c'est le tour
     */
    public Joueur getJoueurActif() {
        if (mancheActuelle != null) {
            return mancheActuelle.getJoueurActif();
        }
        return null;
    }

    /**
     * Vérifie si un joueur a atteint le nombre de pions requis pour gagner
     * 
     * @return Liste des vainqueurs potentiels, null si aucun
     */
    public ArrayList<Joueur> verifierVictoireFinale() {
        ArrayList<Joueur> vainqueurs = new ArrayList<>();

        for (Joueur joueur : joueurs) {
            if (joueur.getPionsFaveur() >= pionsRequis) {
                vainqueurs.add(joueur);
            }
        }

        return vainqueurs.isEmpty() ? null : vainqueurs;
    }

    /**
     * Détermine le vainqueur final en cas d'égalité
     */
    private void determinerVainqueurFinal(ArrayList<Joueur> vainqueurs) {
        if (vainqueurs.size() == 1) {
            vainqueurFinal = vainqueurs.get(0);
        } else {
            int maxPions = pionsRequis;
            Joueur maxJoueur = vainqueurs.get(0);
            for (Joueur joueur : vainqueurs) {
                if (joueur.getPionsFaveur() > maxPions) {
                    maxPions = joueur.getPionsFaveur();
                    maxJoueur = joueur;
                }
            }
            vainqueurFinal = maxJoueur;
        }
    }

    /**
     * Gère la fin d'une manche et vérifie si la partie est terminée
     * 
     * @return true si la partie est terminée, false sinon
     */
    public boolean gererFinManche() {
        if (mancheActuelle == null || !mancheActuelle.isTerminee()) {
            return false;
        }

        // Vérifier si un joueur a atteint le nombre de pions requis
        ArrayList<Joueur> vainqueursPotentiels = verifierVictoireFinale();

        if (vainqueursPotentiels != null) {
            // Un ou plusieurs joueurs ont atteint le nombre de pions requis
            determinerVainqueurFinal(vainqueursPotentiels);
            terminerPartie();
            return true;
        }

        return false;
    }

    /**
     * Termine la partie
     */
    public void terminerPartie() {
        etat = EtatPartie.Terminee;
        mancheActuelle = null;
    }

    /**
     * Réinitialise complètement la partie pour en démarrer une nouvelle
     */
    public void reinitialiser() {
        joueurs.clear();
        manches.clear();
        mancheActuelle = null;
        numeroManche = 0;
        etat = EtatPartie.En_Cours;
        vainqueurFinal = null;
    }

    /**
     * Vérifie si la partie est terminée
     */
    public boolean estTerminee() {
        return etat == EtatPartie.Terminee;
    }

    /**
     * Retourne les scores actuels de tous les joueurs
     */
    public Map<Joueur, Integer> getScores() {
        Map<Joueur, Integer> scores = new LinkedHashMap<>();
        for (Joueur joueur : joueurs) {
            scores.put(joueur, joueur.getPionsFaveur());
        }
        return scores;
    }

    /**
     * Retourne les statistiques de fin de partie
     */
    public String getStatistiquesFinales() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== STATISTIQUES FINALES ===\n");
        sb.append("Nombre de manches jouées : ").append(numeroManche).append("\n");
        sb.append("Vainqueur : ").append(vainqueurFinal != null ? vainqueurFinal.getNom() : "Aucun").append("\n");
        sb.append("\nScores finaux :\n");

        // Trier les joueurs par score décroissant
        ArrayList<Joueur> joueursTriees = new ArrayList<>(joueurs);
        joueursTriees.sort((j1, j2) -> Integer.compare(j2.getPionsFaveur(), j1.getPionsFaveur()));

        for (int i = 0; i < joueursTriees.size(); i++) {
            Joueur j = joueursTriees.get(i);
            sb.append(String.format("%d. %s : %d pions\n", i + 1, j.getNom(), j.getPionsFaveur()));
        }

        return sb.toString();
    }

    // ================ ACCESSEURS ==================
    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public Manche getMancheActuelle() {
        return mancheActuelle;
    }

    public int getNumeroManche() {
        return numeroManche;
    }

    public EtatPartie getEtat() {
        return etat;
    }

    public int getNombreJoueurs() {
        return nombreJoueurs;
    }

    public int getPionsRequis() {
        return pionsRequis;
    }

    public Joueur getVainqueurFinal() {
        return vainqueurFinal;
    }

}
