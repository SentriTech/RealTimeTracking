package com.sentri.settings;

/**
 * Created by sanjun.yyj on 10/19/14.
 */
public class VacantConfig extends NetworkConfig {
    // The empty.csv may store more than 1 minutes,
    // but we only use the first 30 seconds of the vacant network sensing data
    private double sensingTime = 600;

    // exclude the first & last 30 lines
    private int    ignoreTime   = 24;

    // Threshold to detect those links with high variance, higher than most of other links
    private double threshold    = 1;

    public VacantConfig() {
        this.dataFile = "/Users/sanjun.yyj/Develop/workspace/sentri/RealTimeMatlab/empty.csv";
    }

    public double getSensingTime() {
        return sensingTime;
    }

    public void setSensingTime(double sensingTime) {
        this.sensingTime = sensingTime;
    }

    public int getIgnoreTime() {
        return ignoreTime;
    }

    public void setIgnoreTime(int ignoreTime) {
        this.ignoreTime = ignoreTime;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
