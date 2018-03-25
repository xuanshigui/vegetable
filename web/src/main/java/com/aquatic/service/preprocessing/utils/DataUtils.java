package com.aquatic.service.preprocessing.utils;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {

    public static double getMinimum(double[] series){
        double minimum = Double.POSITIVE_INFINITY;
        for(double current:series){
            if(current<minimum){
                minimum = current;
            }
        }
        return minimum;
    }

    public static double getMinimum(List<Double> series){
        double minimum = Double.POSITIVE_INFINITY;
        for(double current:series){
            if(current<minimum){
                minimum = current;
            }
        }
        return minimum;
    }

    public static double getMaximum(List<Double> series){
        double maximum = 0.0;
        for(double current:series){
            if(current>maximum){
                maximum = current;
            }
        }
        return maximum;
    }

    public static double getMaximum(double[] series){
        double maximum = 0.0;
        for(double current:series){
            if(current>maximum){
                maximum = current;
            }
        }
        return maximum;
    }

    public static double getMean(List<Double> series){
        double sum = 0.0;
        int count = 0;
        for(Double current:series){
            if(!current.isNaN()){
                sum = sum+current;
                count++;
            }
        }
        return sum/count;
    }

    public static double getMean(double[] series){
        double sum = 0.0;
        int count = 0;
        for(Double current:series){
            if(!current.isNaN()){
                sum = sum+current;
                count++;
            }
        }
        return sum/count;
    }

    public static double getStandardDeviation(double[] series){
        double sum = 0;
        double avg = getMean(series);
        for(double element:series){
            sum = sum + (element-avg)*(element-avg);
        }
        return Math.sqrt(sum);
    }

    public static double[] listToArray(List<Double> list){
        double[] array = new double[list.size()];
        int count = 0;
        for(Double element:list){
            array[count++] = element;
        }
        return array;
    }
    public static List<Double> arrayToList(double[] array){
        List<Double> list = new ArrayList<>();
        for(double element:array){
            list.add(element);
        }
        return list;
    }

    //计算变异系数
    public static double getVariantCoefficient(double[] series){
        double standardDeviation = getStandardDeviation(series);
        double mean = getMean(series);
        return standardDeviation/mean;
    }
    //计算均方根误差
    public static double getRMSE(List<Double> list1,List<Double> list2){
        int size = list1.size();
        double rmse = 0;
        if(size!=list2.size()){
            return Double.NaN;
        }else{
            double sum = 0;
            for(int i=0;i<size;i++){
                double tmp = list1.get(i)-list2.get(i);
                sum = sum + tmp*tmp;
            }
            double mse = sum/size;
            rmse = Math.sqrt(mse);
        }
        return rmse;
    }
    //计算平均绝对百分比误差
    public static double getMAPE(List<Double> list1,List<Double> list2){
        int size = list1.size();
        double mape = 0;
        if(size!=list2.size()){
            return Double.NaN;
        }else{
            double sum = 0;
            for(int i=0;i<size;i++){
                double tmp = Math.abs(list1.get(i)-list2.get(i))/list1.get(i);
                sum = sum + tmp*100;
            }
            mape=sum/size;
        }
        return mape;
    }
    //计算平均绝对误差
    public static double getMAE(List<Double> list1,List<Double> list2){
        int size = list1.size();
        double mae = 0;
        if(size!=list2.size()){
            return Double.NaN;
        }else{
            double sum = 0;
            for(int i=0;i<size;i++){
                double tmp = Math.abs(list1.get(i)-list2.get(i));
                sum = sum + tmp;
            }
            mae=sum/size;
        }
        return mae;
    }

}
