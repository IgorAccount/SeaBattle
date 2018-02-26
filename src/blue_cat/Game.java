package blue_cat;


import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {

    private boolean running = false;
    private Thread th;
    private Player player1; // usually human
    private Player player2; // usually ai
    private Player nextPlayer;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        player1.setGame(this);
        player2.setGame(this);
        this.nextPlayer = player1;
    }

    public void start() {
        running = true;
        th = new Thread(this, "mainLoop");
        th.start();
    }

    public void stop() {
        running = false;
        th.interrupt();
    }

    public void switchNextPlayer() {
        if (nextPlayer == player1) {
            nextPlayer = player2;
        } else {
            nextPlayer = player1;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        player1.draw(g2d);
        player2.draw(g2d);
    }

    public void run() {
        mainLoop();
    }

    public void mainLoop() {
        while (running) {
            nextPlayer.turn();
            repaint();
            try {
                Thread.sleep(40);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
