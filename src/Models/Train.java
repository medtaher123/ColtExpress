package Models;

import Models.Butin.Magot;

public class Train {
    private final Wagon[] wagons;


    public Train(int nbWagons){
        wagons = new Wagon[nbWagons];
        initTrain();
    }

    public void initTrain(){
        for(int i = 0; i < wagons.length; i++){
            wagons[i] = new Wagon(this,i);
        }
        wagons[0].setLocomotive(true);
        wagons[0].ajouterButin(new Magot());
        wagons[wagons.length-1].setDernierWagon(true);
    }
    public Wagon[] getWagons() {
        return wagons;
    }

    public Wagon getWagonPrecedent(int index) {
        if(index < wagons.length-1){
            return wagons[index+1];
        }
        return null;
    }
    public Wagon getWagonSuivant(int index) {
        if(index > 0){
            return wagons[index-1];
        }
        return null;
    }

    public Wagon getLocomotive() {
        return wagons[0];
    }

    public Wagon getDernierWagon() {
        return wagons[wagons.length-1];
    }
}
