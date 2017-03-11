/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.stat;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 *
 * @author marr
 */
public class DispersionStats {

    public static double getVariance(List<Double> data) {
        double ret = 0;
        double average = MeanStats.getArithmeticMean(data);
        for (double datum : data) {
            ret += (average - datum) * (average - datum);
        }
        return ret / (data.size()-1);
    }

    public static double getStandardDeviation(List<Double> data) {
        return Math.sqrt(getVariance(data));
    }

    public static double getRange(List<Double> data) {
        data.sort((Double x, Double y) -> x.compareTo(y));
        BigDecimal max = new BigDecimal(data.get(data.size() - 1));
        BigDecimal min = new BigDecimal(data.get(0));
        return max.subtract(min, MathContext.DECIMAL32).doubleValue();
    }

    public static double getCoefficientOfVariation(List<Double> data) {
        return DispersionStats.getStandardDeviation(data) / MeanStats.getArithmeticMean(data);
    }

    public static double getInterQuartileRange(List<Double> data) {
        double q3, q1;
        try {
            q3 = PositionalStats.getQuartile(data, 3);
            q1 = PositionalStats.getQuartile(data, 1);
        } catch (WrongQuartileException e) {
            return 0;
        }
        return q3 - q1;
    }

    public static double getInterquartileRange(List<Double> data) {
        double ret = 0;
        try {
            ret = PositionalStats.getQuartile(data, 3) - PositionalStats.getQuartile(data, 1);
        } catch (WrongQuartileException ex) {

        }
        return ret;
    }

    public static double getQuartileCoefficientOfDispersion(List<Double> data) {
        double q3, q1;
        try {
            q3 = PositionalStats.getQuartile(data, 3);
            q1 = PositionalStats.getQuartile(data, 1);
        } catch (WrongQuartileException e) {
            return 0;
        }
        return (q3 - q1) / (q1 + q3);
    }

    public static double getKurtosis(List<Double> data) {
        double fourthMoment = 0;
        double secondMoment = 0;
        double average = MeanStats.getArithmeticMean(data);
        for (double datum : data) {
            fourthMoment += ((average - datum) * (average - datum) * (average - datum) * (average - datum));
        }
        fourthMoment /= data.size();

        for (double datum : data) {
            secondMoment += ((average - datum) * (average - datum));
        }
        secondMoment /= data.size();

        return (fourthMoment / (secondMoment * secondMoment));
    }
}
