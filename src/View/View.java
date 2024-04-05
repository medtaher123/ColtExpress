package View;

import Controllers.Game;
import Models.Bandit;
import Models.Butin.Butin;
import Enums.Direction;
import Models.Train;
import Models.Wagon;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private Train train;
    private Bandit joueur;
    private JPanel panel;

    public View(Game game) {
        this.train = game.getTrain();
        joueur = game.getJoueur();
        initUI();
    }

    private void initUI() {
        setTitle("Train Display");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(1, train.getWagons().length));


        refreshDisplay();

        /*for (Wagon wagon : train.getWagons()) {
            panel.add(createWagonPanel(wagon));
        }*/

        add(panel, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);


    }

    public void refreshDisplay() {
        panel.removeAll();

        for (int i = train.getWagons().length - 1; i >= 0; i--) {
            panel.add(createWagonPanel(train.getWagons()[i]));
        }

        panel.revalidate();
        panel.repaint();
    }

    private JPanel createWagonPanel(Wagon wagon) {
        JPanel wagonPanel = new JPanel(new BorderLayout());
        wagonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel wagonBody = new JPanel();
        if (wagon.isLocomotive()) {
            wagonBody.setBackground(Color.DARK_GRAY);
        } else {
            wagonBody.setBackground(Color.GRAY);
        }
        wagonPanel.add(wagonBody, BorderLayout.SOUTH);

        JPanel wagonRoof = new JPanel();
        wagonRoof.setBackground(Color.WHITE);
        wagonPanel.add(wagonRoof, BorderLayout.NORTH);

        drawBanditsAndButins(wagon, wagonBody, wagonRoof);

        return wagonPanel;
    }

    private void drawBanditsAndButins(Wagon wagon, JPanel wagonBody, JPanel wagonRoof) {
        // Draw bandits inside the wagon
        for (Bandit bandit : wagon.getBanditsInterieurs()) {
            JLabel banditLabel = new JLabel(bandit.getNom());
            banditLabel.setForeground(bandit.getCouleur());
            wagonBody.add(banditLabel);
        }

        // Draw bandits on the roof
        for (Bandit bandit : wagon.getBanditsToit()) {
            JLabel banditLabel = new JLabel(bandit.getNom());
            banditLabel.setForeground(bandit.getCouleur());
            wagonRoof.add(banditLabel);
        }

        // Draw butins inside the wagon
        for (Butin butin : wagon.getButins()) {
            JLabel butinLabel = new JLabel(butin.getValeur() + "");
            butinLabel.setForeground(butin.getCouleur());
            wagonBody.add(butinLabel);
        }
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 3));

        JButton btnHaut = new JButton("HAUT");
        btnHaut.addActionListener(e -> {
            joueur.seDeplacer(Direction.HAUT);
            refreshDisplay();
        });
        controlPanel.add(btnHaut);

        JButton btnBas = new JButton("BAS");
        btnBas.addActionListener(e -> {
            joueur.seDeplacer(Direction.BAS);
            refreshDisplay();
        });
        controlPanel.add(btnBas);

        JButton btnAvant = new JButton("AVANT");
        btnAvant.addActionListener(e -> {
            joueur.seDeplacer(Direction.AVANT);
            refreshDisplay();
        });
        controlPanel.add(btnAvant);

        JButton btnArriere = new JButton("ARRIERE");
        btnArriere.addActionListener(e -> {
            joueur.seDeplacer(Direction.ARRIERE);
            refreshDisplay();
        });
        controlPanel.add(btnArriere);

        JButton btnRecupererButin = new JButton("RECUPERER_BUTIN");
        btnRecupererButin.addActionListener(e -> {
            // Code to execute when RECUPERER_BUTIN button is clicked
        });
        controlPanel.add(btnRecupererButin);

        JButton btnTirer = new JButton("TIRER");
        btnTirer.addActionListener(e -> {
            // Code to execute when TIRER button is clicked
        });
        controlPanel.add(btnTirer);

        return controlPanel;
    }


}