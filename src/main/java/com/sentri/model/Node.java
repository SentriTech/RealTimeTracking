package com.sentri.model;

import com.sentri.io.Sensor;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by sanjun.yyj on 9/14/14.
 */
public class Node implements Serializable,Cloneable {
    private int id;

    private Location location;

    private Sensor sensor;

    public Node() {
    }

    public Node(int id, Location location) {
        this.id = id;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void update() {
    }

    @Override
    public Node clone() {
        try {
            Node node = new Node();
            node.setId(this.id);
            if (this.sensor != null) {
                node.setSensor(this.sensor.clone());
            }
            if (this.location != null) {
                node.setLocation(this.location.clone());
            }
            return node;
        } catch (Exception e) {
            return null;
        }
    }

}
