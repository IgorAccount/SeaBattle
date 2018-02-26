package ru.igor.tests.controller;

import ru.igor.tests.model.CoordsOnField;
import ru.igor.tests.model.Ship;
import ru.igor.tests.model.ShipPosition;
import ru.igor.tests.model.ShipState;

import java.util.ArrayList;
import java.util.Random;

import static ru.igor.tests.constants.Constants.DAMAGEDSHIP;
import static ru.igor.tests.constants.Constants.DAMAGEDWATER;
import static ru.igor.tests.constants.Constants.WATERCELLS;

public class AIBrains {
    private Random random = new Random();
    private int[][] aIField;
    private ArrayList<Ship> aIships;
    private boolean startAnalizedAttack = false;
    private Ship tempShip;
    private int tempFightY, tempFightX;
    public boolean canAIMakeNextStop = true;

    public AIBrains(ArrayList<Ship> shipArrayList, int[][] aIField) {
        this.aIField = aIField;
        this.aIships = shipArrayList;
    }

    private void useAnalizedAttack(Ship ship) {
        if (ship.getDamageCounter() > 0) {
            startAnalizedAttack = true;
        } else
            startAnalizedAttack = false;
    }

    private void setAnalizeAttack(ShipState shipState) {
        if (shipState == ShipState.DEAD) {
            startAnalizedAttack = false;
        }

        if (shipState != ShipState.DEAD) {
            startAnalizedAttack = true;
        }
    }

    public void attack() {
        int fightX = random.nextInt(10);
        int fightY = random.nextInt(10);

        if (startAnalizedAttack && tempShip.getShipState() != ShipState.DEAD) {
            analizedAttack(tempShip, tempFightY, tempFightX);
        } else if (checkDamagedCoords(fightY, fightX, aIships)) {
            if (!checkAlreadyDamaged(fightY, fightX)) {
                aIField[fightY][fightX] = DAMAGEDSHIP;
                canAIMakeNextStop = true;
                //attack();
            }
        } else if (!checkAlreadyDamaged(fightY, fightX)) {
            aIField[fightY][fightX] = DAMAGEDWATER;
            canAIMakeNextStop = false;
        } else if(checkAlreadyDamaged(fightY, fightX)){
            canAIMakeNextStop = true;
        }

    }


    private boolean checkDamagedCoords(int fightY, int fightX, ArrayList<Ship> aIships) {
        for (Ship ship : aIships) {
            CoordsOnField[] coordsOnFields = ship.getCoordOnFields();
            for (int i = 0; i < coordsOnFields.length; i++) {
                if (coordsOnFields[i].getY() == fightY && coordsOnFields[i].getX() == fightX) {
                    if (!crashedShip(ship)) {
                        ship.getDemaged();
                        useAnalizedAttack(ship);
                    }
                    setCurrentShip(ship, fightY, fightX);
                    return true;
                }
            }
        }
        return false;
    }

    boolean crashedShip(Ship ship) {
        if (ship.getShipState() == ShipState.DEAD) {
            return true;
        } else
            return false;
    }

    private boolean checkAlreadyDamaged(int fightY, int fightX) {
        if (aIField[fightY][fightX] == DAMAGEDSHIP || aIField[fightY][fightX] == DAMAGEDWATER) {

            return true;
        }
        return false;
    }

    void setCurrentShip(Ship ship, int fightY, int fightX) {
        if (!crashedShip(ship)) {
            tempShip = ship;
            tempFightY = fightY;
            tempFightX = fightX;
        }
    }

    boolean correspondTheCoordWithShip(Ship ship, int fightY, int fightX) {
        CoordsOnField[] coordsOnFields = ship.getCoordOnFields();
        for (int i = 0; i < coordsOnFields.length; i++) {
            if (coordsOnFields[i].getY() == fightY && coordsOnFields[i].getX() == fightX) {
                return true;
            }
        }
        return false;
    }

