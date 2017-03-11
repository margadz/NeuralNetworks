/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.stat;

import iad.service.IStatMethodsService;
import iad.data.Container;
import iad.data.Entity;
import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author marr
 */
public final class StatCalculator {

    private Map<String, List<List<Double>>> data = new HashMap<>();
    private Map<String, List<Map<String, Double>>> results = new HashMap<>();
    private final IStatMethodsService methodsIterator;
    private final List<Entity> rawData;
    private DecimalFormat df = new DecimalFormat("0.####");

    public StatCalculator(IStatMethodsService methodsIterator) {
        this.methodsIterator = methodsIterator;
        rawData = Container.getInstance().getEntities();
        mapData();
        calculateStats();
    }

    public void calculateStats() {
        for (Map.Entry<String, List<List<Double>>> klass : data.entrySet()) {
            int index = 0;
            for (Map<String, Double> map : results.get(klass.getKey())) {
                methodsIterator.iterateMethods(klass.getValue().get(index++), map);
            }
        }

    }

    private void mapData() {
        for (Entity entity : rawData) {
            if (!data.containsKey(entity.getKlass())) {
                data.put(entity.getKlass(), new LinkedList<>());
                results.put(entity.getKlass(), new LinkedList<>());
            }
        }

        for (Map.Entry<String, List<List<Double>>> klass : data.entrySet()) {
            for (Double param : rawData.get(0).getParams()) {
                klass.getValue().add(new LinkedList<>());
                results.get(klass.getKey()).add(new HashMap<>());
            }
        }
        for (Entity entity : rawData) {
            for (int i = 0; i < entity.getParams().size(); i++) {
                data.get(entity.getKlass()).get(i).add(entity.getParams().get(i));
            }
        }
    }

    public String getStats(String klass, int param) {
        StringBuilder retS = new StringBuilder();
        if (klass.equals("zbiorczy")) {
            for (Map.Entry<String, List<Map<String, Double>>> aKlass : results.entrySet()) {
                retS.append(aKlass.getKey());
                retS.append("\n");
                for (Map.Entry<String, Double> stats : aKlass.getValue().get(param).entrySet()) {
                    retS.append(stats.getKey());
                    retS.append("\t");
                    if (stats.getValue().isNaN()){
                        retS.append("NaN");
                    }else{
                        retS.append(df.format(stats.getValue()));
                    }
                    retS.append("\n");
                }
                aKlass.getValue().get(0);
                retS.append("\n");
            }
        } else {
            retS.append(klass);
            retS.append("\n");
            for (Map.Entry<String, Double> stats : results.get(klass).get(param).entrySet()) {
                retS.append(stats.getKey());
                retS.append("\t");
                    if (stats.getValue().isNaN()){
                        retS.append("NaN");
                    }else{
                        retS.append(df.format(stats.getValue()));
                    }
                retS.append("\n");
            }
        }

        return retS.toString();
    }

    public String testHypothesis(String klass1, String klass2, int param, int isTwoSide, String confidence) {
        HypothesisTest hyp;
        if (isTwoSide == 1) {
            hyp = new HypothesisTest(data.get(klass1).get(param), data.get(klass2).get(0), true, Double.valueOf(confidence));
        } else {
            hyp = new HypothesisTest(data.get(klass1).get(param), data.get(klass2).get(0), false, Double.valueOf(confidence));
        }
        return hyp.getResults();
    }

    public Double[] getFrequencyDistribution(String klass, int param, int[]d) {
        Double[] retArray = null;
        Map<Double,Integer> intervals = new HashMap<>();
        if (klass.equals("zbiorczy")) {
            LinkedList<Double> sumArray = new LinkedList<>();
            for (Map.Entry<String, List<List<Double>>> klas1 : data.entrySet()) {
                sumArray.addAll(klas1.getValue().get(param));
            }
            FrequencyDistribution freq = new FrequencyDistribution(sumArray);
            intervals = freq.getFrequencyDistribution();
            Double numberOfIntervals = Math.sqrt(sumArray.size());
            d[0]=(numberOfIntervals.intValue());
            retArray = sumArray.toArray(new Double[sumArray.size()]);

        } else {
            FrequencyDistribution freq = new FrequencyDistribution(data.get(klass).get(param));
            intervals = freq.getFrequencyDistribution();
            retArray = data.get(klass).get(param).toArray(new Double[data.get(klass).get(param).size()]);
            Double numberOfIntervals = Math.sqrt(data.get(klass).get(param).size());
            d[0]=(numberOfIntervals.intValue());
        }
        for (Map.Entry<Double, Integer> interval : intervals.entrySet()){
            
        }
        return retArray;
    }
}
