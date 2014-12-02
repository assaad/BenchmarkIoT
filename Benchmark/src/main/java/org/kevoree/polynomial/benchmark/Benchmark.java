package org.kevoree.polynomial.benchmark;


import org.kevoree.util.DataPoint;

import java.util.ArrayList;

/**
 * Created by assaa_000 on 24/11/2014.
 */
public abstract class Benchmark {

    protected ArrayList<DataPoint> points;
    private String path = "";
    private String datasetName = "";
    protected boolean gcCollect = true;

    public void setGcCollect(boolean gcCollect){
        this.gcCollect=gcCollect;
    }

    public void setDataPoints(ArrayList<DataPoint> points) {
        this.points = points;
    }

    public abstract double benchmarkWrite(int iterations);

    public abstract double benchmarkRandomRead(int iterations, int values);

    public abstract double benchmarkSequencialRead(int iterations, int values);

    public abstract String getBenchmarkName();

}
