package com.aquatic.service.preprocessing.entity;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Parameters implements Comparable<Parameters>{
	
	List<Double> paraList;
	
	int numberOfParas;
	
	String date;
	String time;
	
	public Parameters(List<Double> paraList, String date, String time) {
		super();
		this.paraList = paraList;
		this.numberOfParas = paraList.size();
		this.date = date;
		this.time = time;
	}
	public List<Double> getParaList() {
		return paraList;
	}
	public void setParaList(List<Double> paraList) {
		this.paraList = paraList;
		this.numberOfParas = paraList.size();
	}
	public int getNumberOfParas() {
		return numberOfParas;
	}
	public void setNumberOfParas(int numberOfParas) {
		this.numberOfParas = numberOfParas;
	}
	public Parameters() {
		// TODO Auto-generated constructor stub
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public boolean equals(Parameters p){
		if(this.getDate().equals(p.getDate())&&this.getTime().equals(p.getTime())){
			return true;
		}else{
			return false;
		}
	}
	public String display() {
		String parameters = this.date +" " + this.time;
		for(Double param:paraList){
			parameters = parameters + "," + param.toString();
		}
		return parameters;
	}
	@Override
	public String toString() {
		String parameters = "";
		for(Double param:paraList){
			if("".equals(parameters)){
				parameters = param.toString();
			}else{
				parameters = parameters + "," + param.toString();
			}
		}
		return parameters;
	}
	@Override
	public int compareTo(Parameters paras) {
		long dev = -2;
		try {
			SimpleDateFormat originFmt = new SimpleDateFormat("yyyy/M/dd H:mm");
			SimpleDateFormat newFmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String time1 = paras.getDate() + " " + paras.getTime();
			Date date1 = originFmt.parse(time1);
			Timestamp timestamp1 = Timestamp.valueOf(newFmt.format(date1));
			String time2 = this.getDate() + " " + this.getTime();
			Date date2 = originFmt.parse(time2);
			Timestamp timestamp2 = Timestamp.valueOf(newFmt.format(date2));
			dev = timestamp1.getTime() - timestamp2.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dev==0){
			return 0;
		}else if(dev>0){
			return -1;
		}else{
			return 1;
		}
		
	}
}
