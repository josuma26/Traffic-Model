/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author jsula
 */
public class DataReader {
    Scanner sc;
    public DataReader(String fileName) throws FileNotFoundException{
        File file = new File(fileName);
        this.sc = new Scanner(file);
    }
    
    public  HashMap<String,String> getSectionData(String section){
        ArrayList<String> data = new ArrayList<>();
        while(true){
            String line = this.sc.nextLine();
            if (line.equals(section)){
                String l;
                do{
                    l = sc.nextLine();
                    data.add(l);
                }
                while(!l.equals(""));
                break;
            }
        }
        
        HashMap<String,String> map = new HashMap();
        for(String s:data.subList(0, data.size()-1)){
            String[] line = s.split("\t");
            map.put(line[0], line[1]);
        }
        return map;
    }
}
