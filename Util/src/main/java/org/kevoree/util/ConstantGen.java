package org.kevoree.util;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class ConstantGen {
    static public void main(String[] args){
        FileWriter outFile;
        try {
            outFile = new FileWriter("ds0.csv");
            PrintWriter out = new PrintWriter(outFile);
            for (int i=0; i<12000000; i++) {
                out.println(i + ", 24.0");
            }
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
