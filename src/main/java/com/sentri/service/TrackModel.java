package com.sentri.service;

import com.sentri.model.Location;
import com.sentri.model.Prediction;
import com.sentri.utils.MatlabHelper;
import java.util.Date;
import java.util.Random;

/**
 * Created by sanjun.yyj on 11/23/14.
 */
public class TrackModel {
    DataHolder dh = DataHolder.getInstance();

    public void initTrack() {
        initParticles();
        dh.trackData.setFilename(dh.trackConfig.getDataFile());
    }

    public void initParticles() {
        Prediction prediction = new Prediction(dh.trackConfig.getNumParticles());
        for (int i = 0; i < dh.trackConfig.getNumParticles(); i++) {

            prediction.getParticle(i).setLocation(new Location(Math.random() * dh.siteConfig.getLength(),
                                                    Math.random() * dh.siteConfig.getWidth()));
            prediction.getParticle(i).setWeight(1.0/dh.trackConfig.getNumParticles());
        }
        prediction.calcParticleCenter();
        for (int i = 0; i < dh.trackConfig.getNumTarget(); i++) {
            dh.targets.get(i).addPrediction(prediction);
        }
    }



    //The dynamic model, or transition model, this function is to add a dynamic to the particle location,
    //it is like adding a possible location according to the previous estimated location
    public Prediction randomWalk(Prediction prediction) {
        Random random = new Random(new Date().getTime());
        for (int j = 0; j < dh.trackConfig.getNumParticles(); j++) {
            Location prevLocation = prediction.getParticle(j).getLocation();
            double x = Math.abs(prevLocation.getX() + dh.modelConfig.getSigmaX() * random.nextGaussian() * dh.trackConfig.getStep());
            if (x > dh.siteConfig.getLength()) {
                x = 2 * dh.siteConfig.getLength() - x;
            }
            double y = Math.abs(prevLocation.getY() + dh.modelConfig.getSigmaY() * random.nextGaussian() * dh.trackConfig.getStep());
            if (y > dh.siteConfig.getWidth()) {
                y = 2 * dh.siteConfig.getWidth() - y;
            }

            Location location = new Location(x, y);
            prediction.getParticle(j).setLocation(location.clone());
        }
        return prediction;
    }

    /**
     * compute the log likelihood of the observation, it is from the measurement model,
     * which compares the predicted data computed by algorithm and
     * the observation data from wireless sensor networks,
     * and get the likelihood for every particle points,
     * assign weights to each particle points,
     * those with higher weights will contributes more to the final estimated locations
     **/
    public double[] computeLikelihood(Location[][] y, double[][] observation) {
        double[][][][] attenuation = MatlabHelper.zeros(dh.network.getNumNode(),
                                                        dh.network.getNumNode(),
                                                        dh.trackConfig.getNumParticles(),
                                                        dh.trackConfig.getNumTarget());
        //dist_i_p_j  = dist_i_p + dist_p_j
        //attenuation_i_p_j = dist_i_p_j - dist_i_j
        for (int i = 0; i < dh.trackConfig.getNumTarget(); i++) {
            for (int j = 0; j < dh.trackConfig.getNumParticles(); j++) {
                for (int k = 0; k < dh.network.getNumNode(); k++) {
                    for (int l = 0; l < dh.network.getNumNode(); l++) {
                        if (dh.network.getAbnormalLinks()[l][k] == 0) {
                            attenuation[l][k][j][i] = y[i][j].calcDist(dh.network.getNodes()[k].getLocation())
                                                    + y[i][j].calcDist(dh.network.getNodes()[l].getLocation())
                                                    - dh.network.getDist()[l][k];
                            attenuation[l][k][j][i] = dh.modelConfig.getPhi() * Math.exp(-attenuation[l][k][j][i]/(2*dh.modelConfig.getSigmaLambda()));
                        } else {
                            attenuation[l][k][j][i] = 0;
                        }
                    }
                }
            }
        }

        double[][][] zCalced = MatlabHelper.sumDim(attenuation, 3);

        //TODO PI???
        double sigmaConst = -0.5 * (dh.network.getNumLink() - dh.network.getAbnormalLinks().length) * Math.log(2 * Math.PI * Math.pow(dh.modelConfig.getSigmaZ(), 2));

        double[][][] logLikelyhood = MatlabHelper.zeros(dh.network.getNumNode(),
                                                        dh.network.getNumNode(),
                                                        dh.trackConfig.getNumParticles());
        for (int i = 0; i < dh.network.getNumNode(); i++) {
            for (int j = 0; j < dh.network.getNumNode(); j++) {
                for (int k = 0; k < dh.trackConfig.getNumParticles(); k++) {
                    if (dh.network.getAbnormalLinks()[i][j] == 0) {
                        logLikelyhood[i][j][k] = - Math.pow(zCalced[i][j][k] - observation[i][j], 2)
                            / (2 * Math.pow(dh.modelConfig.getSigmaZ(), 2));
                    } else {
                        logLikelyhood[i][j][k] = 0;
                    }
                }
            }
        }

        return MatlabHelper.add(MatlabHelper.sumDim(MatlabHelper.sumDim(logLikelyhood, 0), 0), sigmaConst);
    }

