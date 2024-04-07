package Vue.Buttons;

import Vue.Vue;

public class ButtonAction extends Button{


    public ButtonAction(Vue vue) {
        super(vue, "Action !");
    }

    @Override
    public void effectuerAction() {
        jeu.tourSuivant();
    }

    @Override
    public boolean peutExecuter() {
        return jeu.getJoueurCourant().actionsRemplies();
    }
}
