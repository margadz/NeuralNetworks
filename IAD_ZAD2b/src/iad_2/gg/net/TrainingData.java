/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2.gg.net;

import java.util.List;

/**
 *
 * @author marr
 */
public class TrainingData {

    private List<double[]> trainingData;
    private List<double[]> trainingExpected;
    private List<double[]> validationData;
    private List<double[]> validationExpected;
    private List<double[]> testData;
    private List<double[]> testExpected;

    public TrainingData(List<double[]> trainingData, List<double[]> trainingExpected, List<double[]> validationData, List<double[]> validationExpected, List<double[]> testData, List<double[]> testExp) {
        this.trainingData = trainingData;
        this.trainingExpected = trainingExpected;
        this.validationData = validationData;
        this.validationExpected = validationExpected;
        this.testData = testData;
        this.testExpected = testExp;
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

    public List<double[]> getTestData() {
        return testData;
    }

    public List<double[]> getTestExpected() {
        return testExpected;
    }
    
}
