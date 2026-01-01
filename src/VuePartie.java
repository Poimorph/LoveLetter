import java.util.List;

public interface VuePartie {

	private ControleurPartie controleurPartie;

	public abstract void afficherEtatJeu(Partie partie);

	public abstract void afficherMain(Joueur joueur);

	public abstract void afficherMessage(String message);

	public abstract int demanderChoixCarte();

	public abstract Joueur demanderCible(List<Joueur> cibles);

	public abstract int demanderDevinette();

	public abstract void afficherResultatManche(List<Joueur> vainqueurs);

	public abstract void afficherVainqueurFinal(Joueur vainqueur);

}
