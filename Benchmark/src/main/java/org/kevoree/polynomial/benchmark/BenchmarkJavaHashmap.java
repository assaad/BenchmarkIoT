package org.kevoree.polynomial.benchmark;

import java.util.HashMap;

/**
 * Created by assaad on 03/03/15.
 */
public class BenchmarkJavaHashmap extends Benchmark {
    HashMap<Long,Double> tree;
    @Override
    public void init() {
        tree=new HashMap<Long, Double>();
    }

    @Override
    public String getBenchmarkName() {
        return "Java Hash map";
    }

    @Override
    public void put(long t, double value) {
        tree.put(t,value);

    }

    @Override
    public double get(long t) {
        return tree.get(t);
    }

    @Override
    public void finalput() {

    }
}
