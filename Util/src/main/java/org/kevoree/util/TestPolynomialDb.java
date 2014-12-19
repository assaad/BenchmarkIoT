package org.kevoree.util;

import org.kevoree.util.polynomial6.PolynomialModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

/**
 * Created by Assaad on 02/12/2014.
 */
public class TestPolynomialDb {
    public static Long getTimeStamp(int year, int month, int day, int hour, int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, min, 0);
        Date date = cal.getTime(); // get back a Date object
        Long timestamp = date.getTime() - date.getTime() % 1000;
        return timestamp;
    }

    public static void main(String[] args) {

       /* Date d=new Date();
        d.setTime(Long.parseLong("991949460000"));*/

        long starttime;
        long endtime;
        double res;

        TreeMap<Long, Double> eurUsd = new TreeMap<Long, Double>();
        int degradeFactor = 1;
        double toleratedError = 0.9;
        int maxDegree = 20;

        starttime = System.nanoTime();
        DataLoaderZip.setBaseDir("/Users/assaad/work/github/BenchmarkIoT/DataSets/");
        final ArrayList<DataPoint> points = DataLoaderZip.load("ds0.zip");
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Loaded :" + points.size() + " values in " + res + " s!");

        PolynomialModel pm =new PolynomialModel(toleratedError,maxDegree);

       // PolynomialModel pm =new PolynomialModel(degradeFactor,toleratedError,maxDegree);

        try {

            for(DataPoint dp: points){
                pm.feed(dp.time,dp.value);
                eurUsd.put(dp.time,dp.value);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        pm.finalSave();
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Loaded polynomial :" + points.size() + " values in " + res + " s!");

        starttime = System.nanoTime();
        pm.displayStatistics(true);
        endtime = System.nanoTime();
        res=((double)(endtime-starttime))/(1000000);
        System.out.println("Statistic calculated in: "+res+" ms!");



        Long initTimeStamp = points.get(0).time;
        Long finalTimeStamp=  points.get(points.size()-1).time;


      /*  double max=0;

        long itest=0l;
        starttime = System.nanoTime();
        for(int i=0; i<timestamps.size();i++){
            double val = pm.fastReconstruct(timestamps.get(i));
            double val2 = valss.get(i);
            if(Math.abs(val2-val)>max){
                max=Math.abs(val2-val);
                itest=i;
            }
        }
        endtime = System.nanoTime();
        res=((double)(endtime-starttime))/(1000000);
        System.out.println("TEST TEST: "+res+" ms! ERROR "+ max+" @ "+ itest);*/


        starttime = System.nanoTime();
        for(long i=initTimeStamp; i<finalTimeStamp;i+=degradeFactor){
            double val = pm.reconstruct(i);
        }
        endtime = System.nanoTime();
        res=((double)(endtime-starttime))/(1000000);
        System.out.println("Polynomial chain reconstructed in: "+res+" ms!");


        starttime = System.nanoTime();
        for(long i=initTimeStamp; i<finalTimeStamp;i+=degradeFactor){
            double val = pm.fastReconstruct(i);
        }
        endtime = System.nanoTime();
        res=((double)(endtime-starttime))/(1000000);
        System.out.println("Polynomial fast reconstructed in: "+res+" ms!");


        starttime = System.nanoTime();
        for(long i=initTimeStamp; i<finalTimeStamp;i+=degradeFactor){
            double val = eurUsd.get(eurUsd.floorKey(i));
        }
        endtime = System.nanoTime();
        res=((double)(endtime-starttime))/(1000000);
        System.out.println("normal chain in: "+res+" ms!");


    }
}
