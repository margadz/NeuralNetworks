/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2.gg.net;

import iad_2.gg.func.ActivationFunction;
import java.util.Arrays;
import java.util.Random;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author glabg
 */
@XmlRootElement(name = "Neuron")
@XmlAccessorType(XmlAccessType.FIELD)
public class Neuron {

    @XmlAnyElement(lax = true)
    private ActivationFunction activationFunction;
    private int bias = 0;
    private double[] weights;
    private double result = 0.0;
    private double error = 0.0;
    private double activation = 0.0;
    private double[] delta;
    private double derivResult = 0.0;

    public Neuron(){
    
    }
    
    public Neuron(ActivationFunction activationFunction, int bias, int inputs) {
        this.activationFunction = activationFunction;
        this.bias = bias;
        if (inputs == 0) {
            weights = new double[1];
            delta = new double[1];
            weights[0] = 1.0;
        } else {
            weights = new double[inputs + bias];
            delta = new double[inputs + bias];
            Random rand = new Random();
            for (int i = 0; i < inputs; i++) {
                weights[i] = rand.nextDouble() - 0.5;
            }
            if (bias == 1) {
                weights[inputs] = 1;
            }
        }
    }

    public double activate(double[] inputs) throws Exception {
        activation = 0.0;
        if (inputs.length != weights.length - bias) {
            throw new Exception("Input size not correct!");
        }
        for (int i = 0; i < inputs.length; i++) {
            activation += inputs[i] * weights[i];
        }
        result = activationFunction.function(activation);
        derivResult = activationFunction.derivative(activation);
        return result;
    }

    public void setError(double error) {
        this.error = error * derivResult;
    }

    public double getError() {
        return error;
    }

    public double getWeight(int i) {
        return weights[i];
    }

    public void setWeight(int i, double weight) {
        weights[i] = weight;
    }

    public void setResult(double res) {
        this.result = res;
    }

    public double getResult() {
        return result;
    }

    public double getActivationDerivative() {
        return activationFunction.derivative(activation);
    }

    public double[] getWeights() {
        return weights;
    }

    public void setDelta(int i, double delta) {
        this.delta[i] = delta;
    }

    public double getDelta(int i) {
        return delta[i];
    }

    public void clearDelta() {
        Arrays.fill(delta, 0.0);
    }

    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }
    
}
