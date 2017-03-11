/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.stat;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class PositionalStatsTest {

    public PositionalStatsTest() {
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
     * Test of getMedian method, of class PositionalStats.
     */
    @Test
    public void testGetMedianOddNumberOfElements() {
        List<Double> dataOdd = Arrays.asList(9.0, 5.0, 5.6, 8.2, 7.0);
        double expResultOdd = 7.0;
        double resultOdd = PositionalStats.getMedian(dataOdd);
        assertEquals(expResultOdd, resultOdd, 0.001);
    }

    @Test
    public void testGetMedianEvenNumberOfElements() {
        List<Double> dataEven = Arrays.asList(9.0, 5.0, 5.6, 8.2, 7.0, 1.0);
        double expResultEven = 6.3;
        double resultEven = PositionalStats.getMedian(dataEven);
        assertEquals(expResultEven, resultEven, 0.001);
    }

    /**
     * Test of getQurtile method, of class PositionalStats.
     *
     * @throws iad.stat.WrongQuartileException
     */
    @Test(expected = WrongQuartileException.class)
    public void testGetQuartile() throws WrongQuartileException {
        List<Double> data = Arrays.asList(1.0);
        int quartile = 8;
        PositionalStats.getQuartile(data, quartile);
    }

    @Test
    public void testGetQuartileFirstQuartileOddData() {
        List<Double> data = Arrays.asList(7.0, 15.0, 36.0, 6.0, 39.0, 40.0, 41.0, 42.0, 43.0, 47.0, 49.0);
        int quartile = 1;
        double expected = 25.5;
        double result = 0;
        try {
            result = PositionalStats.getQuartile(data, quartile);
        } catch (WrongQuartileException ex) {
            //TODO :)
        }
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void testGetQuartileThirdQuartileOddData() {
        List<Double> data = Arrays.asList(6.0, 7.0, 39.0, 40.0, 41.0, 42.0, 43.0, 15.0, 47.0, 49.0, 36.0);
        int quartile = 3;
        double expected = 42.5;
        double result = 0;
        try {
            result = PositionalStats.getQuartile(data, quartile);
        } catch (WrongQuartileException ex) {
            //TODO :)
        }
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void testGetQuartileFirstQuartileEvenData() {
        List<Double> data = Arrays.asList(7.0, 15.0, 36.0, 39.0, 40.0, 41.0);
        int quartile = 1;
        double expected = 15.0;
        double result = 0;
        try {
            result = PositionalStats.getQuartile(data, quartile);
        } catch (WrongQuartileException ex) {
            //TODO :)
        }
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void testGetQuartileThirdQuartileEvenData() {
        List<Double> data = Arrays.asList(7.0, 15.0, 36.0, 39.0, 40.0, 41.0);
        int quartile = 3;
        double expected = 40.0;
        double result = 0;
        try {
            result = PositionalStats.getQuartile(data, quartile);
        } catch (WrongQuartileException ex) {
            //TODO :)
        }
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void testGetMaxValue() {
        List<Double> data = Arrays.asList(5.0, 1.2, 7.0, 6.3);
        double expected = 7.0;
        double result = PositionalStats.getMaxValue(data);
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void testGetModeAtTheEnd() {
        List<Double> data = Arrays.asList(3.1, 1.2, 2.8, 1.0, 3.1);
        double expected = 3.1;
        double result = PositionalStats.getMode(data);
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void testGetModeAtTheBeginning() {
        List<Double> data = Arrays.asList(1.0, 2.8, 4.1, 3.1, 1.0);
        double expected = 1.0;
        double result = PositionalStats.getMode(data);
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void testGetModeInTheMiddle() {
        List<Double> data = Arrays.asList(1.0, 2.8, 2.8, 3.1, 5.9);
        double expected = 2.8;
        double result = PositionalStats.getMode(data);
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void testGetModeMultiple() {
        List<Double> data = Arrays.asList(3.1, 2.8, 2.8, 3.1, 5.9);
        double expected = Double.NaN;
        double result = PositionalStats.getMode(data);
        assertEquals(expected, result, 0.0);
    }

}
