package org.kevoree.polynomial.benchmark;

import org.kevoree.util.polynomial.PolynomialModel;

import java.util.TreeMap;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkPolynomialTreeMap extends Benchmark {
    @Override
    public double benchmarkWrite(int number) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (number <= 0)
            return 0;

        for (int j = 0; j < number; j++) {
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
        avg = avg / number;
        return avg;
    }

    @Override
    public double benchmarkRead(int number) {

        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (number <= 0)
            return 0;

        PolynomialModel pm = new PolynomialModel(1000,1,20);
        for (int i = 0; i < points.size(); i++) {
            pm.feed(points.get(i).time, points.get(i).value);
        }

        for (int j = 0; j < number; j++) {

            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
                pm.fastReconstruct(points.get(i).time);
            }
            pm.finalSave();
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
            System.gc();
        }
        avg = avg / number;
        return avg;

    }

    @Override
    public String getBenchmarkName() {
        return "Polynomial Tree Map";
    }
}
