import java.util.*;

/**
 * Représente une manche du jeu Love Letter.
 * Une manche se termine quand il ne reste qu'un joueur ou que le deck est vide.
 */
public class Manche {

	private Deck deck;
	private ArrayList<Joueur> joueursActifs;
	private ArrayList<Joueur> tousLesJoueurs;
	private int joueurActifIndex;
	private ArrayList<Joueur> joueursAyantJoueB2;
	private boolean estTerminee;
	private Joueur premierJoueur;
	private ArrayList<Joueur> vainqueurs;

	/**
	 * Constructeur avec liste de joueurs
	 * @param joueurs La liste des joueurs participant à la manche
	 */
	public Manche(ArrayList<Joueur> joueurs) {
		this.tousLesJoueurs = new ArrayList<>(joueurs);
		this.joueursActifs = new ArrayList<>(joueurs);
		this.deck = new Deck();
		this.joueursAyantJoueB2 = new ArrayList<>();
		this.estTerminee = false;
		this.joueurActifIndex = 0;
		this.vainqueurs = new ArrayList<>();
	}

	/**
	 * Constructeur avec liste de joueurs et premier joueur spécifié
	 * @param joueurs La liste des joueurs participant à la manche
	 * @param premierJoueur Le joueur qui commence la manche
	 */
	public Manche(ArrayList<Joueur> joueurs, Joueur premierJoueur) {
		this(joueurs);
		this.premierJoueur = premierJoueur;
		// Trouver l'index du premier joueur
		for (int i = 0; i < joueursActifs.size(); i++) {
			if (joueursActifs.get(i).equals(premierJoueur)) {
				this.joueurActifIndex = i;
				break;
			}
		}
	}

	// ==================== INITIALISATION ====================

	/**
	 * Initialise la manche : prépare le deck et distribue les cartes
	 */
	public void initialiser() {
		// Réinitialiser l'état des joueurs
		for (Joueur joueur : tousLesJoueurs) {
			joueur.reinitialiser();
		}

		// Réinitialiser les listes
		joueursActifs = new ArrayList<>(tousLesJoueurs);
		joueursAyantJoueB2.clear();
		estTerminee = false;
		vainqueurs.clear();

		// Préparer le deck selon le nombre de joueurs
		deck.preparerPourManche(joueursActifs.size());

		// Distribuer une carte à chaque joueur
		deck.distribuer(joueursActifs);

		System.out.println("=== NOUVELLE MANCHE ===");
		System.out.println("Nombre de joueurs : " + joueursActifs.size());
		System.out.println("Premier joueur : " + getJoueurActif().getNom());
	}

	// ==================== DÉROULEMENT DU JEU ====================

	/**
	 * Exécute le tour du joueur actif
	 */
	public void jouerTour() {
		if (estTerminee) {
			System.out.println("La manche est terminée.");
			return;
		}

		Joueur joueurActif = getJoueurActif();
		if (joueurActif == null || joueurActif.isElimine()) {
			passerAuJoueurSuivant();
			return;
		}

		System.out.println("\n--- Tour de " + joueurActif.getNom() + " ---");

		// Lever la protection du joueur au début de son tour
		joueurActif.leverProtection();

		// Le joueur pioche une carte
		Carte cartePiochee = deck.piocher();
		if (cartePiochee != null) {
			joueurActif.recevoirCarte(cartePiochee);
			System.out.println(joueurActif.getNom() + " pioche une carte.");
		} else {
			System.out.println("Le deck est vide !");
		}

		// Vérifier si la manche est terminée après la pioche
		if (verifierFinManche()) {
			terminerManche();
		}
	}

