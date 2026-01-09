package ui.views;

import controller.GameController;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import model.Joueur;
import model.Manche;
import model.Partie;

import java.awt.*;

public class PlateauPanel extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(34, 85, 51);
    private static final Color PANEL_BACKGROUND = new Color(245, 245, 220);

    private GameController controller;

    private JPanel playersZone;
    private JPanel handZone;
    private JPanel centerZone;
    private JPanel infoZone;

    private JButton nouvellePartieBtn;
    private JLabel mancheLabel;
    private JLabel deckLabel;

    public PlateauPanel(GameController c) {
        this.controller = c;
        setLayout(new BorderLayout(10, 10));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === Zone des joueurs (NORD) ===
        playersZone = new JPanel();
        playersZone.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        playersZone.setOpaque(false);
        playersZone.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                "Joueurs",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE));

        // === Zone centrale (historique + infos) ===
        centerZone = new JPanel(new BorderLayout(10, 10));
        centerZone.setOpaque(false);

        // Panneau d'informations de la partie
        infoZone = createInfoPanel();

        // Zone de l'historique
        JPanel historiquePanel = new JPanel(new BorderLayout());
        historiquePanel.setBackground(PANEL_BACKGROUND);
        historiquePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
                "Historique des actions",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12)));

        centerZone.add(infoZone, BorderLayout.NORTH);
        centerZone.add(historiquePanel, BorderLayout.CENTER);

        // === Zone de la main (SUD) ===
        handZone = new HandPanel(controller);
        handZone.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                "Votre main",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE));

        // === Zone de contrôle (OUEST) ===
        JPanel controlZone = createControlPanel();

        // Assemblage
        add(playersZone, BorderLayout.NORTH);
        add(centerZone, BorderLayout.CENTER);
        add(handZone, BorderLayout.SOUTH);
        add(controlZone, BorderLayout.WEST);
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panel.setBackground(new Color(60, 60, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        mancheLabel = new JLabel("Manche: -");
        mancheLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mancheLabel.setForeground(Color.WHITE);

        deckLabel = new JLabel("Deck: - cartes");
        deckLabel.setFont(new Font("Arial", Font.BOLD, 14));
        deckLabel.setForeground(Color.WHITE);

        panel.add(mancheLabel);
        panel.add(deckLabel);

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(60, 60, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(150, 0));

        JLabel titleLabel = new JLabel("Love Letter");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nouvellePartieBtn = new JButton("Nouvelle Partie");
        nouvellePartieBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        nouvellePartieBtn.setMaximumSize(new Dimension(130, 35));
        nouvellePartieBtn.setBackground(new Color(34, 139, 34));
        nouvellePartieBtn.setForeground(Color.WHITE);
        nouvellePartieBtn.setFocusPainted(false);
        nouvellePartieBtn.setFont(new Font("Arial", Font.BOLD, 12));
        nouvellePartieBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nouvellePartieBtn.addActionListener(e -> demanderJoueurs());

        JButton reglesBtn = new JButton("Règles");
        reglesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        reglesBtn.setMaximumSize(new Dimension(130, 30));
        reglesBtn.setFocusPainted(false);
        reglesBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        reglesBtn.addActionListener(e -> afficherRegles());

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(nouvellePartieBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(reglesBtn);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private void demanderJoueurs() {
        String saisie = JOptionPane.showInputDialog(
                this,
                "Entrez les noms des joueurs séparés par des virgules\n(2 à 6 joueurs)",
                "Nouvelle Partie",
                JOptionPane.QUESTION_MESSAGE);

        if (saisie == null || saisie.trim().isEmpty())
            return;

        String[] noms = saisie.split(",");
        for (int i = 0; i < noms.length; i++) {
            noms[i] = noms[i].trim();
        }

        if (noms.length < 2 || noms.length > 6) {
            JOptionPane.showMessageDialog(this,
                    "Le nombre de joueurs doit être entre 2 et 6.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.nouvellePartie(noms);
    }

    private void afficherRegles() {
        String regles = """
                === LOVE LETTER - Règles ===

                But: Être le dernier joueur en lice ou avoir la carte
                de plus haute valeur quand le deck est vide.

                Tour de jeu:
                1. Piochez une carte
                2. Jouez une de vos deux cartes
                3. Appliquez son effet

                Cartes (de 0 à 9):
                0 - Le B2: Le B2 peut etre un très bon attout pour avoir\n\
                son dossier accepté. A mois que d'autres joueurs ne\n\
                pensent la même chose... (si vous etes le seul à avoir\n\
                cette carte à la fin de la manche, vous gagnez \n\
                automatiquement un pion)
                1 - Exam: Essayez de deviner la carte d'un adversaire.\nRéussissez, et il est éliminé!
                2 - Tuteur: le Tuteur peut suivre les dossiers des \nétudiants (voir leur carte).
                3 - Jury: le Jury est redoutable, il compare les dossiers\n\
                des étudiants. Le plus faible est viré. (comparez la valeur\n\
                de votre main avec celle d'un adversaire, le plus faible est éliminé)
                4 - RDE: le Règlement des études est votre plus grand allié, \n\
                il vous protège. (Protection jusqu'au prochain tour)
                5 - Bug Info: le numérique, c'est pratique. Jusqu'à ce qu'un \n\
                bug arrive. (Choisissez un Joueur(y compris vous-même) pour qu'il\n\
                défausse sa main et pioche une nouvelle carte)
                6 - Ancien Élève: L'ancien élève ou alumni connaît bien l'université.\n\
                Et il peux vous aider à booster votre dossier. (Vous piochez 2 cartes,\n\
                vous choississez laquelle garder (entre votre main et les cartes piochées)\n\
                et remettez les autres en dessous du deck)
                7 - Directeur: Le directeur est puissant. Il peut changer comment votre\n\
                dossier est traité. (Échangez votre main avec celle d'un autre joueur \n\
                de votre choix)
                8 - Learning Agreement: Le document le plus important pour votre SEE. \n\
                Mais attention au directeur et aux bugs informatiques! (Échangez votre \n\
                main avec une carte piochée du deck)
                9 - Gestionnaire SEE: La personne qui aura le dernier mot sur votre dossier.\n\
                Gare à ne pas s'attirer les foudres de cette-dernière (Si vous avez cette \n\
                carte à la fin de la manche, vous gagnez automatiquement un pion. Défaussez-la \n\
                est perdez immédiatement la manche!).

                Victoire: Premier à atteindre le nombre de pions requis!
                """;

        JTextArea textArea = new JTextArea(regles);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 350));

        JOptionPane.showMessageDialog(this, scrollPane, "Règles du jeu",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateFromModel() {
        playersZone.removeAll();

        Manche m = controller.getManche();
        if (m == null) {
            mancheLabel.setText("Manche: -");
            deckLabel.setText("Deck: - cartes");
            revalidate();
            repaint();
            return;
        }

        // Mise à jour des infos
        Partie partie = Partie.getInstance();
        if (partie != null) {
            mancheLabel.setText("Manche: " + partie.getNumeroManche());
        }
        deckLabel.setText("Deck: " + m.getDeck().getNombreCartesRestantes() + " cartes");

        // Mise à jour des joueurs
        for (Joueur j : m.getTousLesJoueurs()) {
            PlayerPanel pj = new PlayerPanel(j, controller);
            playersZone.add(pj);
        }

        // Mise à jour de la main
        ((HandPanel) handZone).updateFromModel();

        // Mise à jour de l'historique
        updateHistorique(m);

        revalidate();
        repaint();
    }

    private void updateHistorique(Manche m) {
        // Trouver le panneau d'historique dans centerZone
        Component[] components = centerZone.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getBorder() instanceof TitledBorder) {
                    TitledBorder border = (TitledBorder) panel.getBorder();
                    if (border.getTitle().contains("Historique")) {
                        panel.removeAll();

                        JTextArea historique = new JTextArea(m.getHistoriqueTexte());
                        historique.setEditable(false);
                        historique.setFont(new Font("Arial", Font.PLAIN, 12));
                        historique.setBackground(PANEL_BACKGROUND);

                        JScrollPane scrollPane = new JScrollPane(historique);
                        scrollPane.setBorder(null);
                        panel.add(scrollPane, BorderLayout.CENTER);
                        break;
                    }
                }
            }
        }
    }
}