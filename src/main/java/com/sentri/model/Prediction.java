package com.sentri.model;

import java.io.Serializable;

/**
 * Created by sanjun.yyj on 12/7/14.
 */
public class Prediction implements Serializable,Cloneable {
    Particle[] particles;

    Location location;

    public Prediction() {

    }

    public Prediction(int nParticle) {
        this.particles = new Particle[nParticle];
        for (int i = 0; i < nParticle; i++) {
            this.particles[i] = new Particle();
        }
    }

    public void calcParticleCenter() {
        double x = 0;
        double y = 0;
        for (int i = 0; i < particles.length; i++) {
            x += particles[i].getWeight() * particles[i].getLocation().getX();
            y += particles[i].getWeight() * particles[i].getLocation().getY();
        }
        this.location = new Location(x, y);
    }

    public void setWeights(double[] weights) {
        for (int i=0; i < weights.length; i++) {
            particles[i].setWeight(weights[i]);
        }
    }

    public Particle[] getParticles() {
        return particles;
    }

    public void setParticles(Particle[] particles) {
        this.particles = particles;
    }

    public Particle getParticle(int i) {
        return particles[i];
    }

    public void setParticle(int i, Particle particle) {
        particles[i] = particle;
    }

    public double[] getWeights() {
        double[] weights = new double[particles.length];
        for (int i = 0; i < particles.length; i++) {
            weights[i] = particles[i].getWeight();
        }
        return weights;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Prediction clone() {
        try {
            Prediction prediction = new Prediction(this.particles.length);
            if (this.location != null) {
                prediction.setLocation(this.location.clone());
            }
            if (this.particles != null) {
                for (int i = 0; i < this.particles.length; i++) {
                    prediction.setParticle(i, this.particles[i].clone());
                }
            }
            return prediction;
        } catch (Exception e) {
            return null;
        }
    }

}
