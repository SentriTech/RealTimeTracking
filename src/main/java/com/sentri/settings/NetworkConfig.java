package com.sentri.settings;

/**
 * Created by sanjun.yyj on 11/2/14.
 */
public class NetworkConfig {
    protected String dataFile = "";

    private double nodeIdOffset = 1;

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public double getNodeIdOffset() {
        return nodeIdOffset;
    }

    public void setNodeIdOffset(double nodeIdOffset) {
        this.nodeIdOffset = nodeIdOffset;
    }
}
