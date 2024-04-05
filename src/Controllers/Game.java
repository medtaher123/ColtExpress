package Controllers;

import Models.Bandit;
import Models.Butin.Bijous;
import Models.Butin.Bourse;
import Models.Train;
import Models.Wagon;

import java.awt.*;

public class Game {


    private Bandit joueurCourant;
    private Train train;
    public static final int NB_WAGONS = 4;
    public static final String NOM_BANDIT_1 = "Joe Dalton";

    public static final int NB_ACTIONS = 3;


        public void startGame() {
            joueurCourant = new Bandit("Joe",Color.BLUE, Models.Position.INTERIEUR);
            train = new Train(5);



        }

    public Train getTrain() {
            return train;
    }

    public Bandit getJoueur() {
        return joueurCourant;
    }
}