    private void analizedAttack(Ship ship, int fightY, int fightX) {
        if (getDamageCounter(ship) && !crashedShip(ship)) {
            if (ship.getShipPosition() == ShipPosition.VERTICAL) {
                for (int i = 0; i < 4; i++) {
                    if (fightY + i < aIField.length && correspondTheCoordWithShip(ship, fightY + i, fightX) && aIField[fightY + i][fightX] != DAMAGEDWATER && aIField[fightY + i][fightX] != DAMAGEDSHIP) {
                        ship.getDemaged();
                        aIField[fightY + i][fightX] = DAMAGEDSHIP;
                        canAIMakeNextStop = true;
                       setAnalizeAttack(ship.getShipState());
                        setCurrentShip(ship, fightY + i, fightX);

                        break;
                    } else if (fightY + i < aIField.length && aIField[fightY + i][fightX] == WATERCELLS) {
                        aIField[fightY + i][fightX] = DAMAGEDWATER;
                        canAIMakeNextStop = false;
                        break;

                    } else if (fightY - i >= 0 && correspondTheCoordWithShip(ship, fightY - i, fightX) && aIField[fightY - i][fightX] != DAMAGEDWATER && aIField[fightY - i][fightX] != DAMAGEDSHIP) {
                        ship.getDemaged();
                        aIField[fightY - i][fightX] = DAMAGEDSHIP;
                        setAnalizeAttack(ship.getShipState());
                        setCurrentShip(ship, fightY - i, fightX);
                        canAIMakeNextStop = true;
                        break;
                    } else if (fightY - i >= 0 && aIField[fightY - i][fightX] == WATERCELLS) {
                        aIField[fightY - i][fightX] = DAMAGEDWATER;
                        canAIMakeNextStop = false;
                        break;

                    }
                }

            }

            if (ship.getShipPosition() == ShipPosition.HORIZONTAL) {
                for (int i = 0; i < 4; i++) {
                    if (fightX + i < aIField.length && correspondTheCoordWithShip(ship, fightY, fightX + i) && aIField[fightY][fightX + i] != DAMAGEDWATER && aIField[fightY][fightX + i] != DAMAGEDSHIP) {
                        ship.getDemaged();
                        setAnalizeAttack(ship.getShipState());
                        aIField[fightY][fightX + i] = DAMAGEDSHIP;
                        setCurrentShip(ship, fightY, fightX + i);
                        canAIMakeNextStop = true;
                        break;
                    } else if (fightX + i < aIField.length && aIField[fightY][fightX + i] == WATERCELLS) {
                        aIField[fightY][fightX + i] = DAMAGEDWATER;
                        canAIMakeNextStop = false;
                        break;

                    } else if (fightX - i >= 0 && correspondTheCoordWithShip(ship, fightY, fightX - i) && aIField[fightY][fightX - i] != DAMAGEDWATER && aIField[fightY][fightX - i] != DAMAGEDSHIP) {
                        ship.getDemaged();
                        setAnalizeAttack(ship.getShipState());
                        aIField[fightY][fightX - i] = DAMAGEDSHIP;
                        setCurrentShip(ship, fightY, fightX - i);
                        canAIMakeNextStop = true;
                        break;
                    } else if (fightX - i >= 0 && aIField[fightY][fightX - i] == WATERCELLS) {
                        aIField[fightY][fightX - i] = WATERCELLS;
                        canAIMakeNextStop = false;
                        break;

                    }
                }
            }

        }
    }

    private boolean getDamageCounter(Ship ship) {
        if (ship.getDamageCounter() > 0) {
            return true;
        }
        return false;
    }

    private boolean getRandomDamageCounter(Ship ship) {
        if (ship.getDamageCounter() == 1) {
            return true;
        }
        return false;
    }

}

