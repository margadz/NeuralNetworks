/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.stat;

import iad.stat.distribution.DistributionCalc;
import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author marr
 */
public class HypothesisTest {

    private DecimalFormat df = new DecimalFormat("0.####");
    private DecimalFormat df1 = new DecimalFormat("0.######");
    private DistributionCalc disCalc;
    private boolean normH0 = true;
    private boolean studH0 = true;
    StringBuilder results = new StringBuilder("");

    public HypothesisTest(List<Double> data1, List<Double> data2, boolean isTwoTail, double significance) {

        disCalc = new DistributionCalc(data1, data2, isTwoTail);
        results.append("rozmiar pierwszej próby: ").append(data1.size()).append("\n");
        results.append("rozmiar drugiej próby:   ").append(data2.size()).append("\n");
        testHypothesis(significance);
        prepareResults();
    }

    private void prepareResults() {
        results.append("średnia pierwszej próby:   ").append(df.format(disCalc.getMean1())).append("\n");
        results.append("średnia drugiej próby:     ").append(df.format(disCalc.getMean2())).append("\n");
        results.append("odch. std. pierwszej próby: ").append(df.format(Math.sqrt(disCalc.getVariance1()))).append("\n");
        results.append("odch. std. drugiej próby:   ").append(df.format(Math.sqrt(disCalc.getVariance2()))).append("\n");
        results.append("stopnie swobody:   ").append(df.format(disCalc.getDegreesOfFredom())).append("\n");
        results.append("znormalizowana różnica średnich (z):   ").append(df.format(disCalc.getZScore())).append("\n");
        results.append("wartość p rozkładu normalnego: ").append(df1.format(disCalc.getNormalPValue())).append("\n");
        results.append("wartość p rozkładu t-Studenta: ").append(df1.format(disCalc.getStudentPValue())).append("\n");
        results.append("rozkład normalny:  hipotezę zerową ");
        if (normH0) {
            results.append("przyjęto (hipotezę alternatywną odrzucono)\n");
        } else {
            results.append("odrzucono (hipotezę alternatywną przyjęto)\n");
        }
        results.append("rozkład t-Studenta: hipotezę zerową ");
        if (studH0) {
            results.append("przyjęto (hipotezę alternatywną odrzucono)\n");
        } else {
            results.append("odrzucono (hipotezę alternatywną przyjęto)\n");
        }
    }

    private void testHypothesis(double significance) {
        if (disCalc.getNormalPValue() < significance) {
            normH0 = false;
        }
        if (disCalc.getStudentPValue() < significance) {
            studH0 = false;
        }
    }

    public String getResults() {
        return results.toString();
    }

}
