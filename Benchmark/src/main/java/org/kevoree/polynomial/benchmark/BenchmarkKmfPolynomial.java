package org.kevoree.polynomial.benchmark;


import org.kevoree.modeling.KCallback;
import org.kevoree.modeling.KObject;
import org.kevoree.modeling.bench.*;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkKmfPolynomial extends Benchmark {
    private SmartSystemModel system;
     private PolynomialSensor ps;
    private long psID;

   // private DiskCounter dc;



    public void print(){

        ps.timeWalker().allTimes(new KCallback<long[]>(){
            public void on(long[] collected2) {
                System.out.println("Kmf polynomial: "+collected2.length);
            }
        });
      //  System.out.println("size: "+dc.counter);

    }

    @Override
    public void init() {
        try {
            system = new SmartSystemModel();
            //dc=new DiskCounter();
            //system.manager().setContentDeliveryDriver(dc);
           // system.manager().setContentDeliveryDriver(new Mongodb("localhost", 27017, "mydb"));
            system.connect(new KCallback<Throwable>() {
                public void on(Throwable throwable) {
                    SmartSystemView s0 = system.universe(0).time(0);
                    ps = s0.createPolynomialSensor().setName("sensor0");
                    s0.setRoot(ps,null);
                    psID = ps.uuid();
                }
            });

        }
        catch(Exception ex){

        }
    }

    @Override
    public String getBenchmarkName() {
        return "KMF Polynomial";
    }

    @Override
    public void put(long t, double value) {

        final long tt=t;
        final double vv=value;

        system.universe(0).time(tt).lookup(psID,new KCallback<KObject>(){
            public void on(KObject kObject) {
                PolynomialSensor casted = (PolynomialSensor) kObject;
                casted.setValue(vv);
            }
        });


    }

    @Override
    public void finalput() {
        system.save(new KCallback<Throwable>() {
            public void on(Throwable throwable) {

            }
        });

    }
    @Override
    public void firstget() {

    }

    @Override
    public double get(long t) {

        final long tt=t;
        final double[] value = new double[1];

        system.universe(0).time(tt).lookup(psID,new KCallback<KObject>(){
            public void on(KObject kObject) {
                PolynomialSensor casted = (PolynomialSensor) kObject;
                value[0] = casted.getValue();
            }
        });
        return value[0];


    }
}
