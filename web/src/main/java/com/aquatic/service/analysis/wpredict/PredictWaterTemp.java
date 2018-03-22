package com.aquatic.service.analysis.wpredict;

import com.aquatic.service.preprocessing.entity.Parameters;
import com.aquatic.service.preprocessing.process.TrainTestSplit;
import com.aquatic.utils.PathHelper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PredictWaterTemp {

     //PathHelper.getResourcePath() + "fiveparam/cut"  存放已经切割好的文件的路径
    public double[] predictWaterTemp(int setSerialNumber,String filePath,int centerNumber){
        Double[][] trainingSet = this.getPredictFators(setSerialNumber,"trainingSet",filePath,centerNumber);
        Double[][] testSet = this.getPredictFators(setSerialNumber,"testSet",filePath,centerNumber);

    }

    private Double[][] getPredictFators(int setSerialNumber,String type,String filePath,int centerNumber){
        Map<String,List<List<Parameters>>> setMap;
        Double[][] result = null;
        try {
            setMap = TrainTestSplit.train_test_split(filePath,centerNumber);
            List<List<Parameters>> trainSets = setMap.get(type);
            List<Parameters> trainingSet = trainSets.get(setSerialNumber);
            //水温的预测使用 水温 大气温度 相对湿度 太阳辐射 大气压力 风速作为预测因子
            //以上预测因子所对应序号为0、5、6、7、8、9
            int row = trainingSet.size();
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
        }catch (IOException e){
            System.out.println("未找到文件");
        }
        return result;
    }

}
