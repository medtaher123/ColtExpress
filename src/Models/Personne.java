package Models;

import Enums.Direction;
import Enums.Position;

import java.awt.*;

public abstract class Personne {
    protected final String nom;
    Wagon wagon;
    private final Color couleur;
    protected Position position;

    public Personne(String nom, Color couleur, Position position){
        this.nom = nom;
        this.couleur = couleur;
        this.position = position;
    }

    public void setWagon(Wagon wagon){
        this.wagon = wagon;
    }

    public abstract void seDeplacer(Direction direction);

    public Color getCouleur() {
        return couleur;
    }

    public String getNom() {
        return nom;
    }



    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Wagon getWagon() {
        return wagon;
    }

}
