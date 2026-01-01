public abstract class Carte {

	protected TypeCarte type;

	protected int valeur;

	protected String nom;

	protected String description;

	private Main main;

	private Deck deck;

	private TypeCarte typeCarte;

	private Manche manche;

	private Joueur joueur;

	public Carte(TypeCarte type, int valeur, String nom, String description) {

	}

	public abstract void appliquerEffet(Joueur joueurActif, Joueur cible, Manche manche);

	public abstract boolean necessiteCible();

	public boolean peutCiblerSoiMeme() {
		return false;
	}

	public int compareTo(Carte autre) {
		return 0;
	}

	public TypeCarte getType() {
		return null;
	}

	public int getValeur() {
		return 0;
	}

	public String getNom() {
		return null;
	}

	public String getDescription() {
		return null;
	}

}
