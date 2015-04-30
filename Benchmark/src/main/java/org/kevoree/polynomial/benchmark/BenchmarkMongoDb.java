package org.kevoree.polynomial.benchmark;


import org.kevoree.util.dbdrivers.MongoDbDataBase;


/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkMongoDb extends Benchmark {
    MongoDbDataBase mdb;
    private long[] arrayt;
    private double[] arrayv;
    int size=10000;
    int counter=0;

    @Override
    public void finalput() {

    }

    @Override
    public void init() {
        try{
            arrayt=new long[size];
            arrayv=new double[size];
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

       /* String[] payloads = new String[2];
        payloads[0] = String.valueOf(t);
        payloads[1] = String.valueOf(value);
        mdb.put(payloads);*/

       arrayt[counter]=t;
        arrayv[counter]=value;
        counter++;
        if(counter==size){
            mdb.Arrayput(arrayt,arrayv);
            counter=0;
        }
    }



    @Override
    public double get(long t) {
        return Double.valueOf(mdb.get(String.valueOf(t)));
    }
}
