/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2.gg.net;

import iad_2.gg.func.ActivationFunction;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author glabg
 */
@XmlRootElement(name = "Layer")
@XmlAccessorType (XmlAccessType.FIELD)
public class Layer {
    
    @XmlElement(name = "Neuron")
    private List<Neuron> neurons;
    private boolean inputLayer = false;

    public List<Neuron> getNeurons() {
        return neurons;
    }
    
    public Layer(){
    
    }

    public Layer(int neuronsNo, int neuronsNoPrevLayer, ActivationFunction func, int bias) {
        if (neuronsNoPrevLayer == 0) {
            inputLayer = true;
        }
        this.neurons = new ArrayList<>();
        for (int i = 0; i < neuronsNo; i++) {
            neurons.add(new Neuron(func, bias, neuronsNoPrevLayer));
        }
    }

    public double[] activate(double[] inputs) throws Exception {

        if (inputLayer) {
            for (int i = 0; i < neurons.size(); i++) {
                neurons.get(i).setResult(inputs[i]);
            }
            return inputs;
        }
        double[] result = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            result[i] = neurons.get(i).activate(inputs);
        }
        return result;
    }

    public Neuron getByIndex(int i) {
        return neurons.get(i);
    }
}
