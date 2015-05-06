package org.kevoree.polynomial.benchmark;


import org.kevoree.util.DataPoint;

import java.util.*;

/**
 * Created by assaa_000 on 24/11/2014.
 */
public abstract class Benchmark {

    protected ArrayList<DataPoint> points;
    protected boolean gcCollect = true;

    public void setGcCollect(boolean gcCollect){
        this.gcCollect=gcCollect;
    }

    public void setDataPoints(ArrayList<DataPoint> points) {
        this.points = points;
    }


    public abstract void finalput();

    public double benchmarkWrite(int iterations){

        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (iterations <= 0)
            return 0;

        for (int j = 0; j < iterations; j++) {
            init();
            if(gcCollect) {
                System.gc();
            }
            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
                put(points.get(i).time, points.get(i).value);
            }
            finalput();
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
        }
        avg = avg / iterations;
        return avg;
    }

    public abstract void print();
    public abstract void firstget();

    public double benchmarkRandomRead(int iterations, int value) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (iterations <= 0)
            return 0;

        init();
        ArrayList<Long> results = new ArrayList<Long>();

        for (int i = 0; i < points.size(); i++) {
            put(points.get(i).time, points.get(i).value);
            results.add(points.get(i).time);
        }

        Collections.shuffle(results);

        for (int j = 0; j < iterations; j++) {
            if(gcCollect) {
                System.gc();
            }

            starttime = System.nanoTime();
            for (int i = 0; i < value; i++) {
                get(results.get(i));
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
        }
        avg = avg / iterations;
        return avg;
    }

    public double benchmarkSequencialRead(int iterations, int value) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (iterations <= 0)
            return 0;

        init();
        for (int i = 0; i < points.size(); i++) {
            put(points.get(i).time, points.get(i).value);
        }

        for (int j = 0; j < iterations; j++) {
            if(gcCollect) {
                System.gc();
            }
            firstget();
            starttime = System.nanoTime();
            for (int i = 0; i < value; i++) {
                get(points.get(i).time);
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
        }
        avg = avg / iterations;
        return avg;
    }

    public double benchmarkContinuity( int miss){

        init();
        for (int i = 0; i < points.size(); i+=miss) {
            put(points.get(i).time, points.get(i).value);
        }

        double res=0;

            if(gcCollect) {
                System.gc();
            }

            for (int i = 0; i < points.size(); i++) {
                try {
                    res += Math.abs(get(points.get(i).time) - points.get(i).value);
                }
                catch (Exception ex){
                    System.out.println("i= "+i+ " time "+points.get(i).time);
                }
            }


        return res/points.size();
    }


    public abstract void init();



    public abstract String getBenchmarkName();

    public abstract void put(long t, double value);

    public abstract double get(long t);

}
