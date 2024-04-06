package Controllers;

import Enums.PhaseDeJeu;
import Enums.Position;
import Models.*;
import Models.Actions.ActionAvecEtat;
import Models.Butin.Bijous;
import Models.Butin.Bourse;
import Models.Butin.Butin;

import java.awt.*;
import java.util.Observable;

public class Jeu extends Observable {

    public static final String NOM_BANDIT_1 = "Bandit 1";
    public static final int NB_ACTIONS = 3;

    private Joueur[] joueurs = new Joueur[1];
    private Joueur joueurCourant;
    private Train train;
    private Marshall marshall;
    public static final int NB_WAGONS = 5;


    private PhaseDeJeu phaseDeJeu = PhaseDeJeu.PLANIFICATION;



    public void tourSuivant() {
        marshall.effectuerAction();
    }


    public void startGame() {
        joueurCourant = new Joueur(NOM_BANDIT_1, new Color(0, 0, 0), Position.TOIT);
        joueurs[0] = joueurCourant;
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

    public Joueur getJoueur() {
        return joueurCourant;
    }

    public Marshall getMarshall() {
        return marshall;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }




    public void executerActions() {

        for (int i = 0; i < NB_ACTIONS; i++) {
            joueurCourant.executerActions(i);
            setChanged();
            notifyObservers();
            attendre(1000);
        }
        attendre(1000);
        joueurCourant.clearActions();
    }


    private void attendre(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public PhaseDeJeu getPhaseDeJeu() {
        return phaseDeJeu;
    }
}
