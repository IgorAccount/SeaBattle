package blue_cat;

import java.awt.*;

public class AiPlayer extends Player {
    @Override
    public void draw(Graphics2D g2d) {
        System.out.println("AI draw");
    }

    @Override
    public void turn() {
        System.out.println("AI turn");

        game.switchNextPlayer();
    }
}
