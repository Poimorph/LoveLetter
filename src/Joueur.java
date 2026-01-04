import java.util.*;

public class Joueur {
    private String nom;
    private Main main;
    private ArrayList<Carte> cartesJouees;
    private int pionsFaveur;
    private boolean estProtege;
    private boolean estElimine;


    public Joueur(String nom, Main main) {
        this.nom = nom;
        this.main = main;
        this.cartesJouees = new ArrayList<>();
        this.pionsFaveur = 0;
        this.estProtege = false;
        this.estElimine = false;
    }

    /**
     * Ajoute une carte à la main du joueur (pioche)
     */
    public void recevoirCarte(Carte carte) {
        if (carte != null) {
            main.ajouterCarte(carte);
        }
    }

    /**
     * Joue une carte de la main et l'ajoute aux cartes jouées
     * @param index L'index de la carte dans la main
     * @return La carte jouée
     */
    public Carte jouerCarte(int index) {
        Carte carte = main.retirerCarte(index);
        if (carte != null) {
            cartesJouees.add(carte);
            carte.appliquerEffet(this,this, Partie.getInstance().getMancheActuelle());
        }
        return carte;
    }

    /**
     * Joue une carte spécifique de la main
     * @param carte La carte à jouer
     * @return La carte jouée, ou null si non trouvée
     */
    public Carte jouerCarte(Carte carte) {
        Carte carteRetiree = main.retirerCarte(carte);
        if (carteRetiree != null) {
            cartesJouees.add(carteRetiree);
            carteRetiree.appliquerEffet(this,this, Partie.getInstance().getMancheActuelle());
        }
        return carteRetiree;
    }

    /**
     * Défausse la main du joueur (utilisé lors de l'élimination ou effet de carte)
     * @return La carte défaussée
     */
    public Carte defausserMain() {
        if (!main.estVide()) {
            Carte carte = main.retirerCarte(0);
            if (carte != null) {
                cartesJouees.add(carte);
            }
            return carte;
        }
        return null;
    }

    /**
     * Vérifie si le joueur possède une carte d'un type donné
     */
    public boolean aCarteEnMain(TypeCarte type) {
        return main.contientType(type);
    }

    /**
     * Retourne la valeur de la carte en main (pour comparaison en fin de manche)
     */
    public int getValeurMain() {
        if (main.estVide()) {
            return 0;
        }
        // On suppose que le joueur n'a qu'une carte en main à ce moment
        Carte carte = main.getCarte(0);
        return carte != null ? carte.getValeur() : 0;
    }

    /**
     * Calcule la somme des valeurs des cartes jouées (pour départager en cas d'égalité)
     */
    public int getSommeCartesJouees() {
        int somme = 0;
        for (Carte carte : cartesJouees) {
            somme += carte.getValeur();
        }
        return somme;
    }

