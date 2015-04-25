package pl.edu.agh.miss.firesim.utils;

/**
 * @author mnowak
 */
public class Vector {

    private double x;
    private double y;

    public Vector() {
        x = y = 0.0;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector v) {
        this(v.getX(), v.getY());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Vector setZero() {
        x = y = 0;
        return this;
    }

    public Vector add(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    public Vector add(Vector vec) {
        return add(vec.getX(), vec.getY());
    }

    public Vector scale(double factor) {
        this.x *= factor;
        this.y *= factor;
        return this;
    }

    public static Vector sum(Vector v1, Vector v2) {
        return new Vector(v1).add(v2);
    }

    public static Vector scaled(Vector v, double factor) {
        return new Vector(v).scale(factor);
    }

}
