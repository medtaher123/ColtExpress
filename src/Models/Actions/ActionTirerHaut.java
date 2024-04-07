package Models.Actions;

import Enums.Direction;
import Enums.Position;
import Models.Bandit;

public class ActionTirerHaut extends ActionTirer{
    public ActionTirerHaut() {
        super(Direction.HAUT);
    }

    @Override
    public boolean tirer(Bandit bandit) {
        System.out.println(bandit.getNom() + " tire vers le haut");
        Bandit[] banditsToit = bandit.getWagon().getBanditsToit();
        Bandit banditCible = choisirBandit(banditsToit, bandit);
        if(banditCible == null) {
            //System.out.println("Il n'y a pas de bandit sur le toit");
            return false;
        }
        banditCible.seFaitTirerDessus(bandit);
        return true;
    }


    @Override
    public String toString() {
        return "T⬆️";
    }
}
