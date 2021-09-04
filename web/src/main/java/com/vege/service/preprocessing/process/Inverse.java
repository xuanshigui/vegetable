package com.vege.service.preprocessing.process;

import java.util.List;

import com.vege.service.preprocessing.entity.Parameters;
import com.vege.utils.PathHelper;

public class Inverse {

    public static List<Double> resultInverse(List<Parameters> result,int target){
        //导入气象参数
        List<Parameters> entityList1 = Preprocessing.getEntityList(PathHelper.getExamplePath() + "atmosphere.csv");
        //导入水产数据
        List<Parameters> entityList2 = Preprocessing.preprocessWater();
        //数据融合
        List<Parameters> fusedList = Preprocessing.dataFusion(entityList1, entityList2);

        List<Double> inversedList = Normalization.inverse(fusedList, result, 1);
        return inversedList;
    }

}
