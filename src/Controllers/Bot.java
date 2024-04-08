package Controllers;

import Enums.Direction;
import Enums.Position;
import Models.Actions.*;

import java.awt.*;

public class Bot extends Joueur{

    private static final Action[] actionsPossibles = {
            new ActionDeplacement(Direction.BAS),
            new ActionDeplacement(Direction.HAUT),
            new ActionDeplacement(Direction.ARRIERE),
            new ActionDeplacement(Direction.AVANT),

            new ActionTirerHaut(),
            new ActionTirerBas(),
            new ActionTirerArriere(),
            new ActionTirerAvant(),

            new ActionCollecter()
    };
    private Jeu jeu;
    public Bot(String nom, Color couleur, Position position, Jeu jeu) {
        super(nom, couleur, position);
        this.jeu = jeu;
    }

    public Bot(String nom, Color couleur,Jeu jeu) {
        super(nom, couleur);
        this.jeu = jeu;
    }


    public void choisirActions() {
        for (int i = 0; i < jeu.getNbActions(); i++) {
            addAction(actionsPossibles[(int) (Math.random() * actionsPossibles.length)]);
        }
    }
}