	/**
	 * Fait jouer une carte au joueur actif
	 * @param indexCarte L'index de la carte à jouer dans la main
	 * @param cible Le joueur ciblé (peut être null)
	 */
	public void jouerCarte(int indexCarte, Joueur cible) {
		Joueur joueurActif = getJoueurActif();
		if (joueurActif == null) return;

		Main main = joueurActif.getMain();
		if (main == null || indexCarte < 0 || indexCarte >= main.getNombreCartes()) {
			System.out.println("Index de carte invalide.");
			return;
		}

		// Vérifier la contrainte LA
		ArrayList<Integer> indicesJouables = main.getIndicesCartesJouables();
		if (!indicesJouables.contains(indexCarte)) {
			System.out.println("Vous devez jouer le Learning Agreement !");
			return;
		}

		// Retirer et jouer la carte
		Carte carte = main.retirerCarte(indexCarte);
		if (carte != null) {
			System.out.println(joueurActif.getNom() + " joue " + carte.getNom());

			// Ajouter aux cartes jouées du joueur
			joueurActif.getCartesJouees().add(carte);

			// Ajouter à la défausse
			deck.ajouterDansDefausse(carte);

			// Appliquer l'effet
			carte.appliquerEffet(joueurActif, cible, this);
		}

		// Vérifier si la manche est terminée
		if (verifierFinManche()) {
			terminerManche();
		} else {
			passerAuJoueurSuivant();
		}
	}

	/**
	 * Passe au joueur suivant
	 */
	public void passerAuJoueurSuivant() {
		if (joueursActifs.isEmpty()) return;

		int tentatives = 0;
		do {
			joueurActifIndex = (joueurActifIndex + 1) % joueursActifs.size();
			tentatives++;
		} while (joueursActifs.get(joueurActifIndex).isElimine() && tentatives < joueursActifs.size());

		System.out.println("C'est au tour de " + getJoueurActif().getNom());
	}

	// ==================== GESTION DES JOUEURS ====================

	/**
	 * Élimine un joueur de la manche
	 * @param joueur Le joueur à éliminer
	 */
	public void eliminerJoueur(Joueur joueur) {
		if (joueur == null || joueur.isElimine()) return;

		System.out.println(joueur.getNom() + " est éliminé de la manche !");
		joueur.eliminer();

		// Vérifier si la manche est terminée (un seul joueur restant)
		if (verifierFinManche()) {
			terminerManche();
		}
	}

	/**
	 * Marque qu'un joueur a joué la carte B2 Anglais
	 * @param joueur Le joueur qui a joué B2
	 */
	public void marquerB2Joue(Joueur joueur) {
		if (joueur != null && !joueursAyantJoueB2.contains(joueur)) {
			joueursAyantJoueB2.add(joueur);
		}
	}

	/**
	 * Ancienne signature pour compatibilité
	 * @deprecated Utiliser marquerB2Joue(Joueur) à la place
	 */
	public Joueur marquerB2Joue(int joueurIndex) {
		if (joueurIndex >= 0 && joueurIndex < joueursActifs.size()) {
			Joueur joueur = joueursActifs.get(joueurIndex);
			marquerB2Joue(joueur);
			return joueur;
		}
		return null;
	}

	/**
	 * Retourne la liste des joueurs qui peuvent être ciblés par le joueur actif
	 * @param joueurActif Le joueur qui veut cibler
	 * @return Liste des joueurs ciblables
	 */
	public ArrayList<Joueur> getJoueursCiblables(Joueur joueurActif) {
		ArrayList<Joueur> ciblables = new ArrayList<>();

		for (Joueur joueur : joueursActifs) {
			// Un joueur est ciblable s'il n'est pas éliminé, n'est pas protégé,
			// et n'est pas le joueur actif (sauf pour certaines cartes)
			if (!joueur.isElimine() && !joueur.isProtege() && !joueur.equals(joueurActif)) {
				ciblables.add(joueur);
			}
		}

		return ciblables;
	}

	/**
	 * Retourne la liste des joueurs ciblables incluant soi-même
	 * @param joueurActif Le joueur qui veut cibler
	 * @return Liste des joueurs ciblables (incluant soi-même)
	 */
	public ArrayList<Joueur> getJoueursCiblablesAvecSoiMeme(Joueur joueurActif) {
		ArrayList<Joueur> ciblables = getJoueursCiblables(joueurActif);

		// Ajouter le joueur actif s'il n'est pas déjà dans la liste
		if (!joueurActif.isElimine()) {
			ciblables.add(joueurActif);
		}

		return ciblables;
	}

