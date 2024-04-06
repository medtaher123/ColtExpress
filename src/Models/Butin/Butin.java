package Models.Butin;

import java.awt.*;

public abstract class Butin {

    protected int valeur;

    protected String nom;
    private Color couleur;
    public Butin(int valeur, String nom, Color couleur){
        this.valeur = valeur;
        this.nom = nom;
        this.couleur = couleur;
    }

    public int getValeur() {
        return valeur;
    }

    public Color getCouleur() {
        return couleur;
    }

    public String getNom() {
        return nom;
    }

}
