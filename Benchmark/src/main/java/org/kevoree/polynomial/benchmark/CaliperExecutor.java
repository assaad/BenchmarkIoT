package org.kevoree.polynomial.benchmark;
import com.google.caliper.runner.CaliperMain;

/**
 * Created by assaad on 23/04/15.
 */
public class CaliperExecutor {
    public static void main(String[] args) {
        CaliperMain.main(PopulateBench.class, args);
    }

}