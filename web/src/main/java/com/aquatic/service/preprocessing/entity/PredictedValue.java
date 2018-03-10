package com.aquatic.service.preprocessing.entity;

public class PredictedValue {
	
	//0-6
	int level;
	
	double predictedValue;
	
	double lowBound;
	
	double highBound;

	public double getPredictedValue() {
		return predictedValue;
	}

	public void setPredictedValue(double predictedValue) {
		this.predictedValue = predictedValue;
	}

	public double getLowBound() {
		return lowBound;
	}

	public void setLowBound(double lowBound) {
		this.lowBound = lowBound;
	}

	public double getHighBound() {
		return highBound;
	}

	public void setHighBound(double highBound) {
		this.highBound = highBound;
	}

}
