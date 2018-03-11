package com.aquatic.service.analysis;

public class PriceCollector {
	
	public double[][] getPrice(int fromYear, int toYear,String itemName,String classify){
		CalcToMonth calcToMonth = new CalcToMonth();
		double[][] origin_price = calcToMonth.getPriceByYear(fromYear, toYear, itemName, classify);
		return origin_price;
	}
}
