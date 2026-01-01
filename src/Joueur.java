import java.util.Collection;

public class Joueur {

	private String nom;

	private Main main;

	private List<Carte> cartesJouees;

	private int piosFaveur;

	private boolean estProtege;

	private boolean estElimine;

    public Joueur(String nom, Main main) {}

	public String getNom() {
		return null;
	}

	public Main getMain() {
		return null;
	}

	public List<Carte> getCartesJouees() {
		return null;
	}

	public int getPionsFaveur() {
		return 0;
	}

	public boolean isProtege() {
		return false;
	}

	public boolean isElimine() {
		return false;
	}

	public int getValeurMain() {
		return 0;
	}

	public boolean aCarteEnMain(TypeCarte type) {
		return false;
	}

}
