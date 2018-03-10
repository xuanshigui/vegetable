package com.aquatic.service.preprocessing.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import com.aquatic.service.preprocessing.algorithm.KMeansClustering;
import com.aquatic.service.preprocessing.entity.Parameters;
import com.aquatic.service.preprocessing.entity.Sample;
import com.aquatic.utils.PathHelper;

public class ConstructTrainingset {
	
	public static void main(String[] args) throws IOException {
		//��ȡ������
		KMeansClustering kmc = new KMeansClustering();
		int centerNumber = 3;
		kmc.initDataSet();
		Map<Sample, List<Sample>> result = kmc.kcluster(centerNumber);
		//����ѵ�����Ͳ��Լ�
		List<List<Parameters>> trainingSet =new ArrayList<List<Parameters>>();
		List<List<Parameters>> testSet = new ArrayList<List<Parameters>>();
		converseToEntityList(result,trainingSet,testSet);
		int count = 0;
		for(List<Parameters> entityList:trainingSet){
			//������
			Preprocessing.export(entityList, PathHelper.getExamplePath() + "complete/training"+ count +".csv");
			count++;
		}
		count = 0;
		for(List<Parameters> entityList:testSet){
			//������
			Preprocessing.export(entityList, PathHelper.getExamplePath() + "complete/test"+ count +".csv");
			count++;
		}
	}


	public static List<Parameters> malpositionContact(
			List<Parameters> simplicatedList, int para, int position) {
		//�����и�
		List<List<Parameters>> dayList = cutByDay(simplicatedList);
		//����DayList,��ÿ������ݴ�һλ�и�
		List<Parameters> trainingSet = new ArrayList<>();
		for(List<Parameters> oneDay:dayList){
			for(int i=position;i<(oneDay.size()-position);i++){
				Parameters current = oneDay.get(i);
				List<Double> paraList = current.getParaList();
				paraList.add(oneDay.get(i+position).getParaList().get(para));
				current.setDate(oneDay.get(i+position).getDate());
				current.setTime(oneDay.get(i+position).getTime());
				trainingSet.add(current);
			}
		}
		return trainingSet;
	}


	private static List<List<Parameters>> cutByDay(
			List<Parameters> simplicatedList) {
		List<List<Parameters>> dayList = new ArrayList<>();
		int start = 0;
		int end = 0;
		for(int index=0;index<simplicatedList.size()-1;index++){
			List<Parameters> oneDay = new ArrayList<>();
        	end = index;
        	Parameters param = simplicatedList.get(index);
        	Parameters param_next = simplicatedList.get(index+1);
        	if(end!=(simplicatedList.size()-2)){
        		if(!param.getDate().equals(param_next.getDate())){
            		for(int j=start;j<=end;j++){
            			Parameters present = simplicatedList.get(j);
            			oneDay.add(present);
            		}
            		start = index+1;
            		dayList.add(oneDay);
            	}
        	}else{
        		for(int j=start;j<=end;j++){
        			Parameters present = simplicatedList.get(j);
        			oneDay.add(present);
        		}
        		dayList.add(oneDay);
        	}
		}
		return dayList;
	}

	private static List<Parameters> removeDublicate(List<Parameters> entityList) {
		// TODO Auto-generated method stub
		for(int i = 0;i<entityList.size();i++){
			for(int j = i+1;j<entityList.size();j++){
				if(entityList.get(i).equals(entityList.get(j))){
					entityList.remove(j);
				}
			}
		}
		return entityList;
	}


	public static void converseToEntityList(Map<Sample, List<Sample>> sampleMap, List<List<Parameters>> trainingSet, List<List<Parameters>> testSet){
		
		for(Entry<Sample, List<Sample>> entry : sampleMap.entrySet()){
			//��ת��ΪSampleList
			List<Sample> sampleList = new ArrayList<>();
			List<Parameters> paraList = new ArrayList<>();
			sampleList.add(entry.getKey());
			sampleList.addAll(entry.getValue());
			for(Sample sample:sampleList){
				List<Parameters> series = sample.getSeries();
				paraList.addAll(series);
			}
			//�õ�һ��Sample����Ӧ��EntityList
			//ȥ���ظ�Ԫ��
			List<Parameters> simplicatedList = removeDublicate(paraList);
			//��ʱ������
			Collections.sort(simplicatedList);
			List<Parameters> wholeList = ConstructTrainingset.malpositionContact(simplicatedList,1, 1);
			List<List<Parameters>> dayList = cutByDay(wholeList);
			List<Parameters> testOneDay = new ArrayList<>(); 
			List<Parameters> trainingOneDay = new ArrayList<>(); 
			for(List<Parameters> oneDay:dayList){
				testOneDay.add(oneDay.get(oneDay.size()-1));
				oneDay.remove(oneDay.get(oneDay.size()-1));
				trainingOneDay.addAll(oneDay);
			}
			testSet.add(testOneDay);
			trainingSet.add(trainingOneDay);
		}
	}

}
