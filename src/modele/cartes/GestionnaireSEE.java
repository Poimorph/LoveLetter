package modele.cartes;

import modele.ActionJoueur;
import modele.Carte;
import modele.Joueur;
import modele.Manche;
import modele.TypeCarte;

/**
 * Gestionnaire SEE (Princesse) - Valeur 9
 * Effet : Si vous défaussez cette carte pour quelque raison que ce soit,
 * vous êtes éliminé de la manche.
 * C'est la carte avec la plus haute valeur du jeu.
 */
public class GestionnaireSEE extends Carte {

	public GestionnaireSEE() {
		super(TypeCarte.GESTIONNAIRE_SEE, 9, "Gestionnaire SEE",
				"Si vous défaussez cette carte, vous êtes éliminé. Valeur la plus haute.");
	}

	@Override
	public void appliquerEffet(ActionJoueur action, Manche manche) {
		// Le joueur qui joue/défausse le Gestionnaire SEE est éliminé
		Joueur joueurActif = action.getJoueur();
		System.out.println(joueurActif.getNom() + " a joué le Gestionnaire SEE et est éliminé !");
		manche.eliminerJoueur(joueurActif);
	}

	@Override
	public boolean necessiteCible() {
		return false;
	}

}
