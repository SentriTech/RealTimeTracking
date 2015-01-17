package com.sentri.settings;

import com.sentri.model.Location;
import com.sentri.model.Network;
import com.sentri.model.Node;

/**
 * Created by sanjun.yyj on 9/20/14.
 */
public class SiteConfig {

    //x-aries
    private double length = 5.35;

    //y-aries
    private double width = 4.95;

    public SiteConfig() {

    }

    public SiteConfig(double length, double width, int numNode) {
        this.length = length;
        this.width = width;
    }


    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
