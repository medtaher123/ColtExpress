package Vue;

import Controllers.Bot;
import Controllers.Jeu;
import Controllers.Joueur;
import Enums.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SetupWindow extends JFrame {
    private final JSpinner numPlayersSpinner;
    private final JPanel playersPanel;
    private List<JTextField> playerNameFields;
    private List<JButton> playerColorButtons;
    private List<JCheckBox> playerBotCheckBoxes;
    private final JSpinner numBulletsSpinner;
    private final JSpinner numWagonsSpinner;
    private final JSpinner animationSpeedSpinner;
    private final JSpinner numActionsSpinner;
    private final JButton startGameButton;

    public SetupWindow() {
        setTitle("Configuration du jeu");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));


        JPanel numPlayersPanel = new JPanel(new BorderLayout());
        numPlayersPanel.add(new JLabel("Nombre de joueurs:"), BorderLayout.WEST);
        numPlayersSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 6, 1));
        numPlayersSpinner.setPreferredSize(new Dimension(20, 20));
        numPlayersSpinner.addChangeListener(e -> updatePlayersPanel());
        numPlayersPanel.add(numPlayersSpinner, BorderLayout.CENTER);
        add(numPlayersPanel);

        playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        add(playersPanel);

        JPanel numBulletsPanel = new JPanel(new BorderLayout());
        numBulletsPanel.add(new JLabel("Nombre de Balles:"), BorderLayout.WEST);
        numBulletsSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        numBulletsSpinner.setPreferredSize(new Dimension(20, 20));
        numBulletsPanel.add(numBulletsSpinner, BorderLayout.CENTER);
        add(numBulletsPanel);

        JPanel numWagonsPanel = new JPanel(new BorderLayout());
        numWagonsPanel.add(new JLabel("Nombre de Wagons:"), BorderLayout.WEST);
        numWagonsSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
        numWagonsSpinner.setPreferredSize(new Dimension(20, 20));
        numWagonsPanel.add(numWagonsSpinner, BorderLayout.CENTER);
        add(numWagonsPanel);

        JPanel animationSpeedPanel = new JPanel(new BorderLayout());
        animationSpeedPanel.add(new JLabel("Vitesse d'Animation:"), BorderLayout.WEST);
        animationSpeedSpinner = new JSpinner(new SpinnerNumberModel(1, 0.25, 3, 0.25));
        animationSpeedSpinner.setPreferredSize(new Dimension(20, 20));
        animationSpeedPanel.add(animationSpeedSpinner, BorderLayout.CENTER);
        add(animationSpeedPanel);

        JPanel numActionsPanel = new JPanel(new BorderLayout());
        numActionsPanel.add(new JLabel("Nombre d'actions par tour:"), BorderLayout.WEST);
        numActionsSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        numActionsPanel.add(numActionsSpinner, BorderLayout.CENTER);
        add(numActionsPanel);

        startGameButton = new JButton("Commencer le jeu");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        add(startGameButton);

        updatePlayersPanel();
    }

    private void updatePlayersPanel() {
        int numPlayers = (Integer) numPlayersSpinner.getValue();
        playerNameFields = new ArrayList<>(numPlayers);
        playerColorButtons = new ArrayList<>(numPlayers);
        playerBotCheckBoxes = new ArrayList<>(numPlayers);

        playersPanel.removeAll();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < numPlayers; i++) {
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new GridLayout(1, 4));
            playerPanel.setPreferredSize(new Dimension(playerPanel.getWidth(), 20));

            JTextField playerNameField = new JTextField("Joueur " + (i + 1));
            JButton playerColorButton = new JButton();
            playerColorButton.setPreferredSize(new Dimension(20, 20));
            playerColorButton.setBackground(Color.BLACK);
            playerColorButton.addActionListener(e -> {
                Color newColor = JColorChooser.showDialog(null, "Choisir une couleur", playerColorButton.getBackground());
                if (newColor != null) {
                    playerColorButton.setBackground(newColor);
                }
            });
            JCheckBox playerBotCheckBox = new JCheckBox("Bot");

            playerNameFields.add(playerNameField);
            playerColorButtons.add(playerColorButton);
            playerBotCheckBoxes.add(playerBotCheckBox);

            playerPanel.add(new JLabel("Joueur " + (i + 1) + " Nom:"));
            playerPanel.add(playerNameField);
            playerPanel.add(new JLabel(" Couleur:"));
            playerPanel.add(playerColorButton);
            playerPanel.add(playerBotCheckBox);

            playersPanel.add(playerPanel);
        }

        playersPanel.revalidate();
        playersPanel.repaint();
    }

    private void startGame() {

        int numPlayers = playerNameFields.size();
        int nbBalles = (Integer) numBulletsSpinner.getValue();
        int nbWagons = (Integer) numWagonsSpinner.getValue();
        double vitesseAnimation = (Double) animationSpeedSpinner.getValue();

        Jeu jeu = new Jeu(nbWagons);
        jeu.setVitesseAnimation((float)vitesseAnimation);
        jeu.setNbActions((Integer) numActionsSpinner.getValue());

        for (int i = 0; i < numPlayers; i++) {
            String nom = playerNameFields.get(i).getText();
            Color couleur = playerColorButtons.get(i).getBackground();
            boolean estBot = playerBotCheckBoxes.get(i).isSelected();

            jeu.ajouterJoueur(nom, couleur, nbBalles, estBot);

        }

        jeu.startGame();
        this.dispose();


    }
}