package org.kevoree.polynomial.benchmark;

import org.kevoree.util.DataPoint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkEmpty extends Benchmark {

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
    public void init() {

    }

    @Override
    public String getBenchmarkName() {
        return "Empty iteration";
    }

    @Override
    public void put(long t, double value) {

    }

    @Override
    public double get(long t) {
        return 0;
    }
}
