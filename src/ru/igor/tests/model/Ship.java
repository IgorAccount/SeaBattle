package ru.igor.tests.model;

public class Ship {

    private int decks;
    private int coordX;
    private int coordY;
    private ShipState shipState;
    private ShipPosition shipPosition;
    public CoordsOnField[] coordOnFields;
    private int damageCounter = 0;

    Ship(int decks, int coordY, int coordX, ShipState shipState, ShipPosition shipPosition) {
        this.decks = decks;
        this.coordX = coordX;
        this.coordY = coordY;
        this.shipState = shipState;
        this.shipPosition = shipPosition;
        coordOnFields = new CoordsOnField[decks];
    }

    public void getDemaged() {
        decks--;
        damageCounter++;
        shipState = ShipState.INJURED;
        if (decks == 0) {
            shipState = ShipState.DEAD;
        }
    }

    public int getDamageCounter() {
        return damageCounter;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public ShipPosition getShipPosition() {
        return shipPosition;
    }

    public int getDecks() {
        return decks;
    }

    public ShipState getShipState() {
        return shipState;
    }

    public void setShipState(ShipState shipState) {
        this.shipState = shipState;
    }

    public CoordsOnField[] getCoordOnFields() {
        return coordOnFields;
    }
}
