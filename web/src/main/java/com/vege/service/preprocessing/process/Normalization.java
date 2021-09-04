package com.vege.service.preprocessing.process;

import java.util.ArrayList;
import java.util.List;

import com.vege.service.preprocessing.entity.Parameters;
import com.vege.service.preprocessing.utils.DataUtils;

public class Normalization {

    public static List<Parameters> normalization(List<Parameters> list) {
        List<Parameters> normalizedList = list;
        double[][] originArray = listToArray(normalizedList);
        int row = originArray.length;
        int col = originArray[0].length;
        double[][] normalizedArray = new double[row][col];
        for (int i = 0; i < row; i++) {
            double Max = DataUtils.getMaximum(originArray[i]);
            double Min = DataUtils.getMinimum(originArray[i]);
            double denominator = Max - Min;
            for (int j = 0; j < col; j++) {
                double member = originArray[i][j] - Min;
                normalizedArray[i][j] = member / denominator;
                setListValue(normalizedList, normalizedArray[i][j], i, j);
            }
        }
        return normalizedList;
    }

    public static List<Double> inverse(List<Parameters> originList, List<Parameters> normalizedList, int target) {
        List<Double> inversedList = new ArrayList<>();
        double[][] originArray = listToArray(originList);
        double[][] normalizedArray = listToArray(normalizedList);
        double max = DataUtils.getMaximum(originArray[target]);
        double min = DataUtils.getMinimum(originArray[target]);
        double denominator = max - min;
        double[] inversedArray = new double[originArray[target].length];
        for (int i = 0; i < normalizedArray[target].length; i++) {
            inversedArray[i] = denominator * normalizedArray[target][i] + min;
            inversedList.add(inversedArray[i]);
        }
        return inversedList;
    }

    public static void setListValue(List<Parameters> list, double value, int row, int col) {
        list.get(col).getParaList().set(row, value);
    }

    public static double[][] listToArray(List<Parameters> list) {
        int rows = list.get(0).getParaList().size();
        int cols = list.size();
        double[][] array = new double[rows][cols];
        for (int i = 0; i < cols; i++) {
            Parameters parameters = list.get(i);
            List<Double> paraList = parameters.getParaList();
            for (int j = 0; j < rows; j++) {
                array[j][i] = paraList.get(j);
            }
        }
        return array;
    }

    public static List<Double> inverse(List<Parameters> originList,
                                       double[] normalizedArray, int target) {
        List<Double> inversedList = new ArrayList<>();
        double[][] originArray = listToArray(originList);
        double max = DataUtils.getMaximum(originArray[target]);
        double min = DataUtils.getMinimum(originArray[target]);
        double denominator = max - min;
        double[] inversedArray = new double[originArray[target].length];
        for (int i = 0; i < normalizedArray.length; i++) {
            inversedArray[i] = denominator * normalizedArray[i] + min;
            inversedList.add(inversedArray[i]);
        }
        return inversedList;
    }
}
