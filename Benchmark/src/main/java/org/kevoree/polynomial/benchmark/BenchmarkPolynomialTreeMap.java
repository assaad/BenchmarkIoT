package org.kevoree.polynomial.benchmark;

import org.kevoree.util.DataPoint;
import org.kevoree.util.polynomial.PolynomialModel;


/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkPolynomialTreeMap extends Benchmark {
    PolynomialModel pm;

    @Override
    public void init() {
        pm = new PolynomialModel(1000,1,20);
    }

    /*public void finalinit(){
        pm.finalSave();
    }*/

    @Override
    public String getBenchmarkName() {
        return "Polynomial Tree Map";
    }

    @Override
    public void put(long t, double value) {
        pm.feed(t,value);
    }

    @Override
    public double get(long t) {
        return pm.fastReconstruct(t);
    }
}
