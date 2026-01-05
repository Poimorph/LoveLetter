/**
 * Learning Agreement (Comtesse) - Valeur 7
 * Effet : Aucun effet immédiat.
 * CONTRAINTE : Si vous avez le Roi (Directeur) ou le Prince (Bug Informatique)
 * en main avec cette carte, vous DEVEZ jouer le Learning Agreement.
 */
public class LearningAgreement extends Carte {

	public LearningAgreement() {
		super(TypeCarte.LA, 7, "Learning Agreement",
			"Doit être jouée si vous avez Directeur ou Bug Informatique en main.");
	}

	@Override
	public void appliquerEffet(Joueur joueurActif, Joueur cible, Manche manche) {
		// Pas d'effet immédiat
		System.out.println(joueurActif.getNom() + " joue le Learning Agreement. Aucun effet.");
	}

	@Override
	public boolean necessiteCible() {
		return false;
	}

	/**
	 * Vérifie si le joueur est obligé de jouer cette carte
	 * @param main La main du joueur
	 * @return true si le joueur doit obligatoirement jouer LA
	 */
	public boolean estObligatoire(Main main) {
		// LA est obligatoire si le joueur a aussi Directeur (Roi) ou Bug Informatique (Prince)
		return main.contientType(TypeCarte.DIRLO) || main.contientType(TypeCarte.BUG_INFORMATIQUE);
	}

}
