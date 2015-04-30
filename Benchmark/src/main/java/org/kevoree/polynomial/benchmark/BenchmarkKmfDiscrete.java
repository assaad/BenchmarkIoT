package org.kevoree.polynomial.benchmark;


import org.kevoree.modeling.api.Callback;
import org.kevoree.modeling.api.KObject;
import org.kevoree.modeling.bench.DiscreteSensor;
import org.kevoree.modeling.bench.SmartSystemModel;
import org.kevoree.modeling.bench.SmartSystemView;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkKmfDiscrete extends Benchmark {
    private SmartSystemModel system;
    private DiscreteSensor ps;
    private long psID;


    public void print(){

        ps.timeWalker().allTimes().then(new Callback<long[]>() {
            public void on(long[] collected2) {
                System.out.println("Kmf discrete: "+collected2.length);
            }
        });

    }

    @Override
    public void init() {
        system = new SmartSystemModel();
        system.connect();
        SmartSystemView s0=system.universe(0).time(0);

        ps = s0.createDiscreteSensor().setName("sensor0");
        s0.setRoot(ps);
        psID=ps.uuid();


    }

    @Override
    public String getBenchmarkName() {
        return "KMF Discrete";
    }

    @Override
    public void put(long t, double value) {

        final long tt=t;
        final double vv=value;
        system.universe(0).time(tt).lookup(psID).then(new Callback<KObject>(){
            public void on(KObject kObject) {
                DiscreteSensor casted = (DiscreteSensor) kObject;
                casted.setValue(vv);
            }
        });


    }

    @Override
    public void finalput() {

    }

    @Override
    public double get(long t) {

        final long tt=t;
        final double[] value = new double[1];

        system.universe(0).time(tt).lookup(psID).then(new Callback<KObject>(){
            public void on(KObject kObject) {
                DiscreteSensor casted = (DiscreteSensor) kObject;
                value[0] = casted.getValue();
            }
        });
        return value[0];


    }
}

