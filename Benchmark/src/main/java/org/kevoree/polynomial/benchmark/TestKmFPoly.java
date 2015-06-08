package org.kevoree.polynomial.benchmark;

import org.kevoree.modeling.KCallback;
import org.kevoree.modeling.KObject;
import org.kevoree.modeling.bench.PolynomialSensor;
import org.kevoree.modeling.bench.SmartSystemModel;
import org.kevoree.modeling.bench.SmartSystemView;
import org.kevoree.modeling.drivers.leveldb.LevelDbContentDeliveryDriver;
import org.kevoree.util.DataLoaderZip;
import org.kevoree.util.DataPoint;

import java.util.ArrayList;

/**
 * Created by assaad on 24/04/15.
 */
public class TestKmFPoly {

    private static SmartSystemModel system;
    private static PolynomialSensor ps;
    private static long psID;


    public static void main(String[] arg){
        long starttime;
        long endtime;
        double res;

        int size=500000;

        String dataset;

        dataset = "ds1.zip"; //constant
       /* dataset = "ds12.zip"; //linear
        dataset = "ds1.zip"; //temp
        dataset = "ds3.zip"; //lum
        dataset = "ds5.zip"; //elec
        dataset = "ds9.zip"; //sound
        dataset = "ds11.zip"; //random*/


        DataLoaderZip.setBaseDir("/Users/assaad/work/github/BenchmarkIoT/DataSets/");
        starttime = System.nanoTime();
        final ArrayList<DataPoint> p = DataLoaderZip.load(dataset);
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println();
        System.out.println("Loaded :" + p.size() + " values in " + res + " s!");

        ArrayList<DataPoint> points=new ArrayList<DataPoint>(size);
        for(int i=0;i<size;i++){
            DataPoint dp= new DataPoint();
            dp.time=i;
            dp.value=p.get(i%p.size()).value;
            points.add(dp);
        }


        system = new SmartSystemModel();

        try {
            system.manager().setContentDeliveryDriver(new LevelDbContentDeliveryDriver("/Users/assaad/work/github/BenchmarkIoT/testid/discrete/"));
            system.connect(new KCallback<Throwable>() {
                public void on(Throwable throwable) {
                    SmartSystemView s0 = system.universe(0).time(0);
                    ps = s0.createPolynomialSensor().setName("sensor0");
                    s0.setRoot(ps, null);
                    psID = ps.uuid();
                }
            });
        }
        catch (Exception e){}



        final long starttime2 = System.nanoTime();
        for(int i=0;i<size;i++){

            final long tt=points.get(i).time;
            final double vv=points.get(i).value;
            system.universe(0).time(tt).lookup(psID,new KCallback<KObject>(){
                public void on(KObject kObject) {
                    PolynomialSensor casted = (PolynomialSensor) kObject;
                    casted.setValue(vv);
                }
            });
        }



        system.save(new KCallback<Throwable>() {
            public void on(Throwable throwable) {
                final long endtime2 = System.nanoTime();
                double res2 = ((double) (endtime2 - starttime2)) / (1000000000);
                System.out.println("Write: "+res2+" s");
               // system.manager().cache().clear();
            }
        });


        starttime = System.nanoTime();
        for(int i=0;i<size;i++){

            if(i%50000==0){
                System.out.println(i);
            }
            final long tt=points.get(i).time;
            final double[] value = new double[1];

            system.universe(0).time(tt).lookup(psID,new KCallback<KObject>(){
                public void on(KObject kObject) {
                    PolynomialSensor casted = (PolynomialSensor) kObject;
                    value[0] = casted.getValue();
                }
            });
        }
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Read: "+res+" s");






    }
}
