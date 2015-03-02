package org.kevoree.polynomial.benchmark;

import org.kevoree.util.DataPoint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkEmpty extends Benchmark {
    @Override
    public double benchmarkWrite(int iterations) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (iterations <= 0)
            return 0;

        for (int j = 0; j < iterations; j++) {
            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
            System.gc();
        }
        avg = avg / iterations;
        return avg;
    }

    @Override
    public double benchmarkRandomRead(int iterations, int values) {
        long starttime;
        long endtime;
        double res;
        Random random = new Random();

        double avg = 0;
        if (iterations <= 0)
            return 0;

        for (int j = 0; j < iterations; j++) {
            starttime = System.nanoTime();
            for (int i = 0; i < values; i++) {
                random.nextInt(points.size());
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
            System.gc();
        }
        avg = avg / iterations;
        return avg;
    }

    @Override
    public double benchmarkSequencialRead(int iterations, int values) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (iterations <= 0)
            return 0;

        for (int j = 0; j < iterations; j++) {
            starttime = System.nanoTime();
            for (int i = 0; i < values; i++) {
            }
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
        return "Empty iteration";
    }
}
