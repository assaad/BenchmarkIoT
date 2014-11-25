package org.kevoree.polynomial.benchmark;

import java.util.TreeMap;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkTreeMap extends Benchmark {
    @Override
    public double benchmarkWrite(int number) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (number <= 0)
            return 0;

        for (int j = 0; j < number; j++) {
            TreeMap<Long, Double> treetest = new TreeMap<Long, Double>();
            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
                treetest.put(points.get(i).time, points.get(i).value);
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
            treetest.clear();
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

        TreeMap<Long, Double> treetest = new TreeMap<Long, Double>();
        for (int i = 0; i < points.size(); i++) {
            treetest.put(points.get(i).time, points.get(i).value);
        }

        for (int j = 0; j < number; j++) {
            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
                treetest.get(points.get(i).time);
            }
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
        return "Tree Map";
    }
}