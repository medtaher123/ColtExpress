package Controllers;

import Enums.PhaseDeJeu;
import Enums.Position;
import Models.*;
import Models.Butin.Bijous;
import Models.Butin.Bourse;
import Models.Butin.Butin;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Jeu extends Observable {

    public static final float VITESSE = 2;
    public static final String NOM_BANDIT_1 = "Bandit 1";
    public static final int NB_ACTIONS = 5;

    private List<Joueur> joueurs = new ArrayList<>();
    private int indexJoueurCourant = 0;
    private Train train;
    private Marshall marshall;
    public static final int NB_WAGONS = 5;

    private int indexAction = 0;

    private PhaseDeJeu phaseDeJeu = PhaseDeJeu.PLANIFICATION;



    public void startGame() {
        train = new Train(NB_WAGONS);

        ajouterJoueur(NOM_BANDIT_1, new Color(13, 13, 104), Position.TOIT,3);
        ajouterJoueur("Bandit 2", new Color(255, 0, 0), Position.INTERIEUR,4);

        marshall = new Marshall();
        train.getWagons()[0].setMarshall(marshall);


    }


    public void tourSuivant() {

        // rend le joueur suivant dans le tableau de joueurs le joueur courant
        if(phaseDeJeu == PhaseDeJeu.PLANIFICATION) {
            if (indexJoueurCourant != joueurs.size() - 1) {
                setJoueurCourant(indexJoueurCourant + 1);
                return;
            }
            setJoueurCourant(0);
            phaseDeJeu = PhaseDeJeu.EXECUTION;
        }

        if(phaseDeJeu == PhaseDeJeu.EXECUTION) {
            executerActions();
            phaseDeJeu = PhaseDeJeu.PLANIFICATION;
        }

    }

    private void ajouterJoueur(String nom, Color couleur, Position position, int indexWagon) {
        joueurs.add(new Joueur(nom, couleur, position, joueurs.size()));
        train.getWagons()[indexWagon].ajouterBandit(joueurs.getLast());

    }

    public Train getTrain() {
        return train;
    }

    public Joueur getJoueurCourant() {
        if(indexJoueurCourant == -1){
            return null;
        }
        return joueurs.get(indexJoueurCourant);
    }
    public Personne getJoueurCourantOuMarshall() {
        if(indexJoueurCourant == -1){
            return marshall;
        }
        return joueurs.get(indexJoueurCourant);
    }

    public Marshall getMarshall() {
        return marshall;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }




    public void executerActions() {
        for (indexAction = 0; indexAction < NB_ACTIONS; indexAction++) {
            for (int j = 0; j < joueurs.size(); j++) {
                setJoueurCourant(j);
                getJoueurCourant().executerActions(indexAction);
                setChanged();
                notifyObservers();
                attendre(2000);
            }
            setJoueurCourant(-1);
            marshall.effectuerAction();
            attendre(2000);
        }
        indexJoueurCourant = 0;

        attendre(2000);
        for (Joueur joueur : joueurs) {
            joueur.clearActions();
        }

    }


    private void attendre(long millis) {
        try {
            Thread.sleep((long) (millis/VITESSE));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public PhaseDeJeu getPhaseDeJeu() {
        return phaseDeJeu;
    }

    private void setJoueurCourant(int index){
        indexJoueurCourant = index;
        setChanged();
        notifyObservers();
        attendre(2000);
    }
}
