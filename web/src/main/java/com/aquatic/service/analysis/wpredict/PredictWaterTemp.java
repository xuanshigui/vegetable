package com.aquatic.service.analysis.wpredict;

import com.aquatic.service.preprocessing.entity.Parameters;
import com.aquatic.service.preprocessing.process.TrainTestSplit;
import com.aquatic.utils.PathHelper;
import water_quality_predict.WaterQualityPredict;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PredictWaterTemp {

     //PathHelper.getResourcePath() + "fiveparam/cut"  存放已经切割好的文件的路径
     //setSerialNumber指的是数据集序号setSerialNumber<centerNumber
     //filePath:数据集所在文件夹
     //centerNumber聚类算法中心数
     //para所预测的参数
     //position错位结合的位数
    public double[] predictWaterTemp(int setSerialNumber,String filePath,int centerNumber,int para,int position){
        double[] result = null;
        try {
            Map<String,List<List<Parameters>>> setMap=TrainTestSplit.train_test_split(filePath,centerNumber,para,position);
            Double[][] trainingSet = this.getPredictFators(setMap,setSerialNumber,"trainingSet");
            Double[][] testSet = this.getPredictFators(setMap,setSerialNumber,"testSet");
            Double[] trainLabel = this.getLabels(setMap,setSerialNumber,"trainingSet");
            Double[] testLabel = this.getLabels(setMap,setSerialNumber,"testSet");
            WaterQualityPredict waterQualityPredict = new WaterQualityPredict();
            Object[] originResult = waterQualityPredict.svmpredict(1,trainingSet,trainLabel,testSet);
            System.out.println(originResult.toString());
        }catch (IOException e){
            System.out.println("--------------------------------------------");
            System.out.println("未找到文件");
        }catch (Exception e){
            System.out.println("--------------------------------------------");
            System.out.println("预测程序出现问题");
        }
        return result;
    }

    private Double[] getLabels(Map<String,List<List<Parameters>>> setMap,int setSerialNumber,String type){
        List<List<Parameters>> trainSets = setMap.get(type);
        List<Parameters> trainingSet = trainSets.get(setSerialNumber);
        Double[] result = new Double[trainingSet.size()];
        int i=0;
        for(Parameters parameters:trainingSet){
            List<Double> paraList = parameters.getParaList();
            result[i] = paraList.get(paraList.size()-1);
        }
        return result;
    }

    private Double[][] getPredictFators(Map<String,List<List<Parameters>>> setMap,int setSerialNumber,String type){
        Double[][] result = null;
        List<List<Parameters>> trainSets = setMap.get(type);
        List<Parameters> trainingSet = trainSets.get(setSerialNumber);
        //水温的预测使用 水温 大气温度 相对湿度 太阳辐射 大气压力 风速作为预测因子
        //以上预测因子所对应序号为0、5、6、7、8、9
        int row = trainingSet.size()-1;
        int col = 6;
        result = new Double[row][col];
        for(int i=0;i<row;i++){
            Parameters parameter = trainingSet.get(i);
            result[i][0] = parameter.getParaList().get(0);
            result[i][1] = parameter.getParaList().get(5);
            result[i][2] = parameter.getParaList().get(6);
            result[i][3] = parameter.getParaList().get(7);
            result[i][4] = parameter.getParaList().get(8);
            result[i][5] = parameter.getParaList().get(9);
        }
        return result;
    }

}
