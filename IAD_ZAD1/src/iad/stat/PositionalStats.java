/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.stat;

import java.util.List;

/**
 *
 * @author marr
 */
public class PositionalStats {

    public static double getMedian(List<Double> data) {
        data.sort((Double x, Double y) -> x.compareTo(y));
        if (data.size() % 2 != 0) {
            return data.get(((data.size() + 1) / 2) - 1);
        } else {
            return (data.get((data.size() / 2) - 1) + data.get((data.size() / 2))) / 2;
        }
    }

    public static double getQuartile(List<Double> data, int quartile) throws WrongQuartileException {
        if (quartile != 3 && quartile != 1) {
            throw new WrongQuartileException();
        }
        data.sort((Double x, Double y) -> x.compareTo(y));
        if (data.size() % 2 == 0) {
            if (quartile == 1) {
                return getMedian(data.subList(0, (data.size() / 2)));
            } else if (quartile == 3) {
                return getMedian(data.subList((data.size() / 2), data.size()));
            }
        } else if (quartile == 1) {
            return getMedian(data.subList(0, (data.size() / 2) + 1));
        } else if (quartile == 3) {
            return getMedian(data.subList((data.size() / 2), data.size()));
        }
        return 0;
    }

    public static double getMinValue(List<Double> data) {
        data.sort((Double x, Double y) -> x.compareTo(y));
        return data.get(0);
    }

    public static double getMaxValue(List<Double> data) {
        data.sort((Double x, Double y) -> x.compareTo(y));
        return data.get(data.size() - 1);
    }

    public static double getMode(List<Double> data) {
        data.sort((Double x, Double y) -> x.compareTo(y));
        double mode = 0;
        double numberOfmodes = 0;
        int tmpCount = 0;
        int count = 0;
        int i;
        for (i = 0; i < data.size() - 1; i++) {
            count++;
            if (data.get(i).equals(data.get(i + 1))) {
                if (count > tmpCount) {
                    tmpCount = count;
                    mode = data.get(i);
                    numberOfmodes++;
                }
                count = 0;

            } else if (i == (data.size() - 2) && data.get(i) - data.get(i + 1) == 0.0 && count == tmpCount) {
                tmpCount = count;
                mode = data.get(i);
                numberOfmodes++;
            }

        }
        if (numberOfmodes != 1){
            return Double.NaN;
        }
        return mode;
    }
}


class WrongQuartileException extends Exception{}