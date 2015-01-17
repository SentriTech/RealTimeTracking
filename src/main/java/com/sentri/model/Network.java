package com.sentri.model;

import com.sentri.utils.MatlabHelper;
import java.io.Serializable;
import java.util.*;

/**
 * Created by sanjun.yyj on 9/14/14.
 */
public class Network implements Serializable,Cloneable {
    public static String SOURCE_FILE = "FILE";

    public static String SOURCE_NODE = "NODE";

    private int numNode = 24;

    //link id between node i and j(i<j) is i * numNode + j
    private int numLink = 276;

    private Node[] nodes = new Node[24];

    private int[][] abnormalLinks = new int[24][24];

    private double[][] dist = new double[24][24];

    private double[][] intensity = new double[24][24];

    private double[][] rssEmpty = new double[24][24];

    private double[][] observation = new double[24][24];

    public Network() {

    }

    public Network(int numNode) {
        this.numNode = numNode;
        this.numLink = numNode * (numNode - 1) / 2;
        this.nodes = new Node[numNode];
        this.abnormalLinks = new int[numNode][numNode];
        this.dist = new double[numNode][numNode];
        this.intensity = new double[numNode][numNode];
        this.rssEmpty = new double[numNode][numNode];
        this.observation = new double[numNode][numNode];
    }

    public int getNumNode() {
        return numNode;
    }

    public void setNumNode(int numNode) {
        this.numNode = numNode;
    }

    public int getNumLink() {
        return numLink;
    }

    public void setNumLink(int numLink) {
        this.numLink = numLink;
    }

    public Map<String, Node> getStatus() {
        return null;
    }

