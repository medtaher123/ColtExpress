import Controllers.Jeu;
import Vue.Vue;

public class Main {
    public static void main(String[] args) {

        Jeu jeu = new Jeu();
        jeu.startGame();

        Vue ex = new Vue(jeu);
        ex.setVisible(true);

    }
}