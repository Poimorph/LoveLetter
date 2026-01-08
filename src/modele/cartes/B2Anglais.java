package modele.cartes;

import modele.ActionJoueur;
import modele.Carte;
import modele.Joueur;
import modele.Manche;
import modele.TypeCarte;

/**
 * B2 Anglais (Espionne) - Valeur 0
 * Effet : Aucun effet immédiat.
 * À la fin de la manche, si vous êtes le seul joueur à avoir joué/défaussé
 * cette carte ET que vous remportez la manche, gagnez un pion de faveur
 * supplémentaire.
 */
public class B2Anglais extends Carte {

	public B2Anglais() {
		super(TypeCarte.B2_ANGLAIS, 0, "B2 Anglais",
				"Aucun effet. Bonus : si vous êtes le seul à l'avoir jouée et gagnez la manche, +1 pion.");
	}

	@Override
	public void appliquerEffet(ActionJoueur action, Manche manche) {
		// Pas d'effet immédiat
		// Le joueur est marqué comme ayant joué B2 pour le bonus de fin de manche
		manche.marquerB2Joue(action.getJoueur());
		System.out.println(action.getJoueur().getNom() + " joue B2 Anglais. Aucun effet immédiat.");
	}

	@Override
	public boolean necessiteCible() {
		return false;
	}

}
