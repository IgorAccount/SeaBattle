package blue_cat;

public class Main {
    public static void main(String[] args) {

        Game game = new Game(new HumanPlayer(), new AiPlayer());
        game.start();
    }
}
