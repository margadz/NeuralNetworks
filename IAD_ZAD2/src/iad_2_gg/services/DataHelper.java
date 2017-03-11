/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad_2_gg.services;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author marr
 */
public class DataHelper {

    private final static double[] data1 = {1.0, 0.0, 0.0, 0.0};
    private final static double[] data2 = {0.0, 1.0, 0.0, 0.0};
    private final static double[] data3 = {0.0, 0.0, 1.0, 0.0};
    private final static double[] data4 = {0.0, 0.0, 0.0, 1.0};
    private static Map<Integer, double[]> data;
    private static DataHelper INSTANCE;
    private final static Random random = new Random();

    private DataHelper() {
        data = new HashMap<>();
        data.put(1, data1);
        data.put(2, data2);
        data.put(3, data3);
        data.put(4, data4);
    }

    public static DataHelper GetInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataHelper();
        }
        return INSTANCE;
    }

    public List<double[]> GetRandomData(int size) {
        List<double[]> retData = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 4; j++) {
                retData.add(data.get(random.nextInt(4) + 1));
            }
        }
        return retData;
    }

    public List<double[]> GetStaticData(int size) {
        List<double[]> retData = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            retData.add(data.get(1));
            retData.add(data.get(2));
            retData.add(data.get(3));
            retData.add(data.get(4));
        }
        return retData;
    }

    public static  void randomizeData(List<double[]> d1, List<double[]> d2) throws Exception {
        if (d1.size() != d2.size()) {
            throw new Exception("Lists are not equal");
        }
        Random rnd = ThreadLocalRandom.current();
        for (int i = d1.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            double[] i1 = d1.get(index);
            d1.set(index, d1.get(i));
            d1.set(i, i1);
            double[] i2 = d2.get(index);
            d2.set(index, d2.get(i));
            d2.set(i, i2);

        }
    }
}
