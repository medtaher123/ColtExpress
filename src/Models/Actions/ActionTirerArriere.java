package Models.Actions;

import Enums.Direction;
import Enums.Position;
import Models.Bandit;
import Models.Wagon;

public class ActionTirerArriere extends ActionTirer{
    public ActionTirerArriere() {
        super(Direction.ARRIERE);
    }


    @Override
    protected boolean tirer(Bandit bandit) {
        System.out.println(bandit.getNom() + " tire vers l'arrière");
        Wagon wagonCible = bandit.getWagon().getWagonPrecedent();
        if(wagonCible == null){
            return false;
        }
        if(bandit.getPosition() == Position.INTERIEUR){
            Bandit[] banditsInterieurs = wagonCible.getBanditsInterieurs();
            Bandit banditCible = choisirBandit(banditsInterieurs, bandit);
            if(banditCible == null) {
                return false;
            }
            banditCible.seFaitTirerDessus(bandit);
            return true;
        }

        while(wagonCible != null){
            Bandit[] banditsToit = wagonCible.getBanditsToit();
            Bandit banditCible = choisirBandit(banditsToit, bandit);
            if(banditCible != null){
                banditCible.seFaitTirerDessus(bandit);
                return true;
            }
            wagonCible = wagonCible.getWagonPrecedent();
        }


        return true;
    }

    @Override
    public String toString() {
        return "T⬅️";
    }
}
