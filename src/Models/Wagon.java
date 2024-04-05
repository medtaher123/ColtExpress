package Models;

import Models.Butin.Butin;
import Enums.Position;

import java.util.ArrayList;
import java.util.List;

public class Wagon {

    private final Train train;

    private boolean locomotive;
    private boolean dernierWagon;
    private int index;

    private List<Butin> butins = new ArrayList<Butin>();
    private List<Bandit> banditsInterieurs = new ArrayList<Bandit>();
    private List<Bandit> banditsToit = new ArrayList<Bandit>();

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
        return dernierWagon;
    }
    public void setDernierWagon(boolean estDernierWagon) {
        dernierWagon = estDernierWagon;
    }

    public List<Butin> getButins() {
        return butins;
    }

    public List<Bandit> getBanditsInterieurs() {
        return banditsInterieurs;
    }

    public List<Bandit> getBanditsToit() {
        return banditsToit;
    }

    public boolean isDernierWagon() {
        return dernierWagon;
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
        bandit.setWagon(this);
    }
}
