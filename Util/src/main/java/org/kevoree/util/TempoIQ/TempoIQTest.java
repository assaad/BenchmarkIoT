package org.kevoree.util.TempoIQ;
import com.tempoiq.*;
import org.joda.time.DateTime;
import org.kevoree.util.*;
import org.kevoree.util.DataPoint;

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
        Credentials creds = new Credentials("aeb6ba92253a48258e8f0fcbed7b9472", "9122c838f9c84e9eb76f0387d428fdaf");
        InetSocketAddress socket = new InetSocketAddress("t6vua2-trial.backend.tempoiq.com",443);
        Client client = new Client(creds,socket,"https");

        Result<Device> result= client.getDevice("unilu");
        Device device =result.getValue();

        List<Sensor> sensors= device.getSensors();

        System.out.println(device.getName()+" has "+sensors.size()+" sensors");
        Sensor eurusd= sensors.get(1);
        eurusd.setName("Euro Dollars");

        Sensor luminosity= sensors.get(0);
        luminosity.setName("Luminosity Sensor");


        com.tempoiq.MultiStatus ms = new MultiStatus();
        ArrayList<org.kevoree.util.DataPoint> euros = EurUsdLoader.load();

        for(int i=0; i<euros.size();i++) {
            com.tempoiq.DataPoint dataPoint = new com.tempoiq.DataPoint(new DateTime(euros.get(i).time),euros.get(i).value);
            WriteRequest wr = new WriteRequest().add(device,eurusd,dataPoint);

            Result<Void> writer = client.writeDataPoints(wr);


            if(writer.getState()!=State.SUCCESS){
                System.out.println(writer.getMessage());
            }
            if(i%10000==0){
                System.out.println("Wrote "+i);
            }

        }

        System.out.println(eurusd.getName());


    }
}
