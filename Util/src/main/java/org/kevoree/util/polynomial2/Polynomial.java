package org.kevoree.util.polynomial2;

import org.kevoree.util.DataPoint;
import org.kevoree.util.polynomial.PolynomialFitEjml;
import org.kevoree.util.polynomial.Prioritization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by assaa_000 on 29/10/2014.
 */
public class Polynomial  {

    private double[] weights;
    private Long timeOrigin;
    private List<DataPoint> samples = new ArrayList<DataPoint>();
    private List<Long> sampleTime = new ArrayList<Long>();
    private int degradeFactor;
    private Prioritization prioritization;
    private int maxDegree;
    private double toleratedError;

    public Polynomial(long timeOrigin, double toleratedError, int maxDegree, int degradeFactor, Prioritization prioritization) {
        this.timeOrigin = timeOrigin;
        this.degradeFactor = degradeFactor;
        this.prioritization = prioritization;
        this.maxDegree = maxDegree;
        this.toleratedError = toleratedError;
    }

    public List<DataPoint> getSamples() {
        return samples;
    }

    public int getDegree() {
        if (weights == null) {
            return -1;
        } else {
            return weights.length - 1;
        }
    }

    public Long getTimeOrigin() {
        return timeOrigin;
    }

    private double getMaxErr(int degree, double toleratedError, int maxDegree, Prioritization prioritization) {
        double tol = toleratedError;
        if (prioritization == Prioritization.HIGHDEGREES) {
            tol = toleratedError / Math.pow(2, maxDegree - degree);
        } else if (prioritization == Prioritization.LOWDEGREES) {
            tol = toleratedError / Math.pow(2, degree + 0.5);
        } else if (prioritization == Prioritization.SAMEPRIORITY) {
            tol = toleratedError * degree * 2 / (2 * maxDegree);
        }
        return tol;
    }


    private void internal_feed(Long time, double value) {
        //If this is the first point in the set, add it and return
        if (weights == null) {
            weights = new double[1];
            weights[0] = value;
            timeOrigin = time;
            samples.add(new DataPoint(time, value));
            sampleTime.add(time);
        }
    }

    private double maxError(double[] computedWeights, long time, double value) {
        double maxErr = 0;
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
        return maxErr;
    }

    public boolean comparePolynome(Polynomial p2, double err) {
        if (weights.length != p2.weights.length) {
            return false;
        }
        for (int i = 0; i < weights.length; i++) {
            if (Math.abs(weights[i] - weights[i]) > err) {
                return false;
            }
        }
        return true;
    }

    private double internal_extrapolate(long time, double[] weights) {
        double result = 0;
        double t = ((double) (time - timeOrigin)) / degradeFactor;
        double power = 1;
        for (int j = 0; j < weights.length; j++) {
            result += weights[j] * power;
            power = power * t;
        }
        return result;
    }

    public double extrapolate(long time) {
        return internal_extrapolate(time, weights);
    }


    public boolean insert(long time, double value) {
        //If this is the first point in the set, add it and return
        if (weights == null) {
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
        if (deg < maxDegree) {
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
        return false;
    }

    private static final char sep = '|';

    public String save() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < weights.length; i++) {
            if (i != 0) {
                builder.append(sep);
            }
            builder.append(weights[i]);
        }
        return builder.toString();
    }

    public void load(String payload) {
        String[] elems = payload.split(sep + "");
        weights = new double[elems.length];
        for (int i = 0; i < elems.length; i++) {
            weights[i] = Long.parseLong(elems[i]);
        }
    }

}

