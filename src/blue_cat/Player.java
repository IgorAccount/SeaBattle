package blue_cat;

import java.awt.*;

public abstract class Player {
    protected Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public abstract void draw(Graphics2D g2d);

    public abstract void turn();
}
