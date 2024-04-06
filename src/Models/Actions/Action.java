package Models.Actions;

import Models.Bandit;

public abstract class Action {

    protected Bandit bandit;

    public Action(Bandit bandit){
        this.bandit = bandit;
    }

    public abstract void executer();

    public abstract boolean peutExecuter();

    public abstract String toString();

}
