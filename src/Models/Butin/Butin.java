package Models.Butin;

import java.awt.*;

public abstract class Butin {

    protected int valeur;

    private Color couleur;
    public Butin(int val, Color couleur){
        valeur = val;
        this.couleur = couleur;
    }

    public int getValeur() {
        return valeur;
    }

    public Color getCouleur() {
        return couleur;
    }

}
