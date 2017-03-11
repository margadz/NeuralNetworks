/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2.gg.net;

import iad_2.gg.func.LinearFunction;
import iad_2.gg.func.LogisticFunction;
import iad_2_gg.services.DataHelper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.chart.PieChart;
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
    public List<Layer> layers;
    public List<Double> errorLog = new LinkedList<>();

    public Network() {

    }

    public void prepareNetwork(List<Integer> newLayers, int bias) throws Exception {
        if (!newLayers.get(0).equals(newLayers.get(newLayers.size() - 1))) {
            throw new Exception("Number of inputs and outputs don't match!");
        } else if (bias != 0 && bias != 1) {
            throw new Exception("Bias out of range (0 or 1)");
        }
        layers = new ArrayList<>();
        for (int i = 0; i < newLayers.size(); i++) {
            if (i == 0) {
                layers.add(new Layer(newLayers.get(i), 0, new LinearFunction(), 0));
            } else {
                layers.add(new Layer(newLayers.get(i), newLayers.get(i - 1), new LogisticFunction(), bias));
            }
        }
    }

    public double[] activate(double[] data) throws Exception {
        if (layers == null) {
            throw new Exception("Layers have not been prepared");
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

    private double logError(List<double[]> input, List<double[]> expected) throws Exception {
        int dataLen = input.get(0).length;
        double error = 0.0;
        for (int i = 0; i < input.size(); i++) {
            double[] result = activate(input.get(i));
            double[] exp = expected.get(i);
            for (int j = 0; j < dataLen; j++) {
                error += (result[j] - exp[j]) * (result[j] - exp[j]);
            }
        }
        errorLog.add(error / 2);
        return error / 2;
    }

    public List<Double> getLog() {
        return errorLog;
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
     * @param data
     * @param expected
     * @param learningRate
     * @param momentum
     * @param iterations
     * @param precision
     * @param isRandomOrder
     * @return number of passed iteration
     * @throws Exception
     */
    public int trainNetwork(List<double[]> data, List<double[]> expected, double learningRate, double momentum, int iterations, double precision, boolean isRandomOrder) throws Exception {
        if (layers == null) {
            throw new Exception("Layers have not been prepared");
        }
        
        errorLog.clear();
        double e = 100;
        int itr = 1;
        while (e > precision && itr <= iterations) {
            if (isRandomOrder) {
                DataHelper.randomizeData(data, expected);
            }
            for (int i = 0; i < data.size(); i++) {
//                for (double d : expected.get(i)){
//                    System.out.print(d + " ");
//                }
//                System.out.println("");
                
                clearDelta();
                backPropagation(data.get(i), expected.get(i), learningRate, momentum);
            }
            if (itr % 25 == 0) {
                e = logError(data, expected);
            }
            itr++;
        }
        return itr;
    }
}
