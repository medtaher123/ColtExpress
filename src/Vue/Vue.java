package Vue;

import Controllers.Jeu;
import Controllers.Joueur;
import Enums.EtatAction;
import Enums.PhaseDeJeu;
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
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static Controllers.Jeu.nbActions;

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
    private JPanel panel;

    private boolean buttonsEnabled = true;

    private ButtonActionBandit btnHaut;
    private ButtonActionBandit btnArriere;
    private ButtonActionBandit btnBas;
    private ButtonActionBandit btnAvant;
    private ButtonActionBandit btnRecuperer;
    private ButtonSupprimer btnSupprimer;
    private ButtonAction btnAction;
    private ButtonActionBandit btnTirerHaut;
    private ButtonActionBandit btnTirerBas;
    private ButtonActionBandit btnTirerAvant;
    private ButtonActionBandit btnTirerArriere;
    private Button[] buttons;

    private int MaxLongueurNomJoueur;
    public Vue(Jeu jeu) {
        this.jeu = jeu;
        this.train = jeu.getTrain();
        jeu.addObserver(this);
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
        calculerMaxLongueurNomJoueur();

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


        //setupKeyboardListeners();
        this.setFocusable(true);
    }

    private void calculerMaxLongueurNomJoueur() {
        for (Joueur joueur : jeu.getJoueurs()) {
            if(joueur.getNom().length() > MaxLongueurNomJoueur) {
                MaxLongueurNomJoueur = joueur.getNom().length();
            }
        }
    }

    private void drawActions(Graphics g) {
        int y = getHeight() - 100 - 20 * jeu.getJoueurs().size();
        int x;
        int spacing = 30;

        Font originalFont = g.getFont();
        Font newFont = new Font("Arial Unicode MS", Font.BOLD, 14);

        for (Joueur joueur : jeu.getJoueurs()) {
            y+= 20;
            x = 10;
            g.setColor(joueur.getCouleur());
            g.drawString(joueur.getNom() + ": ", x, y);
            g.setColor(Color.BLACK);
            x += 30 + g.getFontMetrics().stringWidth("a")*MaxLongueurNomJoueur;
            g.setFont(newFont);
            List<ActionAvecEtat> actions = joueur.getActions();
            for (ActionAvecEtat action : actions) {
                String text = "X";
                if ((joueur == jeu.getJoueurCourant() && jeu.getPhaseDeJeu() == PhaseDeJeu.PLANIFICATION) || (jeu.getPhaseDeJeu() == PhaseDeJeu.EXECUTION && action.getEtat() != EtatAction.EN_ATTENTE)) {
                    text = action.toString();

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
                }


                g.drawString(text, x, y);
                x += spacing + g.getFontMetrics().stringWidth("collecter");
                //x += spacing + g.getFontMetrics().stringWidth(text);
                g.setColor(Color.BLACK);
            }
            g.setFont(originalFont);

        }

        if(jeu.getPhaseDeJeu() == PhaseDeJeu.PLANIFICATION) {
            String actionsInfo = jeu.getJoueurCourant().getActions().size() + " / " + nbActions;
            if(jeu.getJoueurCourant().getActions().size() == nbActions) {
                g.setColor(Color.GREEN);
            }
            g.drawString(actionsInfo, getWidth() - g.getFontMetrics().stringWidth(actionsInfo) - 30, y);
        }

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

            if (i < train.getWagons().length - 1) {
                g.setColor(Color.GRAY);
                g.fillRect((i + 1) * (WAGON_WIDTH + WAGON_SPACING), 120 + WAGON_HEIGHT / 2, WAGON_SPACING, 5);
            }
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

        btnHaut = new ButtonActionBandit(new ActionDeplacement(Direction.HAUT),this, KeyEvent.VK_UP);
        btnArriere = new ButtonActionBandit(new ActionDeplacement( Direction.ARRIERE),this, KeyEvent.VK_LEFT);
        btnBas = new ButtonActionBandit(new ActionDeplacement( Direction.BAS),  this, KeyEvent.VK_DOWN);
        btnAvant = new ButtonActionBandit(new ActionDeplacement( Direction.AVANT), this, KeyEvent.VK_RIGHT);
        btnRecuperer = new ButtonActionBandit(new ActionCollecter(), this, KeyEvent.VK_SPACE);
        btnSupprimer = new ButtonSupprimer(this);
        btnAction = new ButtonAction(this);
        btnTirerAvant = new ButtonActionBandit(new ActionTirerAvant(), this, KeyEvent.VK_D);
        btnTirerArriere = new ButtonActionBandit(new ActionTirerArriere(), this, KeyEvent.VK_Q);
        btnTirerHaut = new ButtonActionBandit(new ActionTirerHaut(), this, KeyEvent.VK_Z);
        btnTirerBas = new ButtonActionBandit(new ActionTirerBas(), this, KeyEvent.VK_S);



        buttons = new Button[]{btnHaut, btnArriere, btnBas, btnAvant, btnRecuperer, btnSupprimer, btnAction, btnTirerAvant, btnTirerArriere, btnTirerHaut, btnTirerBas};

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 7));


        controlPanel.add(new JLabel());
        controlPanel.add(btnHaut.getJButton());
        controlPanel.add(new JLabel());
        controlPanel.add(new JLabel());
        controlPanel.add(new JLabel());
        controlPanel.add(btnTirerHaut.getJButton());
        //controlPanel.add(new JLabel());
        controlPanel.add(btnRecuperer.getJButton());
        controlPanel.add(btnSupprimer.getJButton());

        controlPanel.add(btnArriere.getJButton());
        controlPanel.add(btnBas.getJButton());
        controlPanel.add(btnAvant.getJButton());
        controlPanel.add(new JLabel());
        controlPanel.add(btnTirerArriere.getJButton());
        controlPanel.add(btnTirerBas.getJButton());
        controlPanel.add(btnTirerAvant.getJButton());
        //controlPanel.add(new JLabel());
        controlPanel.add(btnAction.getJButton());

        refreshButtons();

        return controlPanel;
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

    private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        int y = 30;
        for (Joueur joueur : jeu.getJoueurs()) {
            g.setColor(joueur.getCouleur());
            String bullets = "|".repeat(joueur.getNbBalles());
            g.drawString(joueur.getNom() + ": $ " + joueur.getScore() + ", Balles: " + bullets, 10, y);
            y += 20;
        }
        g.setColor(Color.BLACK);


        String joueurCourant = jeu.getJoueurCourantOuMarshall().getNom();
        String phaseDuJeu = jeu.getPhaseDeJeu().toString();


        int xPhaseDuJeu = this.getWidth() - g.getFontMetrics().stringWidth(phaseDuJeu) - 50;
        int xJoueurCourant = this.getWidth() / 2 - g.getFontMetrics().stringWidth(joueurCourant) / 2;

        g.drawString(phaseDuJeu, xPhaseDuJeu, 30);
        g.setColor(jeu.getJoueurCourantOuMarshall().getCouleur());
        g.drawString(joueurCourant, xJoueurCourant, 30);
        g.setColor(Color.BLACK);
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