    /**
     * Vérifie si le joueur a joué la carte B2 (pour le bonus)
     */
    public boolean aJoueB2() {
        for (Carte carte : cartesJouees) {
            if (carte.getType() == TypeCarte.B2_ANGLAIS) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si le joueur doit obligatoirement jouer LA (contrainte LA)
     * Le joueur doit jouer LA s'il a LA + (Directeur ou Bug Informatique) en main
     */
    public boolean doitJouerLA() {
        boolean aLA = aCarteEnMain(TypeCarte.LA);
        boolean aDirecteur = aCarteEnMain(TypeCarte.DIRLO);
        boolean aBugInfo = aCarteEnMain(TypeCarte.BUG_INFORMATIQUE);

        return aLA && (aDirecteur || aBugInfo);
    }

    /**
     * Retourne les cartes jouables selon les règles (contrainte LA)
     */
    public ArrayList<Carte> getCartesJouables() {
        if (doitJouerLA()) {
            // Doit jouer LA obligatoirement
            ArrayList<Carte> cartesJouables = new ArrayList<>();
            for (Carte carte : main.getCartes()) {
                if (carte.getType() == TypeCarte.LA) {
                    cartesJouables.add(carte);
                }
            }
            return cartesJouables;
        }
        // Sinon, toutes les cartes sont jouables
        return new ArrayList<>(main.getCartes());
    }

    // ==================== GESTION DE L'ÉTAT ====================

    /**
     * Élimine le joueur de la manche
     */
    public void eliminer() {
        this.estElimine = true;
        this.estProtege = false;
        // Défausser la main face visible
        defausserMain();
    }

    /**
     * Active la protection du joueur (effet Ancien Élève)
     */
    public void activerProtection() {
        this.estProtege = true;
    }

    /**
     * Désactive la protection du joueur (au début de son tour)
     */
    public void leverProtection() {
        this.estProtege = false;
    }

    /**
     * Ajoute un pion de faveur au joueur
     */
    public void ajouterPion() {
        this.pionsFaveur++;
    }

    /**
     * Ajoute plusieurs pions de faveur
     */
    public void ajouterPions(int nombre) {
        this.pionsFaveur += nombre;
    }

    /**
     * Réinitialise l'état du joueur pour une nouvelle manche
     */
    public void reinitialiser() {
        this.main.vider();
        this.cartesJouees.clear();
        this.estProtege = false;
        this.estElimine = false;
    }

    /**
     * Réinitialise complètement le joueur (nouvelle partie)
     */
    public void reinitialiserComplet() {
        reinitialiser();
        this.pionsFaveur = 0;
    }

    // ==================== ÉCHANGE DE CARTES ====================

    /**
     * Échange sa main avec un autre joueur (effet Jury)
     */
    public void echangerMain(Joueur autreJoueur) {
        Main tempMain = this.main;
        this.main = autreJoueur.main;
        autreJoueur.main = tempMain;
    }

    /**
     * Remplace la main actuelle par une nouvelle carte (effet Directeur)
     * @param nouvelleCarte La nouvelle carte à mettre en main
     * @return L'ancienne carte qui était en main
     */
    public Carte remplacerMain(Carte nouvelleCarte) {
        Carte ancienneCarte = null;
        if (!main.estVide()) {
            ancienneCarte = main.retirerCarte(0);
        }
        if (nouvelleCarte != null) {
            main.ajouterCarte(nouvelleCarte);
        }
        return ancienneCarte;
    }

    // ==================== VÉRIFICATIONS ====================

    /**
     * Vérifie si le joueur peut être ciblé par un effet
     */
    public boolean estCiblable() {
        return !estElimine && !estProtege;
    }

    /**
     * Vérifie si le joueur est encore en jeu
     */
    public boolean estEnJeu() {
        return !estElimine;
    }

    /**
     * Vérifie si la main du joueur est complète (2 cartes)
     */
    public boolean aMainComplete() {
        return main.getNombreCartes() == Main.TAILLE_MAX;
    }


	public String getNom() {
		return null;
	}

	public Main getMain() {
		return null;
	}

	public List<Carte> getCartesJouees() {
		return null;
	}

	public int getPionsFaveur() {
		return 0;
	}

	public boolean isProtege() {
		return false;
	}

	public boolean isElimine() {
		return false;
	}


// ==================== AFFICHAGE ====================

    /**
     * Retourne une représentation des cartes jouées (visibles par tous)
     */
    public String afficherCartesJouees() {
        if (cartesJouees.isEmpty()) {
            return nom + " n'a pas encore joué de cartes.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Cartes jouées par ").append(nom).append(" : ");
        for (int i = 0; i < cartesJouees.size(); i++) {
            sb.append(cartesJouees.get(i).getNom());
            if (i < cartesJouees.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * Retourne le statut actuel du joueur
     */
    public String getStatut() {
        if (estElimine) {
            return "Éliminé";
        } else if (estProtege) {
            return "Protégé";
        } else {
            return "En jeu";
        }
    }

    @Override
    public String toString() {
        return String.format("%s [Pions: %d, Statut: %s]", nom, pionsFaveur, getStatut());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Joueur joueur = (Joueur) obj;
        return nom.equals(joueur.nom);
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}
