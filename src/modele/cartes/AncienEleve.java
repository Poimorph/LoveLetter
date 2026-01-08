package modele.cartes;

import java.util.ArrayList;

import modele.ActionJoueur;
import modele.Carte;
import modele.Deck;
import modele.Joueur;
import modele.MainJoueur;
import modele.Manche;
import modele.TypeCarte;

/**
 * Ancien Élève (Chancelier) - Valeur 6
 * Effet : Piochez 2 cartes. Gardez 1 carte parmi les 3,
 * puis remettez les 2 autres sous le deck dans l'ordre de votre choix.
 */
public class AncienEleve extends Carte {

	private int carteGardeeIndex = 0;

	public AncienEleve() {
		super(TypeCarte.ANCIEN, 6, "Ancien Élève",
				"Piochez 2 cartes, gardez-en 1, remettez les 2 autres sous le deck.");
	}

	@Override
	public void appliquerEffet(ActionJoueur action, Manche manche) {
		Deck deck = manche.getDeck();
		Joueur joueurActif = action.getJoueur();

		// Piocher 2 cartes du deck
		ArrayList<Carte> cartesPiochees = deck.piocherMultiple(2);

		if (cartesPiochees.isEmpty()) {
			System.out.println("Pas assez de cartes dans le deck pour l'effet Ancien Élève.");
			return;
		}

		// Le joueur a maintenant sa carte en main + les 2 cartes piochées
		// Il doit choisir 1 carte à garder parmi les 3
		// Pour l'instant, on garde la carte avec la plus haute valeur (logique par
		// défaut)
		// La vraie sélection sera gérée par le contrôleur/vue

		MainJoueur mainJoueur = joueurActif.getMain();
		Carte carteEnMain = mainJoueur.getCarte(0);

		// Créer une liste de toutes les cartes disponibles
		ArrayList<Carte> toutesLesCartes = new ArrayList<>();
		if (carteEnMain != null) {
			toutesLesCartes.add(carteEnMain);
		}
		toutesLesCartes.addAll(cartesPiochees);

		System.out.println(joueurActif.getNom() + " pioche 2 cartes avec l'Ancien Élève.");
		System.out.println("Cartes disponibles : ");
		for (int i = 0; i < toutesLesCartes.size(); i++) {
			System.out.println("  [" + i + "] " + toutesLesCartes.get(i).getNom()
					+ " (Valeur: " + toutesLesCartes.get(i).getValeur() + ")");
		}

		// Garder la carte choisie (par défaut index 0, peut être changé via
		// setCarteGardeeIndex)
		if (carteGardeeIndex < 0 || carteGardeeIndex >= toutesLesCartes.size()) {
			carteGardeeIndex = 0;
		}

		Carte carteGardee = toutesLesCartes.get(carteGardeeIndex);
		toutesLesCartes.remove(carteGardeeIndex);

		// Vider la main et ajouter la carte gardée
		mainJoueur.vider();
		joueurActif.recevoirCarte(carteGardee);

		System.out.println(joueurActif.getNom() + " garde " + carteGardee.getNom());

		// Remettre les 2 autres cartes sous le deck
		for (Carte carte : toutesLesCartes) {
			deck.remettreEnDessous(carte);
			System.out.println(carte.getNom() + " est remis sous le deck.");
		}
	}

	@Override
	public boolean necessiteCible() {
		return false;
	}

	/**
	 * Définit l'index de la carte à garder (0, 1 ou 2)
	 */
	public void setCarteGardeeIndex(int index) {
		this.carteGardeeIndex = index;
	}

}
