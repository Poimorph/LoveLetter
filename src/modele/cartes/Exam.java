package modele.cartes;

import modele.ActionJoueur;
import modele.Carte;
import modele.Joueur;
import modele.Manche;
import modele.TypeCarte;

/**
 * Exam (Garde) - Valeur 1
 * Effet : Devinez la carte d'un adversaire (sauf Exam).
 * Si vous devinez correctement, l'adversaire est éliminé.
 */
public class Exam extends Carte {

	private TypeCarte carteDevinee;

	public Exam() {
		super(TypeCarte.EXAM, 1, "Exam",
				"Devinez la carte d'un adversaire (sauf Exam). Si correct, il est éliminé.");
	}

	@Override
	public void appliquerEffet(ActionJoueur action, Manche manche) {
		Joueur joueurActif = action.getJoueur();
		Joueur cible = action.getCible();
		TypeCarte carteDevinee = action.getCarteDevinee();

		if (cible == null || carteDevinee == null) {
			return;
		}

		// On ne peut pas deviner Exam
		if (carteDevinee == TypeCarte.EXAM) {
			System.out.println("Vous ne pouvez pas deviner Exam !");
			return;
		}

		// Vérifier si la cible a la carte devinée
		if (cible.aCarteEnMain(carteDevinee)) {
			System.out.println(joueurActif.getNom() + " a deviné correctement ! "
					+ cible.getNom() + " avait " + carteDevinee + " et est éliminé !");
			manche.eliminerJoueur(cible);
		} else {
			System.out.println(joueurActif.getNom() + " a deviné " + carteDevinee
					+ " mais c'était incorrect.");
		}
	}

	@Override
	public boolean necessiteCible() {
		return true;
	}

	/**
	 * Définit la carte que le joueur devine
	 * 
	 * @param type Le type de carte deviné (ne peut pas être EXAM)
	 */
	public void setCarteDevinee(TypeCarte type) {
		this.carteDevinee = type;
	}

	public TypeCarte getCarteDevinee() {
		return carteDevinee;
	}

}
