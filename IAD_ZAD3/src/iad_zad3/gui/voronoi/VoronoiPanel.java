/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_zad3.gui.voronoi;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JPanel;

public class VoronoiPanel extends JPanel {

    public static Color voronoiColor = Color.white;
    
    public static int pointRadius = 3;

    private Triangulation dt;                   // Delaunay triangulation
    private Map<Object, Color> colorTable;      // Remembers colors for display
    private Triangle initialTriangle;           // Initial triangle
    private static int initialSize = 10000;     // Size of initial triangle
    private Graphics g;                         // Stored graphics context
    private Random random = new Random();       // Source of random numbers

    /**
     * Create and initialize the DT.
     */
    public VoronoiPanel() {
        initialTriangle = new Triangle(
                new Pnt(-initialSize, -initialSize),
                new Pnt(initialSize, -initialSize),
                new Pnt(0, initialSize));
        dt = new Triangulation(initialTriangle);
        colorTable = new HashMap<Object, Color>();
    }

    /**
     * Add a new site to the DT.
     *
     * @param point the site to add
     */
    public void addSite(Pnt point) {
        try{
        dt.delaunayPlace(point);
        }catch (Exception ex){
            //Do nothing. It's Ok :)
        }
    }

    /**
     * Re-initialize the DT.
     */
    public void clear() {
        dt = new Triangulation(initialTriangle);
    }

    /**
     * Get the color for the spcified item; generate a new color if necessary.
     *
     * @param item we want the color for this item
     * @return item's color
     */
    private Color getColor(Object item) {
        if (colorTable.containsKey(item)) {
            return colorTable.get(item);
        }
        Color color = new Color(Color.HSBtoRGB(random.nextFloat(), 1.0f, 1.0f));
        colorTable.put(item, color);
        return color;
    }

    /* Basic Drawing Methods */
    /**
     * Draw a point.
     *
     * @param point the Pnt to draw
     */
    public void draw(Pnt point) {
        int r = pointRadius;
        int x = (int) point.coord(0);
        int y = (int) point.coord(1);
        g.fillOval(x - r, y - r, r + r, r + r);
    }

    /**
     * Draw a circle.
     *
     * @param center the center of the circle
     * @param radius the circle's radius
     * @param fillColor null implies no fill
     */
    public void draw(Pnt center, double radius, Color fillColor) {
        int x = (int) center.coord(0);
        int y = (int) center.coord(1);
        int r = (int) radius;
        if (fillColor != null) {
            Color temp = g.getColor();
            g.setColor(fillColor);
            g.fillOval(x - r, y - r, r + r, r + r);
            g.setColor(temp);
        }
        g.drawOval(x - r, y - r, r + r, r + r);
    }

    /**
     * Draw a polygon.
     *
     * @param polygon an array of polygon vertices
     * @param fillColor null implies no fill
     */
    public void draw(Pnt[] polygon, Color fillColor) {
        int[] x = new int[polygon.length];
        int[] y = new int[polygon.length];
        for (int i = 0; i < polygon.length; i++) {
            x[i] = (int) polygon[i].coord(0);
            y[i] = (int) polygon[i].coord(1);
        }
        if (fillColor != null) {
            Color temp = g.getColor();
            g.setColor(fillColor);
            g.fillPolygon(x, y, polygon.length);
            g.setColor(temp);
        }
        g.drawPolygon(x, y, polygon.length);
    }

    /* Higher Level Drawing Methods */
    /**
     * Handles painting entire contents of DelaunayPanel. Called automatically;
     * requested via call to repaint().
     *
     * @param g the Graphics context
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;

        // Flood the drawing area with a "background" color
        Color temp = g.getColor();
        g.setColor(voronoiColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(temp);

        drawAllVoronoi(false, true);

        temp = g.getColor();
        g.setColor(Color.white);

        g.setColor(temp);
    }

    /**
     * Draw all the Delaunay triangles.
     *
     * @param withFill true iff drawing Delaunay triangles with fill colors
     */
    public void drawAllDelaunay(boolean withFill) {
        for (Triangle triangle : dt) {
            Pnt[] vertices = triangle.toArray(new Pnt[0]);
            draw(vertices, withFill ? getColor(triangle) : null);
        }
    }

    /**
     * Draw all the Voronoi cells.
     *
     * @param withFill true iff drawing Voronoi cells with fill colors
     * @param withSites true iff drawing the site for each Voronoi cell
     */
    public void drawAllVoronoi(boolean withFill, boolean withSites) {
        // Keep track of sites done; no drawing for initial triangles sites
        HashSet<Pnt> done = new HashSet<Pnt>(initialTriangle);
        for (Triangle triangle : dt) {
            for (Pnt site : triangle) {
                if (done.contains(site)) {
                    continue;
                }
                done.add(site);
                List<Triangle> list = dt.surroundingTriangles(site, triangle);
                Pnt[] vertices = new Pnt[list.size()];
                int i = 0;
                for (Triangle tri : list) {
                    vertices[i++] = tri.getCircumcenter();
                }
                draw(vertices, withFill ? getColor(site) : null);
                if (withSites) {
                    draw(site);
                }
            }
        }
    }

    /**
     * Draw all the empty circles (one for each triangle) of the DT.
     */
    public void drawAllCircles() {
        // Loop through all triangles of the DT
        for (Triangle triangle : dt) {
            // Skip circles involving the initial-triangle vertices
            if (triangle.containsAny(initialTriangle)) {
                continue;
            }
            Pnt c = triangle.getCircumcenter();
            double radius = c.subtract(triangle.get(0)).magnitude();
            draw(c, radius, null);
        }
    }

}