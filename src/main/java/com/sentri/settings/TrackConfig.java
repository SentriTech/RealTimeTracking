package com.sentri.settings;

/**
 * Created by sanjun.yyj on 10/12/14.
 */
public class TrackConfig extends NetworkConfig {

    private int numRound = 135;

    private int numTarget = 1;

    private int numParticles = 2000;

    private int numResample = 7;

    private int numBurn = 500;

    private int numThin = 3;

    private int numTrackingPlot = 0;

    private int p = 2;

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

    public int getNumBurn() {
        return numBurn;
    }

    public void setNumBurn(int numBurn) {
        this.numBurn = numBurn;
    }

    public int getNumThin() {
        return numThin;
    }

    public void setNumThin(int numThin) {
        this.numThin = numThin;
    }

    public int getNumTrackingPlot() {
        return numTrackingPlot;
    }

    public void setNumTrackingPlot(int numTrackingPlot) {
        this.numTrackingPlot = numTrackingPlot;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }
}
