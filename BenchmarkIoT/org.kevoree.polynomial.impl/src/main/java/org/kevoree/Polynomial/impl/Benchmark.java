package org.kevoree.Polynomial.impl;

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
            System.out.println(" Loaded :" +points.size() + " values in " + res + " s!");

           /* FileWriter outFile;
            try {
                outFile = new FileWriter(i+".csv");
                PrintWriter out = new PrintWriter(outFile);
                for (DataPoint dp: points) {
                    out.println(dp.time+","+dp.value);
                }
                out.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }*/




    }
}