	// ==================== FIN DE MANCHE ====================

	/**
	 * Vérifie si la manche doit se terminer
	 * @return true si la manche est terminée
	 */
	public boolean verifierFinManche() {
		// Compter les joueurs encore en jeu
		int joueursEnJeu = 0;
		for (Joueur joueur : joueursActifs) {
			if (!joueur.isElimine()) {
				joueursEnJeu++;
			}
		}

		// La manche se termine si :
		// 1. Il ne reste qu'un seul joueur en jeu
		// 2. Le deck est vide
		return joueursEnJeu <= 1 || deck.estVide();
	}

	/**
	 * Termine la manche et détermine le(s) vainqueur(s)
	 */
	private void terminerManche() {
		if (estTerminee) return;

		estTerminee = true;
		System.out.println("\n=== FIN DE LA MANCHE ===");

		// Déterminer les vainqueurs
		vainqueurs = determinerVainqueurs();

		if (vainqueurs.isEmpty()) {
			System.out.println("Aucun vainqueur !");
		} else if (vainqueurs.size() == 1) {
			System.out.println("Vainqueur : " + vainqueurs.get(0).getNom());
		} else {
			System.out.print("Vainqueurs (égalité) : ");
			for (int i = 0; i < vainqueurs.size(); i++) {
				System.out.print(vainqueurs.get(i).getNom());
				if (i < vainqueurs.size() - 1) System.out.print(", ");
			}
			System.out.println();
		}

		// Attribuer les pions de faveur
		attribuerPionsFaveur();

		// Vérifier le bonus B2
		Joueur bonusB2 = verifierBonusB2();
		if (bonusB2 != null) {
			System.out.println(bonusB2.getNom() + " gagne un pion bonus grâce au B2 Anglais !");
		}
	}

	/**
	 * Détermine le(s) vainqueur(s) de la manche
	 * @return Liste des vainqueurs
	 */
	public ArrayList<Joueur> determinerVainqueurs() {
		ArrayList<Joueur> vainqueursPotentiels = new ArrayList<>();

		// Récupérer tous les joueurs encore en jeu
		for (Joueur joueur : joueursActifs) {
			if (!joueur.isElimine()) {
				vainqueursPotentiels.add(joueur);
			}
		}

		// Si un seul joueur reste, il gagne
		if (vainqueursPotentiels.size() == 1) {
			return vainqueursPotentiels;
		}

		// Si le deck est vide, comparer les valeurs des cartes en main
		if (vainqueursPotentiels.size() > 1) {
			// Trouver la valeur maximale
			int valeurMax = 0;
			for (Joueur joueur : vainqueursPotentiels) {
				int valeur = joueur.getValeurMain();
				if (valeur > valeurMax) {
					valeurMax = valeur;
				}
			}

			// Garder seulement les joueurs avec la valeur max
			ArrayList<Joueur> joueursValeurMax = new ArrayList<>();
			for (Joueur joueur : vainqueursPotentiels) {
				if (joueur.getValeurMain() == valeurMax) {
					joueursValeurMax.add(joueur);
				}
			}

			// En cas d'égalité, comparer la somme des cartes jouées
			if (joueursValeurMax.size() > 1) {
				int sommeMax = 0;
				for (Joueur joueur : joueursValeurMax) {
					int somme = joueur.getSommeCartesJouees();
					if (somme > sommeMax) {
						sommeMax = somme;
					}
				}

				ArrayList<Joueur> vainqueursFinaux = new ArrayList<>();
				for (Joueur joueur : joueursValeurMax) {
					if (joueur.getSommeCartesJouees() == sommeMax) {
						vainqueursFinaux.add(joueur);
					}
				}
				return vainqueursFinaux;
			}

			return joueursValeurMax;
		}

		return vainqueursPotentiels;
	}

