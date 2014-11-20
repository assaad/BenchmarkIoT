package org.kevoree.util;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by assaa_000 on 20/11/2014.
 */
public class ExcelLoader {

    public static HashMap<String,ArrayList<DataPoint>> load(){
        HashMap<String,ArrayList<DataPoint>> result = new HashMap<String, ArrayList<DataPoint>>();


        try {

            File dir = new File("C:\\Users\\assaa_000\\Desktop\\smartmeter");
            File[] directoryListing = dir.listFiles();
            System.out.println("Found " + directoryListing.length + " files");
            if (directoryListing != null) {
                for (File file : directoryListing) {

                    FileInputStream file2 = new FileInputStream(file);

                    //Create Workbook instance holding reference to .xlsx file
                    XSSFWorkbook workbook = new XSSFWorkbook(file2);

                    //Get first/desired sheet from the workbook
                    XSSFSheet sheet = workbook.getSheetAt(0);

                    //Iterate through each rows one by one
                    Iterator<Row> rowIterator = sheet.iterator();
                    int rowNum=0;

                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                        if(rowNum==0)
                            row = rowIterator.next();

                        String equipment = row.getCell(0).getStringCellValue();
                        Date timestamp =  row.getCell(1).getDateCellValue();
                        double electrical = row.getCell(2).getNumericCellValue();

                        ArrayList<DataPoint> ad ;
                        if(result.containsKey(equipment)){
                            ad= result.get(equipment);
                            DataPoint dp = new DataPoint();
                            dp.time=timestamp.getTime();
                            dp.value=electrical;
                            ad.add(dp);
                        }
                        else
                        {
                            ad = new ArrayList<DataPoint>();
                            DataPoint dp = new DataPoint();
                            dp.time=timestamp.getTime();
                            dp.value=electrical;
                            ad.add(dp);
                            result.put(equipment,ad);
                        }


                        rowNum++;

                    }
                    System.out.println("file "+file.getName()+" read "+rowNum);
                    //fileInputStream.close();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }





        return result;
    }
}
