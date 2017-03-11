/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.algorithms.functions;

/**
 *
 * @author marr
 */
public class EuclidianDistance implements Distance{

    @Override
    public double getDistance(double[] c1, double[] c2) {
        double distance = 0;
        for (int i = 0; i < c1.length; i++){
            distance += (c1[i] - c2[i]) * (c1[i] - c2[i]);
        }
        return Math.sqrt(distance);
    }
}
