package org.kevoree.util.polynomial5;



import org.kevoree.util.DataPoint;
import org.kevoree.util.polynomial.Prioritization;
import org.kevoree.util.polynomial.StatClass;

import java.util.ArrayList;
import java.util.TreeMap;

public class PolynomialModel {
    private int degradeFactor;
    private double toleratedError;
    private int maxDegree;
    private Prioritization prioritization = Prioritization.LOWDEGREES;
    private TreeMap<Long, Polynomial> polynomTree = new TreeMap<Long, Polynomial>();
    private Polynomial defaultPolynomialExtrapolation;

    public PolynomialModel(int degradeFactor, double toleratedError, int maxDegree) {
        if (degradeFactor == 0) {
            degradeFactor = 1;
        }
        this.degradeFactor = degradeFactor;
        this.toleratedError = toleratedError;
        this.maxDegree = maxDegree;
    }

    public void feed(long time, double value) {
        if (defaultPolynomialExtrapolation == null) {
            defaultPolynomialExtrapolation = new Polynomial(time, toleratedError, maxDegree, degradeFactor, prioritization);
            defaultPolynomialExtrapolation.insert(time, value);
            return;
        }
        if (defaultPolynomialExtrapolation.insert(time, value)) {
            return;
        }
        DataPoint prev = defaultPolynomialExtrapolation.getSamples().get(defaultPolynomialExtrapolation.getSamples().size() - 1);
        DataPoint newPrev = new DataPoint(prev.time, defaultPolynomialExtrapolation.extrapolate(prev.time));
        polynomTree.put(defaultPolynomialExtrapolation.getTimeOrigin(), defaultPolynomialExtrapolation);

        defaultPolynomialExtrapolation = new Polynomial(newPrev.time, toleratedError, maxDegree, degradeFactor, prioritization);

        defaultPolynomialExtrapolation.insert(newPrev.time,newPrev.value);
        defaultPolynomialExtrapolation.insert(time,value);


    }

    public void finalSave() {
        if (defaultPolynomialExtrapolation != null) {
            polynomTree.put(defaultPolynomialExtrapolation.getTimeOrigin(), defaultPolynomialExtrapolation);
        }
    }

    public double reconstruct(long time) {
        long timeO = polynomTree.floorKey(time);
        Polynomial p = polynomTree.get(timeO);
        return p.extrapolate(time);
    }

    private Polynomial fast = null;
    private long timeE;

    public double fastReconstruct(long time) {
        if (fast != null) {
            if (time < timeE || timeE == -1)
                return fast.extrapolate(time);
        }
        long timeO = polynomTree.floorKey(time);
        fast = polynomTree.get(timeO);
        try {
            timeE = polynomTree.ceilingKey(time);
        } catch (Exception ex) {
            timeE = -1;
        }
        return fast.extrapolate(time);
    }

    public StatClass displayStatistics(boolean display) {
        StatClass global = new StatClass();
        StatClass temp = new StatClass();
        ArrayList<StatClass> debug = new ArrayList<StatClass>();
        long pol = 0;
        for (Long t : polynomTree.keySet()) {
            pol++;
            temp = calculateError(polynomTree.get(t));
            debug.add(temp);
            if (temp.maxErr > global.maxErr) {
                global.maxErr = temp.maxErr;
                global.time = temp.time;
                global.value = temp.value;
                global.calculatedValue = temp.calculatedValue;
            }
            global.avgError += temp.avgError * temp.samples;
            global.samples += temp.samples;
            global.degree += temp.degree;
        }
        global.avgError = global.avgError / global.samples;
        global.polynoms = pol;
        global.storage = (global.degree + pol);
        global.avgDegree = ((double) global.degree) / pol;
        global.timeCompression = (1 - ((double) pol) / global.samples) * 100;
        global.diskCompression = (1 - ((double) global.degree + 2 * pol) / (global.samples * 2)) * 100;
        if (display) {
            System.out.println("Total number of samples: " + global.samples);
            System.out.println("Total number of Polynoms: " + global.polynoms);
            System.out.println("Total doubles in polynoms: " + global.storage);
            System.out.println("Average degrees in polynoms: " + global.avgDegree);
            System.out.println("Time points compression: " + global.timeCompression + " %");
            System.out.println("Disk compression: " + global.diskCompression + " %");
            System.out.println("Maximum error: " + global.maxErr + " at time: " + global.time + " original value was: " + global.value + " calculated value: " + global.calculatedValue);
            System.out.println("Average error: " + global.avgError);
        }
        return global;
    }

    public StatClass calculateError(Polynomial pol) {
        StatClass ec = new StatClass();
        double temp = 0;
        double err = 0;
        DataPoint ds;
        ec.degree = pol.getDegree();
        ec.samples = pol.getSamples().size();
        for (int i = 0; i < pol.getSamples().size(); i++) {
            ds = pol.getSamples().get(i);
            temp = pol.extrapolate(ds.time);
            err = Math.abs(temp - ds.value);
            ec.avgError += err;
            if (err > ec.maxErr) {
                ec.time = ds.time;
                ec.value = ds.value;
                ec.calculatedValue = temp;
                ec.maxErr = err;
            }
        }
        ec.avgError = ec.avgError / pol.getSamples().size();
        return ec;
    }

}

