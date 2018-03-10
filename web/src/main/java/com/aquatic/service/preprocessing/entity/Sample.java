package com.aquatic.service.preprocessing.entity;

import java.util.ArrayList;
import java.util.List;

public class Sample {
	
	//һ������������
	private List<Parameters> series;
	//һ�������еĲ�����������
	private int numberOfNodes;
    
	
	
	@Override
	public String toString() {
		String matrix="";
		for(Parameters para:series){
			String row = "["+ para.toString() +"]";
			matrix = matrix + row;
		}
		return matrix;
	}

	public void arrayToList(double[][] valueArray){
		int rows = valueArray.length;
		int cols = valueArray[0].length;
		this.numberOfNodes = cols;
		for(int i=0;i<cols;i++){
			Parameters para = new Parameters();
			List<Double> paraList = new ArrayList<>();
			for(int j=0;j<rows;j++){
				paraList.add(valueArray[j][i]);
			}
			para.setParaList(paraList);
			this.series.add(para);
    	}  
	}
	
	
	public void setListValue(double value,int row,int col){
		series.get(col).getParaList().set(row, value);
	}
	
	public Double getListValue(int row,int col){
		return series.get(col).getParaList().get(row);
	}
	
	public Sample() {
		series = new ArrayList<>();
		numberOfNodes = 0;
	}

	public List<Parameters> getSeries() {
		return series;
	}

	public void setSeries(List<Parameters> series) {
		this.series = series;
	}

	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	public void setNumberOfNodes(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}

	public boolean compare(Sample s){
		boolean equal = false;
		int count = 0;
		if(this.numberOfNodes==s.numberOfNodes){
			List<Parameters> paraList = s.getSeries();
			int index = 0;
			for(Parameters para:paraList){
				if(para.equals(this.series.get(index++))){
					count++;
				}
			}
		}
		if(count==s.numberOfNodes){
			equal = true;
		}
		return equal;
	}

	public void setTime(Sample sample, Sample time) {
		List<Parameters> origin = sample.getSeries();
		List<Parameters> timeList = time.getSeries();
		int count = 0;
		for(Parameters param:origin){
			Parameters current = timeList.get(count);
			param.setDate(current.getDate());
			param.setTime(current.getTime());
			count++;
		}
	}

	// ȡĳ����������
	public double[] listToArray(int i) {
		double[] paraArray = new double[series.size()];
		int count=0;
		for(Parameters para:series){
			paraArray[count++] = para.getParaList().get(i);
		}
		return paraArray;
	}
}
