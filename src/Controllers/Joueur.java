package Controllers;

import Enums.Position;
import Models.Actions.Action;
import Models.Actions.ActionAvecEtat;
import Models.Bandit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Controllers.Jeu.NB_ACTIONS;

public class Joueur extends Bandit {
    private final List<ActionAvecEtat> actions = new ArrayList<>(NB_ACTIONS);

    public Joueur(String nom, Color couleur, Position position) {
        super(nom, couleur, position);
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

    public void executerActions(int index) {
        actions.get(index).executer();
    }
    public boolean actionsRemplies() {
        return actions.size() == NB_ACTIONS;
    }


}
