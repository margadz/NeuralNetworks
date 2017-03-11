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
public class AsymetryStats {
    public static double getSkewness(List<Double>data){
        double skewness = Double.NaN;
        try {
            skewness = PositionalStats.getQuartile(data, 3) + PositionalStats.getQuartile(data, 1) - (2 * PositionalStats.getMedian(data));
            skewness /= (PositionalStats.getQuartile(data, 3) - PositionalStats.getQuartile(data, 1));
        } catch (WrongQuartileException ex) {
            // TODO :)
        }
        return skewness;
    }
}
