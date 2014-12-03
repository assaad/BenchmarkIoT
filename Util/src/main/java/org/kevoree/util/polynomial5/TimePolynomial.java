package org.kevoree.util.polynomial5;

import org.kevoree.util.DataPoint;
import org.kevoree.util.polynomial.PolynomialFitEjml;
import org.kevoree.util.polynomial.Prioritization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by assaa_000 on 29/10/2014.
 */
public class TimePolynomial {

    //Todo to save and to load
    private double[] weights;
    private int samples;

    private int maxDegree;
    private double toleratedError;

    public TimePolynomial(double toleratedError, int maxDegree, int degradeFactor, Prioritization prioritization) {
        this.maxDegree = maxDegree;
        this.toleratedError = toleratedError;
    }

    public int getDegree() {
        if (weights == null) {
            return -1;
        } else {
            return weights.length - 1;
        }
    }


    private double getMaxErr(int degree, double toleratedError) {
        double tol = toleratedError / Math.pow(2, degree + 0.5);
        return tol;
    }


    public double[] getWeights(){
        return weights;
    }

    public int getSamples(){
        return samples;
    }

    private Long maxError(double[] computedWeights, int id, long time) {
       /* double maxErr = 0;
        double temp = 0;
        Long ds;
        for (int i = 0; i < sampleTime.size(); i++) {
            ds = sampleTime.get(i);
            double val=internal_extrapolate(ds, computedWeights);
            temp = Math.abs(val - extrapolate(ds));
            if (temp > maxErr) {
                maxErr = temp;
            }
        }
        temp = Math.abs(internal_extrapolate(time, computedWeights) - value);
        if (temp > maxErr) {
            maxErr = temp;
        }
        return maxErr;*/
    }


    private Long internal_extrapolate(int id, double[] weights) {
       /* double result = 0;
        double t = ((double) (time - timeOrigin)) / degradeFactor;
        double power = 1;
        for (int j = 0; j < weights.length; j++) {
            result += weights[j] * power;
            power = power * t;
        }
        return result;*/
    }


    public boolean insert(long time, double value) {
        //If this is the first point in the set, add it and return
       /* if (weights == null) {
            internal_feed(time, value);
            return true;
        }
        double maxError = getMaxErr(this.getDegree(), toleratedError, maxDegree, prioritization);
        //If the current model fits well the new value, return
        if (Math.abs(extrapolate(time) - value) <= maxError) {
            samples.add(new DataPoint(time, value));
            sampleTime.add(time);
            return true;
        }
        //If not, first check if we can increase the degree
        int deg = getDegree();
        int newMaxDegree= Math.min(sampleTime.size(),maxDegree);
        while (deg < newMaxDegree) {
            deg++;
            int ss = Math.min(deg * 2, sampleTime.size());
            double[] times = new double[ss + 1];
            double[] values = new double[ss + 1];
            int current = sampleTime.size();
            for (int i = 0; i < ss; i++) {

                Long ds = sampleTime.get(i * current / ss);
                times[i] = ((double) (ds - timeOrigin)) / degradeFactor;
                values[i] = extrapolate(ds);
            }
            times[ss] = ((double) (time - timeOrigin)) / degradeFactor;
            values[ss] = value;
            PolynomialFitEjml pf = new PolynomialFitEjml(deg);
            pf.fit(times, values);
            if (maxError(pf.getCoef(), time, value) <= maxError) {
                weights = new double[pf.getCoef().length];
                for (int i = 0; i < pf.getCoef().length; i++) {
                    weights[i] = pf.getCoef()[i];
                }
                samples.add(new DataPoint(time, value));
                sampleTime.add(time);
                return true;
            }
        }
        return false;*/

    }



}

