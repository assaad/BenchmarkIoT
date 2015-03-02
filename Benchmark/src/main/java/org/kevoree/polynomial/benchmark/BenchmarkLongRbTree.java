package org.kevoree.polynomial.benchmark;

import org.kevoree.modeling.api.rbtree.LongRBTree;

import java.util.Random;


/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkLongRbTree extends Benchmark {
    @Override
    public double benchmarkWrite(int number) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (number <= 0)
            return 0;

        for (int j = 0; j < number; j++) {
            if(gcCollect) {
                System.gc();
            }
            LongRBTree treetest = new LongRBTree();
            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
                treetest.insert(points.get(i).time, points.get(i).time);
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
            treetest = null;

        }
        avg = avg / number;
        return avg;
    }

    @Override
    public double benchmarkRandomRead(int iterations, int value) {

        long starttime;
        long endtime;
        double res;
        Random random = new Random();

        double avg = 0;
        if (iterations <= 0)
            return 0;
        LongRBTree treetest = new LongRBTree();
        for (int i = 0; i < points.size(); i++) {
            treetest.insert(points.get(i).time, points.get(i).time);
        }

        for (int j = 0; j < iterations; j++) {
            if(gcCollect) {
                System.gc();
            }
            starttime = System.nanoTime();
            for (int i = 0; i < value; i++) {
                treetest.previousOrEqual(points.get(random.nextInt(points.size())).time);
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
        LongRBTree treetest = new LongRBTree();
        for (int i = 0; i < points.size(); i++) {
            treetest.insert(points.get(i).time, points.get(i).time);
        }

        for (int j = 0; j < iterations; j++) {
            if(gcCollect) {
                System.gc();
            }
            starttime = System.nanoTime();
            for (int i = 0; i < value; i++) {
                treetest.previousOrEqual(points.get(i).time);
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
        return "Long Rb Tree";
    }
}
