package model.cartes;

import model.ActionJoueur;
import model.Carte;
import model.Joueur;
import model.Manche;
import model.TypeCarte;

/**
 * Règlement des Études (Servante) - Valeur 4
 * Effet : Vous êtes protégé jusqu'au début de votre prochain tour.
 * Les autres joueurs ne peuvent pas vous cibler.
 */
public class ReglementDesEtudes extends Carte {

	public ReglementDesEtudes() {
		super(TypeCarte.RDE, 4, "Règlement des Études",
				"Vous êtes protégé jusqu'au début de votre prochain tour.");
	}

	@Override
	public void appliquerEffet(ActionJoueur action, Manche manche) {
		// Activer la protection du joueur
		Joueur joueurActif = action.getJoueur();
		joueurActif.activerProtection();
		System.out.println(joueurActif.getNom() + " est maintenant protégé jusqu'à son prochain tour.");
	}

	@Override
	public boolean necessiteCible() {
		return false;
	}

}
