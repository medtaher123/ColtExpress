import Controllers.Game;
import View.View;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();
        game.startGame();

        View ex = new View(game);
        ex.setVisible(true);

    }
}