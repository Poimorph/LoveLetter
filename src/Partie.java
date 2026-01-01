import java.util.*;

public class Partie {

	private ArrayList<Joueur> joueurs;

	private Manche mancheActuelle;

	private int numeroManche;

	private EtatPartie etat;

	private int nombreJoueurs;

	private int pionsRequis;

    private ArrayList<Manche> manches;

	public Partie(int nbJoueurs) {
            joueurs = new ArrayList<>();
            numeroManche = 0;
            etat = EtatPartie.En_Cours;
            switch (nbJoueurs) {
                case 2:
                    pionsRequis = 6;
                    break;
                case 3:
                    pionsRequis = 5;
                    break;
                case 4:
                    pionsRequis = 4;
                    break;
                case 5:
                    pionsRequis = 3;
                    break;
                case 6:
                    pionsRequis = 3;
                    break;
                default:
                    System.out.println("Erreur de nombre de joueurs");
            }
            manches = new ArrayList<>();

	}

	public void initialiser(ArrayList<String> nomsJoueurs) {

            for(int i = 0; i < nombreJoueurs; i++) {
                joueurs.add(new Joueur(nomsJoueurs.get(i),new Main()));
            }
	}

	public void demarrerPartie() {
        mancheActuelle = new Manche(joueurs);
        manches.add(mancheActuelle);
        //TODO
        // reste
	}

	public void lancerNouvelleManche() {

	}

	public Joueur getJoueurActif() {
		return null;
	}

	public ArrayList<Joueur> verifierVictoireFinale() {
		boolean fin = false;
        for (Joueur joueur : joueurs)
            if (joueur.getPionsFaveur() >= pionsRequis)
                fin = true;

        return fin ? joueurs : null;
        // TODO revoir logique
	}

	public void terminerPartie() {

	}

	public void reinitialiser() {

	}

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public Manche getMancheActuelle() {
		return mancheActuelle;
	}

	public int getNumeroManche() {
		return numeroManche;
	}

	public EtatPartie getEtat() {
		return etat;
	}

	public int getNombreJoueurs() {
		return nombreJoueurs;
	}

	public int getPionsRequis() {
		return pionsRequis;
	}

}
