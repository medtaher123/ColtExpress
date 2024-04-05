package Vue;

import Controllers.Jeu;
import Models.Bandit;
import Models.Butin.Butin;
import Enums.Direction;
import Models.Train;
import Models.Wagon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Vue extends JFrame {


    public static final Color BACKGROUND_COLOR = new Color(55, 35, 5);
    public static final Color WAGON_COLOR = new Color(139, 69, 19);
    private static final Color LOCOMOTIVE_COLOR = new Color(105, 105, 105);

    private static final int WAGON_WIDTH = 150;
    private static final int WAGON_HEIGHT = 60;
    private static final int WAGON_SPACING = 30;
    private static final int TEXT_OFFSET = 5;

    private Train train;
    private Bandit joueur;
    private JPanel panel;

    public Vue(Jeu jeu) {
        this.train = jeu.getTrain();
        joueur = jeu.getJoueur();
        initUI();
    }

    private void initUI() {
        setTitle("Train Display");
        setSize(WAGON_WIDTH * train.getWagons().length + WAGON_SPACING * (train.getWagons().length + 2), 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Font originalFont = g.getFont();
                Font boldFont = originalFont.deriveFont(Font.BOLD); // Create a new Font object based on the current font but set to bold
                g.setFont(boldFont);

                super.paintComponent(g);
                drawTrain(g);
            }
        };
        panel.setBackground(BACKGROUND_COLOR);

        add(panel, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        setupKeyboardListeners();
        this.setFocusable(true);
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

            g.setColor(Color.BLACK);
            int railY = 100 + WAGON_HEIGHT + wheelRadius;
            g.fillRect(WAGON_SPACING, railY, WAGON_WIDTH * train.getWagons().length + WAGON_SPACING * (train.getWagons().length - 1), 5);

        }
    }

    private void drawBanditsAndButins(Graphics g, Wagon wagon, int x, int y) {
        int textHeight = g.getFontMetrics().getHeight();

        // Bandits Interieurs
        int currentY = y + 15;
        for (Bandit bandit : wagon.getBanditsInterieurs()) {
            g.setColor(bandit.getCouleur());
            g.drawString(bandit.getNom(), x, currentY);
            currentY += textHeight;
        }

        // bandits Toit
        currentY = y - 5;
        for (Bandit bandit : wagon.getBanditsToit()) {
            g.setColor(bandit.getCouleur());
            g.drawString(bandit.getNom(), x, currentY);
            currentY -= textHeight;
        }

        // Butins
        currentY = y + WAGON_HEIGHT + 30;
        for (Butin butin : wagon.getButins()) {
            g.setColor(butin.getCouleur());
            g.drawString(String.valueOf(butin.getValeur()), x, currentY);
            currentY += textHeight;
        }
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 5));

        controlPanel.add(new JLabel());

        JButton btnHaut = new JButton("HAUT");
        btnHaut.addActionListener(e -> {
            joueur.seDeplacer(Direction.HAUT);
            panel.repaint();
        });
        controlPanel.add(btnHaut);

        controlPanel.add(new JLabel());
        controlPanel.add(new JLabel());

        JButton btnTirer = new JButton("TIRER");
        btnTirer.addActionListener(e -> {
            // Code
        });
        controlPanel.add(btnTirer);

        JButton btnArriere = new JButton("ARRIERE");
        btnArriere.addActionListener(e -> {
            joueur.seDeplacer(Direction.ARRIERE);
            panel.repaint();
        });
        controlPanel.add(btnArriere);

        JButton btnBas = new JButton("BAS");
        btnBas.addActionListener(e -> {
            joueur.seDeplacer(Direction.BAS);
            panel.repaint();
        });
        controlPanel.add(btnBas);

        JButton btnAvant = new JButton("AVANT");
        btnAvant.addActionListener(e -> {
            joueur.seDeplacer(Direction.AVANT);
            panel.repaint();
        });
        controlPanel.add(btnAvant);

        controlPanel.add(new JLabel()); // Add an empty label to align the buttons

        JButton btnRecupererButin = new JButton("RECUPERER_BUTIN");
        btnRecupererButin.addActionListener(e -> {
            // Code
        });
        controlPanel.add(btnRecupererButin);

        return controlPanel;
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
}