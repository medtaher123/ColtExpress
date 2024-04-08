package Controllers;

import Enums.Position;
import Models.Actions.Action;
import Models.Actions.ActionAvecEtat;
import Models.Bandit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Controllers.Jeu.nbActions;

public class Joueur extends Bandit {
    private final List<ActionAvecEtat> actions = new ArrayList<>(nbActions);

    public Joueur(String nom, Color couleur, Position position) {
        super(nom, couleur, position);
    }
    public Joueur(String nom, Color couleur){
        super(nom, couleur, Position.values()[(int)(Math.random() * Position.values().length)]);
    }


    public void addAction(Action action) {
        if(actions.size() < nbActions) {
            actions.add(new ActionAvecEtat(action, this));
        }
    }

    public boolean peutAjouterAction() {
        return actions.size() < nbActions;
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

    public void executerActions(int index) {
        actions.get(index).executer();
    }
    public boolean actionsRemplies() {
        return actions.size() == nbActions;
    }

}
