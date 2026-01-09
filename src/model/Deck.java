package model;

import java.util.*;

public class Deck {

    private ArrayList<Carte> cartes;
    private ArrayList<Carte> defausse;
    private Carte carteCachee;
    private ArrayList<Carte> cartesVisibles; // Pour les parties à 2 joueurs

    public Deck() {
        this.cartes = new ArrayList<>();
        this.defausse = new ArrayList<>();
        this.carteCachee = null;
        this.cartesVisibles = new ArrayList<>();
    }

    // ==================== INITIALISATION ====================

    /**
     * Initialise le deck avec toutes les cartes du jeu
     */
    public void initialiser() {
        cartes.clear();
        defausse.clear();
        carteCachee = null;
        cartesVisibles.clear();

        // Création des 21 cartes via la factory
        cartes = CarteFactory.creerDeckComplet();
    }

    /**
     * Mélange les cartes du deck
     */
    public void melanger() {
        Collections.shuffle(cartes);
    }

    /**
     * Cache une carte du dessus du deck (règle Love Letter)
     * Cette carte ne sera pas utilisée pendant la manche
     */
    public void cacherCarte() {
        if (!cartes.isEmpty()) {
            carteCachee = cartes.remove(0);
        }
    }

    /**
     * Met des cartes visibles sur le côté (règle pour 2 joueurs)
     * 
     * @param nombre Le nombre de cartes à mettre de côté face visible
     */
    public void mettreCartesVisibles(int nombre) {
        cartesVisibles.clear();
        for (int i = 0; i < nombre && !cartes.isEmpty(); i++) {
            cartesVisibles.add(cartes.remove(0));
        }
    }

    /**
     * Prépare le deck pour une manche selon le nombre de joueurs
     * 
     * @param nombreJoueurs Le nombre de joueurs dans la partie
     */
    public void preparerPourManche(int nombreJoueurs) {
        // 1. Initialiser avec toutes les cartes
        initialiser();

        // 2. Mélanger
        melanger();

        // 3. Cacher une carte face cachée
        cacherCarte();

        // 4. Pour 2 joueurs, mettre 3 cartes face visible
        if (nombreJoueurs == 2) {
            mettreCartesVisibles(3);
        }
    }

    // ==================== PIOCHE ====================

    /**
     * Pioche une carte du dessus du deck
     * 
     * @return La carte piochée, ou null si le deck est vide
     */
    public Carte piocher() {
        if (cartes.isEmpty()) {
            return null;
        }
        return cartes.remove(0);
    }

    /**
     * Pioche plusieurs cartes du deck
     * 
     * @param nombre Le nombre de cartes à piocher
     * @return Liste des cartes piochées
     */
    public ArrayList<Carte> piocherMultiple(int nombre) {
        ArrayList<Carte> cartespiochees = new ArrayList<>();
        for (int i = 0; i < nombre && !cartes.isEmpty(); i++) {
            cartespiochees.add(cartes.remove(0));
        }
        return cartespiochees;
    }

    /**
     * Regarde la carte du dessus sans la retirer
     * 
     * @return La carte du dessus, ou null si le deck est vide
     */
    public Carte regarderDessus() {
        if (cartes.isEmpty()) {
            return null;
        }
        return cartes.get(0);
    }

    /**
     * Pioche la carte cachée (utilisé en fin de manche si deck vide et un seul
     * joueur restant)
     * 
     * @return La carte cachée
     */
    public Carte piocherCarteCachee() {
        Carte carte = carteCachee;
        carteCachee = null;
        return carte;
    }

    // ==================== DÉFAUSSE ====================

    /**
     * Ajoute une carte à la défausse
     * 
     * @param carte La carte à défausser
     */
    public void ajouterDansDefausse(Carte carte) {
        if (carte != null) {
            defausse.add(carte);
        }
    }

    /**
     * Ajoute plusieurs cartes à la défausse
     * 
     * @param cartes Les cartes à défausser
     */
    public void ajouterDansDefausse(List<Carte> cartes) {
        if (cartes != null) {
            defausse.addAll(cartes);
        }
    }

    /**
     * Remet une carte sous le deck (effet de certaines cartes)
     * 
     * @param carte La carte à remettre sous le deck
     */
    public void remettreEnDessous(Carte carte) {
        if (carte != null) {
            cartes.add(carte);
        }
    }

    /**
     * Remet une carte sur le dessus du deck
     * 
     * @param carte La carte à remettre sur le deck
     */
    public void remettreEnDessus(Carte carte) {
        if (carte != null) {
            cartes.add(0, carte);
        }
    }

    // ==================== VÉRIFICATIONS ====================

    /**
     * Vérifie si le deck est vide
     */
    public boolean estVide() {
        return cartes.isEmpty();
    }

    /**
     * Retourne le nombre de cartes restantes dans le deck
     */
    public int getNombreCartesRestantes() {
        return cartes.size();
    }

