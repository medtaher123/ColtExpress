package Vue.Buttons;

import Controllers.Jeu;
import Vue.Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;

public abstract class Button extends Observable {

    private JButton jbutton;
    protected String text;
    protected Jeu jeu;


    public Button(Vue vue, String text, int keyCode){
        jbutton = new JButton(text);
        this.text = text;
        jeu = vue.getJeu();
        addObserver(vue);
        jbutton.addActionListener(e -> {
            onClick();
        });
        jbutton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0), "doClick");
        jbutton.getActionMap().put("doClick", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClick();
            }
        });
        jbutton.setToolTipText("Presser " + KeyEvent.getKeyText(keyCode) + " pour " + text);
    }
    private void onClick(){
        setChanged();
        notifyObservers();
        effectuerAction();
        setChanged();
        notifyObservers();
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
