/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marr
 */
public class FileService {

    public static List<double[]> readFile(File file) throws IOException {
        List<double[]> list = new ArrayList<>();
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            double[] datum;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                datum = new double[split.length];
                for (int i = 0; i < split.length; i++){
                    datum[i] = Double.valueOf(split[i]);
                }
                list.add(datum);
            }
        }
        DataStats.getMax(list);
        DataStats.getMin(list);
        return list;
    }

}
