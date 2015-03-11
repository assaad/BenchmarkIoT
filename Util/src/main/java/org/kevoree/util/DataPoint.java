package org.kevoree.util;

/**
 * Created by assaa_000 on 19/11/2014.
 */
public class DataPoint implements Comparable {
    public long time;
    public double value;

    public DataPoint(){

    }
    public DataPoint(long time, double value) {
        this.time=time;
        this.value=value;
    }

    @Override
    public int compareTo(Object o) {
        DataPoint dp=(DataPoint) o;
        return Long.compare(time, dp.time);
    }
}
