/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2_gg.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author glabg
 */
public class ChartDialog extends javax.swing.JDialog {

    public ChartDialog(java.awt.Frame parent, boolean modal, String chartTitle, String xLabel, String yLabel, List<Double> values, int interval) {
        super(parent, modal);

        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                xLabel,
                yLabel,
                createDataset(values, interval),
                PlotOrientation.VERTICAL,
                true, true, false);
        lineChart.getCategoryPlot().getDomainAxis().setTickLabelFont(new Font("Dialog", Font.PLAIN, 8));
        //lineChart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("Dialog", Font.PLAIN, 8));
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(1200, 600));
        setContentPane(chartPanel);

//        double[] primitiveData = Arrays.stream(data).mapToDouble(Double::doubleValue).toArray();
//        JFreeChart histogram = buildHistogram(klass, "oś X", "oś Y", primitiveData, div, HistogramType.FREQUENCY);
//        
//        ChartPanel chartPanel = new ChartPanel(histogram);
//        chartPanel.setPreferredSize(new Dimension(800,600));
//        setContentPane(chartPanel);
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
            .addGap(0, 1210, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    private DefaultCategoryDataset createDataset(List<Double> values, int interval){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int i = 1;
        for(double v : values){
            dataset.addValue(v, "error", new Integer(i * interval));
            i++;
        }
        return dataset;
    }
}
