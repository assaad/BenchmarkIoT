package org.kevoree.util;

import org.kevoree.util.polynomial5.PolynomialModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

/**
 * Created by Assaad on 02/12/2014.
 */
public class TestPolynomial {
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

        long timeOrigine = getTimeStamp(2000, 5, 30, 17, 27);
        int degradeFactor = 60000;
        double toleratedError = 0.001;
        int maxDegree = 4;

        TreeMap<Long, Double> eurUsd = new TreeMap<Long, Double>();
        PolynomialModel pm = new PolynomialModel(degradeFactor,toleratedError,maxDegree);

        ArrayList<Long> timestamps = new ArrayList<Long>();
        ArrayList<Double> valss = new ArrayList<Double>();

        starttime = System.nanoTime();
        //String csvFile = "D:\\workspace\\Github\\kevoree-brain\\org.kevoree.brain.learning\\src\\main\\resources\\neweur.csv";
        String csvFile = "/Users/assaad/work/github/kevoree-brain/org.kevoree.brain.learning/src/main/resources/neweur.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                Long timestamp = Long.parseLong(values[2]);
                Double val = Double.parseDouble(values[3]);
                eurUsd.put(timestamp, val);
                pm.feed(timestamp, val);
                timestamps.add(timestamp);
                valss.add(val);

            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        pm.finalSave();
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Loaded :" + eurUsd.size() + " values in " + res + " s!");

        starttime = System.nanoTime();
        pm.displayStatistics(true);
        endtime = System.nanoTime();
        res=((double)(endtime-starttime))/(1000000);
        System.out.println("Statistic calculated in: "+res+" ms!");



        Long initTimeStamp = getTimeStamp(2001,01,01,00,00);
        Long finalTimeStamp= getTimeStamp(2014,9,26,00,00);


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
