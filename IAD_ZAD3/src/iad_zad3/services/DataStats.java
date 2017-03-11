/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.services;

import java.util.List;

/**
 *
 * @author marr
 */
public class DataStats {
    public static double [] minVal;
    public static double [] maxVal;
    
    
    public static void getMin(List<double[]>data){
        int dataLen = data.get(0).length;
        double[] stats = new double[dataLen];
        for (int i = 0; i < dataLen; i++){
            double minValue = data.get(0)[i];
            for (double [] d : data){
                if (minValue > d[i]){
                    minValue = d[i];
                }
            }
            stats[i] = minValue;
        }
        minVal =  stats;
    }
    
        public static void getMax(List<double[]>data){
        int dataLen = data.get(0).length;
        double[] stats = new double[dataLen];
        for (int i = 0; i < dataLen; i++){
            double maxValue = data.get(0)[i];
            for (double [] d : data){
                if (maxValue < d[i]){
                    maxValue = d[i];
                }
            }
            stats[i] = maxValue;
        }
        maxVal =  stats;
    }
}
