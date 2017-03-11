/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.gui;

import iad_zad3.services.DataStats;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author marr
 */
public class Charts {

    private static final Shape circleSmall = new Ellipse2D.Double(1, 1, 1, 1);
    private static final Shape circleBig = new Ellipse2D.Double(3, 3, 6, 6);

    public static JFreeChart getKmeansChart(final List<double[]> data,final List<double[]> centroids) {
        JFreeChart jfreechart = ChartFactory.createScatterPlot("", "x", "y",
                createDataset(data, centroids),
                PlotOrientation.VERTICAL,
                false, false, false);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(1, circleSmall);
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesShape(0, circleBig);
        renderer.setSeriesPaint(0, Color.BLUE);
        ValueAxis domainAxis = xyPlot.getDomainAxis();
        domainAxis.setRange(DataStats.minVal[0]*1.2, DataStats.maxVal[0]*1.2);
        ValueAxis rangeAxis = xyPlot.getRangeAxis();
        rangeAxis.setRange(DataStats.minVal[1]*1.2, DataStats.maxVal[1]*1.2);
        jfreechart.setBackgroundPaint(Color.WHITE);
        return jfreechart;
    }

    private static XYDataset createDataset(final List<double[]> data,final List<double[]> centroids) {
        XYSeries dataSeries = new XYSeries("data");

        for (double[] v : data) {
            dataSeries.add(v[0], v[1]);
        }

        XYSeries centroidsSeries = new XYSeries("centroids");
        for (double[] v : centroids) {
            centroidsSeries.add(v[0], v[1]);
        }

        XYSeriesCollection coll = new XYSeriesCollection();
        coll.addSeries(centroidsSeries);
        coll.addSeries(dataSeries);

        return coll;
    }
}
