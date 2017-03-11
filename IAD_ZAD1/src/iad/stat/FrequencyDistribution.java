/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.stat;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author marr
 */
public class FrequencyDistribution {

    private int numberOfClasses;
    private float classWidth;
    private List<Double> data;
    private List<Double>freqDisData = new ArrayList<>();
    private Map<Double, Integer>intervals = new HashMap<>();

    public FrequencyDistribution(List<Double> data) {
        numberOfClasses = (int) (1 + Math.log(data.size()));
        classWidth = (float) (DispersionStats.getRange(data) / numberOfClasses);
        this.data = data;
        prepareIntervals();
    }

    public Map<Double, Integer> getFrequencyDistribution() {
        for (Map.Entry<Double, Integer> interval : intervals.entrySet()){
            System.out.println(interval.getKey() + " " + interval.getValue());
        }
        return intervals;
    }
    
    private void prepareIntervals(){
        double minV = PositionalStats.getMinValue(data);
        for (int i = 1; i <= numberOfClasses; i++){
            intervals.put(minV + classWidth*i - classWidth/2,0);
        }
        for (double value : data){
            chooseInterval(value);
        }
    }
    
    private double chooseInterval(double value){
        double retInterval = 0;
         for (Map.Entry<Double, Integer> interval : intervals.entrySet()){
             if (value > interval.getKey() - classWidth/2 && value <= interval.getKey() + classWidth/2){
                 interval.setValue(interval.getValue() + 1);
             }
        }
        return retInterval;
    }
}
