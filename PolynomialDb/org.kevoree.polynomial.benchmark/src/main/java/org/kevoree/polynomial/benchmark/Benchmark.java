package org.kevoree.polynomial.benchmark;


import iotbenchmark.IotBenchmarkDimension;
import iotbenchmark.IotBenchmarkUniverse;
import iotbenchmark.Sensor;
import org.kevoree.modeling.api.Callback;
import org.kevoree.modeling.api.KObject;
import org.kevoree.modeling.databases.leveldb.LevelDbDataBase;
import org.kevoree.util.DataLoaderZip;
import org.kevoree.util.DataPoint;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by assaa_000 on 19/11/2014.
 */
public class Benchmark {
    public static void main(String[] args) throws IOException {

        long starttime;
        long endtime;
        double res;
        starttime = System.nanoTime();
        DataLoaderZip.setBaseDir("/Users/duke/Documents/dev/assaad/BenchmarkIoT/DataSets/");
        final ArrayList<DataPoint> points = DataLoaderZip.load("ds1.zip");
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Loaded :" + points.size() + " values in " + res + " s!");

        /*

        RBTree treetest2 = new RBTree();
        starttime = System.nanoTime();
        for(int i=0;i<points.size();i++){
            treetest2.insert(points.get(i).time, State.EXISTS);
        }
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Time to insert in a RBtree: " + res + " s!");
        //System.out.println(treetest2.size());


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
        */


        //System.out.println(treetest.size());

        //  MemoryKDataBase.DEBUG = true;

        final long starttime2 = System.nanoTime();
        final IotBenchmarkUniverse universe = new IotBenchmarkUniverse();
        universe.setDataBase(new LevelDbDataBase("/Users/duke/Documents/dev/assaad/BenchmarkIoT/db"));

        universe.newDimension(new Callback<IotBenchmarkDimension>() {
            @Override
            public void on(final IotBenchmarkDimension dim) {
                final IotBenchmarkDimension[] currentDim = {dim};
                final Sensor sensor = currentDim[0].time(0l).createSensor();
                for (int i = 0; i < points.size(); i++) {
                    final long finaltime = points.get(i).time;
                    final double value = points.get(i).value;


                    if (i % 1000000 == 0) {

                        currentDim[0].saveUnload(new Callback<Throwable>() {
                            @Override
                            public void on(Throwable throwable) {
                                currentDim[0].universe().dimension(currentDim[0].key(), new Callback<IotBenchmarkDimension>() {
                                    @Override
                                    public void on(IotBenchmarkDimension dimension) {
                                        currentDim[0] = dimension;
                                    }
                                });
                            }
                        });

                        System.out.println(i);
                    }
                    currentDim[0].time(finaltime).lookup(sensor.uuid(), new Callback<KObject>() {
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

                currentDim[0].time(0l).lookup(sensor.uuid(), new Callback<KObject>() {
                    @Override
                    public void on(KObject kObject) {
                        System.out.println("Elements inserted: " + kObject.timeTree().size());
                    }
                });

            }
        });


    }

}
