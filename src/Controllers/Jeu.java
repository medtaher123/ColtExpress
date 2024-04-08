package Controllers;

import Enums.PhaseDeJeu;
import Models.*;
import Vue.Vue;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Jeu extends Observable {

    private float vitesseAnimation = 1;
    public static int nbActions = 5;

    private List<Joueur> joueurs = new ArrayList<>();
    private int indexJoueurCourant = 0;
    private Train train;
    private Marshall marshall;

    private int indexAction = 0;

    private PhaseDeJeu phaseDeJeu = PhaseDeJeu.PLANIFICATION;


    public Jeu(int nbWagons){
        train = new Train(nbWagons);
        marshall = new Marshall();
        train.getWagons()[0].setMarshall(marshall);

    }

    public void startGame() {

        Vue ex = new Vue(this);
        ex.setVisible(true);

        if(getJoueurCourant() instanceof Bot){
            ((Bot)getJoueurCourant()).choisirActions();
            tourSuivant();
        }
    }


    public void tourSuivant() {

        if(phaseDeJeu == PhaseDeJeu.PLANIFICATION) {
            if (indexJoueurCourant < joueurs.size() - 1) {
                setJoueurCourant(indexJoueurCourant + 1);
                if(getJoueurCourant() instanceof Bot){
                    ((Bot)getJoueurCourant()).choisirActions();
                    tourSuivant();
                }
                return;
            }
            phaseDeJeu = PhaseDeJeu.EXECUTION;
            setJoueurCourant(0);
        }

        if(phaseDeJeu == PhaseDeJeu.EXECUTION) {
            executerActions();
            phaseDeJeu = PhaseDeJeu.PLANIFICATION;
            indexJoueurCourant = 0;
            attendre(2000);
            if(getJoueurCourant() instanceof Bot){
                ((Bot)getJoueurCourant()).choisirActions();
                tourSuivant();
            }
        }

    }

    public void ajouterJoueur(String nom, Color couleur,int nbBalles ,boolean estBot) {
        Joueur joueur;
        if(estBot){
            joueur = new Bot(nom, couleur,this);
        } else {
            joueur = new Joueur(nom, couleur);
        }
        joueur.setNbBalles(nbBalles);
        joueurs.add(joueur);
        int indexWagon = (int) (Math.random() * (train.getWagons().length - 1)) + 1;
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
        for (indexAction = 0; indexAction < nbActions; indexAction++) {
            for (int j = 0; j < joueurs.size(); j++) {
                setJoueurCourant(j);
                getJoueurCourant().executerActions(indexAction);
                setChanged();
                notifyObservers();
                attendre(2000);
            }
            setJoueurCourant(-1);
            marshall.effectuerAction();
            setChanged();
            notifyObservers();
            attendre(2000);
        }
        for (Joueur joueur : joueurs) {
            joueur.clearActions();
        }

    }


    private void attendre(long millis) {
        try {
            Thread.sleep((long) (millis/ vitesseAnimation));
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

    public void setVitesseAnimation(float vitesseAnimation) {
        this.vitesseAnimation = vitesseAnimation;
    }

    public void setNbActions(int value) {
        nbActions = value;
    }

    public int getNbActions() {
        return nbActions;
    }
}
