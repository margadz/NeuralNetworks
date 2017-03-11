/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2_gg.services;

import iad_2.gg.net.Network;
import iad_2.gg.net.TrainingData;
import java.util.List;

/**
 *
 * @author marr
 */
public class NetworkHelper {

    private Network network;
    private DataParser dataParser;
    private TrainingData data;

    public NetworkHelper(DataParser dataParser) {
        this.dataParser = dataParser;
    }

    public void setUpNetwork(List<Integer> layers, int bias) throws Exception {
        network = NetworkFactory.GetNetwork(layers, bias);
    }

    /**
     *
     * @param numberOfEpochs
     * @param learningRate
     * @param isRandom
     * @param momentum
     * @param dataMode : #0: 100% training, #1: 66% training, 17% validation, 17%
     * test; #2: 66% training, 17% validation, 17% test at random; #3: 40%
     * training, 30% validation, 30% test; #4: 40% training, 30% validation, 30%
     * test at random
     * @param excludedParameters : list of exluclude parameters
     * @throws java.lang.Exception
     */
    public void starTraining(int numberOfEpochs, double learningRate, double momentum, boolean isRandom, int dataMode, List<Integer> excludedParameters) throws Exception {
        data = dataParser.setUpData(dataMode);

        network.turnOnInputs();
        if (!excludedParameters.isEmpty()) {
            exludeInputs(excludedParameters);
        }

        network.trainNetwork(
                data,
                learningRate,
                momentum,
                numberOfEpochs,
                0,
                isRandom
        );
    }

    public List<Double> getTrainingErrorLog() {
        return network.getTraingingLog();
    }

    public List<Double> getValidationErrorLog() {
        return network.getValidationLog();
    }

    public List<Double> getTestScoreLog() {
        return network.getTestScoreLog();
    }

    public List<List<Integer>> getConfusionMatrix(){
        return network.getConfusionMatrix();
    }
    
    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;

    }

    public double[] getResult(double[] data) throws Exception {
        return network.activate(data);
    }

    private void exludeInputs(List<Integer> exluded) {
        for (int i = 0; i < exluded.size(); i++) {
            if ((exluded.get(i) - 1) < 0 || (exluded.get(i) - 1) >= dataParser.getTrainingData().size()) {
                exluded.remove(i);
            }
        }
        network.turnOffInputs(exluded);
    }

}
