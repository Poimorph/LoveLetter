public class CardButton extends JButton {

    public CardButton(
        Carte carte,
        int index,
        GameController c) {

        this.setText(carte.getNom());

        this.addActionListener(e ->
            c.jouerCarte(
                c.getJoueurActif(),
                index));
    }
}
