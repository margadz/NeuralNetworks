/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.gui;

import iad_zad3.algorithms.ClusteringAlgorithm;
import iad_zad3.gui.voronoi.Pnt;
import iad_zad3.gui.voronoi.VoronoiPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author marr
 */
public class ChartWorker extends SwingWorker<Void, ChartPanel> {

    private final JPanel chartPanel;
    private final JPanel voronoiPanel;
    private final ClusteringAlgorithm clusteringAlgorithm;
    private final JTextField errorText;
    private final JProgressBar bar;
    public static final DecimalFormat df = new DecimalFormat("0.#######");
    private ChartPanel chart;
    private boolean animate;
    private int frameDuration;

    public ChartWorker(JPanel chartPanel, JPanel voronoiPanel, JTextField errorText, JProgressBar bar, ClusteringAlgorithm kmeans, boolean animate, int frameDuration) {
        this.chartPanel = chartPanel;
        this.clusteringAlgorithm = kmeans;
        this.errorText = errorText;
        this.bar = bar;
        this.animate = animate;
        this.frameDuration = frameDuration;
        this.voronoiPanel = voronoiPanel;
    }

    @Override
    protected Void doInBackground() throws Exception {
        clusteringAlgorithm.moveCentroids();
        while (clusteringAlgorithm.getCurrentIterations() <= clusteringAlgorithm.getMaxIterations()) {
            publish(showKMeansChart());
            if (animate) {
                try {
                    Thread.sleep(frameDuration);
                } catch (InterruptedException ex) {
                    return null;
                }
            }
            clusteringAlgorithm.moveCentroids();
        }
        return null;
    }

    @Override
    protected void process(List<ChartPanel> chunks) {
        if (animate) {
            chartPanel.removeAll();
            chartPanel.setLayout(new java.awt.BorderLayout());
            chartPanel.add(chunks.get(0), BorderLayout.CENTER);
            chartPanel.validate();
            errorText.setText(df.format(clusteringAlgorithm.getError()));
            updateVoronoi();
        }
        bar.setValue(clusteringAlgorithm.getCurrentIterations());

    }

    private ChartPanel showKMeansChart() {
        if (chart == null) {
            chart = new ChartPanel(Charts.getKmeansChart(clusteringAlgorithm.getData(), clusteringAlgorithm.getCentroids()));
            chart.setPreferredSize(new Dimension(600, 600));
            chart.setDomainZoomable(true);
        } else {
            chart.setChart(Charts.getKmeansChart(clusteringAlgorithm.getData(), clusteringAlgorithm.getCentroids()));
        }
        return chart;
    }

    private void updateVoronoi() {
        VoronoiPanel vPanel = new VoronoiPanel();
        for (Pnt p : GUIHelper.getVoronoiPoints(clusteringAlgorithm)) {
            vPanel.addSite(p);
        }
        JPanel tmpPanel = new JPanel();
        tmpPanel.setSize(new Dimension(700, 700));
        tmpPanel.setLayout(new BorderLayout());
        voronoiPanel.add(tmpPanel, "Center");
        tmpPanel.add(vPanel, "Center");
        voronoiPanel.revalidate();
    }

    @Override
    protected void done() {
        chartPanel.removeAll();
        chartPanel.setLayout(new java.awt.BorderLayout());
        chartPanel.add(showKMeansChart(), BorderLayout.CENTER);
        chartPanel.validate();
        errorText.setText(df.format(clusteringAlgorithm.getError()));
        bar.setValue(clusteringAlgorithm.getCurrentIterations());
        updateVoronoi();
    }
}
