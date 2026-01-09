package model;

public class ActionJoueur {
	private Joueur joueur;
	private Carte carteJouee;
	private Joueur cible;
	private TypeCarte carteDevinee;
	private int carteGardeeIndex;

	public ActionJoueur(Joueur joueur, Carte carteJouee, Joueur cible, TypeCarte carteDevinee) {
		this.joueur = joueur;
		this.carteJouee = carteJouee;
		this.cible = cible;
		this.carteDevinee = carteDevinee;
		this.carteGardeeIndex = -1;
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

	public int getCarteGardeeIndex() {
		return carteGardeeIndex;
	}

	public void setCarteGardeeIndex(int index) {
		this.carteGardeeIndex = index;
	}

}
