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
public class AsymetryStatsTest {
    
    public AsymetryStatsTest() {
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
     * Test of getSkewness method, of class AsymetryStats.
     */
    @Test
    public void testGetSymetricSkewness() {
        List<Double> data = Arrays.asList(0.5,1.0,2.5,2.0,1.5);
        double expResult = 0.0;
        double result = AsymetryStats.getSkewness(data);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getSkewness method, of class AsymetryStats.
     */
    @Test
    public void testGetAsymetricSkewness() {
        List<Double> data = Arrays.asList(1.5,2.3,1.8,4.1,7.1);
        double expResult = 0.565217;
        double result = AsymetryStats.getSkewness(data);
        assertEquals(expResult, result, 0.00001);
    }
    
}
