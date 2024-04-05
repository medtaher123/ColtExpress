package Controllers;

import Enums.Position;
import Models.Bandit;
import Models.Butin.Bijous;
import Models.Butin.Bourse;
import Models.Train;
import Models.Wagon;

import java.awt.*;

public class Jeu {


    private Bandit joueurCourant;
    private Train train;
    public static final int NB_WAGONS = 4;
    public static final String NOM_BANDIT_1 = "Joe Dalton";

    public static final int NB_ACTIONS = 3;


    public void startGame() {
        joueurCourant = new Bandit(NOM_BANDIT_1, new Color(255,200,200), Position.TOIT);
        train = new Train(NB_WAGONS);

        for (Wagon wagon : train.getWagons()) {
            wagon.ajouterButin(new Bijous());
            wagon.ajouterButin(new Bourse(25));
        }

        train.getWagons()[0].ajouterBandit(new Bandit("Marshal",new Color(255,0,0), Position.INTERIEUR));
        train.getWagons()[2].ajouterBandit(joueurCourant);


    }

    public Train getTrain() {
        return train;
    }

    public Bandit getJoueur() {
        return joueurCourant;
    }
}
