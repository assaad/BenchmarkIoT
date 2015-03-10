package org.kevoree.polynomial.benchmark;

import org.kevoree.util.DataLoaderZip;
import org.kevoree.util.DataPoint;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class ExecutorPaper {

    public static void execute(String dataset, String name, int times){

        Benchmark[] toRun = new Benchmark[2];

        toRun[0] = new BenchmarkTreeMap();
        toRun[1] = new BenchmarkPolynomialTreeMap();
        long starttime;
        long endtime;
        double res;
        starttime = System.nanoTime();
        DataLoaderZip.setBaseDir("/Users/assaad/work/github/BenchmarkIoT/DataSets/");

        final ArrayList<DataPoint> points = DataLoaderZip.load(dataset);
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Loaded "+name+" :" + points.size() + " values in " + res + " s!");

        for (int i = 0; i < toRun.length; i++) {
            toRun[i].setGcCollect(true);
            toRun[i].setDataPoints(points);
            System.out.println("Writing on " + toRun[i].getBenchmarkName() + ": " + toRun[i].benchmarkWrite(times)+" s");
        }


        System.out.println("-----------------------------------------------");

        for (int i = 0; i < toRun.length; i++) {
            toRun[i].setDataPoints(points);
            System.out.println("Sequential reading on " + toRun[i].getBenchmarkName() + ": " + toRun[i].benchmarkSequencialRead(times, points.size())+" s");
        }

        System.out.println("-----------------------------------------------");

        for (int i = 0; i < toRun.length; i++) {
            toRun[i].setDataPoints(points);
            System.out.println("Random reading on " + toRun[i].getBenchmarkName() + ": " + toRun[i].benchmarkRandomRead(times,points.size())+" s");
        }

    }


    public static void main(String[] args) throws IOException {
        int times=100;
        execute("ds0.zip","Constant", times); //Constant database
        execute("ds1.zip","Temperature",times); //Temperature database
        execute("ds3.zip","Luminosity",times); //Temperature database
        execute("ds5.zip","Electric",times); //Temperature database
        execute("ds9.zip","Sound",times); //Temperature database



    }
}
