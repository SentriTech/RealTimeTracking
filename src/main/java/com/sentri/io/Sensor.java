package com.sentri.io;

import java.io.Serializable;

/**
 * Created by sanjun.yyj on 9/14/14.
 */
public class Sensor implements Serializable,Cloneable {
    @Override
    public Sensor clone() {
        try {
            Sensor sensor = new Sensor();
            return sensor;
        } catch (Exception e) {
            return null;
        }
    }

}
