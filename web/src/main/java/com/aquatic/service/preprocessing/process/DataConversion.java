package com.aquatic.service.preprocessing.process;

import java.util.ArrayList;
import java.util.List;

import com.aquatic.service.preprocessing.common.Constant;
import com.aquatic.service.preprocessing.entity.Parameters;
public class DataConversion {
	
	public static List<Parameters> converseByAverage(List<Parameters> entityList,int windowSize){
		List<Parameters> resultList = new ArrayList<>();
		int number = 0;
		for(int i=0;i<(entityList.size()-windowSize+1);i=(i+number)){
			number=windowSize;
			Parameters fusion = new Parameters();
			int numberOfParas = entityList.get(0).getNumberOfParas();
			double[] sum = new double[numberOfParas];
			for(int j=1;j<windowSize;j++){
				if(!entityList.get(i+j-1).getDate().equals(entityList.get(i+j).getDate())){
					number = j;
				}
			}
			for(int j=0;j<number;j++){
				Parameters current = entityList.get(i+j);
				List<Double> paras = current.getParaList();
				for(int k = 0;k<paras.size();k++){
					sum[k] = sum[k]+ paras.get(k);
				}
			}
			String time = entityList.get(i).getTime();
			String date = entityList.get(i).getDate();
			fusion.setDate(date);
			fusion.setTime(time);
			List<Double> parasList = new ArrayList<>();
			for(int j=0;j<numberOfParas;j++){
				parasList.add(sum[j]/number);
			}
			fusion.setParaList(parasList);
			resultList.add(fusion);
		}
		
		return resultList;
	}

}
