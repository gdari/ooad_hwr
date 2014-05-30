package ru.nsu.ccfit.hwr.logic.recognition.data;

/**
 * Created by Баира on 21.12.13.
 */
final public class Point {
    /*public! не будем использовать лишних getter/setter */
    public float x;
    public float y;
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Point(Point b) {
        this.x = b.x;
        this.y = b.y;
    }

    static public float getDistance(Point a, Point b) {
        return (float) Math.sqrt (Math.pow(a.x - b.x,2) + Math.pow(a.y - b.y,2));
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
