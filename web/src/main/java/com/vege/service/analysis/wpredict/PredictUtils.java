package com.vege.service.analysis.wpredict;

import com.vege.service.preprocessing.entity.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PredictUtils {
    public static List<Double> getTargetFactor(List<Parameters> list, int target) {
        List<Double> targetList = new ArrayList<>();
        for (Parameters para : list) {
            List<Double> paraList = para.getParaList();
            targetList.add(paraList.get(target));
        }
        return targetList;
    }

    public List<Parameters> getTestSet(Map<String, List<List<Parameters>>> setMap, int centerNumber, String type) {
        List<Parameters> result = new ArrayList<>();
        List<List<Parameters>> testSets = setMap.get(type);
        for (int i = 0; i < centerNumber; i++) {
            List<Parameters> testSet = testSets.get(i);
            result.addAll(testSet);
        }
        return result;
    }

    public static double[] result2array(String predict_origin) {
        List<String> predictList = new ArrayList<String>();
        String[] predictArray = predict_origin.replaceAll("[^0-9.]+", ",").split(",");
        for (String s : predictArray) {
            if (s.length() > 0)
                predictList.add(s);
        }
        if (predictArray.length > 11) {
            predictList.remove(0);
            predictList.remove(0);
            predictList.remove(7);
            predictList.remove(7);
            if (predictArray.length > 18) {

            }
        }
        double[] predict = new double[predictList.size()];
        int i = 0;
        for (String p : predictList) {
            predict[i] = Double.parseDouble(p);
            i++;
        }
        return predict;
    }

    public static double[][] reConstructTrainingSet(double[][] testSet, double[][] trainingSet, Object[] originResult) {
        double[] cgp = new double[originResult.length];
        //参数
        for (int i = 0; i < originResult.length; i++) {
            cgp[i] = Double.parseDouble(originResult[i].toString().trim());
        }
        int rows = testSet.length + trainingSet.length;
        double[][] train = new double[rows + 1][testSet[0].length];
        for (int i = 0; i < testSet.length; i++) {
            train[i] = trainingSet[i];
        }
        for (int i = testSet.length; i < rows; i++) {
            train[i] = trainingSet[i - testSet.length];
        }
        for (int i = 0; i < originResult.length; i++) {
            train[rows][i] = cgp[i];
        }
        train[rows][3] = testSet.length;
        return train;
    }
}
