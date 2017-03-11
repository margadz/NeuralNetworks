/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2.gg.net;

import iad_2.gg.func.LinearFunction;
import iad_2.gg.func.LogisticFunction;
import iad_2.gg.func.TurnOffFunction;
import iad_2_gg.services.DataHelper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author glabg
 */
@XmlRootElement(name = "Network")
@XmlAccessorType(XmlAccessType.FIELD)
public class Network {

    @XmlElement(name = "Layer")
    private List<Layer> layers;
    private int patternLength;
    private List<Double> TrainingErrorLog = new LinkedList<>();
    private List<Double> ValidationErrorLog = new LinkedList<>();
    private List<Double> TestRateLog = new LinkedList<>();
    private List<List<Integer>> confusionMatrix;// = new LinkedList<>();

    public Network() {

    }

    public void prepareNetwork(List<Integer> newLayers, int bias) throws Exception {
        if (bias != 0 && bias != 1) {
            System.out.println("x");
            throw new Exception("Bias out of range (0 or 1)");
        }
        layers = new ArrayList<>();
        for (int i = 0; i < newLayers.size(); i++) {
            if (newLayers.get(i) <= 0) {
                throw new WrongNumberOfNeuronsException();
            }
            if (i == 0) {
                layers.add(new Layer(newLayers.get(i), 0, new LinearFunction(), 0));
            } else {
                layers.add(new Layer(newLayers.get(i), newLayers.get(i - 1), new LogisticFunction(), bias));
            }
        }
    }

    public double[] activate(double[] data) throws Exception {
        if (layers == null) {
            throw new NoNetworkException();
        }

        for (Layer layer : layers) {
            data = layer.activate(data);
        }
        return data;
    }

    private void calculateErrors(double[] expected) {
        Neuron neuron;
        Layer current;
        Layer next = null;
        double error;
        for (int i = layers.size() - 1; i >= 0; i--) {
            current = layers.get(i);
            for (int j = 0; j < current.getNeurons().size(); j++) {
                neuron = current.getByIndex(j);
                error = 0.0;
                if (next != null) {
                    for (int k = 0; k < next.getNeurons().size(); k++) {
                        error += next.getByIndex(k).getError() * next.getByIndex(k).getWeight(j);
                    }
                } else {
                    error = expected[j] - neuron.getResult();
                }
                neuron.setError(error);
            }
            next = current;
        }
    }

    private void logConfusionMatrix(int result, int expected) {
        if (confusionMatrix == null) {
            confusionMatrix = new ArrayList<>();
            for (int i = 0; i < patternLength; i++) {
                ArrayList<Integer> tmp = new ArrayList<>();
                for (int j = 0; j < patternLength; j++) {
                    tmp.add(0);
                }
                confusionMatrix.add(tmp);
            }
        }

        int tmp = confusionMatrix.get(result).get(expected);
        confusionMatrix.get(result).set(expected, ++tmp);

    }

    public List<List<Integer>> getConfusionMatrix() {
        return confusionMatrix;
    }

    private void logTestValues(List<double[]> input, List<double[]> expected, boolean last) throws Exception {
        int patternLen = expected.get(0).length;
        int countMatch = 0;
        for (int i = 0; i < input.size(); i++) {
            double[] result = activate(input.get(i));
//            double[] expec = activate(input.get(i));
            double resultMax = 0;
            int patternRes = 0;
            int patternExp = 0;
            for (int j = 0; j < patternLen; j++) {
                if (resultMax < result[j]) {
                    resultMax = result[j];
                    patternRes = j;
                }
            }
            double expMax = 0;
            for (int j = 0; j < patternLen; j++) {
                if (expMax < expected.get(i)[j]) {
                    expMax = expected.get(i)[j];
                    patternExp = j;
                }
            }
            if (last){
            logConfusionMatrix(patternExp, patternRes);
            }
            if (expected.get(i)[patternRes] == 1) {
                countMatch++;
            }
        }
        TestRateLog.add((countMatch * 1.0) / input.size());
    }

    private double logTrainingError(List<double[]> input, List<double[]> expected) throws Exception {
        int patternLen = expected.get(0).length;
        double error = 0.0;
        for (int i = 0; i < input.size(); i++) {
            double[] result = activate(input.get(i));
            double[] exp = expected.get(i);
            for (int j = 0; j < patternLen; j++) {
                error += ((result[j] - exp[j]) * (result[j] - exp[j])) / 2;
            }
        }
        TrainingErrorLog.add(error / patternLen / input.size());
        return error / patternLen / input.size();
    }

