package model;

import java.util.*;

public class MainJoueur {

    public static final int TAILLE_MAX = 2;
    private ArrayList<Carte> cartes;

    public MainJoueur() {
        this.cartes = new ArrayList<>();
    }

    // ==================== GESTION DES CARTES ====================

    /**
     * Ajoute une carte à la main
     * 
     * @param carte La carte à ajouter
     * @return true si la carte a été ajoutée, false si la main est pleine
     */
    public boolean ajouterCarte(Carte carte) {
        if (carte == null) {
            return false;
        }
        if (cartes.size() >= TAILLE_MAX) {
            System.out.println("Impossible d'ajouter une carte : main pleine");
            return false;
        }
        cartes.add(carte);
        return true;
    }


    /**
     * Retire une carte spécifique de la main
     * 
     * @param carte La carte à retirer
     * @return La carte retirée, ou null si non trouvée
     */
    public Carte retirerCarte(Carte carte) {
        if (carte == null) {
            return null;
        }
        int index = cartes.indexOf(carte);
        if (index != -1) {
            return cartes.remove(index);
        }
        return null;
    }

    /**
     * Retire une carte de la main par son index
     * 
     * @param index L'index de la carte à retirer
     * @return La carte retirée, ou null si l'index est invalide
     */
    public Carte retirerCarte(int index) {
        if (index < 0 || index >= cartes.size()) {
            return null;
        }
        return cartes.remove(index);
    }

    /**
     * Retire une carte par son type
     * 
     * @param type Le type de carte à retirer
     * @return La carte retirée, ou null si non trouvée
     */
    public Carte retirerCarteParType(TypeCarte type) {
        for (int i = 0; i < cartes.size(); i++) {
            if (cartes.get(i).getType() == type) {
                return cartes.remove(i);
            }
        }
        return null;
    }

    /**
     * Récupère une carte par son index sans la retirer
     * 
     * @param index L'index de la carte
     * @return La carte, ou null si l'index est invalide
     */
    public Carte getCarte(int index) {
        if (index < 0 || index >= cartes.size()) {
            return null;
        }
        return cartes.get(index);
    }

    /**
     * Récupère une carte par son type sans la retirer
     * 
     * @param type Le type de carte recherché
     * @return La carte, ou null si non trouvée
     */
    public Carte getCarteParType(TypeCarte type) {
        for (Carte carte : cartes) {
            if (carte.getType() == type) {
                return carte;
            }
        }
        return null;
    }

    /**
     * Retourne une copie de la liste des cartes en main
     */
    public ArrayList<Carte> getCartes() {
        return new ArrayList<>(cartes);
    }

    /**
     * Vide complètement la main
     */
    public void vider() {
        cartes.clear();
    }

    // ==================== VÉRIFICATIONS ====================

