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

    private double sigmaTheta = Math.sqrt(0.001);

    private double sigmaThetaInit = 0.1;

    //0.2%measurement model sigma lambda
    private double sigmaLambda = 0.1;

    private double sigmaPrior = 1;

    private String resampleType = "stratified";

    private double phi = 4;

    private double[][] P = {{0.75, 0.65, 0.65},
                            {0.125, 0.3, 0.05},
                            {0.125, 0.05, 0.3}};

    private double[] C = {0, 0.1, -0.1};

    private double m = 0.05;

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

    public double getSigmaThetaInit() {
        return sigmaThetaInit;
    }

    public void setSigmaThetaInit(double sigmaThetaInit) {
        this.sigmaThetaInit = sigmaThetaInit;
    }

    public double getSigmaZ() {
        return sigmaZ;
    }

    public void setSigmaZ(double sigmaZ) {
        this.sigmaZ = sigmaZ;
    }

    public double getSigmaTheta() {
        return sigmaTheta;
    }

    public void setSigmaTheta(double sigmaTheta) {
        this.sigmaTheta = sigmaTheta;
    }

    public double getSigmaLambda() {
        return sigmaLambda;
    }

    public void setSigmaLambda(double sigmaLambda) {
        this.sigmaLambda = sigmaLambda;
    }

    public double getSigmaPrior() {
        return sigmaPrior;
    }

    public void setSigmaPrior(double sigmaPrior) {
        this.sigmaPrior = sigmaPrior;
    }

    public double[][] getP() {
        return P;
    }

    public void setP(double[][] p) {
        P = p;
    }

    public double[] getC() {
        return C;
    }

    public void setC(double[] c) {
        C = c;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
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
