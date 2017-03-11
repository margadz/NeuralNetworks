/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.gui;

import iad_zad3.algorithms.ClusteringAlgorithm;
import iad_zad3.algorithms.KmeansClustering;
import iad_zad3.algorithms.SOMParameters;
import iad_zad3.algorithms.functions.Distance;
import iad_zad3.gui.voronoi.Pnt;
import iad_zad3.services.DataStats;
import iad_zad3.services.FileService;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author marr
 */
public class GUIHelper {

    private List<double[]> data;
    private SOMParameters params;
    private ClusteringAlgorithm clusteringAlgorithm;

    public void openFile(JFrame parent) throws Exception {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File openFile = fc.getSelectedFile();
            this.data = FileService.readFile(openFile);
        } else {
            throw new NoFileException();
        }
    }

    public List<double[]> getData() throws NoFileException {
        if (data == null) {
            throw new NoFileException();
        } else {
            return this.data;
        }
    }

    public void insertData(List<double[]> data) {
        this.data = data;
    }

    public void setSOMParameters(double maxLambda, double minLambda, double maxEta, double minEta) {
        params = new SOMParameters(maxLambda, minLambda, maxEta, minEta);
    }

    public SOMParameters getSOMParameters() throws NoParamsException {
        if (params == null) {
            throw new NoParamsException();
        } else {
            return params;
        }
    }

    public static List<Pnt> getVoronoiPoints(ClusteringAlgorithm clustering) {
        double xRange = DataStats.maxVal[0] - DataStats.minVal[0];
        double yRange = DataStats.maxVal[1] - DataStats.minVal[0];
//        System.out.println(xRange + " " + yRange);
        List<Pnt> voronoiPoints = new LinkedList<>();
        for (double[] c : clustering.getCentroids()) {
            voronoiPoints.add(new Pnt((c[0] + 11)* 24, (Math.abs(c[1] - 10)) * 24));
//            voronoiPoints.add(new Pnt((c[0] + DataStats.minVal[0]) * 24, (c[1] -  DataStats.minVal[1]) * 24));
        }

        return voronoiPoints;
    }

    public class NoFileException extends Exception {

    }

    public class NoParamsException extends Exception {

    }

}