    private void logValidationError(List<double[]> input, List<double[]> expected) throws Exception {
        int patternLen = expected.get(0).length;
        double error = 0.0;
        for (int i = 0; i < input.size(); i++) {
            double[] result = activate(input.get(i));
            double[] exp = expected.get(i);
            for (int j = 0; j < patternLen; j++) {
                error += ((result[j] - exp[j]) * (result[j] - exp[j])) / 2;
            }
        }
        ValidationErrorLog.add(error / patternLen / input.size());
    }

    public List<Double> getTraingingLog() {
        return TrainingErrorLog;
    }

    public List<Double> getValidationLog() {
        return ValidationErrorLog;
    }

    public List<Double> getTestScoreLog() {
        return TestRateLog;
    }

    public void turnOffInputs(List<Integer> exlude) {
        for (int i : exlude) {
            layers.get(0).getByIndex(i - 1).setActivationFunction(new TurnOffFunction());
        }
    }

    public void turnOnInputs() {
        for (Neuron n : layers.get(0).getNeurons()) {
            n.setActivationFunction(new LinearFunction());
        }
    }

    private void calculateWeights(double learningRate, double momentum) {
        Layer previous = null;
        Neuron neuron;
        double delta;
        double inputValue;
        double weight;
        for (Layer current : layers) {
            if (previous != null) {
                for (int i = 0; i < current.getNeurons().size(); i++) {
                    neuron = current.getByIndex(i);
                    for (int j = 0; j < neuron.getWeights().length; j++) {
                        if (j >= previous.getNeurons().size()) {
                            inputValue = 1.0;
                        } else {
                            inputValue = previous.getByIndex(j).getResult();
                        }
                        delta = inputValue * learningRate * neuron.getError() + neuron.getDelta(j) * momentum;
                        weight = neuron.getWeight(j) + delta;
                        neuron.setWeight(j, weight);
                        neuron.setDelta(j, delta);
                    }
                }
            }
            previous = current;
        }
    }

    private void backPropagation(double[] data, double[] expected, double learningRate, double momentum) throws Exception {
        activate(data);
        calculateErrors(expected);
        calculateWeights(learningRate, momentum);
    }

    private void clearDelta() {
        for (Layer layer : layers) {
            for (Neuron neuron : layer.getNeurons()) {
                neuron.clearDelta();
            }
        }
    }

    /**
     *
     * @param trainingData
     * @param learningRate
     * @param momentum
     * @param iterations
     * @param precision
     * @param isRandomOrder
     * @return number of passed iteration
     * @throws Exception
     */
    public int trainNetwork(TrainingData trainingData, double learningRate, double momentum, int iterations, double precision, boolean isRandomOrder) throws Exception {

        List<double[]> trainData = trainingData.getTrainingData();
        List<double[]> trainExp = trainingData.getTrainingExpected();

        this.patternLength = trainExp.get(0).length;

        if (layers == null) {
            throw new NoNetworkException();
        }
        boolean withValidation = false;
        if (!trainingData.getValidationData().isEmpty()) {
            withValidation = true;
            ValidationErrorLog.clear();
        }

        TrainingErrorLog.clear();
        double e = 100;
        int itr = 1;
        while (e > precision && itr <= iterations) {
            if (isRandomOrder) {
                DataHelper.randomizeData(trainData, trainExp);
            }
            for (int i = 0; i < trainData.size(); i++) {
                clearDelta();
                backPropagation(trainData.get(i), trainExp.get(i), learningRate, momentum);
            }
            if (itr % 50 == 0) {
                e = logTrainingError(trainData, trainExp);
                if (withValidation) {
                    logValidationError(trainingData.getValidationData(), trainingData.getValidationExpected());
                    logTestValues(trainingData.getTestData(), trainingData.getTestExpected(),false);
                }
            }
            itr++;
        }
        logTrainingError(trainData, trainExp);
        logTestValues(trainingData.getTestData(), trainingData.getTestExpected(),true);
        return itr;
    }

    public class WrongNumberOfNeuronsException extends Exception {

        public WrongNumberOfNeuronsException() {
            super("Number of Neurons must be positive!");
        }
    }

    public class NoNetworkException extends Exception {

        public NoNetworkException() {
            super("Network has not been prepared");
        }
    }
}
