package ru.igor.tests.controller;

import ru.igor.tests.model.CoordsOnField;
import ru.igor.tests.model.Ship;
import ru.igor.tests.view.AIField;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static ru.igor.tests.constants.Constants.*;

public class SeaBattleListener implements MouseListener {
    private int xCoord;
    private int yCoord;
    private AIField aiField;
    private CounterListener counterListener;
    private int[][] shipField;

    public SeaBattleListener(AIField aiField) {
        this.aiField = aiField;
        shipField = aiField.getaIField();
    }


    boolean checkBoards(int y, int x) {
        if (y < shipField.length && y >= 0 && x < shipField.length && x >= 0 && aiField.canPlayerMakeNextStep == true) {
            return true;
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {


        counterListener = new CounterListener(e.getY(), e.getX());
        yCoord = counterListener.getY2();
        xCoord = counterListener.getX2();
        for(Ship ship: aiField.getaIships()){
            CoordsOnField[] coordsOnFields = ship.getCoordOnFields();
            for (int i = 0; i <coordsOnFields.length ; i++) {
                if(yCoord == coordsOnFields[i].getY() && xCoord == coordsOnFields[i].getX()){
                    ship.getDemaged();
                }

            }
        }
        if (checkBoards(yCoord, xCoord)) {
            if (shipField[yCoord][xCoord] == SHIPCELLS) {
                shipField[yCoord][xCoord] = DAMAGEDSHIP;
                aiField.draw();
                aiField.canPlayerMakeNextStep = true;
            } else if (shipField[yCoord][xCoord] == WATERCELLS) {
                shipField[yCoord][xCoord] = DAMAGEDWATER;
                aiField.draw();
                aiField.canPlayerMakeNextStep = false;
            } else if(shipField[yCoord][xCoord] == DAMAGEDWATER || shipField[yCoord][xCoord] == DAMAGEDSHIP ){
                aiField.draw();
                aiField.canPlayerMakeNextStep = true;
            }

        } else {aiField.canPlayerMakeNextStep = true;}




    }



    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
