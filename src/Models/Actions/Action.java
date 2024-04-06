package Models.Actions;

import Models.Bandit;

public abstract class Action {

    protected Bandit bandit;

    public Action(Bandit bandit){
        this.bandit = bandit;
    }

    public boolean executer(){
        if(peutExecuter()){
            executerAction();
            return true;
        }
        return false;
    }

    protected abstract void executerAction();

    public abstract boolean peutExecuter();

    public abstract String toString();

}
