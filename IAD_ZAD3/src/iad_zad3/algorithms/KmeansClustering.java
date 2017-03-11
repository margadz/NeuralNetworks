/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.algorithms;

import iad_zad3.algorithms.functions.Distance;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author marr
 */
public class KmeansClustering extends ClusteringAlgorithm {

    private final List<List<double[]>> clusters;

    public KmeansClustering(Distance distance, List<double[]> data, int numberOfCentroids, int iterations) {
        super(distance, data, numberOfCentroids, iterations);
        clusters = new LinkedList<>();
        setUpNewCentroids(numberOfCentroids);

    }

    @Override
    public final void setUpNewCentroids(int numberOfCentroids) {
        currentIteration = 0;
        centroids = new ArrayList<>(numberOfCentroids);
        clusters.clear();
        for (int i = 0; i < numberOfCentroids; i++) {
            centroids.add(getNewCoordinates());
            clusters.add(new LinkedList<>());
        }
        calculateClusters();
    }

    private void calculateClusters() {
        clearClusters();
        globaleError = 0;
        for (int i = 0; i < rawData.size(); i++) {
            double[] tmp = rawData.get(i);
            int centroid = findNearestCentroid(tmp);
            clusters.get(centroid).add(tmp);
            globaleError += distanceFunction.getDistance(tmp, centroids.get(centroid)) * distanceFunction.getDistance(tmp, centroids.get(centroid));
        }
        globaleError /= rawData.size();
    }

    @Override
    public synchronized void moveCentroids() {
        calculateClusters();
        for (int i = 0; i < centroids.size(); i++) {
            if (!clusters.get(i).isEmpty()) {
                double[] newPosition = calculateCenterOfWeight(clusters.get(i));
                centroids.set(i, newPosition);
            }
        }
        
        currentIteration++;
    }

    private double[] calculateCenterOfWeight(List<double[]> cluster) {
        double[] newCoord = new double[coordinatesNumber];
        Arrays.fill(newCoord, 0);
        for (double[] d : cluster) {
            for (int i = 0; i < coordinatesNumber; i++) {
                newCoord[i] += d[i];
            }
        }
        for (int i = 0; i < coordinatesNumber; i++) {
            newCoord[i] /= cluster.size();
        }
        return newCoord;
    }

    private void clearClusters() {
        for (List<double[]> l : clusters) {
            l.clear();
        }
    }

}