//if (getRandomDamageCounter(ship)) {
//
//        if (fightY + 1 < aIField.length && aIField[fightY + 1][fightX] != DAMAGEDSHIP && aIField[fightY + 1][fightX] != DAMAGEDWATER ) {
//        System.out.println("BEFORE DID1");
//        for (int j = 0; j < ship.getDecks(); j++) {
//        CoordsOnField[] shipCoordOnFields = ship.getCoordOnFields();
//        if (fightY + 1 == shipCoordOnFields[j].getY() && fightX == shipCoordOnFields[j].getX() || aIField[fightY+1][fightX] == WATERCELLS) {
//        if (fightY + 1 == shipCoordOnFields[j].getY() && fightX == shipCoordOnFields[j].getX()) {
//        aIField[fightY + 1][fightX] = DAMAGEDSHIP;
//        ship.getDemaged();
//        checkAdditionalState(ship.getShipState());
//        System.out.println("DID 1");
//        setCurrentShip(ship, fightY, fightX);
//
//        break;
//        }
//        }
//        if(aIField[fightY+1][fightX] == WATERCELLS){
//        aIField[fightY+1][fightX] = DAMAGEDWATER;
//        checkAdditionalState(ship.getShipState());
//        setCurrentShip(ship, fightY, fightX);
//        System.out.println("DID1 WATERCELLS");
//        break;
//
//        }
//        System.out.println("AFTER DID1");
//        }
//        } else if (fightX + 1 < aIField.length && aIField[fightY][fightX + 1] != DAMAGEDSHIP && aIField[fightY][fightX + 1] != DAMAGEDWATER  ) {
//        System.out.println("BEFORE DID2");
//        for (int j = 0; j < ship.getDecks(); j++) {
//        CoordsOnField[] shipCoordOnFields = ship.getCoordOnFields();
//        if (fightY == shipCoordOnFields[j].getY() && fightX + 1 == shipCoordOnFields[j].getX() || aIField[fightY][fightX+1] == WATERCELLS) {
//        if(fightY == shipCoordOnFields[j].getY() && fightX + 1 == shipCoordOnFields[j].getX()) {
//        aIField[fightY][fightX + 1] = DAMAGEDSHIP;
//        ship.getDemaged();
//        checkAdditionalState(ship.getShipState());
//        System.out.println("DID 2");
//        setCurrentShip(ship, fightY, fightX);
//        break;
//        }
//        if(aIField[fightY][fightX+1] == WATERCELLS){
//        aIField[fightY][fightX+1] = DAMAGEDWATER;
//        checkAdditionalState(ship.getShipState());
//        setCurrentShip(ship, fightY, fightX);
//        System.out.println("DID2 WATERCELLS");
//        break;
//        }
//        }
//        System.out.println("AFTER DID2");
//        }
//        } else if (fightY - 1 >= 0 && aIField[fightY - 1][fightX] != DAMAGEDSHIP && aIField[fightY - 1][fightX] != DAMAGEDWATER ) {
//        System.out.println("BEFORE DID3");
//        for (int j = 0; j < ship.getDecks(); j++) {
//        CoordsOnField[] shipCoordOnFields = ship.getCoordOnFields();
//        if (fightY - 1 == shipCoordOnFields[j].getY() && fightX == shipCoordOnFields[j].getX() || aIField[fightY - 1][fightX] == WATERCELLS) {
//        if(fightY - 1 == shipCoordOnFields[j].getY() && fightX == shipCoordOnFields[j].getX()) {
//        aIField[fightY - 1][fightX] = DAMAGEDSHIP;
//        ship.getDemaged();
//        checkAdditionalState(ship.getShipState());
//        System.out.println("DID 3");
//        setCurrentShip(ship, fightY, fightX);
//        break;
//        }
//        }
//        if(aIField[fightY - 1][fightX] == WATERCELLS){
//        aIField[fightY - 1][fightX] = DAMAGEDWATER;
//        checkAdditionalState(ship.getShipState());
//        setCurrentShip(ship, fightY, fightX);
//        System.out.println("DID3 WATERCELLS");
//        break;
//        }
//
//        System.out.println("AFTER DID3");
//        }
//        }else   if (fightY >= 0 && aIField[fightY][fightX - 1] != DAMAGEDSHIP && aIField[fightY][fightX - 1] != DAMAGEDWATER ) {
//        System.out.println("BEFORE DID4");
//        for (int j = 0; j < ship.getDecks(); j++) {
//        CoordsOnField[] shipCoordOnFields = ship.getCoordOnFields();
//        if (fightY == shipCoordOnFields[j].getY() && fightX - 1 == shipCoordOnFields[j].getY() || aIField[fightY][fightX - 1] == WATERCELLS) {
//        if(fightY == shipCoordOnFields[j].y && fightX - 1 == shipCoordOnFields[j].x) {
//        aIField[fightY][fightX - 1] = DAMAGEDSHIP;
//        ship.getDemaged();
//        checkAdditionalState(ship.getShipState());
//        System.out.println("DID 4");
//        setCurrentShip(ship, fightY, fightX);
//        break;
//        }
//        }
//        if(aIField[fightY][fightX - 1] == WATERCELLS){
//        aIField[fightY][fightX - 1] = WATERCELLS;
//        checkAdditionalState(ship.getShipState());
//        setCurrentShip(ship, fightY, fightX);
//        System.out.println("DID4 WATERCELLS");
//        break;
//        }
//        System.out.println("AFTER DID4");
//        }
//        }
//        System.out.println("CLOSER TO END AFTER ANALIZED");
//
//
//        }
//        System.out.println("ENDED&" + ship.getDamageCounter());
