package com.sentri.service;

import com.sentri.utils.MatlabHelper;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by sanjun.yyj on 12/14/14.
 */
public class Track extends TestCase {
    @Test
    public void test() {
        TrackSystem trackSystem = new TrackSystem();
        DataHolder dh = DataHolder.getInstance();
        trackSystem.initSystem();
        trackSystem.tracking();
    }
}
