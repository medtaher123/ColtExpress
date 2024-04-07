package Models.Actions;

import Enums.Position;
import Models.Bandit;

public class ActionCollecter extends Action {


    @Override
    public boolean executerAction(Bandit bandit) {

        System.out.println(bandit.getNom() + " collecte!");
        bandit.getWagon().collecterButin(bandit);
        return true;
    }

    @Override
    public boolean peutExecuter(Bandit bandit) {

        if (bandit.getWagon().getButins().isEmpty()) {
            System.out.println("Il n'y a plus de butin à collecter");
            return false;
        }
        if(bandit.getPosition() == Position.TOIT) {
            System.out.println(bandit + " ne peut pas collecter de butin sur le toit");
            return false;
        }
        return true;

    }

    @Override
    public String toString() {
        return "Collecter";
    }


}
