package Models;

import Enums.Direction;
import Models.Butin.Butin;
import Enums.Position;

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
        if(wagon.isLocomotive() && direction == Direction.AVANT) {
            System.out.println("C'est impossible de se déplacer vers l'avant, la locomotive est déjà en tête de train");
            return;
        }
        if(wagon.estDernierWagon() && direction == Direction.ARRIERE) {
            System.out.println("C'est impossible de se déplacer vers l'arrière, le wagon est déjà en queue de train");
            return;
        }
        if(position == Position.TOIT && direction == Direction.HAUT){
            System.out.println("C'est impossible de se déplacer vers le haut, le bandit est déjà sur le toit");
            return;
        }
        if(position == Position.INTERIEUR && direction == Direction.BAS){
            System.out.println("C'est impossible de se déplacer vers le bas, le bandit est déjà à l'intérieur");
            return;
        }

        switch (direction){
            case AVANT:
                wagon.retirerBandit(this);
                setWagon(wagon.getWagonSuivant());
                wagon.ajouterBandit(this);
                break;
            case ARRIERE:
                wagon.retirerBandit(this);
                setWagon(wagon.getWagonPrecedent());
                wagon.ajouterBandit(this);
                break;
            case HAUT:
                wagon.deplacerBanditToit(this);
                break;
            case BAS:
                wagon.deplacerBanditInterieur(this);
                break;
        }


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
