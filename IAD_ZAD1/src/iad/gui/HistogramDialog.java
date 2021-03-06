/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.gui;

import java.awt.Dimension;
import java.util.Arrays;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

/**
 *
 * @author glabg
 */
public class HistogramDialog extends javax.swing.JDialog {

    /**
     * Creates new form HistogramDialog
     */
    public HistogramDialog(java.awt.Frame parent, boolean modal, String klass, String param, Double[] data,int div) {
        super(parent, modal);
        
        double[] primitiveData = Arrays.stream(data).mapToDouble(Double::doubleValue).toArray();
        JFreeChart histogram = buildHistogram(klass, "oś X", "oś Y", primitiveData, div, HistogramType.FREQUENCY);
        
        ChartPanel chartPanel = new ChartPanel(histogram);
        chartPanel.setPreferredSize(new Dimension(800,600));
        setContentPane(chartPanel);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private JFreeChart buildHistogram(final String title, final String xAxisLabel, final String yAxisLabel, final double[] numbers, final int div, final HistogramType type) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(type);
        dataset.addSeries(xAxisLabel, numbers, div);
        JFreeChart histogram = ChartFactory.createHistogram(
                title, 
                xAxisLabel,
                yAxisLabel, 
                dataset, 
                PlotOrientation.VERTICAL, 
                true, true, false);
        
        return histogram;
    }
}
