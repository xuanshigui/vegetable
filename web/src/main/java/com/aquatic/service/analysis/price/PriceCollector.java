package com.aquatic.service.analysis.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceCollector {

	private final CalcToMonth calcToMonth;

	@Autowired
	public PriceCollector(CalcToMonth calcToMonth) {
		this.calcToMonth = calcToMonth;
	}

	public double[][] getPrice(int fromYear, int toYear,String itemName,String classify){
		return calcToMonth.getPriceByYear(fromYear, toYear, itemName, classify);
	}
}
