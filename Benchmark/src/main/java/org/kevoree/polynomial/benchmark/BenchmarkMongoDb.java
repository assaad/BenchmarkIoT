package org.kevoree.polynomial.benchmark;


import org.kevoree.util.dbdrivers.MongoDbDataBase;


/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkMongoDb extends Benchmark {
    MongoDbDataBase mdb;


    @Override
    public void init() {
        try{
        mdb = new MongoDbDataBase("localhost", 27017, "mydb");
            mdb.clean();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public String getBenchmarkName() {
        return "Mongo Db";
    }

    @Override
    public void put(long t, double value) {
        String[] payloads = new String[2];
        payloads[0] = String.valueOf(t);
        payloads[1] = String.valueOf(value);
        mdb.put(payloads);

    }

    @Override
    public double get(long t) {
        return Double.valueOf(mdb.get(String.valueOf(t)));
    }
}
