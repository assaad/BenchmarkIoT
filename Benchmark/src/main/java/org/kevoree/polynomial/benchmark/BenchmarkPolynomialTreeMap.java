package org.kevoree.polynomial.benchmark;


import org.kevoree.util.polynomial.PolynomialModel;


/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkPolynomialTreeMap extends Benchmark {
    PolynomialModel pm;
    public double error=1;

    public int countt=0;

    public void print(){
        pm.displayStatistics(true);
    }

    public void setError(double error){
        this.error=error;
    }

    @Override
    public void init() {
        int degrade= 1;
        pm = new PolynomialModel(degrade,error,20);
    }

    /*public void finalinit(){
        pm.finalSave();
    }*/

    @Override
    public String getBenchmarkName() {
        return "Polynomial Tree Map";
    }

    @Override
    public void put(long t, double value)
    {
       // countt++;
        pm.feed(t,value);
       /* if(pm.getAll()!=countt){
            System.out.println("count: "+countt+" poly: "+pm.getAll());
        }*/
    }

    @Override
    public void finalput() {

    }
    @Override
    public void firstget() {

    }

    @Override
    public double get(long t) {
        return pm.fastReconstruct(t);
    }
}
