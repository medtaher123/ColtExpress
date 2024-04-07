package Models.Actions;


import Enums.EtatAction;
import Models.Bandit;

public class ActionAvecEtat {
    private Action action;
    private EtatAction etat = EtatAction.EN_ATTENTE;

    private Bandit bandit;
    public ActionAvecEtat(Action action, Bandit bandit) {
        this.action = action;
        this.bandit = bandit;
    }

    public Action getAction() {
        return action;
    }
    public EtatAction getEtat() {
        return etat;
    }

    public void setEtat(EtatAction etat) {
        this.etat = etat;
    }

    public void executer() {
        if(action.executer(bandit)){
            etat = EtatAction.EXECUTEE;
        }else {
            etat = EtatAction.ECHOUEE;
        }
    }

    public String toString() {
        return action.toString();
    }
}