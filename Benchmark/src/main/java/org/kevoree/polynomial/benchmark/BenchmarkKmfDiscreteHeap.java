package org.kevoree.polynomial.benchmark;


import org.kevoree.modeling.KCallback;
import org.kevoree.modeling.KObject;
import org.kevoree.modeling.bench.DiscreteSensor;
import org.kevoree.modeling.bench.SmartSystemModel;
import org.kevoree.modeling.bench.SmartSystemView;
import org.kevoree.modeling.memory.struct.HeapMemoryFactory;
import org.kevoree.modeling.memory.struct.OffHeapMemoryFactory;


/**
 * Created by assaa_000 on 25/11/2014.
 */
public class BenchmarkKmfDiscreteHeap extends Benchmark {
    private SmartSystemModel system;
    private DiscreteSensor ps;
    private long psID;



    public void print(){

      /*  ps.timeWalker().allTimes().then(new Callback<long[]>() {
            public void on(long[] collected2) {
                System.out.println("Kmf discrete: "+collected2.length);
            }
        });*/
     //   System.out.println("size: "+dc.counter);

    }

    @Override
    public void init() {
        try {

            system = new SmartSystemModel();
            system.manager().setFactory(new HeapMemoryFactory());
           // system.manager().setFactory(new OffHeapMemoryFactory());

         //   system.manager().setContentDeliveryDriver(new LevelDbContentDeliveryDriver("/Users/assaad/work/github/BenchmarkIoT/testid/"));
           // dc=new DiskCounter();
           // system.manager().setContentDeliveryDriver(dc);

            system.connect(new KCallback<Throwable>() {
                public void on(Throwable throwable) {
                    //System.out.println("inside");
                    SmartSystemView s0 = system.universe(0).time(0);
                    ps = s0.createDiscreteSensor().setName("sensor0");
                    s0.setRoot(ps, null);
                    psID = ps.uuid();
                    system.save(new KCallback<Throwable>() {
                        public void on(Throwable throwable) {

                        }
                    });
                }
            });


        }
        catch(Exception ex){

        }

    }

    @Override
    public String getBenchmarkName() {
        return "KMF Discrete heap";
    }

    @Override
    public void put(long t, double value) {

        final long tt=t;
        final double vv=value;
        system.lookup(0,tt,psID,new KCallback<KObject>(){
            public void on(KObject kObject) {
                DiscreteSensor casted = (DiscreteSensor) kObject;
                casted.setValue(vv);
            }
        });

        /*system.save(new KCallback<Throwable>() {
            public void on(Throwable throwable) {

            }
        });*/


    }

    @Override
    public void finalput() {
     /*   system.save(new KCallback<Throwable>() {
            public void on(Throwable throwable) {

            }
        });*/

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
                DiscreteSensor casted = (DiscreteSensor) kObject;
                value[0] = casted.getValue();
            }
        });
        return value[0];


    }
}

