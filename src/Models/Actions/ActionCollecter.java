package Models.Actions;

import Enums.Position;
import Models.Bandit;

public class ActionCollecter extends Action {


    public ActionCollecter(Bandit bandit) {
        super(bandit);
    }

    @Override
    public void executerAction() {
        if (!peutExecuter()) {
            return;
        }


        System.out.println(bandit.getNom() + " collecte!");

        // collecter un element au hazard dans le wagon
        bandit.getWagon().collecterButin(bandit);
    }

    @Override
    public boolean peutExecuter() {

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
