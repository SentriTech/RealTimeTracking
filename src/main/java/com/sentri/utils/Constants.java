package com.sentri.utils;

/**
 * Created by sanjun.yyj on 9/20/14.
 */
public class Constants {
    //Setting file
    public final static double DEFAULT_SPOT_LENGTH = 4.7;

    public final static double DEFAULT_SPOT_WIDTH = 2.4;

    public final static int DEFAULT_NUM_SENDER = 24;

    public final static int DEFAULT_VACANT_THRESHOLD   = 1;
    public final static int DEFAULT_VACANT_SENSING_TIME = 30;
    public final static int DEFAULT_IGNORE_LINE        = 30;

    public final static double DEFAULT_SIGMA_LAMBDA = 0.2;
    public final static int    DEFAULT_SIGMA_Z      = 2;
    public final static int    DEFAULT_PHI          = 4;
    public final static int    DEFAULT_K            = 350;

    public final static double     DEFAULT_NUM_TARGET             = 2;
    public final static double     DEFAULT_SIGMA_X          = 0.1;
    public final static double     DEFAULT_SIGMA_Y          = 0.1;
    public final static double     DEFAULT_SIGMA_THETA      = Math.sqrt(0.001);
    public final static double     DEFAULT_SIGMA_THETA_INIT = 0.1;
    public final static int        DEFAULT_SIGMA_PRIOR      = 1;
    public final static double[][] DEFAULT_P_ARRAY          =
            {{0.75, 0.65, 0.65}, {0.125, 0.3, 0.05}, {0.125, 0.05, 0.3}};
    public final static double[]   DEFAULT_C_ARRAY          =
            {0, 0.1, -0.1};
    public final static double     DEFAULT_M                = 0.05;

    public final static String DEFAULT_PROPAGATION_TYPE = "RW";

    public final static int DEFAULT_NP_OBJ     = 500;
    public final static int DEFAULT_N_RESAMPLE = 7;

    public final static int DEFAULT_N_BURN = 1000;
    public final static int DEFAULT_N_THIN = 3;

    public final static int DEFAULT_TRACKING_PLOT = 0;

    public final static int DEFAULT_P = 2;

    //System config
    public final static String DATA_FILE_SPLIT = ",";

    public final static int DATA_COLUMN_NUM = 30;

}
