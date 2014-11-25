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

    public void setDataPoints(ArrayList<DataPoint> points) {
        this.points = points;
    }

    public abstract double benchmarkWrite(int number);

    public abstract double benchmarkRead(int number);

    public abstract String getBenchmarkName();

}
