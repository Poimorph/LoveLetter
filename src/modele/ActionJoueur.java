package modele;

public class ActionJoueur {
	private Joueur joueur;
	private Carte carteJouee;
	private Joueur cible;
	private TypeCarte carteDevinee;

	public ActionJoueur(Joueur joueur, Carte carteJouee, Joueur cible, TypeCarte carteDevinee) {
		this.joueur = joueur;
		this.carteJouee = carteJouee;
		this.cible = cible;
		this.carteDevinee = carteDevinee;

	}

	// ==================== GETTERS ====================
	public Joueur getJoueur() {
		return joueur;
	}

	public Carte getCarteJouee() {
		return carteJouee;
	}

	public Joueur getCible() {
		return cible;
	}

	public TypeCarte getCarteDevinee() {
		return carteDevinee;
	}

}
