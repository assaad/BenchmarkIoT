package org.kevoree.polynomial.benchmark;

/**
 * Created by assaad on 23/04/15.
 */
import com.google.caliper.Benchmark;
import com.google.caliper.Param;

/**
 * Created by duke on 23/04/15.
 */
public class PopulateBench {

    @SuppressWarnings("UnusedDeclaration")
    @Benchmark
    void timeNanoTime(int reps) {
        for (int i = 0; i < 100000; i++) {
            System.nanoTime();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    @Benchmark
    void timeCurrentTimeMillis(int reps) {
        for (int i = 0; i < 1; i++) {
            System.currentTimeMillis();
        }
    }

}
