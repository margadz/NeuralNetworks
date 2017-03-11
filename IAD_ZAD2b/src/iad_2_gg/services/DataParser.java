/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2_gg.services;

import iad_2.gg.net.TrainingData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author glabg
 */
public class DataParser {

    private int numberOfClasses = 1;
    private List<Integer> classesIterators;
    public List<double[]> rawData;
    public List<double[]> rawExpected;
    private final List<double[]> trainingData;
    private final List<double[]> trainingExpected;
    private final List<double[]> validationData;
    private final List<double[]> validationExpected;
    private final List<double[]> testData;
    private final List<double[]> testExpected;

    public DataParser(List<double[]> data, List<double[]> expected) {
        this.rawData = data;
        this.rawExpected = expected;
        this.trainingData = new ArrayList<>();
        this.trainingExpected = new ArrayList<>();
        this.validationData = new ArrayList<>();
        this.validationExpected = new ArrayList<>();
        this.testData = new ArrayList<>();
        this.testExpected = new ArrayList<>();
        checkClasses();
    }

    /**
     *
     * @param subData #0: 100% training, #1: 66% training, 17% validation, 17%
     * test; #2: 66% training, 17% validation, 17% test at random; #3: 40%
     * training, 30% validation, 30% test; #4: 40% training, 30% validation, 30%
     * test at random
     * @return TrainingData
     */
    public TrainingData setUpData(int subData) {

        clearAllData();
        switch (subData) {
            case 1:
                divideData(false);
                break;
            case 2:
                randomDivide(false);
                break;
            case 3:
                divideData(true);
                break;
            case 4:
                randomDivide(true);
                break;

            default:
                this.trainingData.addAll(rawData);
                this.trainingExpected.addAll(rawExpected);
                this.validationData.clear();
                this.validationExpected.clear();
                this.testData.clear();
                this.testExpected.clear();
                break;
        }
        return new TrainingData(trainingData, trainingExpected, validationData, validationExpected, testData, testExpected);
    }

    private void randomDivide(boolean altMode) {
        double range1 = 0.66;
        double range2 = 0.83;
        if (altMode) {
            range1 = 0.40;
            range2 = 0.70;
        }

        Random rnd = ThreadLocalRandom.current();
        for (int i = rawData.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            double[] i1 = rawData.get(index);
            rawData.set(index, rawData.get(i));
            rawData.set(i, i1);
            double[] i2 = rawExpected.get(index);
            rawExpected.set(index, rawExpected.get(i));
            rawExpected.set(i, i2);
        }

        for (int i = 0; i < rawData.size(); i++) {
            if ((i * 1.0) / rawData.size() <= range1) {
                trainingData.add(rawData.get(i));
                trainingExpected.add(rawExpected.get(i));
            } else if ((i * 1.0) / rawData.size() <= range2) {
                validationData.add(rawData.get(i));
                validationExpected.add(rawExpected.get(i));
            } else {
                testData.add(rawData.get(i));
                testExpected.add(rawExpected.get(i));
            }
        }
//        printData();
    }

    private void divideData(boolean altMode) {
        double range1 = 0.66;
        double range2 = 0.83;
        if (altMode) {
            range1 = 0.40;
            range2 = 0.70;
        }

        for (int i = 0; i < numberOfClasses; i++) {
            int classRange;
            if (i == numberOfClasses - 1) {
                classRange = rawExpected.size() - classesIterators.get(i);
            } else {
                classRange = classesIterators.get(i + 1) - classesIterators.get(i);
            }
            for (int j = classesIterators.get(i); j < classesIterators.get(i) + classRange; j++) {

                if (1.0 * (j - classesIterators.get(i)) / classRange <= range1) {
                    trainingData.add(rawData.get(j));
                    trainingExpected.add(rawExpected.get(j));
                } else if (1.0 * (j - classesIterators.get(i)) / classRange <= range2) {
                    validationData.add(rawData.get(j));
                    validationExpected.add(rawExpected.get(j));
                } else {
                    testData.add(rawData.get(j));
                    testExpected.add(rawExpected.get(j));
                }
            }
        }
//        printData();
    }

//    private void printData() {
//        System.out.println("data");
//        for (double[] d : trainingData) {
//            for (double dd : d) {
//                System.out.print(dd + " ");
//            }
//            System.out.println("");
//        }
//
//        System.out.println("val");
//        for (double[] d : validationData) {
//            for (double dd : d) {
//                System.out.print(dd + " ");
//            }
//            System.out.println("");
//        }
//        System.out.println("test");
//        for (double[] d : testData) {
//            for (double dd : d) {
//                System.out.print(dd + " ");
//            }
//            System.out.println("");
//        }
//    }
    
    private boolean comparePatterns(double[] p1, double[] p2) {
        for (int i = 0; i < p1.length; i++) {
            if (p1[i] != p2[i]) {
                return false;
            }
        }
        return true;
    }

    private void checkClasses() {
        classesIterators = new ArrayList<>();
        classesIterators.add(0);
        double[] tmpPattern = rawExpected.get(0);
        for (int i = 1; i < rawExpected.size(); i++) {
            if (!comparePatterns(tmpPattern, rawExpected.get(i))) {
                tmpPattern = rawExpected.get(i);
                classesIterators.add(i);
                numberOfClasses++;
            }
        }
    }

    private void clearAllData() {
        this.trainingData.clear();
        this.trainingExpected.clear();
        this.validationData.clear();
        this.validationExpected.clear();
        this.testData.clear();
        this.testExpected.clear();
    }

    public List<double[]> getTrainingData() {
        return trainingData;
    }

    public List<double[]> getTrainingExpected() {
        return trainingExpected;
    }

    public List<double[]> getValidationData() {
        return validationData;
    }

    public List<double[]> getValidationExpected() {
        return validationExpected;
    }

}
