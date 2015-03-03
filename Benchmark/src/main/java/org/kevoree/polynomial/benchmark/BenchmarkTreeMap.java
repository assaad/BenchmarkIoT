package org.kevoree.polynomial.benchmark;

import org.kevoree.util.DataPoint;

import java.util.Random;
import java.util.TreeMap;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkTreeMap extends Benchmark {

    private TreeMap<Long, Double> treeMap;

    @Override
    public void init() {
        treeMap=new TreeMap<Long, Double>();
    }

    @Override
    public String getBenchmarkName() {
        return "Java Tree Map";
    }

    @Override
    public void put(long t, double value) {
        treeMap.put(t,value);

    }

    @Override
    public double get(long t) {
        return treeMap.get(t);

    }
}
