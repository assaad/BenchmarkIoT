package org.kevoree.polynomial.benchmark;

import org.kevoree.modeling.api.time.rbtree.RBTree;
import org.kevoree.modeling.api.time.rbtree.State;

import java.util.Random;


/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkRbTree extends Benchmark {
    @Override
    public double benchmarkWrite(int number) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (number <= 0)
            return 0;

        for (int j = 0; j < number; j++) {
            System.gc();
            RBTree treetest = new RBTree();
            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
                treetest.insert(points.get(i).time, State.EXISTS);
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
        RBTree treetest = new RBTree();
        for (int i = 0; i < points.size(); i++) {
            treetest.insert(points.get(i).time, State.EXISTS);
        }

        for (int j = 0; j < iterations; j++) {
            System.gc();
            starttime = System.nanoTime();
            for (int i = 0; i < value; i++) {
                treetest.lookup(points.get(random.nextInt(points.size())).time);
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
        RBTree treetest = new RBTree();
        for (int i = 0; i < points.size(); i++) {
            treetest.insert(points.get(i).time, State.EXISTS);
        }

        for (int j = 0; j < iterations; j++) {

            starttime = System.nanoTime();
            for (int i = 0; i < value; i++) {
                treetest.lookup(points.get(i).time);
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
        return "Rb Tree";
    }
}
