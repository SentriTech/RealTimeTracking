package com.sentri.service;

import com.sentri.utils.MatlabHelper;
import java.util.Random;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by sanjun.yyj on 12/14/14.
 */
public class Track extends TestCase {
    @Test
    public void test() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(random.nextGaussian());
        }
    }
}
