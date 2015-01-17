package com.sentri.settings;

/**
 * Created by sanjun.yyj on 9/20/14.
 */
public class ModelConfig {
    //random step length / variance on x-axis
    private double sigmaX = 0.2;

    //random step length / variance on y-axis
    private double sigmaY = 0.2;

    //measurement noise;
    private double sigmaZ = 0.7;

    //0.2%measurement model sigma lambda
    private double sigmaLambda = 0.3;


    private String resampleType = "stratified";

    private double phi = 3;

    private String propagationType = "RW";

    public ModelConfig() {

    }

    public double getSigmaX() {
        return sigmaX;
    }

    public void setSigmaX(double sigmaX) {
        this.sigmaX = sigmaX;
    }

    public double getSigmaY() {
        return sigmaY;
    }

    public void setSigmaY(double sigmaY) {
        this.sigmaY = sigmaY;
    }

    public double getSigmaZ() {
        return sigmaZ;
    }

    public void setSigmaZ(double sigmaZ) {
        this.sigmaZ = sigmaZ;
    }

    public double getSigmaLambda() {
        return sigmaLambda;
    }

    public void setSigmaLambda(double sigmaLambda) {
        this.sigmaLambda = sigmaLambda;
    }

    public String getPropagationType() {
        return propagationType;
    }

    public void setPropagationType(String propagationType) {
        this.propagationType = propagationType;
    }

    public String getResampleType() {
        return resampleType;
    }

    public void setResampleType(String resampleType) {
        this.resampleType = resampleType;
    }

    public double getPhi() {
        return phi;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }
}
