package Models.Actions;

import Enums.Direction;
import Enums.Position;
import Models.Bandit;

public class ActionTirerBas extends ActionTirer{
    public ActionTirerBas() {
        super(Direction.BAS);
    }

    @Override
    protected boolean tirer(Bandit bandit) {
        System.out.println(bandit.getNom() + " tire vers le bas");

        Bandit[] banditsBas = bandit.getWagon().getBanditsInterieurs();
        Bandit banditCible = choisirBandit(banditsBas, bandit);
        if(banditCible == null) {
            //System.out.println("Il n'y a pas de bandit à l'intérieur");
            return false;
        }
        banditCible.seFaitTirerDessus(bandit);
        return true;
    }


    @Override
    public String toString() {
        return "T⬇️";
    }
}
