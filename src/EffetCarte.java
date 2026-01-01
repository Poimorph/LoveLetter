public interface EffetCarte {

	public abstract void appliquerEffet(Joueur joueurActif, Joueur cible, Manche manche);

	public abstract boolean necessiteCible();

	public abstract boolean peutCiblerSoiMeme();

	public abstract List<Joueur> getCiblesValides(Joueur joueurActif, List<Joueur> joueurs);

}
