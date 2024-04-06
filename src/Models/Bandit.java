package Models;

import Enums.Direction;
import Models.Butin.Butin;
import Enums.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bandit extends Personne{


    private List<Butin> butins = new ArrayList<Butin>();




    public Bandit(String nom, Color couleur, Position position){
        super(nom, couleur, position);
    }

    public void ajouterButin(Butin butin){
        butins.add(butin);
    }
    public void retirerButin(Butin butin){
        butins.remove(butin);
    }

    public void deposerButin(Butin butin){
        wagon.ajouterButin(butin);
        retirerButin(butin);
    }
    public void deposerToutButin(){
        for(Butin butin : butins){
            wagon.ajouterButin(butin);
        }
        butins.clear();
    }


    public int getScore() {
        return butins.stream().mapToInt(Butin::getValeur).sum();
    }

    public void seDeplacer(Direction direction){
        if(wagon.isLocomotive() && direction == Direction.AVANT) {
            System.out.println( nom + " est déjà dans la locomotive");
            return;
        }
        if(wagon.estDernierWagon() && direction == Direction.ARRIERE) {
            System.out.println(nom + " est déjà dans le dernier wagon");
            return;
        }
        if(position == Position.TOIT && direction == Direction.HAUT){
            System.out.println(nom +" est déjà sur le toit");
            return;
        }
        if(position == Position.INTERIEUR && direction == Direction.BAS){
            System.out.println(nom + " est déjà à l'intérieur");
            return;
        }

        switch (direction){
            case AVANT:
                wagon.retirerBandit(this);
                setWagon(wagon.getWagonSuivant());
                wagon.ajouterBandit(this);
                System.out.println( nom + " se déplace vers l'avant");
                break;
            case ARRIERE:
                wagon.retirerBandit(this);
                setWagon(wagon.getWagonPrecedent());
                wagon.ajouterBandit(this);
                System.out.println( nom + " se déplace vers l'arrière");
                break;
            case HAUT:
                wagon.deplacerBanditToit(this);
                System.out.println( nom + " grimpe sur le toit");
                break;
            case BAS:
                wagon.deplacerBanditInterieur(this);
                System.out.println( nom + " descend à l'intérieur");
                break;
        }

    }

    public void seFaitTirerDessus() {
        // deposer un butin au hazard
        if(!butins.isEmpty()) {
            Butin butin = butins.get((int) (Math.random() * butins.size()));
            deposerButin(butin);
        }
        seDeplacer(Direction.HAUT);


    }
}
