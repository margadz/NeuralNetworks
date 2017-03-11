/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.algorithms;

import iad_zad3.algorithms.functions.Distance;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author marr
 */
public class KohonenClustering extends SOMClustering {

    private final boolean fixedRange;
    private double[] winners;

    public KohonenClustering(Distance distance, List<double[]> data, int numberOfCentroids, int iterations, SOMParameters param, boolean fixedRange) {
        super(distance, data, numberOfCentroids, iterations, param);
        this.fixedRange = fixedRange;
        setUpNewCentroids(numberOfCentroids);
    }

    @Override
    public final void setUpNewCentroids(int numberOfCentroids) {
        currentIteration = 0;
        currentLambda = maxLambda;
        currentEta = maxEta;
        centroids = new ArrayList<>(numberOfCentroids);
        setUpWinnersArray(numberOfCentroids);
        for (int i = 0; i < numberOfCentroids; i++) {
            centroids.add(getNewCoordinates());
        }
    }

    @Override
    public synchronized void moveCentroids() {
        globaleError = 0;
        findWinners();
        for (int i = 0; i < rawData.size(); i++) {
            int nearest = findNearestCentroid(rawData.get(i));
            globaleError += (distanceFunction.getDistance(centroids.get(nearest), rawData.get(i)) * distanceFunction.getDistance(centroids.get(nearest), rawData.get(i)));
            for (int j = 0; j < centroids.size(); j++) {
                double dist = 0;
                if (nearest != j) {
                    dist = distanceFunction.getDistance(centroids.get(nearest), centroids.get(j));
                }
                calculateNewCentroidPosition(j, i, dist);
            }
        }

        decreaseEtaAndLambda();
        globaleError /= rawData.size();
        currentIteration++;
    }

    private void calculateNewCentroidPosition(int centroid, int datum, double distanceFromTheWinner) {
        double fatigue = (currentIteration * 1.0 / iterations) <= 0.05 ? winners[centroid] : 1;
        double distanceFactor = neighbourFunction(fatigue * distanceFromTheWinner);

        double[] centroidTable = centroids.get(centroid);
        double[] dataTable = rawData.get(datum);
        for (int i = 0; i < coordinatesNumber; i++) {
            double move = currentEta * distanceFactor * (dataTable[i] - centroidTable[i]);
            centroidTable[i] += move;
        }
    }

    private double neighbourFunction(double distance) {
        if (distance == 0) {
            return 1;
        } else if (fixedRange) {
            if (distance <= currentLambda) {
                return 1;
            } else {
                return 0;
            }
        }
        return Math.exp(-((distance * distance) / (2.0 * (currentLambda * currentLambda))));
    }

    private void setUpWinnersArray(int numberOfCentroids) {
        this.winners = new double[numberOfCentroids];
        Arrays.fill(winners, 0.1);
    }

    private void resetWinners() {
        Arrays.fill(winners, 0.1);
    }

    private void findWinners() {
        resetWinners();
        for (int i = 0; i < rawData.size(); i++) {
            int nearest = findNearestCentroid(rawData.get(i));
            winners[nearest]++;
        }
    }
}
