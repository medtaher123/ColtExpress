package Vue.Buttons;

import Vue.Vue;

import java.awt.event.KeyEvent;

public class ButtonSupprimer extends Button{
    public ButtonSupprimer(Vue vue) {
        super(vue,  "⌫", KeyEvent.VK_BACK_SPACE);
    }

    @Override
    public void effectuerAction() {
        if(!peutExecuter()) return;
        jeu.getJoueurCourant().popAction();
    }

    @Override
    public boolean peutExecuter() {
        return !jeu.getJoueurCourant().getActions().isEmpty();
    }
}
