package com.karimelkheshen;

import java.util.concurrent.ThreadLocalRandom;

public class Point {

    public int xVal;
    public int yVal;

    public Point() {
        this.xVal = 0;
        this.yVal = 0;
    }

    public Point(int xVal, int yVal) {
        this.xVal = xVal;
        this.yVal = yVal;
    }

    public Point(Point p) {
        this.xVal = p.xVal;
        this.yVal = p.yVal;
    }

    public void setxyVal(int xVal, int yVal) {
        this.xVal = xVal;
        this.yVal = yVal;
    }

    public void setRandxyVal(int min, int max) {
        this.xVal = ThreadLocalRandom.current().nextInt(min, max + 1);
        this.yVal = ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public boolean isEqualTo(Point o) {
        return (this.xVal == o.xVal && this.yVal == o.yVal);
    }

}
