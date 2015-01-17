package com.sentri.service;

import com.sentri.io.DataFile;
import com.sentri.model.Network;
import com.sentri.model.Particle;
import com.sentri.model.Target;
import com.sentri.settings.ModelConfig;
import com.sentri.settings.SiteConfig;
import com.sentri.settings.TrackConfig;
import com.sentri.settings.VacantConfig;
import java.util.ArrayList;
import java.util.List;

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

}
