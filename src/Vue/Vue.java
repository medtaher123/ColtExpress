package Vue;

import Controllers.Jeu;
import Controllers.Joueur;
import Models.*;
import Models.Actions.*;
import Models.Actions.Action;
import Models.Butin.Butin;
import Enums.Direction;
import Vue.Buttons.Button;
import Vue.Buttons.ButtonAction;
import Vue.Buttons.ButtonActionBandit;
import Vue.Buttons.ButtonSupprimer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Vue extends JFrame implements Observer {

    private static final String BACKGROUND_IMAGE_PATH = "images/background.jpg";
    private BufferedImage backgroundImage;
    public static final Color WAGON_COLOR = new Color(0, 117, 238);
    private static final Color LOCOMOTIVE_COLOR = new Color(0, 61, 173);

    private static final int WAGON_WIDTH = 150;
    private static final int WAGON_HEIGHT = 60;
    private static final int WAGON_SPACING = 30;
    private static final int TEXT_OFFSET = 5;

    private Jeu jeu;
    private Train train;
    private Bandit joueur;
    private JPanel panel;

    private boolean buttonsEnabled = true;

    private ButtonActionBandit btnHaut;
    private ButtonActionBandit btnTirer;
    private ButtonActionBandit btnArriere;
    private ButtonActionBandit btnBas;
    private ButtonActionBandit btnAvant;
    private ButtonActionBandit btnRecuperer;
    private ButtonSupprimer btnSupprimer;
    private ButtonAction btnAction;
    private Button[] buttons;

    public Vue(Jeu jeu) {
        this.jeu = jeu;
        this.train = jeu.getTrain();
        jeu.addObserver(this);
        joueur = jeu.getJoueur();
        try {
            this.backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initUI();
    }

    private void initUI() {
        setTitle("Colt Express");
        setSize((WAGON_WIDTH + WAGON_SPACING) * train.getWagons().length + WAGON_SPACING * 2, 392);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Font originalFont = g.getFont();
                Font boldFont = originalFont.deriveFont(Font.BOLD); // Create a new Font object based on the current font but set to bold
                g.setFont(boldFont);

                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
                drawTrain(g);
                drawScore(g);
                drawActions(g);
            }
        };

        add(panel, BorderLayout.CENTER);
        add(creerPanneauDeControle(), BorderLayout.SOUTH);


        setupKeyboardListeners();
        this.setFocusable(true);
    }

    private void drawActions(Graphics g) {
        int y = getHeight() - 100;
        int x = 10;
        int spacing = 30;

        Font originalFont = g.getFont();
        Font newFont = new Font("Arial Unicode MS", Font.BOLD, 14); // Change "Arial Unicode MS" to the name of a font that supports the characters

        g.drawString("Actions: ", x, y);
        x += 70;
        g.setFont(newFont);
        for (ActionAvecEtat action : jeu.getJoueur().getActions()) {

            switch (action.getEtat()) {
                case EN_ATTENTE:
                    g.setColor(Color.BLACK);
                    break;
                case EXECUTEE:
                    g.setColor(Color.GREEN);
                    break;
                case ECHOUEE:
                    g.setColor(Color.RED);
                    break;
            }

            g.drawString(action.toString(), x, y);
            x += spacing + g.getFontMetrics().stringWidth(action.toString());
        }

        g.setFont(originalFont); // Reset to the original font
    }

    private void drawTrain(Graphics g) {
        for (int i = 0; i < train.getWagons().length; i++) {
            Wagon wagon = train.getWagons()[train.getWagons().length- i - 1];
            g.setColor(wagon.isLocomotive() ? LOCOMOTIVE_COLOR : WAGON_COLOR);
            g.fillRect(i * (WAGON_WIDTH + WAGON_SPACING) + WAGON_SPACING, 100, WAGON_WIDTH, WAGON_HEIGHT);
            g.setColor(Color.BLACK);
            g.drawRect(i * (WAGON_WIDTH + WAGON_SPACING) + WAGON_SPACING, 100, WAGON_WIDTH, WAGON_HEIGHT);
            drawBanditsAndButins(g, wagon, i * (WAGON_WIDTH + WAGON_SPACING) + WAGON_SPACING + TEXT_OFFSET, 100);


            int wheelRadius = 10;
            int wheelY = 100 + WAGON_HEIGHT - wheelRadius;

            g.setColor(Color.BLACK);
            g.fillOval(i * (WAGON_WIDTH + WAGON_SPACING) + WAGON_SPACING + wheelRadius, wheelY, wheelRadius * 2, wheelRadius * 2);
            g.fillOval(i * (WAGON_WIDTH + WAGON_SPACING) + WAGON_SPACING + WAGON_WIDTH - wheelRadius * 3, wheelY, wheelRadius * 2, wheelRadius * 2);

            drawBanditsAndButins(g, wagon, i * (WAGON_WIDTH + WAGON_SPACING) + WAGON_SPACING + TEXT_OFFSET, 100);

        }
    }

    private void drawBanditsAndButins(Graphics g, Wagon wagon, int x, int y) {
        int textHeight = g.getFontMetrics().getHeight();

        // Bandits Interieurs
        int currentY = y + 15;
        Marshall marshall = wagon.getMarshall();
        if (marshall != null) {
            g.setColor(marshall.getCouleur());
            g.drawString(marshall.getNom(), x, currentY);
            currentY += textHeight;
        }
        for (Personne personne : wagon.getBanditsInterieurs()) {
            g.setColor(personne.getCouleur());
            g.drawString(personne.getNom(), x, currentY);
            currentY += textHeight;
        }

        // bandits Toit et Marshall
        currentY = y - 5;
        for (Personne personne : wagon.getBanditsToit()) {
            g.setColor(personne.getCouleur());
            g.drawString(personne.getNom(), x, currentY);
            currentY -= textHeight;
        }

        // Butins
        currentY = y + WAGON_HEIGHT + 30;
        for (Butin butin : wagon.getButins()) {
            g.setColor(butin.getCouleur());
            g.drawString( butin.getNom() , x, currentY);
            currentY += textHeight;
        }
    }


    private JPanel creerPanneauDeControle() {

        btnHaut = new ButtonActionBandit(new ActionDeplacement(joueur, Direction.HAUT),this);
        btnTirer = new ButtonActionBandit(new ActionTirer(joueur),this);
        btnArriere = new ButtonActionBandit(new ActionDeplacement(joueur, Direction.ARRIERE),this);
        btnBas = new ButtonActionBandit(new ActionDeplacement(joueur, Direction.BAS),  this);
        btnAvant = new ButtonActionBandit(new ActionDeplacement(joueur, Direction.AVANT), this);
        btnRecuperer = new ButtonActionBandit(new ActionCollecter(joueur), this);
        btnSupprimer = new ButtonSupprimer(this);
        btnAction = new ButtonAction(this);

        buttons = new Button[]{btnHaut, btnTirer, btnArriere, btnBas, btnAvant, btnRecuperer, btnSupprimer, btnAction};

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 6));


        controlPanel.add(new JLabel());
        controlPanel.add(btnHaut.getJButton());
        controlPanel.add(new JLabel());
        controlPanel.add(new JLabel());
        controlPanel.add(btnTirer.getJButton());
        controlPanel.add(btnSupprimer.getJButton());

        controlPanel.add(btnArriere.getJButton());
        controlPanel.add(btnBas.getJButton());
        controlPanel.add(btnAvant.getJButton());
        controlPanel.add(new JLabel());
        controlPanel.add(btnRecuperer.getJButton());
        controlPanel.add(btnAction.getJButton());

        return controlPanel;
    }

    public JButton creerJButton(Action action){
        JButton btn = new JButton(action.toString());
        btn.addActionListener(e -> {
            if(!action.peutExecuter()){
                return;
            }
            action.executer();
            panel.repaint();
            jeu.tourSuivant();
            panel.repaint();
        });
        return btn;
    }

    private void refreshButtons() {
        for (Button button : buttons) {
            button.setEnabled(buttonsEnabled);
        }
    }

    public void setButtonsEnabled(boolean enabled) {
        buttonsEnabled = enabled;
        refreshButtons();
    }
    public void setButtonsEnabled(boolean enabled, Action action) {
        buttonsEnabled = enabled;
        refreshButtons();
    }
    private void setupKeyboardListeners() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        joueur.seDeplacer(Direction.HAUT);
                        break;
                    case KeyEvent.VK_DOWN:
                        joueur.seDeplacer(Direction.BAS);
                        break;
                    case KeyEvent.VK_LEFT:
                        joueur.seDeplacer(Direction.ARRIERE);
                        break;
                    case KeyEvent.VK_RIGHT:
                        joueur.seDeplacer(Direction.AVANT);
                        break;
                    case KeyEvent.VK_T:
                        // Code
                        break;
                    case KeyEvent.VK_R:
                        // Code
                        break;
                }
                panel.repaint();
            }
        });
    }


    private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        int y = 30;
        for (Joueur joueur : jeu.getJoueurs()) {
            g.drawString(joueur.getNom() + ": " + joueur.getScore(), 10, y);
            y += 20;
        }


        // Ajouter le nom du joueur courant et la phase du jeu en haut à droite
        String joueurCourant = "Joueur courant: " + jeu.getJoueur().getNom();
        String phaseDuJeu = "Phase: " + jeu.getPhaseDeJeu().toString();

        // Calculer les positions x pour que le texte soit aligné à droite
        int xPhaseDuJeu = this.getWidth() - g.getFontMetrics().stringWidth(phaseDuJeu) - 50;
        int xJoueurCourant = this.getWidth() - g.getFontMetrics().stringWidth(joueurCourant) - 50;
        
        g.drawString(phaseDuJeu, xPhaseDuJeu, 30);
        g.drawString(joueurCourant, xJoueurCourant, 50);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Vue.update");
        panel.paintImmediately(panel.getBounds());
        if( o instanceof Button) {
            refreshButtons();
            //refreshButtons();
            //panel.repaint();
        }




    }

    public Jeu getJeu() {
        return jeu;
    }
}