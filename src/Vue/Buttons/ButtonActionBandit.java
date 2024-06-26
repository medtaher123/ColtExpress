package Vue.Buttons;

import Models.Actions.Action;
import Vue.Vue;

import javax.swing.JButton;

public class ButtonActionBandit extends Button{

    private Action action;

    public ButtonActionBandit(Action action, Vue vue, int keyCode) {
        super(vue, action.toString(), keyCode);
        this.action = action;
    }

    public void effectuerAction() {
        //action.executer();
        jeu.getJoueurCourant().addAction(action);
    }

    @Override
    public boolean peutExecuter() {
        return jeu.getJoueurCourant().peutAjouterAction(); // && action.peutExecuter()
    }




}
