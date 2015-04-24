package org.kevoree.polynomial.benchmark;

import org.kevoree.util.DataPoint;

import java.util.ArrayList;

/**
 * Created by assaad on 24/04/15.
 */
public class TestKmF {
    public static void main(String[] arg){

        int size=1000000;
        ArrayList<DataPoint> points=new ArrayList<DataPoint>(size);
        for(int i=0;i<size;i++){
            DataPoint dp= new DataPoint();
            dp.time=i;
            dp.value=42;
            points.add(dp);
        }


        BenchmarkKmfPolynomial bkmf = new BenchmarkKmfPolynomial();
        bkmf.init();
        bkmf.setDataPoints(points);


        BenchmarkKmfDiscrete dkmf = new BenchmarkKmfDiscrete();
        dkmf.init();
        dkmf.setDataPoints(points);


        BenchmarkPolynomialTreeMap btm= new BenchmarkPolynomialTreeMap();
        btm.setDataPoints(points);
        btm.init();

        for(int i=0;i<size;i++){
            bkmf.put(points.get(i).time,points.get(i).value);
            btm.put(points.get(i).time,points.get(i).value);
            dkmf.put(points.get(i).time,points.get(i).value);
        }


        bkmf.print();
        btm.print();
        dkmf.print();




    }
}
