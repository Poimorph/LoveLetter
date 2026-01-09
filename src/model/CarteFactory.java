package model;

import java.util.ArrayList;

import model.cartes.AncienEleve;
import model.cartes.B2Anglais;
import model.cartes.BugInformatique;
import model.cartes.Directeur;
import model.cartes.Exam;
import model.cartes.GestionnaireSEE;
import model.cartes.Jury;
import model.cartes.LearningAgreement;
import model.cartes.ReglementDesEtudes;
import model.cartes.TuteurPedagogique;

public class CarteFactory {

	/**
	 * Crée une carte du type spécifié
	 * 
	 * @param type Le type de carte à créer
	 * @return La carte créée
	 */
	public static Carte creerCarte(TypeCarte type) {
		switch (type) {
			case B2_ANGLAIS:
				return new B2Anglais();
			case EXAM:
				return new Exam();
			case TUTEUR_PEDAGOGIQUE:
				return new TuteurPedagogique();
			case JURY:
				return new Jury();
			case RDE:
				return new ReglementDesEtudes();
			case BUG_INFORMATIQUE:
				return new BugInformatique();
			case ANCIEN:
				return new AncienEleve();
			case LA:
				return new LearningAgreement();
			case DIRLO:
				return new Directeur();
			case GESTIONNAIRE_SEE:
				return new GestionnaireSEE();
			default:
				throw new IllegalArgumentException("Type de carte inconnu : " + type);
		}
	}

	/**
	 * Crée un deck complet de 21 cartes selon les règles de Love Letter
	 * 
	 * @return Liste de toutes les cartes du jeu
	 */
	public static ArrayList<Carte> creerDeckComplet() {
		ArrayList<Carte> deck = new ArrayList<>();

		// 2x B2 Anglais (Espionne - valeur 0)
		deck.add(creerCarte(TypeCarte.B2_ANGLAIS));
		deck.add(creerCarte(TypeCarte.B2_ANGLAIS));

		// 6x Exam (Garde - valeur 1)
		for (int i = 0; i < 6; i++) {
			deck.add(creerCarte(TypeCarte.EXAM));
		}

		// 2x Tuteur Pédagogique (Prêtre - valeur 2)
		deck.add(creerCarte(TypeCarte.TUTEUR_PEDAGOGIQUE));
		deck.add(creerCarte(TypeCarte.TUTEUR_PEDAGOGIQUE));

		// 2x Jury (Baron - valeur 3)
		deck.add(creerCarte(TypeCarte.JURY));
		deck.add(creerCarte(TypeCarte.JURY));

		// 2x Règlement des Études (Servante - valeur 4)
		deck.add(creerCarte(TypeCarte.RDE));
		deck.add(creerCarte(TypeCarte.RDE));

		// 2x Bug Informatique (Prince - valeur 5)
		deck.add(creerCarte(TypeCarte.BUG_INFORMATIQUE));
		deck.add(creerCarte(TypeCarte.BUG_INFORMATIQUE));

		// 2x Ancien Élève (Chancelier - valeur 6)
		deck.add(creerCarte(TypeCarte.ANCIEN));
		deck.add(creerCarte(TypeCarte.ANCIEN));

		// 1x Learning Agreement (Comtesse - valeur 8)
		deck.add(creerCarte(TypeCarte.LA));

		// 1x Directeur (Roi - valeur 7)
		deck.add(creerCarte(TypeCarte.DIRLO));

		// 1x Gestionnaire SEE (Princesse - valeur 9)
		deck.add(creerCarte(TypeCarte.GESTIONNAIRE_SEE));

		return deck;
	}

}
