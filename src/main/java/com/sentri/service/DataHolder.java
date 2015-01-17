package com.sentri.service;

import com.sentri.io.DataFile;
import com.sentri.model.Location;
import com.sentri.model.Network;
import com.sentri.model.Particle;
import com.sentri.model.Target;
import com.sentri.settings.ModelConfig;
import com.sentri.settings.SiteConfig;
import com.sentri.settings.TrackConfig;
import com.sentri.settings.VacantConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sanjun.yyj on 12/7/14.
 */
public class DataHolder {
    public TrackConfig  trackConfig  = new TrackConfig();
    public VacantConfig vacantConfig = new VacantConfig();
    public ModelConfig  modelConfig  = new ModelConfig();
    public SiteConfig   siteConfig   = new SiteConfig();

    public DataFile vacantData = new DataFile();
    public DataFile trackData  = new DataFile();

    public Particle[] particles = new Particle[2000];

    List<Location> sensorLocations = new ArrayList<Location>();


    //TODO remove it
    double[][] dataAfterIgnore;

    public Network network = new Network();

    public List<Target> targets = new ArrayList<Target>();

    public static DataHolder dataHolder = null;

    public static DataHolder getInstance() {
        if (dataHolder == null) {
            dataHolder = new DataHolder();
            dataHolder.targets.add(new Target());
        }
        return dataHolder;
    }

    public void init(Map<String, String> configMap) {
        vacantConfig.setDataFile(configMap.get("vacant.file"));
        vacantConfig.setIgnoreTime(Integer.valueOf(configMap.get("vacant.ignore")));
        vacantConfig.setSensingTime(Double.valueOf(configMap.get("vacant.sensor")));
        vacantConfig.setNodeIdOffset(Double.valueOf(configMap.get("vacant.nodeIdOffset")));
        vacantConfig.setThreshold(Double.valueOf(configMap.get("vacant.threshold")));

        trackConfig.setDataFile(configMap.get("track.file"));
        trackConfig.setNodeIdOffset(Double.valueOf(configMap.get("track.nodeIdOffset")));
        trackConfig.setNumResample(Integer.valueOf(configMap.get("track.num.resample")));
        trackConfig.setNumParticles(Integer.valueOf(configMap.get("track.num.particle")));
        trackConfig.setNumRound(Integer.valueOf(configMap.get("track.num.round")));
        trackConfig.setNumTarget(Integer.valueOf(configMap.get("track.num.target")));
        trackConfig.setStep(Double.valueOf(configMap.get("track.step")));

        siteConfig.setLength(Double.valueOf(configMap.get("site.length")));
        siteConfig.setWidth(Double.valueOf(configMap.get("site.width")));

        modelConfig.setPhi(Double.valueOf(configMap.get("model.phi")));
        modelConfig.setPropagationType(configMap.get("model.propagation.type"));
        modelConfig.setResampleType(configMap.get("model.resample.type"));
        modelConfig.setSigmaX(Double.valueOf(configMap.get("model.sigma.x")));
        modelConfig.setSigmaY(Double.valueOf(configMap.get("model.sigma.y")));
        modelConfig.setSigmaZ(Double.valueOf(configMap.get("model.sigma.z")));
        modelConfig.setSigmaLambda(Double.valueOf(configMap.get("model.sigma.lambda")));

        String[] sensors = configMap.get("network.sensors").split(";", -1);
        for (String sensor : sensors) {
            String[] geo =  sensor.split(",", -1);
            sensorLocations.add(new Location(Double.valueOf(geo[0]), Double.valueOf(geo[1])));
        }
    }

}
