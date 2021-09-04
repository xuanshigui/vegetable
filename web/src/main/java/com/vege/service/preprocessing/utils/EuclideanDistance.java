package com.vege.service.preprocessing.utils;

import com.vege.service.preprocessing.entity.Sample;

public class EuclideanDistance {

    public static double getEuclideanDistance(double[] series1, double[] series2) {
        double distance = Double.POSITIVE_INFINITY;
        int seriesLength1 = series1.length;
        int seriesLength2 = series2.length;
        if (seriesLength1 < seriesLength2) {
            seriesLength2 = seriesLength1;
        } else {
            seriesLength1 = seriesLength2;
        }
        double sum = 0;
        for (int i = 0; i < seriesLength1; i++) {
            double difference = 0;
            double square = 0;
            difference = series1[i] - series2[i];
            square = difference * difference;
            sum = sum + square;
        }
        distance = Math.sqrt(sum);
        return distance;
    }

    public static double getEuclideanDistance(Sample sample1, Sample sample2) {
        double distance = 0.0;
        int arrayLength = sample1.getSeries().get(0).getNumberOfParas();
        double[] distanceArray = new double[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            double[] series1 = sample1.listToArray(i);
            double[] series2 = sample2.listToArray(i);
            distanceArray[i] = getEuclideanDistance(series1, series2);
        }
        for (double dis : distanceArray) {
            distance = distance + dis;
        }
        return distance;
    }

}
