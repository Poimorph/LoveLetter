package model.cartes;

import model.ActionJoueur;
import model.Carte;
import model.Deck;
import model.Joueur;
import model.Manche;
import model.TypeCarte;

/**
 * Bug Informatique (Prince) - Valeur 5
 * Effet : Choisissez un joueur (vous-même ou un adversaire).
 * Ce joueur défausse sa main et pioche une nouvelle carte.
 * Si la carte défaussée est le Directeur (Princesse), ce joueur est éliminé.
 */
public class BugInformatique extends Carte {

	public BugInformatique() {
		super(TypeCarte.BUG_INFORMATIQUE, 5, "Bug Informatique",
				"Un joueur défausse sa main et pioche. Si c'est le Directeur, il est éliminé.");
	}

	@Override
	public void appliquerEffet(ActionJoueur action, Manche manche) {
		// Si pas de cible spécifiée, on cible le joueur actif
		Joueur cibleEffective = (action.getCible() != null) ? action.getCible() : action.getJoueur();

		// Récupérer la carte en main avant de la défausser
		Carte carteDefaussee = cibleEffective.defausserMain();

		if (carteDefaussee != null) {
			System.out.println(cibleEffective.getNom() + " défausse " + carteDefaussee.getNom());

			// Ajouter la carte à la défausse
			manche.getDeck().ajouterDansDefausse(carteDefaussee);

			// Si c'est la Gestionnaire SEE (Princesse), le joueur est éliminé
			if (carteDefaussee.getType() == TypeCarte.GESTIONNAIRE_SEE) {
				System.out.println(cibleEffective.getNom() + " a défaussé la Gestionnaire SEE et est éliminé !");
				manche.eliminerJoueur(cibleEffective);
				return;
			}
		}

		// Piocher une nouvelle carte
		Deck deck = manche.getDeck();
		Carte nouvelleCarte = deck.piocher();

		// Si le deck est vide, piocher la carte cachée
		if (nouvelleCarte == null) {
			nouvelleCarte = deck.piocherCarteCachee();
		}

		if (nouvelleCarte != null) {
			cibleEffective.recevoirCarte(nouvelleCarte);
			System.out.println(cibleEffective.getNom() + " pioche une nouvelle carte.");
		} else {
			System.out.println("Plus de cartes à piocher !");
		}
	}

	@Override
	public boolean necessiteCible() {
		return true;
	}

	@Override
	public boolean peutCiblerSoiMeme() {
		return true;
	}

}
