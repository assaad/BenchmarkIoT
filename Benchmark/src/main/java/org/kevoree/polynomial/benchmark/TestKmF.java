package org.kevoree.polynomial.benchmark;

/**
 * Created by assaad on 24/04/15.
 */
public class TestKmF {
    public static void main(String[] arg){
        BenchmarkKmfPolynomial bkmf = new BenchmarkKmfPolynomial();
        bkmf.init();
        bkmf.put(5, 22);
        System.out.println(bkmf.get(5));



    }
}
