package Vue.Buttons;

import Controllers.Jeu;
import Vue.Vue;

import javax.swing.*;
import java.util.Observable;

public abstract class Button extends Observable {

    private JButton jbutton;
    protected String text;
    protected Jeu jeu;


    public Button(Vue vue, String text){
        jbutton = new JButton(text);
        this.text = text;
        jeu = vue.getJeu();
        addObserver(vue);
        jbutton.addActionListener(e -> {
            effectuerAction();
            setChanged();
            notifyObservers();
        });
    }

    public abstract void effectuerAction();

    public abstract boolean peutExecuter();

    public void setEnabled(boolean enabled) {
        jbutton.setEnabled(enabled && peutExecuter());
    }



    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }


    public JButton getJButton() {
        return jbutton;
    }


}
