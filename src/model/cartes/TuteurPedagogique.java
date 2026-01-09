package model.cartes;

import model.ActionJoueur;
import model.Carte;
import model.Joueur;
import model.MainJoueur;
import model.Manche;
import model.TypeCarte;

/**
 * Tuteur Pédagogique (Prêtre) - Valeur 2
 * Effet : Regardez la carte en main d'un adversaire.
 */
public class TuteurPedagogique extends Carte {

	public TuteurPedagogique() {
		super(TypeCarte.TUTEUR_PEDAGOGIQUE, 2, "Tuteur Pédagogique",
				"Regardez la carte en main d'un adversaire.");
	}

	@Override
	public void appliquerEffet(ActionJoueur action, Manche manche) {
		Joueur joueurActif = action.getJoueur();
		Joueur cible = action.getCible();
		if (cible == null) {
			System.out.println("Aucune cible valide.");
			return;
		}

		// Révéler la carte de la cible au joueur actif
		MainJoueur mainCible = cible.getMain();
		if (mainCible != null && !mainCible.estVide()) {
			Carte carteCible = mainCible.getCarte(0);
			System.out.println(joueurActif.getNom() + " regarde la main de " + cible.getNom()
					+ " : " + carteCible.getNom() + " (Valeur: " + carteCible.getValeur() + ")");
		} else {
			System.out.println(cible.getNom() + " n'a pas de carte en main.");
		}
	}

	@Override
	public boolean necessiteCible() {
		return true;
	}

}
