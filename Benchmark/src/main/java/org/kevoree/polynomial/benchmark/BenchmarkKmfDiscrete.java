package org.kevoree.polynomial.benchmark;

import org.kevoree.util.DataPoint;

import java.util.ArrayList;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkKmfDiscrete extends Benchmark {
    @Override
    public double benchmarkWrite(int number) {
        /* Old Code was
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
         */


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
                //Insert into KMF Here
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
        return 0;
    }

    @Override
    public String getBenchmarkName() {
        return "KMF discrete";
    }
}
