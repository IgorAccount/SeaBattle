package ru.igor.tests.model;

import java.util.ArrayList;
import java.util.Random;


import static ru.igor.tests.constants.Constants.SHIPCELLS;

public class ShipsFactory {
    private int[][] aiField = new int[10][10];
    Random random = new Random();

    public ShipsFactory() {

    }

    private static Ship getShip(int decks, int y, int x, ShipPosition shipPosition) {

        if (decks > 0 && decks < 5) {
            return new Ship(decks, y, x, ShipState.ALIVE, shipPosition);
        }
        return null;
    }

    private boolean checkSurroundingBack(ShipPosition shipPosition, int shipDescks, int y, int x) {

        if (shipPosition == ShipPosition.HORIZONTAL) {
            for (int i = 0; i < shipDescks; i++) {
                if (i == 0) {
                    if (((x == 0 || (((x - 1 >= 0 && aiField[y][x - 1] != SHIPCELLS) && y == 0 && y + 1 < aiField.length && aiField[y + 1][x - 1] != SHIPCELLS) ||
                            ((x - 1 >= 0 && aiField[y][x - 1] != SHIPCELLS) && y == aiField.length - 1 && y - 1 >= 0 && aiField[y - 1][x - 1] != SHIPCELLS) ||
                            (x - 1 >= 0 && aiField[y][x - 1] != SHIPCELLS) && y - 1 >= 0 && y + 1 < aiField.length && aiField[y + 1][x - 1] != SHIPCELLS && aiField[y - 1][x - 1] != SHIPCELLS)

                    )))

                    {
                        return true;
                    }
                }
            }
        }

        if (shipPosition == ShipPosition.VERTICAL) {
            for (int i = 0; i < shipDescks; i++) {
                if (i == 0) {
                    if (((y == 0 || (((y - 1 >= 0 && aiField[y - 1][x] != SHIPCELLS) && x == 0 && x + 1 < aiField.length && aiField[y - 1][x + 1] != SHIPCELLS) ||
                            ((y - 1 >= 0 && aiField[y - 1][x] != SHIPCELLS) && x == aiField[i].length - 1 && x - 1 >= 0 && aiField[y - 1][x - 1] != SHIPCELLS) ||
                            (y - 1 >= 0 && aiField[y - 1][x] != SHIPCELLS) && x - 1 >= 0 && x + 1 < aiField.length && aiField[y - 1][x + 1] != SHIPCELLS && aiField[y - 1][x - 1] != SHIPCELLS)
                    ))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkSurroundingAhead(ShipPosition shipPosition, int shipDescks, int y, int x) {

        if (shipPosition == ShipPosition.HORIZONTAL) {
            for (int i = 0; i < shipDescks; i++) {
                if (i == shipDescks - 1) {
//if (x - 1 > -1 && (a[y][x-1] != SHIPCELLS || a[y][x-1] > -1) )
                    if ((
                            (x + i == aiField[i].length - 1 || (((x + i + 1 < aiField[i].length && aiField[y][x + i + 1] != SHIPCELLS && y == 0 && y + 1 < aiField.length && aiField[y + 1][x + i + 1] != SHIPCELLS) ||
                                    (x + i + 1 < aiField[i].length && aiField[y][x + i + 1] != SHIPCELLS && y == aiField.length - 1 && y - 1 >= 0 && aiField[y - 1][x + i + 1] != SHIPCELLS) ||
                                    (x + i + 1 < aiField[i].length && aiField[y][x + i + 1] != SHIPCELLS && y - 1 < aiField.length && y - 1 >= 0 && y + 1 < aiField.length && aiField[y - 1][x + i + 1] != SHIPCELLS && aiField[y + 1][x + i + 1] != SHIPCELLS)

                            ))))) {
                        return true;
                    }
                }
            }
        }

        if (shipPosition == ShipPosition.VERTICAL) {
            for (int i = 0; i < shipDescks; i++) {
                if (i == shipDescks - 1) {
                    if ((
                            ((y + i == aiField.length - 1) || (((y + i + 1 < aiField.length && aiField[y + i + 1][x] != SHIPCELLS && x == 0 && x + 1 < aiField[i].length && aiField[y + i + 1][x + 1] != SHIPCELLS) ||
                                    (y + i + 1 < aiField.length && aiField[y + i + 1][x] != SHIPCELLS && x == aiField[i].length - 1 && x - 1 >= 0 && aiField[y + i + 1][x - 1] != SHIPCELLS) ||
                                    (y + i + 1 < aiField.length && aiField[y + i + 1][x] != SHIPCELLS && x + 1 < aiField[i].length && x - 1 >= 0 && aiField[y + i + 1][x - 1] != SHIPCELLS && aiField[y + i + 1][x + 1] != SHIPCELLS)

                            ))))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkArrayBounds(ShipPosition shipPosition, int shipDescks, int localY, int localX) {
        int localCounter = 0;
        if (shipPosition == ShipPosition.HORIZONTAL) {
            for (int i = 0; i < shipDescks; i++) {
                if (localX + i < aiField[i].length && aiField[localY][localX + i] != SHIPCELLS) localCounter++;
            }
            if (localCounter == shipDescks) return true;
        }
        if (shipPosition == ShipPosition.VERTICAL) {
            for (int i = 0; i < shipDescks; i++) {
                if (localY + i < aiField.length && aiField[localY + i][localX] != SHIPCELLS) localCounter++;
            }
            if (localCounter == shipDescks) return true;
        }
        return false;
    }

    // Расстояние между клетками у+1 и у-1
    private boolean checkDistanceBetweenShipCell(ShipPosition shipPosition, int decks, int localY, int localX) {
        if (shipPosition == ShipPosition.HORIZONTAL) {
            int localCounter = 0;
            for (int i = 0; i < decks; i++) {
                if ((((localY == 0 || (localY - 1 >= 0 && aiField[localY - 1][localX + i] != SHIPCELLS)) &&
                        (localY == aiField.length - 1 || (localY + 1 < aiField.length && aiField[localY + 1][localX + i] != SHIPCELLS))) ||
                        (localY - 1 >= 0 && localY + 1 < aiField.length && aiField[localY - 1][localX + i] != SHIPCELLS) && aiField[localY + 1][localX + i] != SHIPCELLS)
                        ) {
                    localCounter++;
                }
            }
            if (localCounter == decks) {
                return true;
            }
        }

        if (shipPosition == ShipPosition.VERTICAL) {
            int localCounter = 0;
            for (int i = 0; i < decks; i++) {
                if ((((localX == 0 || (localX - 1 >= 0 && aiField[localY + i][localX - 1] != SHIPCELLS)) &&
                        (localX == aiField.length - 1 || (localX + 1 < aiField.length && aiField[localY + i][localX + 1] != SHIPCELLS))) ||
                        (localX - 1 >= 0 && localX + 1 < aiField.length && aiField[localY + i][localX - 1] != SHIPCELLS) && aiField[localY + i][localX + 1] != SHIPCELLS)
                        ) {
                    localCounter++;
                }
            }
            if (localCounter == decks) {
                return true;
            }
        }
        return false;
    }

    void fillField(ShipPosition shipPosition, Ship ship, int localY, int localX) {
        if (shipPosition == ShipPosition.HORIZONTAL) {
            //CoordsOnField[] coordsOnFields = ship.getCoordOnFields();
            for (int j = 0; j < ship.getDecks(); j++) {
                aiField[localY][localX + j] = SHIPCELLS;
                //coordsOnFields[j] = new CoordsOnField(localY, localX)
            }
        }
        if (shipPosition == ShipPosition.VERTICAL) {
            for (int j = 0; j < ship.getDecks(); j++) {
                aiField[localY + j][localX] = SHIPCELLS;
            }
        }
    }

    public ArrayList<Ship> createShips() {
        ArrayList<Ship> arrayList = new ArrayList<Ship>();
        int oneDeck = 4;
        int twoDecks = 3;
        int threeDecks = 2;
        int fourDecks = 1;
        boolean go = true;

        while (go) {
            Ship ship;
            ShipPosition shipposTemp;
            int localDecks = random.nextInt(5);
            int randX = random.nextInt(10);
            int randY = random.nextInt(10);
            shipposTemp = random.nextBoolean() ? ShipPosition.VERTICAL : ShipPosition.HORIZONTAL;

            if (localDecks == 4 && fourDecks != 0) {
                ship = getShip(localDecks, randY, randX, shipposTemp);
                int localX = ship.getCoordX();
                int localY = ship.getCoordY();

                if (checkArrayBounds(shipposTemp, localDecks, localY, localX) && checkDistanceBetweenShipCell(shipposTemp, localDecks, localY, localX) && checkSurroundingBack(shipposTemp, localDecks, localY, localX) && checkSurroundingAhead(shipposTemp, localDecks, localY, localX)) {
                    fillField(shipposTemp, ship, localY, localX);
                    arrayList.add(ship);
                    fourDecks--;
                }
            }
            if (localDecks == 3 && threeDecks != 0) {
                ship = getShip(localDecks, randY, randX, shipposTemp);
                int localX = ship.getCoordX();
                int localY = ship.getCoordY();
                if (checkArrayBounds(shipposTemp, localDecks, localY, localX) && checkDistanceBetweenShipCell(shipposTemp, localDecks, localY, localX) && checkSurroundingBack(shipposTemp, localDecks, localY, localX) && checkSurroundingAhead(shipposTemp, localDecks, localY, localX)) {
                    fillField(shipposTemp, ship, localY, localX);
                    arrayList.add(ship);
                    threeDecks--;
                }
            }

            if (localDecks == 2 && twoDecks != 0) {
                ship = getShip(localDecks, randY, randX, shipposTemp);
                int localX = ship.getCoordX();
                int localY = ship.getCoordY();

                if (checkArrayBounds(shipposTemp, localDecks, localY, localX) && checkDistanceBetweenShipCell(shipposTemp, localDecks, localY, localX) && checkSurroundingBack(shipposTemp, localDecks, localY, localX) && checkSurroundingAhead(shipposTemp, localDecks, localY, localX)) {
                    fillField(shipposTemp, ship, localY, localX);
                    arrayList.add(ship);
                    twoDecks--;
                }
            }

            if (localDecks == 1 && oneDeck != 0) {
                ship = getShip(localDecks, randY, randX, shipposTemp);
                int localX = ship.getCoordX();
                int localY = ship.getCoordY();

                if (checkArrayBounds(shipposTemp, localDecks, localY, localX) && checkDistanceBetweenShipCell(shipposTemp, localDecks, localY, localX) && checkSurroundingBack(shipposTemp, localDecks, localY, localX) && checkSurroundingAhead(shipposTemp, localDecks, localY, localX)) {
                    fillField(shipposTemp, ship, localY, localX);
                    arrayList.add(ship);
                    oneDeck--;
                }
            }

            if (threeDecks == 0 && twoDecks == 0 && oneDeck == 0 && fourDecks == 0) {
                go = false;
                //System.out.println(arrayList.size());
                return arrayList;
            }
        }
        return null;
    }
}
