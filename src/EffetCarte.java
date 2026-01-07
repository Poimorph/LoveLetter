import java.util.ArrayList;

public interface EffetCarte {

	public abstract void appliquerEffet(Joueur joueurActif, Joueur cible, Manche manche);

	public abstract boolean necessiteCible();

	public abstract boolean peutCiblerSoiMeme();

	public abstract ArrayList<Joueur> getCiblesValides(Joueur joueurActif, ArrayList<Joueur> joueurs);

}
