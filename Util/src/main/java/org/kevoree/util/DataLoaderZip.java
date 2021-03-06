package org.kevoree.util;

import org.uncommons.maths.random.MersenneTwisterRNG;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by assaa_000 on 19/11/2014.
 */
public class DataLoaderZip {


    private static String baseDir = "D:\\workspace\\Github\\PolynomialModel\\DataSets\\";

    public static void setBaseDir(String baseDir) {
        DataLoaderZip.baseDir = baseDir;
    }

    public static Long getTimeStamp(int year, int month, int day, int hour, int min, int sec) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, min, sec);
        Date date = cal.getTime(); // get back a Date object
        Long timestamp = date.getTime() - date.getTime() % 1000;
        return timestamp;
    }

    public static ArrayList<DataPoint> load(String filename, int maxLines) {
        String csvFile = baseDir + filename;
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<DataPoint> results = new ArrayList<DataPoint>();
        try {
            final ZipFile zf = new ZipFile(csvFile);
            final Enumeration<? extends ZipEntry> entries = zf.entries();
            ZipInputStream zipInput = null;
            while (entries.hasMoreElements() && results.size()<maxLines) {
                final ZipEntry zipEntry = entries.nextElement();
                final String fileName = zipEntry.getName();
                InputStream inputs = zf.getInputStream(zipEntry);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputs, "UTF-8"));
                while ((line = br.readLine()) != null && results.size()<maxLines) {
                    String[] values = line.split(cvsSplitBy);
                    DataPoint dp = new DataPoint();
                    dp.time = Long.parseLong(values[0]);
                    dp.value = Double.parseDouble(values[1]);
                    results.add(dp);
                }

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return results;
    }

    public static ArrayList<DataPoint> load(String filename) {
        if(filename.equals("ds11.zip")){
            org.uncommons.maths.random.MersenneTwisterRNG rng = new MersenneTwisterRNG();
            ArrayList<DataPoint> results = new ArrayList<DataPoint>();
            long t=0;
            for (int i=0; i<10000000; i++) {
                DataPoint dp = new DataPoint();
                dp.time = t;
                dp.value = rng.nextDouble()*100;
                results.add(dp);
                t++;
            }
            return results;
        }

        if(filename.equals("ds12.zip")){
            ArrayList<DataPoint> results = new ArrayList<DataPoint>();
            long t=0;
            for (int i=0; i<10000000; i++) {
                DataPoint dp = new DataPoint();
                dp.time = t;
                dp.value = i*5;
                results.add(dp);
                t++;
            }
            return results;
        }

        if(filename.equals("ds0.zip")){
            ArrayList<DataPoint> results = new ArrayList<DataPoint>();
            long t=0;
            for (int i=0; i<10000000; i++) {
                DataPoint dp = new DataPoint();
                dp.time = t;
                dp.value = 42;
                results.add(dp);
                t++;
            }
            return results;
        }


        String csvFile = baseDir + filename;
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<DataPoint> results = new ArrayList<DataPoint>();
        try {
            final ZipFile zf = new ZipFile(csvFile);
            final Enumeration<? extends ZipEntry> entries = zf.entries();
            ZipInputStream zipInput = null;
            while (entries.hasMoreElements()) {
                final ZipEntry zipEntry = entries.nextElement();
                final String fileName = zipEntry.getName();
                InputStream inputs = zf.getInputStream(zipEntry);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputs, "UTF-8"));
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(cvsSplitBy);
                    DataPoint dp = new DataPoint();
                    dp.time = Long.parseLong(values[0]);
                    dp.value = Double.parseDouble(values[1]);
                    results.add(dp);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
