package com.aquatic.service.preprocessing.process;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import com.aquatic.service.preprocessing.algorithm.KMeansClustering;
import com.aquatic.service.preprocessing.entity.Parameters;
import com.aquatic.service.preprocessing.entity.Sample;
import com.aquatic.utils.PathHelper;

public class TrainTestSplit {

    //filePath:数据集所在文件夹
    //centerNumber聚类算法中心数
    //para所预测的参数
    //position错位结合的位数
    public static Map<String,List<List<Parameters>>> train_test_split(String filePath,int centerNumber,int para, int position) throws IOException {

        KMeansClustering kmc = new KMeansClustering();
        kmc.initDataSet("E:/prediction/fiveparam/cut");
        Map<Sample, List<Sample>> result = kmc.kcluster(centerNumber);

        List<List<Parameters>> trainingSet = new ArrayList<List<Parameters>>();
        List<List<Parameters>> testSet = new ArrayList<List<Parameters>>();
        converseToEntityList(result, trainingSet, testSet,para,position);
        Map<String,List<List<Parameters>>> setMap = new HashMap<>();
        setMap.put("trainingSet",trainingSet);
        setMap.put("testSet",testSet);
        return setMap;
    }


    private static List<Parameters> malpositionContact(
            List<Parameters> simplicatedList, int para, int position) {

        List<List<Parameters>> dayList = cutByDay(simplicatedList);

        List<Parameters> trainingSet = new ArrayList<>();
        for (List<Parameters> oneDay : dayList) {
            for (int i = position; i < (oneDay.size() - position); i++) {
                Parameters current = oneDay.get(i);
                List<Double> paraList = current.getParaList();
                paraList.add(oneDay.get(i + position).getParaList().get(para));
                current.setDate(oneDay.get(i + position).getDate());
                current.setTime(oneDay.get(i + position).getTime());
                trainingSet.add(current);
            }
        }
        return trainingSet;
    }

    private static List<List<Parameters>> cutByDay(
            List<Parameters> simplicatedList) {
        List<List<Parameters>> dayList = new ArrayList<>();
        int start = 0;
        int end;
        for (int index = 0; index < simplicatedList.size() - 1; index++) {
            List<Parameters> oneDay = new ArrayList<>();
            end = index;
            Parameters param = simplicatedList.get(index);
            Parameters param_next = simplicatedList.get(index + 1);
            if (end != (simplicatedList.size() - 2)) {
                if (!param.getDate().equals(param_next.getDate())) {
                    for (int j = start; j <= end; j++) {
                        Parameters present = simplicatedList.get(j);
                        oneDay.add(present);
                    }
                    start = index + 1;
                    dayList.add(oneDay);
                }
            } else {
                for (int j = start; j <= end; j++) {
                    Parameters present = simplicatedList.get(j);
                    oneDay.add(present);
                }
                dayList.add(oneDay);
            }
        }
        return dayList;
    }

    private static List<Parameters> removeDublicate(List<Parameters> entityList) {
        // TODO Auto-generated method stub
        for (int i = 0; i < entityList.size(); i++) {
            for (int j = i + 1; j < entityList.size(); j++) {
                if (entityList.get(i).equals(entityList.get(j))) {
                    entityList.remove(j);
                }
            }
        }
        return entityList;
    }


    public static void converseToEntityList(Map<Sample, List<Sample>> sampleMap, List<List<Parameters>> trainingSet, List<List<Parameters>> testSet,int para, int position) {

        for (Entry<Sample, List<Sample>> entry : sampleMap.entrySet()) {
            //鍏堣浆鎹负SampleList
            List<Sample> sampleList = new ArrayList<>();
            List<Parameters> paraList = new ArrayList<>();
            sampleList.add(entry.getKey());
            sampleList.addAll(entry.getValue());
            for (Sample sample : sampleList) {
                List<Parameters> series = sample.getSeries();
                paraList.addAll(series);
            }

            List<Parameters> simplicatedList = removeDublicate(paraList);

            Collections.sort(simplicatedList);
            List<Parameters> wholeList = TrainTestSplit.malpositionContact(simplicatedList,para,position);
            List<List<Parameters>> dayList = cutByDay(wholeList);
            List<Parameters> testOneDay = new ArrayList<>();
            List<Parameters> trainingOneDay = new ArrayList<>();
            for (List<Parameters> oneDay : dayList) {
                testOneDay.add(oneDay.get(oneDay.size() - 1));
                oneDay.remove(oneDay.get(oneDay.size() - 1));
                trainingOneDay.addAll(oneDay);
            }
            testSet.add(testOneDay);
            trainingSet.add(trainingOneDay);
        }
    }

}
