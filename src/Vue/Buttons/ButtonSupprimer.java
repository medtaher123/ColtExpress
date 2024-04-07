package Vue.Buttons;

import Vue.Vue;

public class ButtonSupprimer extends Button{
    public ButtonSupprimer(Vue vue) {
        super(vue,  "⌫");
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
