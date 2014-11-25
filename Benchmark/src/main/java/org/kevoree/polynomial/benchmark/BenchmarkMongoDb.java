package org.kevoree.polynomial.benchmark;


import org.kevoree.util.dbdrivers.MongoDbDataBase;


/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkMongoDb extends Benchmark {
    @Override
    public double benchmarkWrite(int number) {
        long starttime;
        long endtime;
        double res;

        double avg = 0;
        if (number <= 0)
            return 0;

        try {
            MongoDbDataBase mdb = new MongoDbDataBase("localhost", 27017, "mydb");

            for (int j = 0; j < number; j++) {
                mdb.clean();
                starttime = System.nanoTime();
                for (int i = 0; i < points.size(); i++) {
                    String[] payloads = new String[2];
                    payloads[0] = String.valueOf(points.get(0).time);
                    payloads[1] = String.valueOf(points.get(0).value);
                    mdb.put(payloads);
                    if (i % 1000000 == 0) {
                        System.out.println(i);
                    }
                }
                endtime = System.nanoTime();
                res = ((double) (endtime - starttime)) / (1000000000);
                avg += res;
                System.gc();
            }
            avg = avg / number;
            return avg;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public double benchmarkRead(int number) {
        return 0;
    }

    @Override
    public String getBenchmarkName() {
        return "Mongo Db";
    }
}
