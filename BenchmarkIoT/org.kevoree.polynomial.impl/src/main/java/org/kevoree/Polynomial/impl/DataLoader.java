package org.kevoree.Polynomial.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.DoubleSummaryStatistics;

/**
 * Created by assaa_000 on 19/11/2014.
 */
public class DataLoader {

    public static Long getTimeStamp(int year, int month, int day, int hour, int min, int sec) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, min, sec);
        Date date = cal.getTime(); // get back a Date object
        Long timestamp = date.getTime() - date.getTime() % 1000;
        return timestamp;
    }




    public static ArrayList<DataPoint> load(String filename, int column){
        String csvFile = "D:\\workspace\\Github\\PolynomialModel\\BenchmarkIoT\\org.kevoree.polynomial.impl\\src\\main\\resources\\"+filename;

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\t";

        ArrayList<DataPoint> results = new ArrayList<DataPoint>();


        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(cvsSplitBy);
                DataPoint dp = new DataPoint();
                dp.time = (long) Double.parseDouble(values[0])*1000;
                dp.value = Double.parseDouble(values[column]);
                results.add(dp);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return results;
    }
}
