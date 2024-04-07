package Models;

import Models.Butin.Bijous;
import Models.Butin.Bourse;
import Models.Butin.Butin;
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
        initButins();
        wagons[0].setLocomotive(true);
    }

    private void initButins() {
        for (int j = 1; j < wagons.length; j++) {
            int nbButins = (int) (Math.random() * 4) + 1;
            for (int i = 0; i < nbButins; i++) {
                Butin butin;
                if (Math.random() < 0.5) {
                    butin = new Bijous();
                } else {
                    butin = new Bourse();
                }
                wagons[j].ajouterButin(butin);
            }
        }
        wagons[0].ajouterButin(new Magot());
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
