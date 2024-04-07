package Models;

import Enums.Direction;
import Enums.Position;

import java.awt.*;

public class Marshall extends Personne{

    public static final double NERVOSITE_MARSHALL = 0.3;

    public Marshall(){
        super("Marshal", new Color(255,255,225), Position.INTERIEUR);
    }

    public void seDeplacer() {
        if(Math.random() < NERVOSITE_MARSHALL){
        System.out.println("Le Marshall se déplace");
            if(wagon.isLocomotive()) {
                seDeplacer(Direction.ARRIERE);
                return;
            }
            if(wagon.estDernierWagon()) {
                seDeplacer(Direction.AVANT);
                return;
            }

            Direction direction= Direction.ARRIERE;
            if(Math.random() < 0.5) {
                direction = Direction.AVANT;
            }
            seDeplacer(direction);
        }
    }

    public void effectuerAction() {
        seDeplacer();
        tirerSurBandits();
    }

    private void tirerSurBandits() {

        for(Bandit bandit : wagon.getBanditsInterieurs()){
            System.out.println("Le Marshall tire sur " + bandit.getNom());
            bandit.seFaitTirerDessus(this);
        }
    }

    @Override
    public void seDeplacer(Direction direction) {
        if(wagon.isLocomotive() && direction == Direction.AVANT) {
            System.out.println( nom + " est déjà dans la locomotive");
            return;
        }
        if(wagon.estDernierWagon() && direction == Direction.ARRIERE) {
            System.out.println(nom + " est déjà dans le dernier wagon");
            return;
        }
        wagon.retirerMarshall();
        switch (direction){
            case AVANT:
                wagon.getWagonSuivant().setMarshall(this);
                break;
            case ARRIERE:
                wagon.getWagonPrecedent().setMarshall(this);
                break;
        }
        System.out.println(nom + " se déplace vers " + direction);
    }
}
