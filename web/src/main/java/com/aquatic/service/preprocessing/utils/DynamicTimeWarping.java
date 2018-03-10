package com.aquatic.service.preprocessing.utils;

import java.util.ArrayList;
import java.util.List;

import com.aquatic.service.preprocessing.entity.Sample;

public class DynamicTimeWarping {

    public static double dtwDistance(double[] series1, double[] series2) {
        //��Ž��
        double distance = 0;
        //���г���
        int seriesLength1 = series1.length;
        int seriesLength2 = series2.length;
        //֡ƥ��������
        double[][] distanceForFrame = calDistanceForFrame(series1, series2);
        //������۾���
        double[][] distanceAccumulateMatrix = new double[seriesLength1][seriesLength2];
        //��ʼ��
        for (int i = 0; i < seriesLength1; i++) {
            for (int j = 0; j < seriesLength1; j++) {
                distanceAccumulateMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        distanceAccumulateMatrix[0][0] = distanceForFrame[0][0];
        //��̬�滮
        for (int i = 1; i < seriesLength1; i++) {
            for (int j = 0; j < seriesLength1; j++) {
                double[] dis = new double[3];
                //D1 = D(i-1,j);
                dis[0] = distanceAccumulateMatrix[i - 1][j];
                if (j > 0) {
                    dis[1] = distanceAccumulateMatrix[i - 1][j - 1];
                } else {
                    dis[1] = Double.POSITIVE_INFINITY;
                }
                if (j > 1) {
                    dis[2] = distanceAccumulateMatrix[i - 1][j - 2];
                } else {
                    dis[2] = Double.POSITIVE_INFINITY;
                }
                //D(i,j) = d(i,j) + min([D1,D2,D3]);
                distanceAccumulateMatrix[i][j] = distanceForFrame[i][j] + DataUtils.getMinimum(dis);
            }
        }
        distance = distanceAccumulateMatrix[seriesLength1 - 1][seriesLength2 - 1];
        return distance;
    }

    public static double[][] calDistanceForFrame(double[] series1, double[] series2) {
        //���г���
        int seriesLength1 = series1.length;
        int seriesLength2 = series2.length;
        double[][] result = new double[seriesLength1][seriesLength2];
        //i���ƶ���¼����1
        for (int i = 0; i < seriesLength1; i++) {
            //j���ƶ���¼����2
            for (int j = 0; j < seriesLength2; j++) {
                double difference = 0;
                double square = 0;
                difference = series1[i] - series2[j];
                square = difference * difference;
                result[i][j] = square;
            }
        }
        return result;
    }

    public static double dtwDistance(Sample sample1, Sample sample2) {
        double distance = 0.0;
        double[] distanceArray = new double[5];
        for (int i = 0; i < 5; i++) {
            double[] series1 = sample1.listToArray(i);
            double[] series2 = sample2.listToArray(i);
            distanceArray[i] = dtwDistance(series1, series2);
        }
        for (double dis : distanceArray) {
            distance = distance + dis;
        }
        return distance;
    }
}