    /**
     * Vérifie si une carte d'un certain type est encore dans le deck
     * 
     * @param type Le type de carte recherché
     * @return true si au moins une carte de ce type est dans le deck
     */
    public boolean contientType(TypeCarte type) {
        for (Carte carte : cartes) {
            if (carte.getType() == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compte le nombre de cartes d'un type donné restant dans le deck
     * 
     * @param type Le type de carte à compter
     * @return Le nombre de cartes de ce type
     */
    public int compterType(TypeCarte type) {
        int count = 0;
        for (Carte carte : cartes) {
            if (carte.getType() == type) {
                count++;
            }
        }
        return count;
    }

    // ==================== GETTERS ====================

    /**
     * Retourne la liste des cartes du deck (copie)
     */
    public ArrayList<Carte> getCartes() {
        return new ArrayList<>(cartes);
    }

    /**
     * Retourne la défausse
     */
    public ArrayList<Carte> getDefausse() {
        return new ArrayList<>(defausse);
    }

    /**
     * Retourne la carte cachée
     */
    public Carte getCarteCachee() {
        return carteCachee;
    }

    /**
     * Retourne les cartes visibles (partie à 2 joueurs)
     */
    public ArrayList<Carte> getCartesVisibles() {
        return new ArrayList<>(cartesVisibles);
    }

    /**
     * Retourne le nombre de cartes dans la défausse
     */
    public int getNombreCartesDefausse() {
        return defausse.size();
    }

    // ==================== ANALYSE DU JEU ====================

    /**
     * Retourne les cartes qui ont été jouées ou défaussées (information publique)
     * Combine les cartes visibles et la défausse
     */
    public ArrayList<Carte> getCartesPubliques() {
        ArrayList<Carte> cartesPubliques = new ArrayList<>();
        cartesPubliques.addAll(defausse);
        cartesPubliques.addAll(cartesVisibles);
        return cartesPubliques;
    }

    /**
     * Calcule les cartes possiblement encore en jeu
     * (utile pour l'aide au joueur ou l'IA)
     * 
     * @return Map avec le type de carte et le nombre possiblement restant
     */
    public String analyserCartesRestantes() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ANALYSE DES CARTES ===\n");

        // Compter les cartes déjà vues (défausse + visibles)
        ArrayList<Carte> cartesVues = getCartesPubliques();

        for (TypeCarte type : TypeCarte.values()) {
            int totalOriginal = compterCartesOriginales(type);
            int vues = 0;

            for (Carte carte : cartesVues) {
                if (carte.getType() == type) {
                    vues++;
                }
            }

            int restantes = totalOriginal - vues;
            if (totalOriginal > 0) {
                sb.append(String.format("%s : %d/%d possiblement en jeu\n",
                        type.name(), restantes, totalOriginal));
            }
        }

        return sb.toString();
    }

    /**
     * Retourne le nombre original de cartes d'un type dans le deck complet
     */
    private int compterCartesOriginales(TypeCarte type) {
        switch (type) {
            case GESTIONNAIRE_SEE:
                return 1;
            case LA:
                return 1;
            case DIRLO:
                return 1;
            case ANCIEN:
                return 2;
            case BUG_INFORMATIQUE:
                return 2;
            case RDE:
                return 2;
            case JURY:
                return 2;
            case TUTEUR_PEDAGOGIQUE:
                return 2;
            case EXAM:
                return 6;
            case B2_ANGLAIS:
                return 2;
            default:
                return 0;
        }
    }

    // ==================== DISTRIBUTION ====================

    /**
     * Distribue une carte à chaque joueur
     * 
     * @param joueurs La liste des joueurs
     */
    public void distribuer(ArrayList<Joueur> joueurs) {
        for (Joueur joueur : joueurs) {
            Carte carte = piocher();
            if (carte != null) {
                joueur.recevoirCarte(carte);
            }
        }
    }

    /**
     * Distribue plusieurs cartes à chaque joueur
     * 
     * @param joueurs      La liste des joueurs
     * @param nombreCartes Le nombre de cartes par joueur
     */
    public void distribuer(ArrayList<Joueur> joueurs, int nombreCartes) {
        for (int i = 0; i < nombreCartes; i++) {
            for (Joueur joueur : joueurs) {
                Carte carte = piocher();
                if (carte != null) {
                    joueur.recevoirCarte(carte);
                }
            }
        }
    }

    // ==================== AFFICHAGE ====================

    /**
     * Affiche l'état du deck
     */
    public String afficherEtat() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ÉTAT DU DECK ===\n");
        sb.append("Cartes restantes : ").append(cartes.size()).append("\n");
        sb.append("Cartes en défausse : ").append(defausse.size()).append("\n");
        sb.append("Carte cachée : ").append(carteCachee != null ? "Oui" : "Non").append("\n");

        if (!cartesVisibles.isEmpty()) {
            sb.append("Cartes visibles (2 joueurs) : ");
            for (Carte carte : cartesVisibles) {
                sb.append(carte.getNom()).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Affiche la défausse (information publique)
     */
    public String afficherDefausse() {
        if (defausse.isEmpty()) {
            return "Défausse vide";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== DÉFAUSSE ===\n");

        for (int i = 0; i < defausse.size(); i++) {
            Carte carte = defausse.get(i);
            sb.append(String.format("%d. %s (Valeur: %d)\n",
                    i + 1, carte.getNom(), carte.getValeur()));
        }

        return sb.toString();
    }

    /**
     * Affiche les cartes visibles (partie à 2 joueurs)
     */
    public String afficherCartesVisibles() {
        if (cartesVisibles.isEmpty()) {
            return "Pas de cartes visibles";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== CARTES ÉCARTÉES (VISIBLES) ===\n");

        for (Carte carte : cartesVisibles) {
            sb.append("- ").append(carte.getNom())
                    .append(" (Valeur: ").append(carte.getValeur()).append(")\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("Deck [%d cartes, %d en défausse]",
                cartes.size(), defausse.size());
    }

}
