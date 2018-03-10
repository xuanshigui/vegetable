package com.aquatic.service.preprocessing.process;

import java.util.ArrayList;
import java.util.List;

import com.aquatic.service.preprocessing.utils.DataUtils;

public class AbnormalDetection {
	
	public static List<Double> abnormalDetect(List<Double> dataArrayList,double alpha,int windSize){
		//�ָʹ�ø�����˹׼����м��
		int numberOfSubSet = dataArrayList.size()-windSize+1;
		for(int i=0;i<numberOfSubSet;i++){
			List<Double> subList = new ArrayList<>();
			for(int j=0;j<windSize;j++){
				 subList.add(dataArrayList.get(i+j));
			}
			AbnormalDetection.grubbs(subList, alpha);
			for(int j=0;j<windSize;j++){
				 dataArrayList.set(i+j, subList.get(j));
			}
		}
		//ȥ��ͻ���0�Ϳ�ʼ��0
		removeZeros(dataArrayList);
		//ȥ��ͻ���ֵ
		removeSaltation(dataArrayList);
		return dataArrayList;
	}
	
	//ȥ��ͻ���ֵ
	public static List<Double> removeSaltation(List<Double> dataArrayList){
		for(int i=1;i<dataArrayList.size();i++){
			double difference = Math.abs((dataArrayList.get(i)-dataArrayList.get(i-1)));
			if((!dataArrayList.get(i).isNaN())&&(!dataArrayList.get(i-1).isNaN())){
				if(difference/dataArrayList.get(i-1)>0.2){
					dataArrayList.set(i, Double.NaN);
				}
			}
		}
		return dataArrayList;
	}
	
	//ȥ��ͻ���0����ʼ��0��������0
	public static List<Double> removeZeros(List<Double> dataArrayList){
		if(dataArrayList.get(0)==0){
			dataArrayList.set(0, Double.NaN);
		}
		for(int i=1;i<dataArrayList.size();i++){
			double min = DataUtils.getMaximum(dataArrayList);
			if(dataArrayList.get(i)==0){
				if((dataArrayList.get(i-1)-dataArrayList.get(i))>min){
					continue;
				}
				dataArrayList.set(i, Double.NaN);
			}
		}
		return dataArrayList;
	}
	
	//ȥ��ͻ������ֵ�Ϳ�ʼ�����ֵ
	public static List<Double> removeMaxValues(List<Double> dataArrayList){
		
		return dataArrayList;
	}
	
	//������˹׼��
    public static List<Double> grubbs(List<Double> dataArrayList,double alpha) {
    	int length = dataArrayList.size();
        //��Ϊ������˹׼��ֻ�ܶԴ��ڵ���3�����ݽ����жϣ�����������С��3ʱ��ֱ�ӷ���
        if (length < 3) {
            return dataArrayList;
        }
        Double[] dataArray = new Double[dataArrayList.size()];
        for(int i=0;i<dataArrayList.size();i++){
        	dataArray[i] = dataArrayList.get(i);
        }
        double[] proArray = new double[dataArray.length];
        int count = 0;
        for(Double d:dataArray){
        	proArray[count] = d.doubleValue();
        	count++;
        }
        //�������ƽ��ֵ�ͱ�׼��
        double average = DataUtils.getMean(proArray);
        double minValue = DataUtils.getMinimum(proArray);
        double maxValue = DataUtils.getMaximum(proArray);
        double standard = DataUtils.getStandardDeviation(proArray);
        //������Сֵ�����ֵG1��Gn
        double dubMin = average - minValue;
        double dubMax = maxValue - average;
        double G1 = dubMin / standard;
        double Gn = dubMax / standard;
        //���Ƚϣ��Ƿ��޳�
        if (G1 > calcG(alpha, length)) {
            for(int i=0;i<dataArrayList.size();i++){
            	if(dataArrayList.get(i)==minValue){
            		dataArrayList.set(i, Double.NaN);
            	}
            }
            if (Gn > calcG(alpha, length)) {
            	for(int i=0;i<dataArrayList.size();i++){
                	if(dataArrayList.get(i)==maxValue){
                		dataArrayList.set(i, Double.NaN);
                	}
                }
            }
        } else if (Gn > calcG(alpha, length)) {
        	for(int i=0;i<dataArrayList.size();i++){
            	if(dataArrayList.get(i)==maxValue){
            		dataArrayList.set(i, Double.NaN);
            	}
            }
        }
        return dataArrayList;
    }

    private static double calcG(double alpha, int n) {
        double[] N = { 1.1546847100299753, 1.4962499999999703,
                1.763678479497787, 1.9728167175443088, 2.1391059896012203,
                2.2743651271139984, 2.386809875078279, 2.4820832497170997,
                2.564121252001767, 2.6357330437346365, 2.698971864039854,
                2.755372404941574, 2.8061052912205966, 2.8520798130619083,
                2.894013795424427, 2.932482154393285, 2.9679513293748547,
                3.0008041587489247, 3.031358153993366, 3.0598791335206963,
                3.086591582831163, 3.1116865231590722, 3.135327688211162,
                3.157656337622164, 3.178795077984819, 3.198850919445483,
                3.2179177419513314, 3.2360783011390764, 3.2534058719727748,
                3.26996560491852, 3.2858156522011304, 3.301008108808857,
                3.31558980320037, 3.329602965279218, 3.3430857935316243,
                3.356072938839107, 3.368595919061223, 3.3806834758032323,
                3.3923618826659503, 3.403655212591846, 3.41458557057518,
                3.4251732969213213, 3.435437145364717, 3.4453944396432576,
                3.4550612115453876, 3.464452322969104, 3.4735815741386,
                3.482461799798589, 3.491104954935569, 3.4995221913492585,
                3.507723926208097, 3.5157199035634887, 3.5235192496631433,
                3.5311305227901078, 3.5385617582575746, 3.5458205091071684,
                3.5529138829882037, 3.5598485756350797 };
        return N[n-3];
    }
    
    /*public static void main(String[] args) {
		double[] test = {8.2,5.4,14.0,7.3,4.7,9.0,6.5,10.1,7.7,23.0,0};
		ArrayList<Double> testList = new ArrayList<>();
		for(double t:test){
			testList.add(t);
		}
		System.out.println(testList.toString());
		AbnormalDetection ad = new AbnormalDetection();
		ad.grubbs(testList,0.01);
		System.out.println(testList.toString());
	}*/
    
}
