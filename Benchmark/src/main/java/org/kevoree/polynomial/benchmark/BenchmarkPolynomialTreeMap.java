package org.kevoree.polynomial.benchmark;


import org.kevoree.util.polynomial.PolynomialModel;


/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkPolynomialTreeMap extends Benchmark {
    PolynomialModel pm;
    public double error;

    @Override
    public void init() {
        int degrade= (int)(points.get(1).time -points.get(0).time);
        pm = new PolynomialModel(degrade,error,5);
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
