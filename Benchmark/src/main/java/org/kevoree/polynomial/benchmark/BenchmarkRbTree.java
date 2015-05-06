package org.kevoree.polynomial.benchmark;

import org.kevoree.modeling.api.rbtree.IndexRBTree;
import org.kevoree.util.DataPoint;

import java.util.Random;


/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkRbTree extends Benchmark {
    IndexRBTree treetest;

    @Override
    public void init() {
        treetest = new IndexRBTree();
    }

    @Override
    public String getBenchmarkName() {
        return "IndexRb Tree";
    }

    @Override
    public void put(long t, double value) {
        treetest.insert(t);

    }
    @Override
    public void finalput() {

    }

    @Override
    public void print() {

    }

    @Override
    public void firstget() {

    }

    @Override
    public double get(long t) {
        treetest.previousOrEqual(t);
        return 0;
    }
}
