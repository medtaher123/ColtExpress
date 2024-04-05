package Models;

import Models.Butin.Butin;
import Models.Butin.Magot;
import Models.Position;

import java.util.ArrayList;
import java.util.Collection;
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
    public void setDernierWagon(boolean wagon) {
        dernierWagon = wagon;
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

}
