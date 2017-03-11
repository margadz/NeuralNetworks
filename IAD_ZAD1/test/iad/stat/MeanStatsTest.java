/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.stat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
public class MeanStatsTest {
    
    public MeanStatsTest() {
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
     * Test of getArithmeticMean method, of class MeanStats.
     */
    @Test
    public void testGetArithmeticMean() {
        List<Double> data = Arrays.asList(5.0,8.0,4.0,38.0);
        double expResult = 13.75;
        double result = MeanStats.getArithmeticMean(data);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getGeometricMean method, of class MeanStats.
     */
    @Test
    public void testGetGeometricMean() {
        List<Double> data = Arrays.asList(3.0,9.0,8.0,6.0);
        double expResult = 6.0;
        double result = MeanStats.getGeometricMean(data);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getHarmonicMean method, of class MeanStats.
     */
    @Test
    public void testGetHarmonicMean() {
        List<Double> data = Arrays.asList(3.0,9.0,9.0,3.0);
        double expResult = 4.5;
        double result = MeanStats.getHarmonicMean(data);
        assertEquals(expResult, result, 0.0);
    }
    
}
