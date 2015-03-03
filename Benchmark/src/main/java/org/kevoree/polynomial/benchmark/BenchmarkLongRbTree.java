package org.kevoree.polynomial.benchmark;

import org.kevoree.modeling.api.rbtree.LongRBTree;



/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkLongRbTree extends Benchmark {
    LongRBTree treetest;

    @Override
    public void init() {
        treetest = new LongRBTree();
    }

    @Override
    public String getBenchmarkName() {
        return "Long Rb Tree";
    }

    @Override
    public void put(long t, double value) {
        treetest.insert(t,t);
    }

    @Override
    public double get(long t) {
        treetest.previousOrEqual(t);
        return 0;
    }
}
