package ru.igor.tests.view;

import ru.igor.tests.controller.AIBrains;
import ru.igor.tests.battleField.BattleField;
import ru.igor.tests.constants.Constants;
import ru.igor.tests.controller.SeaBattleListener;
import ru.igor.tests.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static ru.igor.tests.constants.Constants.*;

public class AIField extends JPanel {
    private BattleField bf;
    private int[][] aIField;
    private ArrayList<Ship> aIships;
    private ShipsFactory shipsFactory;
    public boolean canPlayerMakeNextStep = false;

    AIField() {
        setSize(Constants.WIDTH, HEIGTH);
        bf = new BattleField();
        aIField = bf.getAiField();
        shipsFactory = new ShipsFactory();
        aIships = shipsFactory.createShips();
        pushWater();
        pushShips();
        addMouseListener(new SeaBattleListener(this));
    }

    public int[][] getaIField() {
        return aIField;
    }

    public ArrayList<Ship> getaIships() {
        return aIships;
    }

    void pushWater() {
        for (int i = 0; i < aIField.length; i++) {
            for (int j = 0; j < aIField[i].length; j++) {
                aIField[i][j] = WATERCELLS;
            }
        }
    }

    public void draw() {
        this.repaint();
    }

    void pushShips() {
        for (Ship array : aIships) {
            int localX = array.getCoordX();
            int localY = array.getCoordY();
            if (array.getShipPosition() == ShipPosition.VERTICAL) {
                for (int i = 0; i < array.getDecks(); i++) {
                    aIField[i + localY][localX] = SHIPCELLS;
                    array.coordOnFields[i] = new CoordsOnField(localY + i, localX); // change to the right version "Y"
                }
            }
            if (array.getShipPosition() == ShipPosition.HORIZONTAL) {
                for (int i = 0; i < array.getDecks(); i++) {
                    aIField[localY][i + localX] = SHIPCELLS;
                    array.coordOnFields[i] = new CoordsOnField(localY, localX + i);
                }
            }
        }
    }

    void deadShips(Graphics2D graphics2D) {
        for (Ship array : aIships) {
            if (array.getShipState() == ShipState.DEAD) {
                CoordsOnField[] coordsOnFields = array.getCoordOnFields();
                for (int i = 0; i < coordsOnFields.length; i++) {
                    int y = coordsOnFields[i].y;
                    int x = coordsOnFields[i].x;

                }
            }

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int i = 0; i < aIField.length; i++) {
            g.drawLine(0, i * CELLSIZE, aIField.length * CELLSIZE, i * CELLSIZE);
        }

        for (int i = 0; i < aIField.length; i++) {
            g.drawLine(i * CELLSIZE, 0, i * CELLSIZE, aIField.length * CELLSIZE);
        }


        for (int i = 0; i < aIField.length; i++) {
            for (int j = 0; j < aIField[i].length; j++) {
                if (aIField[i][j] == WATERCELLS || aIField[i][j] == SHIPCELLS) {
                    g.setColor(Color.CYAN);
                    g.fillRect(j * CELLSIZE, i * CELLSIZE, CELLSIZE - 1, CELLSIZE - 1);
                }
                g.setColor(Color.green);
                if (aIField[i][j] == DAMAGEDWATER) {
                    g.fillRect(j * CELLSIZE, i * CELLSIZE , (CELLSIZE - 1), (CELLSIZE - 1) );
                }
                g.setColor(Color.RED);
                if (aIField[i][j] == DAMAGEDSHIP) {
                    g.fillRect(j * CELLSIZE, i * CELLSIZE, CELLSIZE - 1, CELLSIZE - 1);
                }
            }
        }

        for (Ship array : aIships) {
                if (array.getShipState() == ShipState.DEAD) {
                CoordsOnField[] coordsOnFields = array.getCoordOnFields();
                for (int i = 0; i < coordsOnFields.length; i++) {
                    int y = coordsOnFields[i].getY();
                    int x = coordsOnFields[i].getX();
                    g.setColor(Color.MAGENTA);
                    g.fillRect(x * CELLSIZE, y * CELLSIZE, CELLSIZE - 1, CELLSIZE - 1);
                }
            }

        }

    }
}



/*
    void aiAttack() {
        while (checkWin) {
            if (playerField.nextStepMyField) {
                nextStepAiField = true;
                checkNotWinAiField();

                synchronized (playerfield) {
                    playerfiled.notify
                }

            } else if (nextStepAiField) {
                aiSteps.attack();
                this.repaint();
                checkNotWinMyField();
                playerField.nextStepMyField = true;
            }
        }
    }

*/