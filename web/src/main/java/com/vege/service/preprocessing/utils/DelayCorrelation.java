package com.vege.service.preprocessing.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.vege.service.preprocessing.entity.Parameters;
import com.vege.service.preprocessing.process.Preprocessing;
import com.vege.utils.PathHelper;

public class DelayCorrelation {

    public static double[] getDelayCorrelationCoeff(List<Double> list1, List<Double> list2) {

        int length1 = list1.size();
        int length2 = list2.size();
        double[] series1 = DataUtils.listToArray(list1);
        double[] series2 = DataUtils.listToArray(list2);
        int lag = (int) length1 / 2;
        double[] cor = new double[lag];
        if (length1 != length2) {
            System.out.println("You must use two series with the same length!");
        } else {
            for (int index = 0; index < lag; index++) {
                double[] segment1 = Arrays.copyOfRange(series1, 0, (length1 - index));
                double[] segment2 = Arrays.copyOfRange(series2, index, length1);
                cor[index] = getDeCorreCoeff(segment1, segment2);
            }
        }
        return cor;
    }

    public static Map<Integer, Double> getDelayCorrelationCoeffMap(List<Double> list1, List<Double> list2) {
        Map<Integer, Double> delayCorrelationMap = new HashMap<>();
        double[] cor = getDelayCorrelationCoeff(list1, list2);
        for (int i = 0; i < cor.length; i++) {
            delayCorrelationMap.put(i, cor[i]);
        }
        return delayCorrelationMap;
    }

    public static Map<Integer, Double> getDelayCorrelationCoeffMap(double[] series1, double[] series2) {
        Map<Integer, Double> delayCorrelationMap = new HashMap<>();
        double[] cor = getDelayCorrelationCoeff(DataUtils.arrayToList(series1), DataUtils.arrayToList(series2));
        for (int i = 0; i < cor.length; i++) {
            delayCorrelationMap.put(i, cor[i]);
        }
        return delayCorrelationMap;
    }

    private static double getDeCorreCoeff(double[] segment1, double[] segment2) {
        double avg_seg1 = DataUtils.getMean(segment1);
        double avg_seg2 = DataUtils.getMean(segment2);
        //sx=sqrt(sum((ab-avex).^2))
        double sx = DataUtils.getStandardDeviation(segment1);
        double sy = DataUtils.getStandardDeviation(segment2);
        int length = segment1.length;
        double sum = 0;
        for (int index = 0; index < length; index++) {
            sum = sum + (segment1[index] - avg_seg1) * (segment2[index] - avg_seg2);
        }
        if (sum == 0) {
            if (sx == 0 && sy == 0) {
                return 1;
            } else {
                return 0;
            }
        }
        return sum / (sy * sx);
    }

    public static void main(String[] args) {
        String filePath = PathHelper.getExamplePath() + "fiveparam" + PathHelper.SEPARATOR + "cut";
        String fileName = filePath + PathHelper.SEPARATOR + "fiveparam" + 0 + ".csv";
        List<Parameters> entityList = Preprocessing.getEntityList(fileName);
        int length = entityList.size();
        double[] dissovledOxygen = new double[length];
        double[] waterTemp = new double[length];
        Map<Integer, Double> delayCor = DelayCorrelation.getDelayCorrelationCoeffMap(waterTemp, dissovledOxygen);
        Set<Entry<Integer, Double>> entrySet = delayCor.entrySet();
        for (Entry<Integer, Double> entry : entrySet) {
            int key = entry.getKey();
            double value = entry.getValue();
            System.out.println(key + ":" + value);
        }
    }
}
