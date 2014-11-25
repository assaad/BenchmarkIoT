package org.kevoree.polynomial.benchmark;

import org.kevoree.util.DataPoint;

import java.util.ArrayList;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkArrayList extends Benchmark {
    @Override
    public double benchmarkWrite(int number) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (number <= 0)
            return 0;

        for (int j = 0; j < number; j++) {
            ArrayList<DataPoint> points2 = new ArrayList<DataPoint>();
            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
                points2.add(new DataPoint(points.get(i).time, points.get(i).value));
            }
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            avg += res;
            points2.clear();
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

        ArrayList<DataPoint> points2 = new ArrayList<DataPoint>();
        for (int i = 0; i < points.size(); i++) {
            points2.add(new DataPoint(points.get(i).time, points.get(i).value));
        }

        for (int j = 0; j < number; j++) {
            starttime = System.nanoTime();
            for (int i = 0; i < points.size(); i++) {
                points2.get(points2.indexOf(points2.get(i)));
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
        return "Array List";
    }
}
