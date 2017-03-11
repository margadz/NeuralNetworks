/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.algorithms;

import iad_zad3.algorithms.functions.Distance;
import iad_zad3.services.DataStats;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author marr
 */
public abstract class ClusteringAlgorithm {

    protected final Distance distanceFunction;
    protected final int coordinatesNumber;
    protected final List<double[]> rawData;
    protected List<double[]> centroids;
    protected final int iterations;
    protected volatile int currentIteration;

    protected double globaleError;

    public ClusteringAlgorithm(final Distance distance,final List<double[]> data,final int numberOfCentroids,final int iterations) {
        this.distanceFunction = distance;
        this.rawData = data;
        centroids = new ArrayList<>(numberOfCentroids);
        coordinatesNumber = data.get(0).length;
        this.currentIteration = 0;
        this.iterations = iterations;
    }

    public abstract void setUpNewCentroids(final int numberOfCentroids);

    public abstract void moveCentroids();

    public synchronized List<double[]> getCentroids() {
        return this.centroids;
    }

    public synchronized double getError() {
        return this.globaleError;
    }

    protected double[] getNewCoordinates() {
        double[] coord = new double[coordinatesNumber];
        Random random = new Random();
        for (int i = 0; i < coordinatesNumber; i++) {
            coord[i] = ((random.nextDouble() - 0.5) * (DataStats.maxVal[i]- DataStats.minVal[i]));
        }
        return coord;
    }

    protected int findNearestCentroid(double[] d) {
        int centroid = 0;
        double dist = distanceFunction.getDistance(centroids.get(0), d);
        for (int i = 1; i < centroids.size(); i++) {
            double tmp = distanceFunction.getDistance(centroids.get(i), d);
            if (dist > tmp) {
                dist = tmp;
                centroid = i;
            }
        }
        return centroid;
    }

    public List<double[]> getData() {
        return this.rawData;
    }

    public int getCurrentIterations() {
        return currentIteration;
    }

    public void resetIterations() {
        this.currentIteration = 0;
    }

    public int getMaxIterations() {
        return iterations;
    }
}
