package org.kevoree.polynomial.benchmark;


import java.util.TreeMap;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkTreeMap extends Benchmark {

    private TreeMap<Long, DPoint> treeMap;

    @Override
    public void init() {
        treeMap=new TreeMap<Long, DPoint>();
    }

    @Override
    public String getBenchmarkName() {
        return "Java Tree Map";
    }

    @Override
    public void put(long t, double value) {
        DPoint dp = new DPoint();
        dp.value=value;
        treeMap.put(t,dp);
    }

    @Override
    public void finalput() {

    }

    @Override
    public double get(long t) {
        try {

            return treeMap.get(treeMap.floorKey(t)).value;
        }
        catch (Exception ex){
            System.out.println(t);
        }
return 0;
    }
}
