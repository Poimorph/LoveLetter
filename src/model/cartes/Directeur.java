package model.cartes;

import model.ActionJoueur;
import model.Carte;
import model.Joueur;
import model.Manche;
import model.TypeCarte;

/**
 * Directeur (Roi) - Valeur 7
 * Effet : Échangez votre main avec celle d'un adversaire.
 */
public class Directeur extends Carte {

	public Directeur() {
		super(TypeCarte.DIRLO, 7, "Directeur",
				"Échangez votre main avec celle d'un adversaire.");
	}

	@Override
	public void appliquerEffet(ActionJoueur action, Manche manche) {
		Joueur joueurActif = action.getJoueur();
		Joueur cible = action.getCible();
		if (cible == null) {
			System.out.println("Aucune cible valide pour l'échange.");
			return;
		}

		// Échanger les mains
		joueurActif.echangerMain(cible);
		System.out.println(joueurActif.getNom() + " échange sa main avec " + cible.getNom() + ".");
	}

	@Override
	public boolean necessiteCible() {
		return true;
	}

}
