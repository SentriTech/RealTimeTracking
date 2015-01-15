package com.sentri.model;

import java.io.Serializable;

/**
 * Created by sanjun.yyj on 9/20/14.
 */
public class Location implements Serializable,Cloneable {
    private double x;
    private double y;

    public Location() {

    }

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Location(Location loc) {
        this.x = loc.x;
        this.y = loc.y;
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

    public double calcDist(Location l) {
        return Math.pow(Math.pow(l.getX() - x, 2) + Math.pow(l.getY() - y, 2), 0.5);
    }

    @Override
    public Location clone() {
        try {
            Location location = new Location(this);
            return location;
        } catch (Exception e) {
            return null;
        }
    }

}
