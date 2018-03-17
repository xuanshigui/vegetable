package com.aquatic.service.analysis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import com.aquatic.dao.PriceShuichanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import price_prediction.Predictor;

@Service("priceShuichanService")
public class PriceShuichanServiceImpl implements PriceShuichanService {

	final PriceShuichanDao priceShuichanDao;

	@Autowired
	public PriceShuichanServiceImpl(PriceShuichanDao priceShuichanDao) {
		this.priceShuichanDao = priceShuichanDao;
	}

	@Override
	public List<String> getNameList() {
		List<Object[]> objectList = priceShuichanDao.findNames();
		List<String> nameList = new ArrayList<>();
		for(Object o[]:objectList){
			String name = (String)o[0];
			nameList.add(name);
		}
		return nameList;
	}
	@Override
	public List<String> getTimeList() {
		List<Object> objectList = priceShuichanDao.findYears();
		List<String> dateList = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		int minYear =calendar.get(Calendar.YEAR);
		for(Object o:objectList){
			int year = (int) o;
			if(minYear>year){
				minYear = year;
			}
		}
		int currentYear = calendar.get(Calendar.YEAR);
		for(int i=minYear+5;i<currentYear;i++){
			dateList.add(i+"");
		}
		return dateList;
	}
	
	@Override
	public double[][] predict(String name, String year) {
		String[] pname = name.split("\\(");
		String aname = pname[0];
		int fromYear = Integer.parseInt(year)-4;
		int toYear = Integer.parseInt(year);
		PriceCollector pc = new PriceCollector();
		double[][] originMeanPrice = pc.getPrice(fromYear, toYear, aname, "");
		double[][] processed_result = new double[5][12];
		try {
			Predictor predictor = new Predictor();
	           Object[] result = null;
	           result = predictor.price_prediction(3, originMeanPrice);
	           String predict_origin = result[0].toString().trim();
	           double[] predictValue = result2array(predict_origin);
	           String jdwc_origin = result[1].toString().trim();
	           double[] jdwc = result2array(jdwc_origin);
	           String xdwc_origin = result[2].toString().trim();
	           double[] xdwc= result2array(xdwc_origin);
	           double rmse = Double.parseDouble(result[3].toString());
	           processed_result[0] = predictValue;
	           processed_result[1] = jdwc;
	           processed_result[2] = xdwc;
	           processed_result[3] = originMeanPrice[4];
	           processed_result[4][0] = rmse;
	       } catch (Exception e) {
	           System.out.println("Error!");
	       } 
		return processed_result;
	}
	private static double[] result2array(String predict_origin) {
		List<String> predictList = new ArrayList<String>();
		   for(String s:predict_origin.replaceAll("[^0-9.]+", ",").split(",")){
		       if (s.length()>0)
		    	   predictList.add(s);
		   }
		   predictList.remove(0);
		   predictList.remove(0);
		   predictList.remove(7);
		   predictList.remove(7);
		   double[] predict = new double[predictList.size()];
		   int i=0;
		   for(String p:predictList){
			   predict[i]=Double.parseDouble(p);
			   i++;
		   }
		return predict;
	}
	@Override
	public boolean isPredictable(String name, String year) {
		String[] pname = name.split("\\(");
		String aname = pname[0];
		boolean flag = true;
		int fromYear = Integer.parseInt(year)-4;
		int toYear = Integer.parseInt(year);
		PriceCollector pc = new PriceCollector();
		double[][] originMeanPrice = pc.getPrice(fromYear, toYear, aname, "");
		int count = 0;
		for(int i=0;i<originMeanPrice.length;i++){
			for(int j=0;j<originMeanPrice[0].length;j++){
				if(originMeanPrice[i][j]!=0){
					count++;
				}
			}
		}
		if(count<60){
			flag=false;
		}
		return flag;
	}
	@Override
	public List<String> doubleToString(double[] array) {
		List<String> result = new ArrayList<>();
		for(double dou:array){
			String item = String.format("%1$.2f", dou) + " ";
			result.add(item);
		}
		return result;
	}

	@Override
	public double[][] predict(List<String[]> priceList) {
		double[][] originMeanPrice = new double[5][12];
		double[][] processed_result = new double[5][12];
		for(int i=0;i<5;i++){
			String[] temp = priceList.get(i);
			for(int j=0;j<12;j++){
				originMeanPrice[i][j] = Double.parseDouble(temp[j]);
			}
		}
		try {
			Predictor predictor = new Predictor();
	           Object[] result = null;
	           result = predictor.price_prediction(4, originMeanPrice);
	           String predict_origin = result[0].toString().trim();
	           double[] predictValue = result2array(predict_origin);
	           String jdwc_origin = result[1].toString().trim();
	           double[] jdwc = result2array(jdwc_origin);
	           String xdwc_origin = result[2].toString().trim();
	           double[] xdwc= result2array(xdwc_origin);
	           double rmse = Double.parseDouble(result[3].toString());
	           processed_result[0] = predictValue;
	           processed_result[1] = jdwc;
	           processed_result[2] = xdwc;
	           processed_result[3] = processed_result[4];
	           processed_result[4][0] = rmse;
	       } catch (Exception e) {
	           System.out.println("Error!");
	       } 
		return processed_result;
	}
	
}
