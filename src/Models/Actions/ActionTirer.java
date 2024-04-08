package Models.Actions;

import Enums.Direction;
import Models.Bandit;

public abstract class ActionTirer extends Action {

    private Direction direction;

    public ActionTirer(Direction direction) {
        this.direction = direction;
    }

    /**
     * Chisi un bandit aléatoire parmi les bandits sauf le bandit courant
     * @param bandits
     * @param bandit
     * @return
     */
    protected Bandit choisirBandit(Bandit[] bandits, Bandit bandit) {
        if (bandits.length == 0 || (bandits.length == 1 && bandits[0] == bandit)) {
            return null;
        }
        Bandit banditChoisi = bandits[(int) (Math.random() * bandits.length)];
        while (banditChoisi == bandit) {
            banditChoisi = bandits[(int) (Math.random() * bandits.length)];
        }
        return banditChoisi;
    }

    protected boolean executerAction(Bandit bandit) {
        bandit.setNbBalles(bandit.getNbBalles() - 1);
        return tirer(bandit);
    }

    protected abstract boolean tirer(Bandit bandit);

    @Override
    public boolean peutExecuter(Bandit bandit) {
        if(bandit.getNbBalles() == 0){
            System.out.println(bandit.getNom() + " n'a plus de balles");
            return false;
        }
        return true;

    }


}