    /**
     * This function is to do the particle resampling for particle filter
     * resample n particles from a weighted set with weights w
     * type = 'multinomial', 'stratified'
     **/
    public int[] resample(double[] weight) {
        int[] index = new int[weight.length];
        //System.out.println(JSON.toJSONString(weight));
        double[] edges = MatlabHelper.cumsum(weight);
        double[] v = new double[weight.length];
        for (int i = 0; i < weight.length; i++) {
            v[i] = (Math.random() + i) / weight.length;
        }
        for (int i = 0; i < weight.length; i++) {
            for (int j = 0; j < weight.length; j++) {
                if (v[i] >= edges[j] && v[i] < edges[j+1]) {
                    index[i] = j;
                    break;
                }
            }
        }
        return index;
    }

    /**
     * Description: get the average rss of the network
     *  In a time interval specified by measurementTimePerSample, each link
     *  average its collected RSS. If no RSS measurements for a link in that time interval,
     *  set it to be the RSS of vacant network Notes:
     *  Only one node transmits at a time. The first value of each line in the
     *  data files indicates the node that is reporting its data. The 2nd column
     *  to the 29th (since there are 28 total nodes) are the received signal
     *  strength (RSS) values from each of the nodes to the reporting node.
     *  For example, if the first column is 3, then the 2nd column would represent
     *  the RSS from node 0 to node 3. Remember that the nodes' ID start from 0
     **/
    public double[][] computeNewRss(double[][] data, int numNode, double[][] rssEmpty) {
        double[][] rssAverageAcrossTime = MatlabHelper.zeros(numNode);
        double[] transmitterId = MatlabHelper.getColumn(data, 0);
        double[][] rssData = MatlabHelper.subMatrix(data, 0, data.length, 1, numNode);
        for (int i = 0; i < numNode; i++) {
            int[] sameNodeGroup = MatlabHelper.findEqual(transmitterId, i);
            if (sameNodeGroup.length > 0) {
                for (int j = 0; j < numNode; j++) {
                    for (int k = 0; k < sameNodeGroup.length; k++) {
                        rssAverageAcrossTime[i][j] = rssAverageAcrossTime[i][j] + rssData[sameNodeGroup[k]][j];
                    }
                    rssAverageAcrossTime[i][j] = rssAverageAcrossTime[i][j] / sameNodeGroup.length;
                }
            }
        }
        //if the transmitted data of one node is missing for two consecutive times,
        //we use the data of the node as the receiving end,
        //to measure each link, which can't be missing since every row has 28 columns of data.
        for (int i = 0; i < numNode; i++) {
            if (rssAverageAcrossTime[i][0] == 0) {
                for (int j = 0; j < numNode; j++) {
                    rssAverageAcrossTime[i][j] = rssAverageAcrossTime[j][i];
                }
            }
        }
        int[][] noMeasurementIndex = MatlabHelper.findEqual(rssAverageAcrossTime, 0);
        double[][] noMeasurementData = MatlabHelper.getData(rssEmpty, noMeasurementIndex);
        rssAverageAcrossTime = MatlabHelper.add(rssAverageAcrossTime, noMeasurementData);
        double[][] rssAverageAcrossTimeReverse = MatlabHelper.reverse(rssAverageAcrossTime);
        double[][] rssAverage = MatlabHelper.divide(
                MatlabHelper.add(rssAverageAcrossTime,
                                 rssAverageAcrossTimeReverse),
                2);
        return rssAverage;
    }

    /**
     * This function is to read and process new coming data from wireless sensor network, in real time.
     */
    public void readNewData() {
        int[][] linkIndex = MatlabHelper.findNotEqual(
                MatlabHelper.tril(
                        MatlabHelper.ones(dh.network.getNumNode()),
                        -1),
                0);

        dh.trackData.loadData(dh.network.getNumNode());
        double[][] data = dh.trackData.getData();
        data = MatlabHelper.minus(data, 0, data.length, 0, 1, dh.trackConfig.getNodeIdOffset());

        double[][] rssMeasured = computeNewRss(data, dh.network.getNumNode(), dh.network.getRssEmpty());
        double[][] linkMeasured = MatlabHelper.getData(rssMeasured, linkIndex);
        double[][] linkAverage = MatlabHelper.getData(dh.network.getRssEmpty(), linkIndex);
        for (int i = 0; i < dh.network.getNumNode(); i++) {
            for (int j = 0; j < dh.network.getNumNode(); j++) {
                while (linkMeasured[i][j] > 127) {
                    linkMeasured[i][j] = linkMeasured[i][j] - 256;
                }
                while (linkAverage[i][j] > 127) {
                    linkAverage[i][j] = linkAverage[i][j] - 256;
                }
            }
        }

        double[][] observation = MatlabHelper.abs(
                MatlabHelper.minus(linkMeasured,
                                   linkAverage));
        observation = MatlabHelper.setData(observation,
                                           MatlabHelper.findMore(observation,
                                                                 20),
                                           0);
        observation = MatlabHelper.setData(observation,
                                           MatlabHelper.findLess(observation,
                                                                 -5),
                                           0);
        dh.network.setObservation(observation);
    }

}
