package org.kevoree.Polynomial.impl;

import java.util.ArrayList;

/**
 * Created by assaa_000 on 19/11/2014.
 */
public class Benchmark {

    public static void main(String[] args) {
        long starttime;
        long endtime;
        double res;

        starttime = System.nanoTime();
        ArrayList<DataPoint> points = DataLoader.load("all.txt",2);
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Loaded :" +points.size() + " values in " + res + " s!");

    }
}
