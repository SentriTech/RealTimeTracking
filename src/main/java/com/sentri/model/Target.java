package com.sentri.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjun.yyj on 9/14/14.
 */
public class Target implements Serializable,Cloneable {

    private int id;

    private int role;

    private List<Prediction> predictions = new ArrayList<Prediction>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public void addPrediction(Prediction prediction) {
        this.predictions.add(prediction);
    }

    public Prediction getPrevPrediction() {
        return predictions.get(predictions.size()-2);
    }

    public Prediction getCurrPrediction() {
        return predictions.get(predictions.size()-1);
    }

    public void setCurrPrediction(Prediction prediction) {
        predictions.set(predictions.size()-1, prediction);
    }

    public void setPrediction(int i, Prediction prediction) {
        this.predictions.set(i, prediction);
    }

    @Override
    public Target clone() {
        try {
            Target target = new Target();
            target.setId(this.id);
            target.setRole(this.role);
            if (this.predictions != null) {
                for (Prediction prediction : this.predictions) {
                    target.addPrediction(prediction.clone());
                }
            }
            return target;
        } catch (Exception e) {
            return null;
        }
    }


}
