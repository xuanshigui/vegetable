package com.aquatic.service.preprocessing.process;

import java.util.List;

import com.aquatic.service.preprocessing.entity.Parameters;
import com.aquatic.utils.PathHelper;

public class ReverseTest {

    public static void main(String[] args) {
        List<Parameters> entityList1 = Preprocessing.getEntityList(PathHelper.getExamplePath() + "atmosphere.csv");
        //ˮ������Ԥ����
        List<Parameters> entityList2 = Preprocessing.preprocessWater();
        List<Parameters> normalizedList = Preprocessing.getEntityList(PathHelper.getExamplePath() + "complete/test1.csv");

        //�����ں�
        List<Parameters> fusedList = Preprocessing.dataFusion(entityList1, entityList2);
        //Preprocessing.export(fusedList, "E:/prediction/complete/origin.csv");
        double[] result = {0.217915995557216, 0.194729933910402, 0.200743388118601, 0.200739900013770, 0.200647087176039};
        List<Double> inversedList = Normalization.inverse(fusedList, result, 1);
        //List<Double> inversedList = Normalization.inverse(fusedList,normalizedList,1);
        for (Double data : inversedList) {
            System.out.println(data);
        }
    }

}
