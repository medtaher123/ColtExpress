package Models;

import Models.Butin.Butin;
import Models.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bandit {

    Wagon wagon;
    private List<Butin> butins = new ArrayList<Butin>();
    private Color couleur;
    private Position position;

    private String nom = "Joe Dalton";
    public Bandit(String nom, Color couleur, Position position){
        this.nom = nom;
        this.couleur = couleur;
        this.position = position;
    }

    public void ajouterButin(Butin butin){
        butins.add(butin);
    }
    public void retirerButin(Butin butin){
        butins.remove(butin);
    }
    public void setWagon(Wagon wagon){
        this.wagon = wagon;
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

    public void seDeplacer(Direction direction){
        System.out.println( "Je me déplace vers " + direction);
    }

    public Color getCouleur() {
        return couleur;
    }

    public String getNom() {
        return nom;
    }


    public void setNom(String a) {
        this.nom = a;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
