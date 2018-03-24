package com.aquatic.service.analysis;

import java.util.List;


public interface PriceShuichanService {

    public List<String> getNameList();

    public List<String> getTimeList();

    public boolean isPredictable(String name, String year);

    public double[][] predict(String name, String year);

    public List<String> doubleToString(double[] array);

    public double[][] predict(List<String[]> priceList);
}
