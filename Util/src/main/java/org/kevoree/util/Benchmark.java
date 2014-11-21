package org.kevoree.util;



import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by assaa_000 on 19/11/2014.
 */
public class Benchmark {

    public static void main(String[] args) {
        long starttime;
        long endtime;
        double res;


        for(int i=1; i<=9; i+=2) {
            starttime = System.nanoTime();

            ArrayList<DataPoint> points = DataLoaderZip.load("ds"+i+".zip");
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            System.out.println(" Loaded :" + points.size() + " values in " + res + " s!");

            long seed = System.nanoTime();
            Collections.shuffle(points, new Random(seed));
            FileWriter outFile;
            try {
                outFile = new FileWriter("ds"+(i+1)+".csv");
                PrintWriter out = new PrintWriter(outFile);
                for (DataPoint dp : points) {
                    out.println(dp.time + "," + dp.value);
                }
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }



    }
}
