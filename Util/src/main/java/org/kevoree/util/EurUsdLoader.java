package org.kevoree.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by assaad on 04/03/15.
 */
public class EurUsdLoader {

    public static ArrayList<DataPoint> load(){
        long starttime;
        long endtime;
        double res;

        starttime = System.nanoTime();
        String csvFile = "/Users/assaad/work/github/eurusd/newEurUsd.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        ArrayList<DataPoint> eurs = new ArrayList<DataPoint>();
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator 2000.05.30,17:35
                String[] values = line.split(cvsSplitBy);
                DataPoint dp=new DataPoint(Long.parseLong(values[2]),Double.parseDouble(values[3]));
                eurs.add(dp);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Loaded :" + eurs.size() + " values in " + res + " s!");

        return eurs;
    }
}
