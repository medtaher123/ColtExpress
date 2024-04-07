package Models.Actions;

import Enums.Direction;
import Enums.Position;
import Models.Bandit;
import Models.Wagon;

public class ActionTirerAvant extends ActionTirer {
    public ActionTirerAvant() {
        super(Direction.AVANT);

    }


    @Override
    protected boolean tirer(Bandit bandit) {
        // Si le bandit est à l'intérieur, il ne peut tirer que sur les bandits du wagon suivant
        // Si le bandit est sur le toit, il peut tirer sur les bandits du toit de tous les wagons suivants
        System.out.println(bandit.getNom() + " tire vers l'avant");
        Wagon wagonCible = bandit.getWagon().getWagonSuivant();

        if (wagonCible == null) {
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
            wagonCible = wagonCible.getWagonSuivant();
        }



        return false;
    }


    @Override
    public String toString() {
        return "T➡️";
    }
}
