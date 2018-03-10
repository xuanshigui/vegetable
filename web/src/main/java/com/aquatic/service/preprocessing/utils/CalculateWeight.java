package com.aquatic.service.preprocessing.utils;

import java.util.ArrayList;
import java.util.List;
import com.aquatic.service.preprocessing.common.Constant;
import com.aquatic.service.preprocessing.entity.PredictedValue;
import com.aquatic.service.preprocessing.entity.Sample;

public class CalculateWeight {
	
	private List<Double> criticalDistanceWeight = new ArrayList<Double>();
	
	private List<Double> variableCoeffcientWeight = new ArrayList<Double>();
	
	//�������ϵ��Ȩֵ
	public void calVariableCoeffcientWeight(Sample sampleSet){
		double sum = 0;
		int arrayLength = sampleSet.getSeries().get(0).getNumberOfParas();
		double[] variableCoefficient = new double[arrayLength];
		for(int i=0;i<arrayLength;i++){
			double[] series = sampleSet.listToArray(i);
			variableCoefficient[i] = DataUtils.getVariantCoefficient(series);
			sum = sum + variableCoefficient[i];
		}
		for(int i = 0;i<variableCoefficient.length;i++){
			double weight = variableCoefficient[i]/sum;
			criticalDistanceWeight.add(i, weight);
		}
	}
	
	//�����ٽ���Ծ���Ȩֵ
	public void calCriRelDisWeight(List<PredictedValue> predictedValueList){
		double[] criticalRelatedDistance = new double[predictedValueList.size()];
		int count = 0;
		double sum  = 0;
		for(PredictedValue pv:predictedValueList){
			criticalRelatedDistance[count] = getCriticalRelatedDistance(pv.getLowBound(), pv.getHighBound(), pv.getPredictedValue());
			sum = sum+criticalRelatedDistance[count];
			count++;
		}
		for(int i = 0;i<criticalRelatedDistance.length;i++){
			double weight = criticalRelatedDistance[i]/sum;
			criticalDistanceWeight.add(i, weight);
		}
	}
	
	//�����ٽ���Ծ���
	private double getCriticalRelatedDistance(double lowBound, double highBound,
			double predictValue) {
		double criticalRelatedDistance=0;
		double region = 0;
		if(highBound>lowBound){
			region = highBound-lowBound;
		}else{
			region = lowBound-highBound;
		}
		criticalRelatedDistance = (region-predictValue)/region;
		return criticalRelatedDistance;
	}

	public List<Double> getCriticalDistanceWeight() {
		return criticalDistanceWeight;
	}

	public void setCriticalDistanceWeight(List<Double> criticalDistanceWeight) {
		this.criticalDistanceWeight = criticalDistanceWeight;
	}

	public List<Double> getVariableCoeffcientWeight() {
		return variableCoeffcientWeight;
	}

	public void setVariableCoeffcientWeight(List<Double> variableCoeffcientWeight) {
		this.variableCoeffcientWeight = variableCoeffcientWeight;
	}

}
