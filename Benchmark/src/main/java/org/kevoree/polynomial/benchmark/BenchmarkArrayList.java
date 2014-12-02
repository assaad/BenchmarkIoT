package org.kevoree.polynomial.benchmark;

import org.kevoree.util.DataPoint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkArrayList extends Benchmark {
    @Override
    public double benchmarkWrite(int iterations) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (iterations <= 0)
            return 0;

        for (int j = 0; j < iterations; j++) {
            ArrayList<DataPoint> points2 = new ArrayList<DataPoint>();
            if(gcCollect) {
                System.gc();
            }
            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
                points2.add(new DataPoint(points.get(i).time, points.get(i).value));
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
            points2.clear();
        }
        avg = avg / iterations;
        return avg;
    }

    @Override
    public double benchmarkRandomRead(int iterations, int value) {
        long starttime;
        long endtime;
        double res;
        Random rand = new Random();

        double avg = 0;
        if (iterations <= 0)
            return 0;

        ArrayList<DataPoint> points2 = new ArrayList<DataPoint>();
        for (int i = 0; i < points.size(); i++) {
            points2.add(new DataPoint(points.get(i).time, points.get(i).value));
        }

        for (int j = 0; j < iterations; j++) {
            if(gcCollect) {
                System.gc();
            }
            starttime = System.nanoTime();
            for (int i = 0; i < value; i++) {
                points2.get(points2.indexOf(points2.get(rand.nextInt(points2.size()))));
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;

        }
        avg = avg / iterations;
        return avg;

    }

    @Override
    public double benchmarkSequencialRead(int iterations, int value) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (iterations <= 0)
            return 0;

        ArrayList<DataPoint> points2 = new ArrayList<DataPoint>();
        for (int i = 0; i < points.size(); i++) {
            points2.add(new DataPoint(points.get(i).time, points.get(i).value));
        }

        for (int j = 0; j < iterations; j++) {
            if(gcCollect) {
                System.gc();
            }
            starttime = System.nanoTime();
            for (int i = 0; i < value; i++) {
                points2.get(points2.indexOf(points2.get(i))); //tochange
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;

        }
        avg = avg / iterations;
        return avg;
    }

    @Override
    public String getBenchmarkName() {
        return "Array List";
    }
}
