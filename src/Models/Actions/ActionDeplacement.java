package Models.Actions;

import Enums.Direction;
import Enums.Position;
import Models.Bandit;

public class ActionDeplacement extends Action {


    private Direction direction;
    public ActionDeplacement(Direction direction) {
        this.direction = direction;
    }

    public boolean executerAction(Bandit bandit){

        switch (direction){
            case AVANT:
                bandit.getWagon().retirerBandit(bandit);
                bandit.getWagon().getWagonSuivant().ajouterBandit(bandit);
                System.out.println( bandit.getNom() + " se déplace vers l'avant");
                break;
            case ARRIERE:
                bandit.getWagon().retirerBandit(bandit);
                bandit.getWagon().getWagonPrecedent().ajouterBandit(bandit);
                System.out.println( bandit.getNom() + " se déplace vers l'arrière");
                break;
            case HAUT:
                bandit.getWagon().deplacerBanditToit(bandit);
                System.out.println( bandit.getNom() + " grimpe sur le toit");
                break;
            case BAS:
                bandit.getWagon().deplacerBanditInterieur(bandit);
                System.out.println( bandit.getNom() + " descend à l'intérieur");
                break;
        }



        System.out.println( bandit + ": Je me déplace vers " + direction);
        return true;
    }

    public boolean peutExecuter(Bandit bandit){
        if(bandit.getWagon().isLocomotive() && direction == Direction.AVANT) {
            System.out.println( bandit + " est déjà dans la locomotive");
            return false;
        }
        if(bandit.getWagon().estDernierWagon() && direction == Direction.ARRIERE) {
            System.out.println(bandit + " est déjà dans le dernier wagon");
            return false;
        }
        if(bandit.getPosition() == Position.TOIT && direction == Direction.HAUT){
            System.out.println(bandit +" est déjà sur le toit");
            return false;
        }
        if(bandit.getPosition() == Position.INTERIEUR && direction == Direction.BAS){
            System.out.println(bandit + " est déjà à l'intérieur");
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        switch (direction){
            case AVANT:
                return "➡️";
            case ARRIERE:
                return "⬅️";
            case HAUT:
                return "⬆️";
            case BAS:
                return "⬇️";
            default:
                return "Déplacement";
        }
    }
}
