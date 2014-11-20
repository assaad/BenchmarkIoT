package org.kevoree.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by assaa_000 on 20/11/2014.
 */
public class WaveLoader {
    public static ArrayList<DataPoint>  load (){
        float[][] inputAudio = new float[0][];
        ArrayList<DataPoint> results = new ArrayList<DataPoint>();

        try {
            inputAudio = com.ritolaaudio.simplewavio.Utils.WAVToFloats(new File("D:\\workspace\\Github\\PolynomialModel\\DataSets\\UncleBibby_-_06_-_Punch_It.wav"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int sizetoRead = inputAudio.length;
        int time = 0;
        for (int i = 0; i < sizetoRead; i++) {
            float[] frame = inputAudio[i];
            if(frame.length > 1){
                frame[1] = 0;
            }
            DataPoint dp = new DataPoint();
            dp.time = time;
            dp.value = frame[0];
            results.add(dp);
            time++;
        }//end for(frames)
        return results;
    }
}
