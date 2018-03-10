package com.aquatic.service.preprocessing.process;

import java.util.List;

import com.aquatic.service.preprocessing.utils.DataUtils;

public class FillUp {
	
	public static List<Double> fillUpByMeanValue(List<Double> dataArrayList){
		double mean = DataUtils.getMean(dataArrayList);
		for(int i=0;i<dataArrayList.size();i++){
			if(dataArrayList.get(i).isNaN()){
				dataArrayList.set(i, mean);
			}
		}
		return dataArrayList;
	}

}
