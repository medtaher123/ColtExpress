package Models.Butin;

import java.awt.*;

public class Bourse extends Butin{


    public Bourse(int val) {
        super(val, "Bourse", new Color(50, 50, 0));
    }

    public Bourse() {
        this((int)(Math.random()*500));
    }
}

