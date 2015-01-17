package com.sentri.service;

import com.google.gson.Gson;
import com.sentri.model.*;
import com.sentri.utils.MatlabHelper;

/**
 * Created by sanjun.yyj on 11/2/14.
 */
public class TrackSystem {
    DataHolder  dh          = DataHolder.getInstance();
    TrackModel  trackModel  = new TrackModel();
    VacantModel vacantModel = new VacantModel();


    public void initSystem() {

        dh.network.create(dh.sensorLocations);
        vacantModel.initVacant();
        trackModel.initTrack();
    }

    //Multiple Particle Filtering, this is the main tracking algorithm
    public void tracking(int round) {
        //particle structure


    }

    public void track() {
        trackModel.readNewData();
        double[][] observation = dh.network.getObservation();
        MatlabHelper.setData(observation, dh.network.getAbnormalLinks(), 0);
        System.out.println(new Gson().toJson(observation));

        //predictive estimates
        for (int i = 0; i < dh.trackConfig.getNumTarget(); i++) {
            Prediction currPrediction = dh.targets.get(i).getCurrPrediction();
            Prediction nextPrediction = currPrediction.clone();
            //System.out.println("location : " + JSON.toJSONString(location));

            //particle propagation
            nextPrediction = trackModel.randomWalk(nextPrediction);
            for (int t = 0; t < 2000; t++) {
                dh.particles[t] = nextPrediction.getParticles()[t].clone();
            }

            //update weights
            Location[][] y = new Location[dh.trackConfig.getNumTarget()][dh.trackConfig.getNumParticles()];

            for (int j = 0; j < dh.trackConfig.getNumTarget(); j++) {
                //assume one target moved only
                if (i == j) {
                    for (int k = 0; k < dh.trackConfig.getNumParticles(); k++) {
                        y[j][k] = new Location(nextPrediction.getParticle(k).getLocation());
                    }
                } else {
                    //notice default value of other target
                    for (int k = 0; k < dh.trackConfig.getNumParticles(); k++) {
                        //TODO error when multi target
                        //TODO get by round id
                        y[j][k] = new Location(currPrediction.getLocation());
                    }
                }
            }

            double[] likelihood = trackModel.computeLikelihood(y, observation);
            //System.out.println("likelihood" + JSON.toJSONString(likelihood));
            nextPrediction.setWeights(likelihood);

            //normalize the weights to sum to one
            //for each target, sum particle weight && norm to 1
            double maxWeight = nextPrediction.getMaxWeight();
            for (int j = 0; j < dh.trackConfig.getNumParticles(); j++) {
                nextPrediction.getParticle(j).setWeight(nextPrediction.getParticle(j).getWeight() - maxWeight);
            }
            double sumWeight = nextPrediction.getSumExpWeight();
            for (int j = 0; j < dh.trackConfig.getNumParticles(); j++) {
                nextPrediction.getParticle(j).setWeight(Math.exp(nextPrediction.getParticle(j).getWeight()) / sumWeight);
            }
            //System.out.println(JSON.toJSONString(nextPrediction.getWeights()));

            //resample particles
            int[] outIndex = trackModel.resample(nextPrediction.getWeights());
            //System.out.println(JSON.toJSONString(outIndex));
            for (int j = 0; j < dh.trackConfig.getNumParticles(); j++) {
                nextPrediction.getParticle(j).setWeight(1.0 / dh.trackConfig.getNumParticles());
                nextPrediction.getParticle(j).setLocation(nextPrediction.getParticle(outIndex[j]).getLocation().clone());
            }
            //System.out.println(JSON.toJSONString(nextPrediction));
            nextPrediction.calcParticleCenter();
            System.out.println("location : " + new Gson().toJson(nextPrediction.getLocation()));
            dh.targets.get(i).addPrediction(nextPrediction);
        }
    }
}
