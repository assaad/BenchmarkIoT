package org.kevoree.polynomial.benchmark;


import iotbenchmark.IotBenchmarkDimension;
import iotbenchmark.IotBenchmarkUniverse;
import iotbenchmark.IotBenchmarkView;

import iotbenchmark.Sensor;
import org.kevoree.modeling.api.Callback;
import org.kevoree.modeling.api.KObject;
import org.kevoree.modeling.api.time.rbtree.RBTree;
import org.kevoree.modeling.api.time.rbtree.State;
import org.kevoree.util.DataLoaderZip;
import org.kevoree.util.DataPoint;


import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by assaa_000 on 19/11/2014.
 */
public class Benchmark {
    public static void main(String[] args) {
        long starttime;
        long endtime;
        double res;

        starttime = System.nanoTime();
        final ArrayList<DataPoint> points = DataLoaderZip.load("ds1.zip");
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Loaded :" +points.size() + " values in " + res + " s!");



        RBTree treetest2 = new RBTree();
        starttime = System.nanoTime();
        for(int i=0;i<points.size();i++){
            treetest2.insert(points.get(i).time, State.EXISTS);
        }
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Time to insert in a RBtree: " + res + " s!");
        System.out.println(treetest2.size());


        ArrayList<DataPoint> points2 = new ArrayList<DataPoint>();
        starttime = System.nanoTime();
        for(int i=0;i<points.size();i++){
            points2.add(new DataPoint(points.get(i).time,points.get(i).value));
        }
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Time to insert in a Arraylist: " + res + " s!");


        TreeMap<Long, Double> treetest = new TreeMap<Long, Double>();
        starttime = System.nanoTime();
        for(int i=0;i<points.size();i++){
            treetest.put(points.get(i).time,points.get(i).value);
        }
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Time to insert in a treemap: " + res + " s!");
        System.out.println(treetest.size());






/*
        final long starttime2 = System.nanoTime();
        final IotBenchmarkUniverse universe = new IotBenchmarkUniverse();
        universe.newDimension(new Callback<IotBenchmarkDimension>() {
            @Override
            public void on(IotBenchmarkDimension dimension0) {

                IotBenchmarkView t0 = dimension0.time(0l);
                final Sensor sensor = t0.createSensor();

                for(int i=0; i<points.size();i++){

                    final long finaltime = points.get(i).time;
                    final double value= points.get(i).value;

                    if(i%100000==0){
                        try {
                            dimension0.saveUnload(new Callback<Throwable>() {
                                @Override
                                public void on(Throwable throwable) {

                                    //throwable.printStackTrace();
                                }
                            });
                        }
                        catch (Exception ex){
                            System.out.println("ex found");
                            //ex.printStackTrace();
                        }
                        System.out.println(i);
                    }

                    dimension0.time(finaltime).lookup(sensor.uuid(), new Callback<KObject>() {
                        @Override
                        public void on(KObject kObject) {
                            Sensor casted = (Sensor) kObject;
                            casted.setValue(value);
                        }
                    });
                }

                long endtime2 = System.nanoTime();
                double res2 = ((double) (endtime2 - starttime2)) / (1000000000);
                System.out.println("Took: " + res2 + " s!");
                System.out.println("Elements inserted: "+sensor.timeTree().size());
            }
        });
*/

    }
}
