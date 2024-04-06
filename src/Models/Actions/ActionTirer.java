package Models.Actions;

import Models.Bandit;

public class ActionTirer extends Action {

    public ActionTirer(Bandit bandit){
        super(bandit);
    }
    @Override
    public void executerAction() {
        System.out.println(bandit.getNom() + " tire!");
    }

    @Override
    public boolean peutExecuter() {
        return false;
    }

    @Override
    public String toString() {
        return "Tirer";
    }
}
