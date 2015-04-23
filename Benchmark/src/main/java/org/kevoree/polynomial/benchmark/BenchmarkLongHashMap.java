package org.kevoree.polynomial.benchmark;

import org.kevoree.modeling.api.map.LongHashMap;

/**
 * Created by assaad on 03/03/15.
 */
public class BenchmarkLongHashMap extends Benchmark {
    LongHashMap<Double> tree;
    @Override
    public void init() {
        tree=new LongHashMap<Double>(100000,1);
    }

    @Override
    public String getBenchmarkName() {
        return "Kevoree Long Hashmap";
    }

    @Override
    public void put(long t, double value) {
        tree.put(t,value);

    }

    @Override
    public double get(long t) {
        return tree.get(t);
    }
}
