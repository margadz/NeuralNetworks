/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.stat;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marr
 */
public class DispersionStatsTest {

    public DispersionStatsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getVariance method, of class DispersionStats.
     */
    @Test
    public void testGetVariance() {
        List<Double> data = Arrays.asList(5.0, 7.0, 5.0, 4.0);
        double expResult = 1.5833;
        double result = DispersionStats.getVariance(data);
        assertEquals(expResult, result, 0.001);
    }

    /**
     * Test of getStandardDeviation method, of class DispersionStats.
     */
    @Test
    public void testGetStandardDeviation() {
        List<Double> data = Arrays.asList(5.0, 7.0, 5.0, 4.0);
        double expResult = 1.2583;
        double result = DispersionStats.getStandardDeviation(data);
        assertEquals(expResult, result, 0.001);
    }

    /**
     * Test of getRange method, of class DispersionStats.
     */
    @Test
    public void testGetRange() {
        List<Double> data = Arrays.asList(5.0, 4.4, 8.1, 5.0);
        double expResult = 3.7;
        double result = DispersionStats.getRange(data);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getCoefficientOfVariation method, of class DispersionStats.
     */
    @Test
    public void testGetCoefficientOfVariation() {
        List<Double> data = Arrays.asList(5.0, 4.4, 7.0, 8.1, 5.0);
        double expResult = 0.2669;
        double result = DispersionStats.getCoefficientOfVariation(data);
        assertEquals(expResult, result, 0.001);
    }

    @Test
    public void testgetInterquartileRange() {
        List<Double> data = Arrays.asList(7.0, 15.0, 36.0, 39.0, 40.0, 41.0);
        double result = DispersionStats.getInterquartileRange(data);
        double expected = 25.0;
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void testQuartileCoefficientOfDispersion() {
        List<Double> data = Arrays.asList(6.0, 7.0, 39.0, 40.0, 41.0, 42.0, 43.0, 15.0, 47.0, 49.0, 36.0);
        double result = DispersionStats.getQuartileCoefficientOfDispersion(data);
        double expected = 0.25;
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void testgetKurtosis() {
        List<Double> data = Arrays.asList(5.0,4.0,8.0,2.0);
        double result = DispersionStats.getKurtosis(data);
        double expected = 1.9237;
        assertEquals(expected, result, 0.001);
    }
}