	/**
	 * Attribue les pions de faveur aux vainqueurs
	 */
	public void attribuerPionsFaveur() {
		for (Joueur vainqueur : vainqueurs) {
			vainqueur.ajouterPion();
			System.out.println(vainqueur.getNom() + " gagne 1 pion de faveur !");
		}
	}

	/**
	 * Vérifie et attribue le bonus B2 Anglais
	 * Le bonus est attribué si un seul joueur parmi les vainqueurs a joué B2
	 * @return Le joueur qui reçoit le bonus, ou null
	 */
	public Joueur verifierBonusB2() {
		// Trouver les vainqueurs qui ont joué B2
		ArrayList<Joueur> vainqueursAvecB2 = new ArrayList<>();
		for (Joueur vainqueur : vainqueurs) {
			if (joueursAyantJoueB2.contains(vainqueur)) {
				vainqueursAvecB2.add(vainqueur);
			}
		}

		// Le bonus n'est attribué que si un seul vainqueur a joué B2
		if (vainqueursAvecB2.size() == 1) {
			Joueur joueurBonus = vainqueursAvecB2.get(0);
			joueurBonus.ajouterPion();
			return joueurBonus;
		}

		return null;
	}

	// ==================== GETTERS ====================

	/**
	 * Retourne la liste des joueurs encore actifs (non éliminés)
	 */
	public ArrayList<Joueur> getJoueursActifs() {
		ArrayList<Joueur> actifs = new ArrayList<>();
		for (Joueur joueur : joueursActifs) {
			if (!joueur.isElimine()) {
				actifs.add(joueur);
			}
		}
		return actifs;
	}

	/**
	 * Retourne le joueur dont c'est le tour
	 */
	public Joueur getJoueurActif() {
		if (joueursActifs.isEmpty()) return null;
		if (joueurActifIndex < 0 || joueurActifIndex >= joueursActifs.size()) {
			joueurActifIndex = 0;
		}
		return joueursActifs.get(joueurActifIndex);
	}

	/**
	 * Retourne le deck de la manche
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * Retourne la carte cachée
	 */
	public Carte getCarteCachee() {
		return deck.getCarteCachee();
	}

	/**
	 * Retourne les cartes visibles (parties à 2 joueurs)
	 */
	public ArrayList<Carte> getCartesVisibles() {
		return deck.getCartesVisibles();
	}

	/**
	 * Vérifie si la manche est terminée
	 */
	public boolean isTerminee() {
		return estTerminee;
	}

	/**
	 * Retourne les vainqueurs de la manche
	 */
	public ArrayList<Joueur> getVainqueurs() {
		return vainqueurs;
	}

	/**
	 * Retourne tous les joueurs de la manche
	 */
	public ArrayList<Joueur> getTousLesJoueurs() {
		return tousLesJoueurs;
	}

	/**
	 * Retourne les joueurs ayant joué B2 Anglais
	 */
	public ArrayList<Joueur> getJoueursAyantJoueB2() {
		return joueursAyantJoueB2;
	}

	// ==================== AFFICHAGE ====================

	/**
	 * Affiche l'état actuel de la manche
	 */
	public String afficherEtat() {
		StringBuilder sb = new StringBuilder();
		sb.append("=== ÉTAT DE LA MANCHE ===\n");
		sb.append("Joueur actif : ").append(getJoueurActif().getNom()).append("\n");
		sb.append("Cartes dans le deck : ").append(deck.getNombreCartesRestantes()).append("\n");
		sb.append("\nJoueurs :\n");

		for (Joueur joueur : joueursActifs) {
			sb.append("  - ").append(joueur.getNom());
			sb.append(" [").append(joueur.getStatut()).append("]");
			if (joueur.equals(getJoueurActif())) {
				sb.append(" <- Tour actuel");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	@Override
	public String toString() {
		return String.format("Manche [%d joueurs actifs, %d cartes restantes, %s]",
			getJoueursActifs().size(),
			deck.getNombreCartesRestantes(),
			estTerminee ? "Terminée" : "En cours");
	}

}