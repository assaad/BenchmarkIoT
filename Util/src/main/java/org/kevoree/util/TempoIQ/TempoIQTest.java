package org.kevoree.util.TempoIQ;
import com.tempoiq.*;
import org.joda.time.DateTime;
import org.kevoree.util.*;
import org.kevoree.util.DataPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by assaad on 04/03/15.
 */
public class TempoIQTest {
    public static void main(String[] args){
        String csvFile = "/Users/assaad/work/github/BenchmarkIoT/password.txt";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        String[] auths =new String[2];
        ArrayList<DataPoint> results = new ArrayList<DataPoint>();


        try {
            br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
            auths= line.split(cvsSplitBy);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }



        Credentials creds = new Credentials(auths[0],auths[1]);
        InetSocketAddress socket = new InetSocketAddress("t6vua2-trial.backend.tempoiq.com",443);
        Client client = new Client(creds,socket,"https");

        Result<Device> result= client.getDevice("unilu");
        Device device =result.getValue();

        List<Sensor> sensors= device.getSensors();

        System.out.println(device.getName()+" has "+sensors.size()+" sensors");
        Sensor eurusd= sensors.get(1);



        com.tempoiq.MultiStatus ms = new MultiStatus();
        ArrayList<org.kevoree.util.DataPoint> euros = EurUsdLoader.load();

        long starttime;
        long endtime;
        double res;
        int count=0;

        starttime = System.nanoTime();
        int batch=20000;

        for(int i=0; i<euros.size()-batch;i+=batch) {

            ArrayList<com.tempoiq.DataPoint> resAll= new ArrayList<com.tempoiq.DataPoint>();

            for(int j=0;j<batch;j++){
                com.tempoiq.DataPoint dataPoint = new com.tempoiq.DataPoint(new DateTime(euros.get(i+j).time),euros.get(i+j).value);
                resAll.add(dataPoint);
            }
            WriteRequest wr = new WriteRequest().add(device,eurusd,resAll);

            Result<Void> writer = client.writeDataPoints(wr);


            if(writer.getState()!=State.SUCCESS){
                System.out.println(writer.getMessage()+"at i= "+i);
            }
            endtime = System.nanoTime();
            count++;
            res = ((double) (endtime - starttime)) / (1000000000);
            System.out.println("Wrote "+i+" Avg per "+batch+" is: "+res/count);

        }

        System.out.println(eurusd.getName());


    }
}
