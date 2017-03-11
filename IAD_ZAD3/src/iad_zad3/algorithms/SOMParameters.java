/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.algorithms;

/**
 *
 * @author marr
 */
public class SOMParameters {

    private final double maxLambda;
    private final double minLambda;
    private final double maxEta;
    private final double minEta;

    public SOMParameters(double maxLambda, double minLambda, double maxEta, double minEta) {
        this.maxLambda = maxLambda;
        this.minLambda = minLambda;
        this.maxEta = maxEta;
        this.minEta = minEta;
    }

    public double getMaxLambda() {
        return maxLambda;
    }

    public double getMinLambda() {
        return minLambda;
    }

    public double getMaxEta() {
        return maxEta;
    }

    public double getMinEta() {
        return minEta;
    }
    
    
}
