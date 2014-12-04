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


    private double getMaxErr(int degree) {
        double tol = toleratedError / Math.pow(2, degree + 0.5);
        return tol;
    }


    public double[] getWeights(){
        return weights;
    }

    public Long internal_extrapolate(int id, double[] newWeights){
        double result = 0;
        double t = id;
        double power = 1;
        for (int j = 0; j < newWeights.length; j++) {
            result += newWeights[j] * power;
            power = power * t;
        }
        return (long) result;
    }

    public int getSamples(){
        return samples;
    }

    //Not suitable for non-sequential timepoints
    private Long maxError(double[] computedWeights, Long newtime) {
        Long maxErr = 0l;

        Long time;
        Long temp;

        for (int i = 0; i < samples; i++) {
            time= internal_extrapolate(i,computedWeights);
            temp = Math.abs(time - getTime(i));
            if (temp > maxErr) {
                maxErr = temp;
            }
        }
        temp = Math.abs(internal_extrapolate(samples, computedWeights) - newtime);
        if (temp > maxErr) {
            maxErr = temp;
        }
        return maxErr;
    }




    public boolean insert(int id, Long time) {
        //If this is the first point in the set, add it and return
        if (weights == null) {
            weights = new double[1];
            weights[0]=time;
            samples=1;
            return true;
        }

        if(time> getTime(samples-1)){
            //List is ordered
            //First evaluate if it fits in the current model
            double maxError = getMaxErr(this.getDegree());
            if (Math.abs(getTime(samples) - time) <= maxError) {
                //Here the current time fits
               ///
                samples++;
                return true;
            }

            //Else increase the degree till maxDegree
            int deg = getDegree();
            int newMaxDegree= Math.min(samples,maxDegree);
            while (deg < newMaxDegree) {
                deg++;
                int ss = Math.min(deg * 2, samples);
                double[] ids = new double[ss + 1];
                double[] times = new double[ss + 1];
                int idtemp;
                for (int i = 0; i < ss; i++) {
                    idtemp= (int) (i*samples/ss);
                    ids[i]= idtemp;
                    times[i]=getTime(idtemp);
                }
                ids[ss]=samples;
                times[ss]=time;

                PolynomialFitEjml pf = new PolynomialFitEjml(deg);
                pf.fit(ids, times);
                if (maxError(pf.getCoef(), time) <= maxError) {
                    weights = new double[pf.getCoef().length];
                    for (int i = 0; i < pf.getCoef().length; i++) {
                        weights[i] = pf.getCoef()[i];
                    }
                    samples++;
                    return true;
                }
            }
            return false;
        }
        else{
            //trying to insert in past

        }
       /* double maxError = getMaxErr(this.getDegree(), toleratedError, maxDegree, prioritization);
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

        return false;

    }

    public Long getTime( int id){
        double result = 0;
        double t = id;
        double power = 1;
        for (int j = 0; j < weights.length; j++) {
            result += weights[j] * power;
            power = power * t;
        }
        return (long) result;
    }



}

