package org.kevoree.Polynomial.impl.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by assaa_000 on 19/11/2014.
 */
public class DataLoaderZip {

    public static Long getTimeStamp(int year, int month, int day, int hour, int min, int sec) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, min, sec);
        Date date = cal.getTime(); // get back a Date object
        Long timestamp = date.getTime() - date.getTime() % 1000;
        return timestamp;
    }




    public static ArrayList<DataPoint> load(String filename){
        String csvFile = "D:\\workspace\\Github\\PolynomialModel\\DataSets\\"+filename;

        String line = "";
        String cvsSplitBy = ",";

        ArrayList<DataPoint> results = new ArrayList<DataPoint>();

        try
        {


            final ZipFile zf = new ZipFile(csvFile);

            final Enumeration<? extends ZipEntry> entries = zf.entries();
            ZipInputStream zipInput = null;

            while (entries.hasMoreElements())
            {
                final ZipEntry zipEntry=entries.nextElement();
                final String fileName = zipEntry.getName();
                InputStream inputs=zf.getInputStream(zipEntry);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputs, "UTF-8"));
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(cvsSplitBy);
                    DataPoint dp = new DataPoint();
                    dp.time = Long.parseLong(values[0]);
                    dp.value = Double.parseDouble(values[1]);
                    results.add(dp);
                }

            }


        }
        catch(Exception e)
        {
           e.printStackTrace();
        }




        return results;
    }
}
