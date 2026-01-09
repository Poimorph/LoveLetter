package model.cartes;

import java.util.ArrayList;

import model.ActionJoueur;
import model.Carte;
import model.Deck;
import model.Joueur;
import model.MainJoueur;
import model.Manche;
import model.TypeCarte;

/**
 * Ancien Élève (Chancelier) - Valeur 6
 * Effet : Piochez 2 cartes. Gardez 1 carte parmi les 3,
 * puis remettez les 2 autres sous le deck dans l'ordre de votre choix.
 */
public class AncienEleve extends Carte {

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

		// Récupérer l'index choisi par le joueur via l'action
		int carteGardeeIndex = action.getCarteGardeeIndex();
		if (carteGardeeIndex < 0 || carteGardeeIndex >= toutesLesCartes.size()) {
			// Par défaut, garder la carte avec la plus haute valeur
			carteGardeeIndex = 0;
			int maxValeur = toutesLesCartes.get(0).getValeur();
			for (int i = 1; i < toutesLesCartes.size(); i++) {
				if (toutesLesCartes.get(i).getValeur() > maxValeur) {
					maxValeur = toutesLesCartes.get(i).getValeur();
					carteGardeeIndex = i;
				}
			}
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
	 * Retourne true car cette carte nécessite un choix de carte à garder
	 */
	public boolean necessiteChoixCarte() {
		return true;
	}

}
