package org.kevoree.polynomial.benchmark;


import org.kevoree.util.dbdrivers.MongoDbDataBase;
import org.kevoree.util.polynomial.PolynomialModel;


/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkMongoDbPolynomial extends Benchmark {
    MongoDbDataBase mdb;
    PolynomialModel pm;
    public double error;


    public void print(){
        pm.displayStatistics(true);
    }

    @Override
    public void init() {
        int degrade= (int)(points.get(1).time -points.get(0).time);
        pm = new PolynomialModel(degrade,error,5);
        try{
            mdb = new MongoDbDataBase("localhost", 27017, "mydb");
            mdb.clean();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*public void finalinit(){
        pm.finalSave();
    }*/

    @Override
    public String getBenchmarkName() {
        return "Polynomial Mongo Db";
    }



    @Override
    public void put(long t, double value) {
        pm.feed(t,value);
    }

    @Override
    public void finalput() {
        mdb.Arrayput(pm.getlistOfTime(), pm.getPolynomials());

    }


    @Override
    public void firstget(){
        int degrade= (int)(points.get(1).time -points.get(0).time);
        PolynomialModel pmnew = new PolynomialModel(degrade,error,5);
        Object[] times =pm.getlistOfTime();
        String[] polys = new String[times.length];
        for(int i=0;i<times.length;i++){
            polys[i]= mdb.get(times[i].toString());
        }
        pmnew.load(times,polys);
        pm=pmnew;
    }

    @Override
    public double get(long t) {
        return pm.fastReconstruct(t);
    }
}
