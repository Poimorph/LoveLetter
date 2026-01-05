/**
 * Jury (Baron) - Valeur 3
 * Effet : Comparez votre carte en main avec celle d'un adversaire.
 * Le joueur avec la carte de plus faible valeur est éliminé.
 * En cas d'égalité, personne n'est éliminé.
 */
public class Jury extends Carte {

	public Jury() {
		super(TypeCarte.JURY, 3, "Jury",
			"Comparez votre main avec un adversaire. Le plus faible est éliminé.");
	}

	@Override
	public void appliquerEffet(Joueur joueurActif, Joueur cible, Manche manche) {
		if (cible == null) {
			System.out.println("Aucune cible valide.");
			return;
		}

		int valeurJoueurActif = joueurActif.getValeurMain();
		int valeurCible = cible.getValeurMain();

		System.out.println(joueurActif.getNom() + " (valeur " + valeurJoueurActif + ") vs "
			+ cible.getNom() + " (valeur " + valeurCible + ")");

		if (valeurJoueurActif > valeurCible) {
			System.out.println(cible.getNom() + " a la carte la plus faible et est éliminé !");
			manche.eliminerJoueur(cible);
		} else if (valeurCible > valeurJoueurActif) {
			System.out.println(joueurActif.getNom() + " a la carte la plus faible et est éliminé !");
			manche.eliminerJoueur(joueurActif);
		} else {
			System.out.println("Égalité ! Personne n'est éliminé.");
		}
	}

	@Override
	public boolean necessiteCible() {
		return true;
	}

}
