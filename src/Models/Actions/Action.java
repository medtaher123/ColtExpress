package Models.Actions;

import Models.Bandit;

public abstract class Action {




    public final boolean executer(Bandit bandit){
        if(peutExecuter(bandit)){
            return executerAction(bandit);
        }
        return false;
    }

    protected abstract boolean executerAction(Bandit bandit);

    public abstract boolean peutExecuter(Bandit bandit);

    public abstract String toString();


}
