package Controllers;

import Enums.Position;
import Models.*;
import Models.Butin.Bijous;
import Models.Butin.Bourse;
import Models.Butin.Butin;

import java.awt.*;

public class Jeu {


    private Bandit[] bandits = new Bandit[1];
    private Bandit joueurCourant;
    private Train train;
    private Marshall marshall;
    public static final int NB_WAGONS = 4;
    public static final String NOM_BANDIT_1 = "Bandit 1";

    public static final int NB_ACTIONS = 3;

    public void tourSuivant() {
        marshall.effectuerAction();
    }


    public void startGame() {
        joueurCourant = new Bandit(NOM_BANDIT_1, new Color(0, 0, 0), Position.TOIT);
        bandits[0] = joueurCourant;
        train = new Train(NB_WAGONS);

        initButins();


        marshall = new Marshall();
        train.getWagons()[0].setMarshall(marshall);
        train.getWagons()[2].ajouterBandit(joueurCourant);


    }

    private void initButins() {
        for (Wagon wagon : train.getWagons()) {
            int nbButins = (int) (Math.random() * 4) + 1;
            for (int i = 0; i < nbButins; i++) {
                Butin butin;
                if (Math.random() < 0.5) {
                    butin = new Bijous();
                } else {
                    butin = new Bourse();
                }
                wagon.ajouterButin(butin);
            }
        }
    }

    public Train getTrain() {
        return train;
    }

    public Bandit getJoueur() {
        return joueurCourant;
    }

    public Marshall getMarshall() {
        return marshall;
    }

    public Bandit[] getBandits() {
        return bandits;
    }
}
