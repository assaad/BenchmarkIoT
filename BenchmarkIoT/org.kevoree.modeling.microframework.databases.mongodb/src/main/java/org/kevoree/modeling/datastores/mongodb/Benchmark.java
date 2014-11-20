package org.kevoree.modeling.datastores.mongodb;

import org.kevoree.Polynomial.impl.DataLoaderZip;
import org.kevoree.Polynomial.impl.DataPoint;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by assaa_000 on 19/11/2014.
 */
public class Benchmark {
    public static void main(String[] args) {
        long starttime;
        long endtime;
        double res;

        starttime = System.nanoTime();
        ArrayList<DataPoint> points = DataLoaderZip.load("oneweeksample.txt", 2);
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Loaded :" +points.size() + " values in " + res + " s!");

        try {
            MongoDbDataBase mdb = new MongoDbDataBase("localhost" , 27017, "mydb");
            for(int i=0; i<points.size();i++){
                String[][] payloads=new String[1][2];
                payloads[0][0]=String.valueOf(points.get(0).time);
                payloads[0][1]=String.valueOf(points.get(0).value);
                mdb.put(payloads);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