    /**
     * Vérifie si la main contient une carte d'un type donné
     * 
     * @param type Le type de carte recherché
     * @return true si la main contient ce type de carte
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
     * Vérifie si la main contient une carte d'une valeur donnée
     * 
     * @param valeur La valeur recherchée
     * @return true si la main contient une carte de cette valeur
     */
    public boolean contientValeur(int valeur) {
        for (Carte carte : cartes) {
            if (carte.getValeur() == valeur) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si la main est vide
     */
    public boolean estVide() {
        return cartes.isEmpty();
    }

    /**
     * Vérifie si la main est complète (2 cartes)
     */
    public boolean estComplete() {
        return cartes.size() >= TAILLE_MAX;
    }

    /**
     * Retourne le nombre de cartes en main
     */
    public int getNombreCartes() {
        return cartes.size();
    }



    // ==================== ÉCHANGES ====================

    /**
     * Échange le contenu de cette main avec une autre main
     * 
     * @param autreMain La main avec laquelle échanger
     */
    public void echangerAvec(MainJoueur autreMain) {
        if (autreMain == null) {
            return;
        }
        ArrayList<Carte> temp = this.cartes;
        this.cartes = autreMain.cartes;
        autreMain.cartes = temp;
    }

    // ==================== CALCULS ====================

    /**
     * Retourne la valeur maximale des cartes en main
     */
    public int getValeurMax() {
        int max = 0;
        for (Carte carte : cartes) {
            if (carte.getValeur() > max) {
                max = carte.getValeur();
            }
        }
        return max;
    }

    /**
     * Retourne la somme des valeurs des cartes en main
     */
    public int getSommeValeurs() {
        int somme = 0;
        for (Carte carte : cartes) {
            somme += carte.getValeur();
        }
        return somme;
    }

    /**
     * Retourne la carte avec la plus haute valeur
     */
    public Carte getCarteMaxValeur() {
        Carte carteMax = null;
        int valeurMax = -1;
        for (Carte carte : cartes) {
            if (carte.getValeur() > valeurMax) {
                valeurMax = carte.getValeur();
                carteMax = carte;
            }
        }
        return carteMax;
    }

    // ==================== CONTRAINTE LA ====================

    /**
     * Vérifie si le joueur doit obligatoirement jouer LA
     * (LA + Directeur ou LA + Bug Informatique en main)
     */
    public boolean doitJouerLA() {
        boolean aLA = contientType(TypeCarte.LA);
        boolean aDirecteur = contientType(TypeCarte.DIRLO);
        boolean aBugInfo = contientType(TypeCarte.BUG_INFORMATIQUE);

        return aLA && (aDirecteur || aBugInfo);
    }

    /**
     * Retourne les indices des cartes jouables selon la contrainte LA
     */
    public ArrayList<Integer> getIndicesCartesJouables() {
        ArrayList<Integer> indices = new ArrayList<>();

        if (doitJouerLA()) {
            // Seule LA est jouable
            for (int i = 0; i < cartes.size(); i++) {
                if (cartes.get(i).getType() == TypeCarte.LA) {
                    indices.add(i);
                }
            }
        } else {
            // Toutes les cartes sont jouables
            for (int i = 0; i < cartes.size(); i++) {
                indices.add(i);
            }
        }

        return indices;
    }

    /**
     * Retourne les noms des cartes jouables selon la contrainte LA
     */
    public ArrayList<String> getNomsCartesJouables() {
        ArrayList<String> noms = new ArrayList<>();
        ArrayList<Carte> cartesJouables = getCartesJouables();
        for (Carte carte : cartesJouables) {
            noms.add(carte.getNom());
        }
        
        return noms;
    }

    /**
     * Retourne les cartes jouables selon la contrainte LA
     */
    public ArrayList<Carte> getCartesJouables() {
        ArrayList<Carte> cartesJouables = new ArrayList<>();

        if (doitJouerLA()) {
            // Seule LA est jouable
            for (Carte carte : cartes) {
                if (carte.getType() == TypeCarte.LA) {
                    cartesJouables.add(carte);
                }
            }
        } else {
            // Toutes les cartes sont jouables
            cartesJouables.addAll(cartes);
        }

        return cartesJouables;
    }

    // ==================== AFFICHAGE ====================

    /**
     * Affiche le contenu de la main (pour le joueur propriétaire)
     */
    public String afficher() {
        if (cartes.isEmpty()) {
            return "Main vide";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Main (").append(cartes.size()).append("/").append(TAILLE_MAX).append(") : ");

        for (int i = 0; i < cartes.size(); i++) {
            Carte carte = cartes.get(i);
            sb.append("[").append(i).append("] ");
            sb.append(carte.getNom());
            sb.append(" (").append(carte.getValeur()).append(")");

            if (i < cartes.size() - 1) {
                sb.append(" | ");
            }
        }

        return sb.toString();
    }

    /**
     * Affiche le contenu de la main avec les descriptions des cartes
     */
    public String afficherDetaille() {
        if (cartes.isEmpty()) {
            return "Main vide";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== VOTRE MAIN ===\n");

        for (int i = 0; i < cartes.size(); i++) {
            Carte carte = cartes.get(i);
            sb.append("[").append(i).append("] ");
            sb.append(carte.getNom());
            sb.append(" (Valeur: ").append(carte.getValeur()).append(")\n");
            sb.append("    → ").append(carte.getDescription()).append("\n");
        }

        // Indication si contrainte LA
        if (doitJouerLA()) {
            sb.append("\nCONTRAINTE : Vous devez jouer LA !");
        }

        return sb.toString();
    }

    /**
     * Affiche la main de façon cachée (pour les adversaires)
     */
    public String afficherCache() {
        return "Main : " + cartes.size() + " carte(s)";
    }

    @Override
    public String toString() {
        return afficher();
    }

}
