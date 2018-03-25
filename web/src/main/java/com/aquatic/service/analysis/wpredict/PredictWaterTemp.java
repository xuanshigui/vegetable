package com.aquatic.service.analysis.wpredict;

import com.aquatic.service.preprocessing.entity.Parameters;
import com.aquatic.service.preprocessing.process.Normalization;
import com.aquatic.service.preprocessing.process.Preprocessing;
import com.aquatic.service.preprocessing.process.TrainTestSplit;
import com.aquatic.service.preprocessing.utils.DataUtils;
import water_para_predict.WaterQualityPredict;
import water_quality_svr_train.WaterQualitySVMTrain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PredictWaterTemp {
    public static List<Double> predict(String path){
        List<Double> inversedList = null;
        PredictUtils tp = new PredictUtils();
        PredictWaterTemp pwt = new PredictWaterTemp();
        Map<String, List<List<Parameters>>> setMap;
        try {
            setMap = TrainTestSplit.train_test_split(path,3,0,1);
            List<Parameters> testSet = tp.getTestSet(setMap,3,"testSet");
            List<Double> targetFactor =tp.getTargetFactor(testSet, 0);
            List<Double> result = new ArrayList<>();
            for(int i=0;i<3;i++){
                List<Double> predict = pwt.predictWaterTemp(i,setMap);
                result.addAll(predict);
            }
            double[] normalizedArray = DataUtils.listToArray(result);
            List<Parameters> entityList1 = Preprocessing.getEntityList("E:/prediction/atmosphere.csv");
            //水质数据预处理
            List<Parameters> entityList2 = Preprocessing.preprocessWater();
            //数据融合，得到原始数据集，即可还原序列
            List<Parameters> fusedList = Preprocessing.dataFusion(entityList1,entityList2);
            inversedList = Normalization.inverse(fusedList, normalizedArray, 0);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inversedList;
    }

    public List<Double> predictWaterTemp(int setSerialNumber,Map<String,List<List<Parameters>>> setMap){
        List<Double> result;
        double[] resultArray = null;
        try {
            double[][] trainingSet = this.getPredictFators(setMap,setSerialNumber,"trainingSet");
            double[][] testSet = this.getPredictFators(setMap,setSerialNumber,"testSet");

            WaterQualitySVMTrain wqsvmt = new WaterQualitySVMTrain();
            Object[] originResult = wqsvmt.water_para_train(3, trainingSet);
            System.out.println("=========================寻参完成==============================");

            double[][] train = this.reConstructTrainingSet(testSet, trainingSet, originResult);
            WaterQualityPredict wqp = new WaterQualityPredict();
            Object[] predict = wqp.water_para_model(1, train);
            resultArray = PredictUtils.result2array(predict[0].toString());
            result = DataUtils.arrayToList(resultArray);
        }catch (Exception e){
            System.out.println("--------------------------------------------");
            System.out.println("预测出现问题！");
            e.printStackTrace();
        }
        result = DataUtils.arrayToList(resultArray);
        return result;
    }

/*    private double[] getLabels(Map<String,List<List<Parameters>>> setMap,int setSerialNumber,String type){
        List<List<Parameters>> trainSets = setMap.get(type);
        List<Parameters> trainingSet = trainSets.get(setSerialNumber);
        double[] result = new double[trainingSet.size()];
        int i=0;
        for(Parameters parameters:trainingSet){
            List<Double> paraList = parameters.getParaList();
            result[i] = paraList.get(12);
            i++;
        }
        return result;
    }*/

    private double[][] reConstructTrainingSet(double[][] testSet,double[][] trainingSet,Object[] originResult){
        double[] cgp = new double[originResult.length];
        //参数
        for(int i=0;i<originResult.length;i++){
            cgp[i] = Double.parseDouble(originResult[i].toString().trim());
        }
        int rows= testSet.length+trainingSet.length;
        double[][] train = new double[rows+1][testSet[0].length];
        for(int i=0;i<testSet.length;i++){
            train[i] = trainingSet[i];
        }
        for(int i=testSet.length;i<rows;i++){
            train[i] = trainingSet[i-testSet.length];
        }
        for(int i=0;i<originResult.length;i++){
            train[rows][i] = cgp[i];
        }
        train[rows][3]= testSet.length;
        return train;
    }

    private double[][] getPredictFators(Map<String,List<List<Parameters>>> setMap,int setSerialNumber,String type){
        double[][] result = null;
        List<List<Parameters>> trainSets = setMap.get(type);
        List<Parameters> trainingSet = trainSets.get(setSerialNumber);
        //水温的预测使用 水温 大气温度 相对湿度 太阳辐射 大气压力 风速作为预测因子
        //以上预测因子所对应序号为0、5、6、7、8、10
        int row = trainingSet.size();
        int col = 7;
        result = new double[row][col];
        for(int i=0;i<row;i++){
            Parameters parameter = trainingSet.get(i);
            result[i][0] = parameter.getParaList().get(0);//水温
            result[i][1] = parameter.getParaList().get(5);//相对湿度
            result[i][2] = parameter.getParaList().get(6);//大气温度
            result[i][3] = parameter.getParaList().get(7);//大气压力
            result[i][4] = parameter.getParaList().get(8);//风速
            result[i][5] = parameter.getParaList().get(10);//太阳辐射
            result[i][6] = parameter.getParaList().get(12);
        }
        return result;
    }
}
