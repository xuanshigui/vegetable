package com.aquatic.service.analysis;

import org.springframework.beans.factory.annotation.Autowired;

public class PriceCollector {

	@Autowired
	private  CalcToMonth calcToMonth;

	public double[][] getPrice(int fromYear, int toYear,String itemName,String classify){
		CalcToMonth calcToMonth = new CalcToMonth();
		return calcToMonth.getPriceByYear(fromYear, toYear, itemName, classify);
	}
}
