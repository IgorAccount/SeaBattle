package ru.igor.tests.controller;
import ru.igor.tests.constants.*;

import static ru.igor.tests.constants.Constants.CELLSIZE;

public class CounterListener {
    private int x;
    private int y;
    private int x2;
    private int y2;

    public CounterListener(int y, int x){
        this.x = x;
        this.y = y;
        countLines();

    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    void countLines(){
         y2 = y / CELLSIZE;
         x2 = x / CELLSIZE;
        System.out.println(x + "x " + " y" + y);
    }

}
