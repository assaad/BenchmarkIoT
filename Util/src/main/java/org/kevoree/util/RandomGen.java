package org.kevoree.util;

import org.uncommons.maths.random.MersenneTwisterRNG;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by assaad on 10/03/15.
 */
public class RandomGen {
    static public void main(String[] args){
        FileWriter outFile;
        try {
            org.uncommons.maths.random.MersenneTwisterRNG rng = new MersenneTwisterRNG();
            outFile = new FileWriter("ds11.csv");
            PrintWriter out = new PrintWriter(outFile);
            for (int i=0; i<12000000; i++) {
                out.println(i + ","+rng.nextDouble()*1000);
            }
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