    public void addNode(int i, Node node) {
        this.nodes[i] = node;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    public int[][] getAbnormalLinks() {
        return abnormalLinks;
    }

    public void setAbnormalLinks(int[][] abnormalLinks) {
        this.abnormalLinks = abnormalLinks;
    }

    public double[][] getRssEmpty() {
        return rssEmpty;
    }

    public void setRssEmpty(double[][] rssEmpty) {
        this.rssEmpty = rssEmpty;
    }

    public double[][] getDist() {
        return dist;
    }

    public void setDist(double[][] dist) {
        this.dist = dist;
    }

    public double[][] getObservation() {
        return observation;
    }

    public void setObservation(double[][] observation) {
        this.observation = observation;
    }

    public double[][] getIntensity() {
        return intensity;
    }

    public void setIntensity(double[][] intensity) {
        this.intensity = intensity;
    }
    
    public void create() {
        /*
        this.addNode(0, new Node(0, new Location(0.57, 0)));
        this.addNode(1, new Node(1, new Location(1.14, 0)));
        this.addNode(2, new Node(2, new Location(1.71, 0)));
        this.addNode(3, new Node(3, new Location(2.28, 0)));
        this.addNode(4, new Node(4, new Location(2.85, 0)));
        this.addNode(5, new Node(5, new Location(3.42, 0)));
        this.addNode(6, new Node(6, new Location(4, 0.57)));
        this.addNode(7, new Node(7, new Location(4, 1.14)));
        this.addNode(8, new Node(8, new Location(4, 1.71)));
        this.addNode(9, new Node(9, new Location(4, 2.28)));
        this.addNode(10, new Node(10, new Location(4, 2.85)));
        this.addNode(11, new Node(11, new Location(4, 3.42)));
        this.addNode(12, new Node(12, new Location(3.42, 4)));
        this.addNode(13, new Node(13, new Location(2.85, 4)));
        this.addNode(14, new Node(14, new Location(2.28, 4)));
        this.addNode(15, new Node(15, new Location(1.71, 4)));
        this.addNode(16, new Node(16, new Location(1.14, 4)));
        this.addNode(17, new Node(17, new Location(0.57, 4)));
        this.addNode(18, new Node(18, new Location(0, 3.42)));
        this.addNode(19, new Node(19, new Location(0, 2.85)));
        this.addNode(20, new Node(20, new Location(0, 2.28)));
        this.addNode(21, new Node(21, new Location(0, 1.71)));
        this.addNode(22, new Node(22, new Location(0, 1.14)));
        this.addNode(23, new Node(23, new Location(0, 0.57)));
        */

        this.addNode(0, new Node(0, new Location(0.77, 0)));
        this.addNode(1, new Node(1, new Location(1.555, 0)));
        this.addNode(2, new Node(2, new Location(2.155, 0)));
        this.addNode(3, new Node(3, new Location(2.755, 0)));
        this.addNode(4, new Node(4, new Location(3.655, 0)));
        this.addNode(5, new Node(5, new Location(4.555, 0)));
        this.addNode(6, new Node(6, new Location(5.36, 0.565)));
        this.addNode(7, new Node(7, new Location(5.36, 1.485)));
        this.addNode(8, new Node(8, new Location(5.36, 2.465)));
        this.addNode(9, new Node(9, new Location(5.36, 3.075)));
        this.addNode(10, new Node(10, new Location(5.36, 3.665)));
        this.addNode(11, new Node(11, new Location(5.36, 4.465)));
        this.addNode(12, new Node(12, new Location(4.595, 4.95)));
        this.addNode(13, new Node(13, new Location(4.025, 4.95)));
        this.addNode(14, new Node(14, new Location(3.405, 4.95)));
        this.addNode(15, new Node(15, new Location(2.805, 4.95)));
        this.addNode(16, new Node(16, new Location(2.2215, 4.95)));
        this.addNode(17, new Node(17, new Location(1.635, 4.95)));
        this.addNode(18, new Node(18, new Location(0, 3.61)));
        this.addNode(19, new Node(19, new Location(0, 3.01)));
        this.addNode(20, new Node(20, new Location(0, 2.41)));
        this.addNode(21, new Node(21, new Location(0, 1.81)));
        this.addNode(22, new Node(22, new Location(0, 1.21)));
        this.addNode(23, new Node(23, new Location(0, 0.61)));

        double[][] dist = MatlabHelper.zeros(this.getNumNode());
        for (int i = 0; i < this.getNumNode(); i++) {
            for (int j = 0; j < this.getNumNode(); j++) {
                dist[i][j] = Math.sqrt(Math.pow(this.getNodes()[i].getLocation().getX()
                                                        - this.getNodes()[j].getLocation().getX(),
                                                2)
                                               + Math.pow(this.getNodes()[i].getLocation().getY()
                                                                  - this.getNodes()[j].getLocation().getY(),
                                                          2));

            }
        }
        this.setDist(dist);
    }

    public void create(List<Location> sensorLocations) {

        for (int i = 0; i < sensorLocations.size(); i++) {
            this.addNode(i, new Node(i, sensorLocations.get(i).clone()));
        }

        double[][] dist = MatlabHelper.zeros(this.getNumNode());
        for (int i = 0; i < this.getNumNode(); i++) {
            for (int j = 0; j < this.getNumNode(); j++) {
                dist[i][j] = Math.sqrt(Math.pow(this.getNodes()[i].getLocation().getX()
                                                        - this.getNodes()[j].getLocation().getX(),
                                                2)
                                               + Math.pow(this.getNodes()[i].getLocation().getY()
                                                                  - this.getNodes()[j].getLocation().getY(),
                                                          2));

            }
        }
        this.setDist(dist);
    }

    @Override
    public Network clone() {
        try {
            Network network = new Network(this.numNode);
            network.setNumNode(this.numNode);
            network.setNumLink(this.numLink);
            if (this.nodes != null) {
                for (int i = 0; i < this.nodes.length; i++) {
                    network.addNode(i, this.nodes[i].clone());
                }
            }
            if (this.dist != null) {
                network.setDist(this.dist.clone());
            }
            if (this.observation != null) {
                network.setObservation(this.observation.clone());
            }
            if (this.abnormalLinks != null) {
                network.setAbnormalLinks(this.abnormalLinks.clone());
            }
            if (this.intensity != null) {
                network.setIntensity(this.intensity.clone());
            }
            if (this.rssEmpty != null) {
                network.setRssEmpty(this.rssEmpty.clone());
            }
            return network;
        } catch (Exception e) {
            return null;
        }
    }

}
