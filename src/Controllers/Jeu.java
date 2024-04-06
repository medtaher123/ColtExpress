package Controllers;

import Enums.PhaseDeJeu;
import Enums.Position;
import Models.*;
import Models.Actions.Action;
import Models.Actions.ActionAvecEtat;
import Models.Butin.Bijous;
import Models.Butin.Bourse;
import Models.Butin.Butin;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Jeu extends Observable {

    public static final String NOM_BANDIT_1 = "Bandit 1";
    public static final int NB_ACTIONS = 3;

    private Bandit[] bandits = new Bandit[1];
    private Bandit joueurCourant;
    private Train train;
    private Marshall marshall;
    public static final int NB_WAGONS = 5;
    public List<ActionAvecEtat> actions = new ArrayList<>(NB_ACTIONS);

    private PhaseDeJeu phaseDeJeu = PhaseDeJeu.PLANIFICATION;



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


    public void addAction(Action action) {
        if(actions.size() < NB_ACTIONS) {
            actions.add(new ActionAvecEtat(action));
        }
    }
    public boolean peutAjouterAction() {
        return actions.size() < NB_ACTIONS;
    }
    public void popAction() {
        actions.removeLast();
    }
    public List<ActionAvecEtat> getActions() {
        return actions;
    }
    public void clearActions() {
        actions.clear();
    }

    public void executerActions() {
        for (ActionAvecEtat action : actions) {
            action.executer();
            setChanged();
            notifyObservers();

            attendre(1000);


        }
        attendre(1000);
        actions.clear();
    }


    private void attendre(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean peutExecuterActions() {
        return actions.size() == NB_ACTIONS;
    }
}
