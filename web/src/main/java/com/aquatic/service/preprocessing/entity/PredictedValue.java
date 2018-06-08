package com.aquatic.service.preprocessing.entity;

public class PredictedValue {

	//0-6
	private int level;

	private double predictedValue;

	private double lowBound;

	private double highBound;

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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
