package org.kevoree.polynomial.benchmark;

import org.kevoree.modeling.api.time.rbtree.RBTree;
import org.kevoree.modeling.api.time.rbtree.State;


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
            RBTree treetest = new RBTree();
            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
                treetest.insert(points.get(i).time, State.EXISTS);
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
            treetest = null;
            System.gc();
        }
        avg = avg / number;
        return avg;
    }

    @Override
    public double benchmarkRead(int number) {
        return 0;
    }

    @Override
    public String getBenchmarkName() {
        return "Rb Tree";
    }
}
