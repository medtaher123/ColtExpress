package Models;

import Models.Butin.Butin;
import Enums.Position;

import java.util.ArrayList;
import java.util.List;

public class Wagon {

    private final Train train;

    private boolean locomotive;
    private final int index;

    private Marshall marshall=null;
    private final List<Butin> butins = new ArrayList<Butin>();
    private final List<Bandit> banditsInterieurs = new ArrayList<Bandit>();
    private final List<Bandit> banditsToit = new ArrayList<Bandit>();

    public Wagon(Train train, int index) {
        this(train, index, false);
    }
    public Wagon(Train train, int index, boolean locomotive){
        this.train = train;
        this.locomotive = locomotive;
        this.index = index;
    }

    public boolean isLocomotive() {
        return locomotive;
    }
    public void setLocomotive(boolean estLocomotive) {
        locomotive = estLocomotive;
    }

    public void ajouterButin(Butin butin) {
        butins.add(butin);
    }

    public boolean estDernierWagon() {
        return index == train.getWagons().length - 1;
    }

    public List<Butin> getButins() {
        return butins;
    }

    public void deplacerBanditInterieur(Bandit bandit){
        banditsToit.remove(bandit);
        banditsInterieurs.add(bandit);
        bandit.setPosition(Position.INTERIEUR);
    }
    public void deplacerBanditToit(Bandit bandit){
        banditsInterieurs.remove(bandit);
        banditsToit.add(bandit);
        bandit.setPosition(Position.TOIT);
    }


    public Wagon getWagonSuivant() {
        return train.getWagonSuivant(index);
    }
    public Wagon getWagonPrecedent() {
        return train.getWagonPrecedent(index);
    }


    public void retirerBandit(Bandit bandit, Position position) {
        if(position == Position.INTERIEUR) {
            banditsInterieurs.remove(bandit);
        } else {
            banditsToit.remove(bandit);
        }
    }
    public void retirerBandit(Bandit bandit) {
        retirerBandit(bandit, bandit.getPosition());
    }

    public void ajouterBandit(Bandit bandit) {
        ajouterBandit(bandit, bandit.getPosition());
    }
    public void ajouterBandit(Bandit bandit, Position position) {
        if(position == Position.INTERIEUR) {
            banditsInterieurs.add(bandit);
        } else {
            banditsToit.add(bandit);
        }
        bandit.setPosition(position);
        bandit.setWagon(this);
    }

    public void setMarshall(Marshall marshall) {
        this.marshall = marshall;
        marshall.setWagon(this);
    }
    public Marshall getMarshall() {
        return marshall;
    }
    public void retirerMarshall() {
        marshall = null;
    }
    public void collecterButin(Bandit bandit) {
        if(butins.isEmpty()) {
            System.out.println("Il n'y a plus de butin à collecter");
            return;
        }

        // collecter un element au hazard dans le wagon
        Butin butin = butins.get((int) (Math.random() * butins.size()));
        butins.remove(butin);
        bandit.ajouterButin(butin);
    }

    public Bandit[] getBanditsInterieurs() {
        return banditsInterieurs.toArray(new Bandit[0]);
    }
    public Bandit[] getBanditsToit() {
        return banditsToit.toArray(new Bandit[0]);
    }
}
