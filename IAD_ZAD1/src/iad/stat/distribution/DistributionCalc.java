/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.stat.distribution;

import iad.stat.DispersionStats;
import iad.stat.MeanStats;
import java.util.List;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;

/**
 *
 * @author marr
 */
public class DistributionCalc {

    NormalDistribution norm;
    TDistribution tstud;
    private double degreesOfFredom;
    private double mean1;
    private double mean2;
    private double variance1;
    private double variance2;
    private double zScore;
    private int n1;
    private int n2;
    private double normalPValue;
    private double studentPValue;

    public DistributionCalc(List<Double> data1, List<Double> data2, boolean isTwoTailed) {
        calculateMV(data1, data2);
        calculateDF();
        calculateZScore();
        calculateNorm(isTwoTailed);
        calculateStudent(isTwoTailed);
    }

    private void calculateDF() {
        this.degreesOfFredom = ((variance1 / n1) + (variance2 / n2)) * ((variance1 / n1) + (variance2 / n2));
        this.degreesOfFredom /= ((variance1 / n1) * (variance1 / n1) / (n1 - 1)) + ((variance2 / n2) * (variance2 / n2) / (n2 - 1));
    }

    private void calculateZScore() {
        zScore = (mean1 - mean2) / Math.sqrt((variance1 / n1) + (variance2 / n2));
    }

    private void calculateMV(List<Double> data1, List<Double> data2) {
        this.mean1 = MeanStats.getArithmeticMean(data1);
        this.mean2 = MeanStats.getArithmeticMean(data2);
        this.variance1 = DispersionStats.getVariance(data1);
        this.variance2 = DispersionStats.getVariance(data2);
        this.n1 = data1.size();
        this.n2 = data2.size();
    }

    private void calculateNorm(boolean twoTailed) {
        norm = new NormalDistribution();
        if (twoTailed) {
            normalPValue = (1 - norm.cumulativeProbability(Math.abs(zScore))) * 2;
        } else {
            normalPValue = 1 - norm.cumulativeProbability(Math.abs(zScore));
        }

    }

    private void calculateStudent(boolean twoTailed) {
        tstud = new TDistribution(degreesOfFredom);
        if (twoTailed) {
            studentPValue = (1 - tstud.cumulativeProbability(Math.abs(zScore))) * 2;
        } else {
            studentPValue = 1 - tstud.cumulativeProbability(Math.abs(zScore));
        }
    }

    public double getDegreesOfFredom() {
        return degreesOfFredom;
    }

    public double getZScore() {
        return zScore;
    }

    public Double getMean1() {
        return mean1;
    }

    public double getMean2() {
        return mean2;
    }

    public double getVariance1() {
        return variance1;
    }

    public double getVariance2() {
        return variance2;
    }

    public int getN1() {
        return n1;
    }

    public int getN2() {
        return n2;
    }

    public double getNormalPValue() {
        return normalPValue;
    }

    public double getStudentPValue() {
        return studentPValue;
    }

}
