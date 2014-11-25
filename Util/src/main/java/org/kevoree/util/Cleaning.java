package org.kevoree.util;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by assaa_000 on 24/11/2014.
 */
public class Cleaning {
    public static void main(String[] args) {
        long starttime;
        long endtime;
        double res;


        for(int i=1; i<=10;i++){
            starttime = System.nanoTime();
            ArrayList<DataPoint> points = DataLoaderZip.load("ds"+i+".zip");
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            System.out.println(" Loaded ds"+i+": " + points.size() + " values in " + res + " s!");

            TreeMap<Long, Double> test = new TreeMap<Long, Double>();
            ArrayList<DataPoint> points2 = new ArrayList<DataPoint>(points.size());

            for(int j=0; j<points.size();j++){
                if(test.containsKey(points.get(j).time)==false){
                    test.put(points.get(j).time,points.get(j).value);
                    points2.add(points.get(j));
                }
                else {
                    System.out.println("ERROR!!!!");
                }
            }
            System.out.println("Final val of ds"+i+": " + points2.size());

         /*   FileWriter outFile;
            try {
                outFile = new FileWriter("ds"+(i)+".csv");
                PrintWriter out = new PrintWriter(outFile);
                for (DataPoint dp : points2) {
                    out.println(dp.time + "," + dp.value);
                }
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }*/
        }



    }

}
