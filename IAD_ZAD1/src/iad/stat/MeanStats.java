/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.stat;



import java.util.List;



/**
 *
 * @author marr
 */
public class MeanStats {
   
    public static double getArithmeticMean(List<Double>data){
        double ret = 0;
        for(double datum: data){
        ret += datum;
    }
        return ret/data.size();
    }
    
    public static double getGeometricMean(List<Double>data){
        double ret = data.get(0);
        for (int i=1; i<data.size(); i++){
            ret *= data.get(i);
        }
        return Math.pow(ret, 1.0/data.size());
    }
    
    public static double getHarmonicMean(List<Double>data){
        double ret = 0;
        for (double datum: data){
            ret += 1.0/datum;
        }
        return data.size()/ret;
    }
}
