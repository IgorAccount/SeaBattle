package blue_cat;

import java.awt.*;

public class HumanPlayer extends Player {
    @Override
    public void draw(Graphics2D g2d) {
        System.out.println("Human draw");
    }

    @Override
    public void turn() {
        System.out.println("Human turn");

        game.switchNextPlayer();
    }
}
