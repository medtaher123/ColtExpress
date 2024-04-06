package Models.Actions;


import Enums.EtatAction;

public class ActionAvecEtat {
    private Action action;
    private EtatAction etat = EtatAction.EN_ATTENTE;

    public ActionAvecEtat(Action action) {
        this.action = action;
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
        if(action.executer()){
            etat = EtatAction.EXECUTEE;
        }else {
            etat = EtatAction.ECHOUEE;
        }
    }

    public String toString() {
        return action.toString();
    }
}