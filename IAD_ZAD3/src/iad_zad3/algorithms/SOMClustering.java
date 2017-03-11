/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.algorithms;

import iad_zad3.algorithms.functions.Distance;
import java.util.List;

/**
 *
 * @author marr
 */
public abstract class SOMClustering extends ClusteringAlgorithm {

    protected final double maxLambda;
    protected final double minLambda;
    protected final double maxEta;
    protected final double minEta;
    protected volatile double currentLambda;
    protected volatile double currentEta;

    public SOMClustering(Distance distance, List<double[]> data, int numberOfCentroids, int iterations, final SOMParameters param) {
        super(distance, data, numberOfCentroids, iterations);
        this.maxLambda = param.getMaxLambda();
        this.minLambda = param.getMinLambda();
        this.maxEta = param.getMaxEta();
        this.minEta = param.getMinEta();
    }

    public void decreaseEtaAndLambda() {
        currentLambda = maxLambda * Math.pow((minLambda / maxLambda), currentIteration * 1.0 / iterations);
        currentEta = maxEta * Math.pow((minEta / maxEta), currentIteration * 1.0 / iterations);
    }
}
