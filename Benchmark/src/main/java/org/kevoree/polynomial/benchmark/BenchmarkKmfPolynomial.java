package org.kevoree.polynomial.benchmark;


import org.kevoree.modeling.api.Callback;
import org.kevoree.modeling.api.KObject;
import org.kevoree.modeling.bench.*;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkKmfPolynomial extends Benchmark {
    private SmartSystemModel system;

    private long psID;


    @Override
    public void init() {
        system = new SmartSystemModel();
        system.connect();
        SmartSystemView s0=system.universe(0).time(0);
        PolynomialSensor ps = s0.createPolynomialSensor().setName("sensor0");
        s0.setRoot(ps);
        psID=ps.uuid();
    }

    @Override
    public String getBenchmarkName() {
        return "KMF Polynomial";
    }

    @Override
    public void put(long t, double value) {

        final long tt=t;
        final double vv=value;



        system.universe(0).time(tt).lookup(psID).then(new Callback<KObject>(){
            public void on(KObject kObject) {
                PolynomialSensor casted = (PolynomialSensor) kObject;
                casted.setValue(vv);
            }
        });


    }

    @Override
    public double get(long t) {

        final long tt=t;
        final double[] value = new double[1];

        system.universe(0).time(tt).lookup(psID).then(new Callback<KObject>(){
            public void on(KObject kObject) {
                PolynomialSensor casted = (PolynomialSensor) kObject;
                value[0] = casted.getValue();
            }
        });
        return value[0];


    }
}
