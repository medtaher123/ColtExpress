package Vue.Buttons;

import Models.Actions.Action;
import Vue.Vue;

import javax.swing.JButton;

public class ButtonActionBandit extends Button{

    private Action action;

    public ButtonActionBandit(Action action, Vue vue) {
        super(vue, action.toString());
        this.action = action;
    }

    public void effectuerAction() {
        //action.executer();
        jeu.getJoueur().addAction(action);
    }

    @Override
    public boolean peutExecuter() {
        return jeu.getJoueur().peutAjouterAction(); // && action.peutExecuter()
    }




}
