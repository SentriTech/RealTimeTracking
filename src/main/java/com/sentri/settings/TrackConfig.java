package com.sentri.settings;

/**
 * Created by sanjun.yyj on 10/12/14.
 */
public class TrackConfig extends NetworkConfig {

    private int numRound = 600;

    private int numTarget = 1;

    private int numParticles = 2000;

    private int numResample = 7;

    public int getNumRound() {
        return numRound;
    }

    public TrackConfig() {
        this.dataFile = "/Users/sanjun.yyj/Develop/workspace/sentri/RealTimeMatlab/round.csv";
    }

    public void setNumRound(int numRound) {
        this.numRound = numRound;
    }

    public int getNumTarget() {
        return numTarget;
    }

    public void setNumTarget(int numTarget) {
        this.numTarget = numTarget;
    }

    public int getNumParticles() {
        return numParticles;
    }

    public void setNumParticles(int numParticles) {
        this.numParticles = numParticles;
    }

    public int getNumResample() {
        return numResample;
    }

    public void setNumResample(int numResample) {
        this.numResample = numResample;
    }
}
