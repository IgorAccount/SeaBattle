package ru.igor.tests.view;

import ru.igor.tests.battleField.BattleField;
import ru.igor.tests.constants.Constants;
import ru.igor.tests.controller.AIBrains;
import ru.igor.tests.model.CoordsOnField;
import ru.igor.tests.model.Ship;
import ru.igor.tests.model.ShipPosition;
import ru.igor.tests.model.ShipsFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static ru.igor.tests.constants.Constants.*;
import static ru.igor.tests.constants.Constants.DAMAGEDSHIP;
import static ru.igor.tests.constants.Constants.DAMAGEDWATER;

public class PlayerField extends JPanel {
    private BattleField bf;
    private int[][] playerField;
    private ArrayList<Ship> playerShips;
    private ShipsFactory shipsFactory;
    public boolean canAiMakeNextStep = true;
    AIBrains aiBrains;

    public PlayerField() {
        setSize(Constants.WIDTH, HEIGTH);
        bf = new BattleField();
        playerField = bf.getMyField();
        shipsFactory = new ShipsFactory();
        playerShips = shipsFactory.createShips();
        aiBrains = new AIBrains(playerShips, playerField);
        pushWater();
        pushShips();
    }

    public void attackPlayerField(){
        aiBrains.attack();
        canAiMakeNextStep = aiBrains.canAIMakeNextStop;

    }

    public int[][] getPlayerField() {
        return playerField;
    }

    public ArrayList<Ship> getPlayerShips() {
        return playerShips;
    }

    private void pushWater() {
        for (int i = 0; i < playerField.length; i++) {
            for (int j = 0; j < playerField[i].length; j++) {
                playerField[i][j] = WATERCELLS;
            }
        }
    }

    private void pushShips() {
        for (Ship array : playerShips) {
            int localX = array.getCoordX();
            int localY = array.getCoordY();
            if (array.getShipPosition() == ShipPosition.VERTICAL) {
                for (int i = 0; i < array.getDecks(); i++) {
                    playerField[i + localY][localX] = SHIPCELLS;
                    array.coordOnFields[i] = new CoordsOnField(localY + i, localX); // change to the right version "Y"
                }
            }
            if (array.getShipPosition() == ShipPosition.HORIZONTAL) {
                for (int i = 0; i < array.getDecks(); i++) {
                    playerField[localY][i + localX] = SHIPCELLS;
                    array.coordOnFields[i] = new CoordsOnField(localY, localX + i);
                }
            }
        }
    }

    public void draw(){
        this.repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int i = 0; i < playerField.length; i++) {
            g.drawLine(0, i * CELLSIZE, playerField.length * CELLSIZE, i * CELLSIZE);
        }

        for (int i = 0; i < playerField.length; i++) {
            g.drawLine(i * CELLSIZE, 0, i * CELLSIZE, playerField.length * CELLSIZE);
        }

        g.setColor(Color.BLACK);
        for (int i = 0; i < playerField.length; i++) {
            for (int j = 0; j < playerField[i].length; j++) {
                if (playerField[i][j] == SHIPCELLS) {
                    g.fillRect(j * CELLSIZE, i * CELLSIZE, CELLSIZE, CELLSIZE);
                }
            }
        }
        for (int i = 0; i < playerField.length; i++) {
            for (int j = 0; j < playerField[i].length; j++) {
                if (playerField[i][j] == WATERCELLS) {
                    g.setColor(Color.CYAN);
                    g.fillRect(j * CELLSIZE, i * CELLSIZE, CELLSIZE - 1, CELLSIZE - 1);
                }
                g.setColor(Color.green);
                if (playerField[i][j] == DAMAGEDWATER) {
                    g.fillRect(j * CELLSIZE, i * CELLSIZE, CELLSIZE - 1, CELLSIZE - 1);
                }
                g.setColor(Color.RED);
                if (playerField[i][j] == DAMAGEDSHIP) {
                    g.fillRect(j * CELLSIZE, i * CELLSIZE, CELLSIZE - 1, CELLSIZE - 1);
                }
            }
        }
    }
}