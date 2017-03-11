/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.stat;

import iad.service.IStatMethodsService;
import java.util.List;
import java.util.Map;



/**
 *
 * @author marr
 */
public class StatRaportGenerator implements IStatMethodsService {

    private List<Double> data;
    private Map<String, Double> stats;

    @Override
    public void iterateMethods(List<Double> data, Map<String, Double> stats) {
        this.data = data;
        this.stats = stats;
        meansStats();
        positionalStats();
        dispersionStats();
        asymetryStats();
    }

    private void meansStats() {
        stats.put("średnia arytmetyczna", MeanStats.getArithmeticMean(data));
        stats.put("średnia geometryczna", MeanStats.getGeometricMean(data));
        stats.put("średnia harmoniczna", MeanStats.getHarmonicMean(data));
    }

    private void positionalStats() {
        stats.put("mediana", PositionalStats.getMedian(data));
        stats.put("dominanta", PositionalStats.getMode(data));
        try {

            stats.put("pierwszy kwartyl", PositionalStats.getQuartile(data, 1));
            stats.put("trzeci kwartyl", PositionalStats.getQuartile(data, 3));
        } catch (WrongQuartileException ex) {
            //TODO :)
        }
        stats.put("wartość najniższa", PositionalStats.getMinValue(data));
        stats.put("wartość najwyższa", PositionalStats.getMaxValue(data));
    }

    private void dispersionStats(){
        stats.put("wariancja", DispersionStats.getVariance(data));
        stats.put("odchylenie standardowe", DispersionStats.getStandardDeviation(data));
        stats.put("odchylenie ćwiartkowe", DispersionStats.getQuartileCoefficientOfDispersion(data));
        stats.put("współczynnik zmienności", DispersionStats.getCoefficientOfVariation(data));
        stats.put("rozstęp", DispersionStats.getRange(data));
        stats.put("rozstęp ćwiartkowy", DispersionStats.getInterQuartileRange(data));
        stats.put("kurtoza", DispersionStats.getKurtosis(data));
    }
    
    private void asymetryStats(){
        stats.put("współczynnik asymetrii", AsymetryStats.getSkewness(data));
    }
}
