package ui.dialogs;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Dialogue affichant les règles du jeu Love Letter - Version UTBM
 */
public class ReglesDialog extends JDialog {

    // Couleurs du thème

    // private static final Color BACKGROUND_COLOR = new Color(34, 85, 51);
    // private static final Color PANEL_BACKGROUND = new Color(245, 245, 220);

    private static final Color HEADER_BG = new Color(34, 85, 51);
    private static final Color BACKGROUND = new Color(245, 245, 220);
    private static final Color CARD_BG = new Color(255, 253, 240);
    private static final Color ACCENT = new Color(180, 100, 40);

    public ReglesDialog(Window parent) {
        super(parent, "Règles du jeu - Love Letter UTBM", ModalityType.APPLICATION_MODAL);
        setSize(700, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND);

        // En-tête
        JPanel headerPanel = createHeader();
        add(headerPanel, BorderLayout.NORTH);

        // Onglets
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(BACKGROUND);
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));

        tabbedPane.addTab("Objectif", createObjectifPanel());
        tabbedPane.addTab("Déroulement", createDeroulementPanel());
        tabbedPane.addTab("Les Cartes", createCartesPanel());
        tabbedPane.addTab("Règles spéciales", createSpecialesPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Bouton fermer
        JButton closeBtn = new JButton("Fermer");
        closeBtn.setBackground(HEADER_BG);
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(BACKGROUND);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bottomPanel.add(closeBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Love Letter - Édition UTBM");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("La quête du Semestre à l'Étranger");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitle.setForeground(new Color(255, 220, 180));

        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);

        return header;
    }

    private JScrollPane createObjectifPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addSection(panel, "But du jeu",
                "Vous êtes un étudiant de l'UTBM tentant de faire valider votre dossier " +
                        "de Semestre d'Études à l'Étranger (SEE). Pour cela, vous devez atteindre " +
                        "le Gestionnaire SEE (la carte la plus forte) ou être le dernier étudiant en lice !");

        addSection(panel, "Victoire de manche",
                "• Être le dernier joueur encore en jeu, OU\n" +
                        "• Avoir la carte de plus haute valeur quand la pioche est vide");

        addSection(panel, "Victoire de partie",
                "Le premier joueur à accumuler le nombre requis de pions de faveur gagne :\n" +
                        "• 2 joueurs : 6 pions\n" +
                        "• 3 joueurs : 5 pions\n" +
                        "• 4 joueurs : 4 pions\n" +
                        "• 5-6 joueurs : 3 pions");

        panel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND);
        return scrollPane;
    }

    private JScrollPane createDeroulementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addSection(panel, "Mise en place",
                "1. Mélangez les 21 cartes\n" +
                        "2. Mettez 1 carte de côté face cachée (elle ne sera pas utilisée)\n" +
                        "3. À 2 joueurs : mettez aussi 3 cartes face visible\n" +
                        "4. Distribuez 1 carte à chaque joueur");

        addSection(panel, "Tour de jeu",
                "1. Piochez 1 carte (vous en avez maintenant 2)\n" +
                        "2. Jouez 1 carte de votre main\n" +
                        "3. Appliquez son effet\n" +
                        "4. Passez au joueur suivant");

        addSection(panel, "Fin de manche",
                "La manche se termine quand :\n" +
                        "• Il ne reste qu'un seul joueur en jeu, OU\n" +
                        "• La pioche est vide\n\n" +
                        "Le vainqueur reçoit 1 pion de faveur (+1 bonus si seul à avoir joué B2 Anglais)");

        panel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND);
        return scrollPane;
    }

    private JScrollPane createCartesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tableau des cartes
        String[][] cartes = {
                { "0", "B2 Anglais", "2",
                        "Pas d'effet. Bonus : +1 pion si vous êtes le seul vainqueur à l'avoir jouée." },
                { "1", "Exam", "6", "Devinez la carte d'un adversaire (sauf Exam). Si correct, il est éliminé." },
                { "2", "Tuteur Pédagogique", "2", "Regardez la main d'un adversaire." },
                { "3", "Jury", "2", "Comparez votre carte avec celle d'un adversaire. La plus faible est éliminée." },
                { "4", "Règlement des Études", "2", "Vous êtes protégé jusqu'à votre prochain tour." },
                { "5", "Bug Informatique", "2",
                        "Un joueur (vous ou autre) défausse sa main et pioche. Si Gestionnaire SEE défaussé = éliminé." },
                { "6", "Ancien Élève", "2", "Piochez 2 cartes, gardez-en 1, remettez les 2 autres sous la pioche." },
                { "7", "Directeur", "1", "Échangez votre main avec celle d'un adversaire." },
                { "8", "Learning Agreement", "1",
                        "Pas d'effet. DOIT être jouée si vous avez aussi Directeur ou Bug Informatique." },
                { "9", "Gestionnaire SEE", "1",
                        "Si vous jouez ou défaussez cette carte, vous êtes éliminé. Carte la plus forte." }
        };

        for (String[] carte : cartes) {
            panel.add(createCarteRow(carte[0], carte[1], carte[2], carte[3]));
            panel.add(Box.createVerticalStrut(8));
        }

        panel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JPanel createCarteRow(String valeur, String nom, String quantite, String effet) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Valeur (badge)
        JLabel valeurLabel = new JLabel(valeur, SwingConstants.CENTER);
        valeurLabel.setFont(new Font("Arial", Font.BOLD, 18));
        valeurLabel.setForeground(Color.WHITE);
        valeurLabel.setOpaque(true);
        valeurLabel.setBackground(HEADER_BG);
        valeurLabel.setPreferredSize(new Dimension(35, 35));
        valeurLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Nom et quantité
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(CARD_BG);

        JLabel nomLabel = new JLabel(nom + " (x" + quantite + ")");
        nomLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nomLabel.setForeground(HEADER_BG);

        JTextArea effetArea = new JTextArea(effet);
        effetArea.setFont(new Font("Arial", Font.PLAIN, 11));
        effetArea.setForeground(new Color(80, 80, 80));
        effetArea.setBackground(CARD_BG);
        effetArea.setLineWrap(true);
        effetArea.setWrapStyleWord(true);
        effetArea.setEditable(false);
        effetArea.setBorder(null);

        infoPanel.add(nomLabel, BorderLayout.NORTH);
        infoPanel.add(effetArea, BorderLayout.CENTER);

        card.add(valeurLabel, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }

    private JScrollPane createSpecialesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addSection(panel, "Contrainte du Learning Agreement",
                "Si vous avez le Learning Agreement (8) ET le Directeur (7) ou le Bug Informatique (5) " +
                        "en main, vous DEVEZ obligatoirement jouer le Learning Agreement.");

        addSection(panel, "Protection (Règlement des Études)",
                "Un joueur protégé ne peut pas être ciblé par les cartes adverses. " +
                        "La protection dure jusqu'au début de son prochain tour.");

        addSection(panel, "Tous protégés",
                "Si tous les adversaires sont protégés et que vous jouez une carte qui nécessite " +
                        "une cible, l'effet est simplement annulé.");

        addSection(panel, "Pioche vide",
                "Si la pioche est vide et qu'il reste plusieurs joueurs, celui avec la carte " +
                        "de plus haute valeur gagne. En cas d'égalité, on compare la somme des cartes jouées.");

        addSection(panel, "Bonus B2 Anglais",
                "Si vous gagnez la manche ET que vous êtes le SEUL vainqueur à avoir joué " +
                        "le B2 Anglais pendant cette manche, vous recevez 1 pion de faveur supplémentaire.");

        panel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND);
        return scrollPane;
    }

    private void addSection(JPanel parent, String title, String content) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(BACKGROUND);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(HEADER_BG);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 12));
        contentArea.setForeground(new Color(60, 60, 60));
        contentArea.setBackground(BACKGROUND);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        section.add(titleLabel);
        section.add(contentArea);
        parent.add(section);
    }

    public void afficher() {
        setVisible(true);
    }
}