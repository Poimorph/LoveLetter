package model;

public abstract class Carte implements Comparable<Carte> {

	protected TypeCarte type;
	protected int valeur;
	protected String nom;
	protected String description;

	public Carte(TypeCarte type, int valeur, String nom, String description) {
		this.type = type;
		this.valeur = valeur;
		this.nom = nom;
		this.description = description;
	}

	/**
	 * Applique l'effet de la carte
	 * 
	 * @param joueurActif Le joueur qui joue la carte
	 * @param cible       Le joueur ciblé (peut être null si pas de cible)
	 * @param manche      La manche en cours
	 */
	public abstract void appliquerEffet(ActionJoueur action, Manche manche);

	/**
	 * Indique si la carte nécessite de choisir une cible
	 */
	public abstract boolean necessiteCible();

	/**
	 * Indique si le joueur peut se cibler lui-même avec cette carte
	 */
	public boolean peutCiblerSoiMeme() {
		return false;
	}

	@Override
	public int compareTo(Carte autre) {
		return Integer.compare(this.valeur, autre.valeur);
	}

	// ==================== GETTERS ====================

	public TypeCarte getType() {
		return type;
	}

	public int getValeur() {
		return valeur;
	}

	public String getNom() {
		return nom;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return String.format("%s (Valeur: %d)", nom, valeur);
	}

}
