package org.kevoree.benchmark.discretedb.mongodb;



import org.kevoree.util.DataLoaderZip;
import org.kevoree.util.DataPoint;

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
        ArrayList<DataPoint> points = DataLoaderZip.load("ds1.zip");
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Loaded :" +points.size() + " values in " + res + " s!");

        starttime = System.nanoTime();
        try {
            MongoDbDataBase mdb = new MongoDbDataBase("localhost" , 27017, "mydb");
            mdb.clean();
            for(int i=0; i<points.size();i++){
                String[] payloads=new String[2];
                payloads[0]=String.valueOf(points.get(0).time);
                payloads[1]=String.valueOf(points.get(0).value);
                mdb.put(payloads);
                if(i%500000==0) {
                    System.out.println(i);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Database inserted in: "+ res + " s!");



       /*try {
       //An empty db test
            MongoDbDataBase mdb = new MongoDbDataBase("localhost" , 27017, "mydb");
            mdb.clean();
            String[] payloads=new String[2];
            payloads[0]="12345";
            payloads[1]="3.1416";
            mdb.put(payloads);
            System.out.println("result: "+mdb.get("12345"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
*/


    }
}
