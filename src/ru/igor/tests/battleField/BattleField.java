package ru.igor.tests.battleField;

public class BattleField {

    private int[][] aiField;
    private int[][] myField;

    public BattleField() {
        aiField = new int[10][10];
        myField = new int[10][10];
    }

    public int[][] getAiField() {
        return aiField;
    }

    public int[][] getMyField() {
        return myField;
    }
}
