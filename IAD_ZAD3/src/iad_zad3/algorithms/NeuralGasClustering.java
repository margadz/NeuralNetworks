/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.algorithms;

import iad_zad3.algorithms.functions.Distance;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author marr
 */
public class NeuralGasClustering extends SOMClustering {

    private volatile Queue<CentroidWithDistance> sortedDistance;

    public NeuralGasClustering(Distance distance, List<double[]> data, int numberOfCentroids, int iterations, SOMParameters param) {
        super(distance, data, numberOfCentroids, iterations, param);
        setUpNewCentroids(numberOfCentroids);
    }

    @Override
    public final void setUpNewCentroids(int numberOfCentroids) {
        currentIteration = 0;
        currentLambda = maxLambda;
        currentEta = maxEta;
        centroids = new ArrayList<>(numberOfCentroids);
        sortedDistance = new PriorityQueue<>(numberOfCentroids, COMPARATOR);
        for (int i = 0; i < numberOfCentroids; i++) {
            centroids.add(getNewCoordinates());
        }
    }

    @Override
    public synchronized void moveCentroids() {
        globaleError = 0;
        for (int i = 0; i < rawData.size(); i++) {
            int nearest = findNearestCentroid(rawData.get(i));
            createDistanceQueue(nearest);

            globaleError += (distanceFunction.getDistance(centroids.get(nearest), rawData.get(i)) * distanceFunction.getDistance(centroids.get(nearest), rawData.get(i)));

            int distance = 0;
            while (!sortedDistance.isEmpty()) {
                CentroidWithDistance centroid = sortedDistance.poll();
                calculateNewCentroidPosition(centroid.centroidId, i, distance);
                distance++;
            }
        }
        decreaseEtaAndLambda();
        globaleError /= rawData.size();
        currentIteration++;
    }

    private void calculateNewCentroidPosition(int centroid, int datum, int distanceFromTheWinner) {
        double distanceFactor = neighbourFunction(distanceFromTheWinner);
        double[] centroidTable = centroids.get(centroid);
        double[] dataTable = rawData.get(datum);
        for (int i = 0; i < coordinatesNumber; i++) {
            double move = currentEta * distanceFactor * (dataTable[i] - centroidTable[i]);
            centroidTable[i] += move;
        }
    }

    private double neighbourFunction(int distance) {
        if (distance == 0) {
            return 1;
        } else {
            return Math.exp(-(distance * 1.0 / currentLambda));
        }
    }

    private void createDistanceQueue(int centroid) {
        sortedDistance.clear();
        for (int i = 0; i < centroids.size(); i++) {
            double dist = distanceFunction.getDistance(centroids.get(centroid), centroids.get(i));
            sortedDistance.add(new CentroidWithDistance(i, dist));
        }
    }

    private class CentroidWithDistance {

        private final int centroidId;
        private final double distance;

        public CentroidWithDistance(final int centroidId, final double distance) {
            this.centroidId = centroidId;
            this.distance = distance;
        }

        public int getCentroidId() {
            return centroidId;
        }

        public double getDistance() {
            return distance;
        }
    }

        private static Comparator<CentroidWithDistance> COMPARATOR = new Comparator<CentroidWithDistance>() {
        @Override
        public int compare(CentroidWithDistance o1, CentroidWithDistance o2) {
            if (o1.distance > o2.distance){
                return 1;
            }else if (o1.distance < o2.distance){
                return -1;
            }else{
                return 0;
            }
            
        }
    };

}
