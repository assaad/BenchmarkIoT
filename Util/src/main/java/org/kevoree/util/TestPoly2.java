package org.kevoree.util;

/**
 * Created by assaad on 18/12/14.
 */
import org.kevoree.util.polynomial6.PolynomialModel;
import org.kevoree.util.polynomial6.TimePolynomial;

import java.util.Random;


public class TestPoly2 {

    public static void main(String[] args){


        PolynomialModel pm = new PolynomialModel(0.3,20);

        for(int i=0; i<1000; i++){
            pm.feed(10*i+1205870,5+2.4*i+7*i*i);
        }
        pm.finalSave();
        pm.displayStatistics(true);

    }

}
