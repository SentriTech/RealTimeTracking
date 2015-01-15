package com.sentri.model;

import java.io.Serializable;

/**
 * Created by sanjun.yyj on 12/7/14.
 */
public class Particle implements Serializable,Cloneable {
    private double weight;

    private Location location;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Particle clone() {
        try {
            Particle particle = new Particle();
            particle.setWeight(this.weight);
            if (this.location != null) {
                particle.setLocation(this.location.clone());
            }
            return particle;
        } catch (Exception e) {
            return null;
        }
    }
}
