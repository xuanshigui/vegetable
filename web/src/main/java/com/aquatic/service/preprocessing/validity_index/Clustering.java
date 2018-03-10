package com.aquatic.service.preprocessing.validity_index;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.aquatic.service.preprocessing.entity.Sample;
import com.aquatic.service.preprocessing.utils.DynamicTimeWarping;
import com.aquatic.service.preprocessing.utils.EuclideanDistance;


public class Clustering {
	
	public static double daviesBouldinIndex(Map<Sample, List<Sample>> result){
		double dbi = 0;
		double sum = 0;
		for (Entry<Sample, List<Sample>> entry1 : result.entrySet()) {  
            Sample sample1 = entry1.getKey();
            double avg_i = 0;
            double avg_j = 0;
            double dcen = 0;
            double max = 0;
            //find max{(avg_i+avg_j)/dcen}
            for(Entry<Sample, List<Sample>> entry2 : result.entrySet()){
            	Sample sample2 = entry2.getKey();
            	if(sample2.compare(sample1)){
            		continue;
            	}else{
            		dcen = Clustering.distance(sample1, sample2, 0.5, 0.5);
                	avg_i = avgOfSamplesInOneCluster(entry1.getValue());
                	avg_j = avgOfSamplesInOneCluster(entry2.getValue());
                	double db = (avg_i+avg_j)/dcen;
                	if(db>max){
                		max = db;
                	}
            	}
            }
            sum = sum + max;
        }
		dbi = sum/result.size();
		return dbi;
	}
	
	public static double avgOfSamplesInOneCluster(List<Sample> list){
		double avg = 0;
		List<Double> distanceList = new ArrayList<Double>();
		for(int i=0;i<(list.size()-1);i++){
			Sample sample1 = list.get(i);
			for(int j=i+1;j<list.size();j++){
				Sample sample2 = list.get(j);
				double distance = Clustering.distance(sample1, sample2, 0.5,0.5);
				distanceList.add(distance);
			}
		}
		double sum = 0;
		for(Double distance:distanceList){
			sum = sum + distance;
		}
		avg=2*sum/(list.size()*(list.size()-1));
		return avg;
	}
	
	public static double distance(Sample sample1, Sample sample2,double alpha,double beta) {  
        double distance = Double.MAX_VALUE;
        double euclideanDistance = EuclideanDistance.getEuclideanDistance(sample1, sample2);
        double dtwDistance = DynamicTimeWarping.dtwDistance(sample1, sample2);
        distance = alpha*euclideanDistance+beta*dtwDistance;
        return distance;  
    }  

	public static void display(Map<Sample, List<Sample>> result){
		for (Entry<Sample, List<Sample>> entry : result.entrySet()) {  
            System.out.println("===============�۴�����Ϊ��" + entry.getKey() + "================");
        }  
	}
}
