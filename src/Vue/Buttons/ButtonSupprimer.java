package Vue.Buttons;

import Vue.Vue;

public class ButtonSupprimer extends Button{
    public ButtonSupprimer(Vue vue) {
        super(vue, "␈");
    }

    @Override
    public void effectuerAction() {
        jeu.getJoueur().popAction();
    }

    @Override
    public boolean peutExecuter() {
        return !jeu.getJoueur().getActions().isEmpty();
    }
}
