package org.kevoree.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by assaad on 03/03/15.
 */
public class TreeTest {
    public static void main(String[] arg){
        TreeMap<Long, Double> testing = new TreeMap<Long, Double>();
        int max=2000000;
        int number=20;

        Random random=new Random();
        long starttime;
        long endtime;
        double res;

        double total=0;

        long seed = System.nanoTime();
        ArrayList<Long> values = new ArrayList<Long>();



        for(long i=0;i<max;i++){
            values.add(i);
            double vv=random.nextDouble();
            total+=vv;
            testing.put(i,vv);
        }


        double avg = 0;
        double val=0;


        for (int j = 0; j < number; j++) {
            val=0;
            starttime = System.nanoTime();
            for (int i=0;i<max;i++) {
                val+=testing.get(values.get(i));
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
        }
        avg = avg / number;
        System.out.println("Original sum "+total+" after sequential "+val);
        System.out.println("Sequential read avg: "+avg+" s");


        System.out.println("Before shuffling, value at 0: "+values.get(0));
        Collections.shuffle(values, new Random(seed));
        System.out.println("Shuffle completed, value at 0: "+values.get(0));

        avg = 0;
        for (int j = 0; j < number; j++) {
            val=0;
            starttime = System.nanoTime();
            for (int i=0;i<max;i++) {
                val+=testing.get(values.get(i));
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
        }
        avg = avg / number;
        System.out.println("Original sum "+total+" after random "+val);
        System.out.println("Random read avg: "+avg+" s");


    }
}
