package Vue.Buttons;

import Vue.Vue;

import java.awt.event.KeyEvent;

public class ButtonAction extends Button{


    public ButtonAction(Vue vue) {
        super(vue, "Action !", KeyEvent.VK_ENTER);
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
