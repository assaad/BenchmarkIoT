package org.kevoree.util;



import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by assaa_000 on 19/11/2014.
 */
public class BenchmarkExcel {

    public static void main(String[] args) {
        long starttime;
        long endtime;
        double res;


            starttime = System.nanoTime();
          //  ArrayList<DataPoint> points = DataLoader.load("noise.data",1);
          HashMap<String,ArrayList<DataPoint>> points =  ExcelLoader.load();
            endtime = System.nanoTime();
            res = ((double) (endtime - starttime)) / (1000000000);
            System.out.println(" Loaded :" +points.size() + " values in " + res + " s!");

        int max = 0;
        String maxKey="";
        for(String k: points.keySet()){
            if(points.get(k).size()>max){
                max= points.get(k).size();
                maxKey=k;
            }
        }
        System.out.println("Maximum meter "+maxKey+" "+ points.get(maxKey).size());

            try {
                FileWriter outFile = new FileWriter("ds5.csv");
                PrintWriter out = new PrintWriter(outFile);
                for (DataPoint dp: points.get(maxKey)) {
                    out.println(dp.time+","+dp.value);
                }
                out.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }




    }
}
