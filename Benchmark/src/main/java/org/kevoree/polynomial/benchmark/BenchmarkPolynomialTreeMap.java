package org.kevoree.polynomial.benchmark;

import org.kevoree.util.polynomial.PolynomialModel;

import java.util.Random;
import java.util.TreeMap;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkPolynomialTreeMap extends Benchmark {
    @Override
    public double benchmarkWrite(int iterations) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (iterations <= 0)
            return 0;

        for (int j = 0; j < iterations; j++) {
            PolynomialModel pm = new PolynomialModel(1000,1,20);
            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
                pm.feed(points.get(i).time, points.get(i).value);
            }
            pm.finalSave();
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
            pm=null;
            System.gc();
        }
        avg = avg / iterations;
        return avg;
    }

    @Override
    public double benchmarkRandomRead(int iterations, int value) {

        long starttime;
        long endtime;
        double res;
        Random random= new Random();

        double avg = 0;
        if (iterations <= 0)
            return 0;

        PolynomialModel pm = new PolynomialModel(1000,1,20);
        for (int i = 0; i < points.size(); i++) {
            pm.feed(points.get(i).time, points.get(i).value);
        }

        for (int j = 0; j < iterations; j++) {

            starttime = System.nanoTime();
            for (int i = 0; i < value; i++) {
                pm.fastReconstruct(points.get(random.nextInt(points.size())).time);
            }
            pm.finalSave();
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
            System.gc();
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

        PolynomialModel pm = new PolynomialModel(1000,1,20);
        for (int i = 0; i < points.size(); i++) {
            pm.feed(points.get(i).time, points.get(i).value);
        }

        for (int j = 0; j < iterations; j++) {

            starttime = System.nanoTime();
            for (int i = 0; i < value; i++) {
                pm.fastReconstruct(points.get(i).time);
            }
            pm.finalSave();
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
            System.gc();
        }
        avg = avg / iterations;
        return avg;
    }

    @Override
    public String getBenchmarkName() {
        return "Polynomial Tree Map";
    }
}
