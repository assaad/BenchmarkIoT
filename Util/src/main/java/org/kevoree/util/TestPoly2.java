package org.kevoree.util;

/**
 * Created by assaad on 18/12/14.
 */
import org.kevoree.util.polynomial6.PolynomialModel;
import org.kevoree.util.polynomial6.TimePolynomial;

import java.util.Random;


public class TestPoly2 {

    public static void main(String[] args){

        TimePolynomial tp = new TimePolynomial();

        //long[] tmp = {1234l,1236l,1238l,1242l};

        long[] tmp = new long[1000];
        tmp[0]=1234567000l;


        int period=10000;
        int variance=10;
        Random random=new Random();

        for(int i=1; i<tmp.length;i++){
            tmp[i]=tmp[i-1]+period+random.nextInt(variance*2)-variance;
        }

        for(int i=0; i<tmp.length;i++){
            if(tp.insert(tmp[i])==false){
             System.out.println("Stop inserting at: "+ i);
                break;
            }
        }

        System.out.println("Sample inserted: "+tp.getSamples());
        for(int i=0; i<tp.getSamples();i++){
           // System.out.println("["+i+"]: "+tp.getTime(i));

            if(Math.abs((tp.getTime(i)-tmp[i]))>(period/5)){
                System.out.println("Error at " + i);
            }
        }
        System.out.println("Done");

    }

}
