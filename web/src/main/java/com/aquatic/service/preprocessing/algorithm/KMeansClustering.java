package com.aquatic.service.preprocessing.algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.aquatic.service.preprocessing.entity.Sample;
import com.aquatic.service.preprocessing.entity.Parameters;
import com.aquatic.service.preprocessing.process.Preprocessing;
import com.aquatic.service.preprocessing.utils.CsvUtils;
import com.aquatic.service.preprocessing.utils.DynamicTimeWarping;
import com.aquatic.service.preprocessing.utils.EuclideanDistance;
import com.aquatic.service.preprocessing.validity_index.Clustering;


public class KMeansClustering {
	
	private List<Sample> dataset = null;
	  
    public KMeansClustering() throws IOException {  
        initDataSet();  
    }
    
    public void initDataSet() throws IOException {  
        dataset = new ArrayList<Sample>();  
        String filePath = "E:/prediction/fiveparam/cut";
        int fileNumber = CsvUtils.countFiles(filePath);
        for(int i=0;i<fileNumber;i++){
        	String fileName = filePath + "/fiveparam" + i + ".csv";
        	List<Parameters> entityList = Preprocessing.getEntityList(fileName);
        	Sample sample = new Sample();
        	sample.setNumberOfNodes(entityList.size());
        	sample.setSeries(entityList);
        	dataset.add(sample);
        }
    }
    public Map<Sample,List<Sample>> kcluster(int k) {  
        // ���������������ѡȡk����������Ϊ�۴�����  
        // ÿ���۴���������Щ��  
        Map<Sample,List<Sample>> nowClusterCenterMap = new HashMap<Sample, List<Sample>>();  
        for (int i = 0; i < k; i++) {  
            Random random = new Random();  
            int num = random.nextInt(dataset.size());  
            nowClusterCenterMap.put(dataset.get(num), new ArrayList<Sample>());  
        }  
          
        //��һ�εľ۴�����  
        Map<Sample,List<Sample>> lastClusterCenterMap = null;  
  
        // �ҵ�����������ĵ�,Ȼ������Ը�����Ϊmap����list��  
        while (true) {  
        	//����dataset sample����dataset�е�ÿһ��sample
            for (Sample sample : dataset) {  
                double shortest = Double.MAX_VALUE;  
                Sample key = null;
                //��ǰ�ľ������ļ�ֵ�ԣ���k��
                Set<Entry<Sample,List<Sample>>> entrySet = nowClusterCenterMap.entrySet();
                //������������
                for (Entry<Sample, List<Sample>> entry : entrySet) { 
                	//��ǰ�ľ������ļ���һ��Sample��
                	Sample sampleKey = entry.getKey();
                    double distance = distance(sample, sampleKey,0.5,0.5);  
                    if (distance < shortest) {  
                        shortest = distance;  
                        key = entry.getKey();  
                    }  
                }  
                nowClusterCenterMap.get(key).add(sample);
            }  
            Set<Entry<Sample,List<Sample>>> entrySet = nowClusterCenterMap.entrySet();
            int no = 0;
            for(Entry<Sample, List<Sample>> entry : entrySet){
            	Sample sampleKey = entry.getKey();
            	List<Sample> list = nowClusterCenterMap.get(sampleKey);
            	System.out.println(no+"-"+list.size());
            	no++;
            }
            //����������һ����ͬ�����������̽���  
            if (isEqualCenter(lastClusterCenterMap,nowClusterCenterMap)) {  
                break;  
            }  
            lastClusterCenterMap = nowClusterCenterMap;  
            nowClusterCenterMap = new HashMap<Sample, List<Sample>>();  
            //�����ĵ��Ƶ������г�Ա��ƽ��λ�ô�,�������µľ۴�����  
            for (Entry<Sample,List<Sample>> entry : lastClusterCenterMap.entrySet()) {  
                List<Sample> entryVaule = entry.getValue();
                if(entryVaule.size()!=0){
                	nowClusterCenterMap.put(getNewCenterSample(entryVaule), new ArrayList<Sample>());
                }
            }  
        }  
        return nowClusterCenterMap;  
    }  

    
    private boolean isEqualCenter(Map<Sample, List<Sample>> lastClusterCenterMap,  
            Map<Sample, List<Sample>> nowClusterCenterMap) {  
        if (lastClusterCenterMap == null) {  
            return false;  
        }else {  
            for (Entry<Sample, List<Sample>> entry : lastClusterCenterMap.entrySet()) {  
                boolean contain = false;
                List<Parameters> lastParaList = entry.getKey().getSeries();
                Set<Entry<Sample,List<Sample>>> entrySet = nowClusterCenterMap.entrySet();
                for(Entry<Sample,List<Sample>> set:entrySet){
                	List<Parameters> nowParaList = set.getKey().getSeries();
                	if(lastParaList.size()==nowParaList.size()){
                		int count=0;
                		for(int i=0;i<lastParaList.size();i++){
                			
                			if(lastParaList.get(i).equals(nowParaList.get(i))){
                				count++;
                			}
                		}
                		if(count==lastParaList.size()){
                			contain=true;
                		}
                	}
                }
            	if (contain) {  
                    return false;  
                }  
            }  
        }  
        return true;  
    }  
    
    private double distance(Sample sample1, Sample sample2,double alpha,double beta) {  
        double distance = Double.MAX_VALUE;
        double euclideanDistance = EuclideanDistance.getEuclideanDistance(sample1, sample2);
        double dtwDistance = DynamicTimeWarping.dtwDistance(sample1, sample2);
        distance = alpha*euclideanDistance+beta*dtwDistance;
        return distance;  
    }  
    
    //����µ����ĵ�(��þ�ֵ����)
    private Sample getNewCenterSample(List<Sample> value) {
    	int sampleNumber = value.size();
    	int cols = value.get(0).getNumberOfNodes();
    	int rows = value.get(0).getSeries().get(0).getNumberOfParas();
    	//������۾���
    	double[][] sumArray = new double[rows][cols];
    	//��ʼ��������۾���
    	for(int i=0;i<rows;i++){
    		for(int j=0;j<cols;j++){
    			sumArray[i][j] = 0.0;
        	}
    	}
        for (Sample sample : value) {  
        	double[][] array = new double[rows][cols];
        	for(int i=0;i<rows;i++){
        		for(int j=0;j<cols;j++){
        			array[i][j] = sample.getListValue(i, j);
        			sumArray[i][j] = sumArray[i][j] + array[i][j];
            	}
        	}
        }  
        //��ֵArray
        double[][] avgArray = new double[rows][cols];
        Sample sample = new Sample();  
        for(int i=0;i<rows;i++){
    		for(int j=0;j<cols;j++){
    			avgArray[i][j] = sumArray[i][j]/sampleNumber;
        	}
    	}
        double nearest = Double.POSITIVE_INFINITY;
        sample.arrayToList(avgArray);
        Sample time = value.get(0);
        for (Sample current : value) {  
        	double dis = Clustering.distance(sample, current, 0.5, 0.5);
        	if(dis<nearest){
        		time = current;
        	}
        } 
        sample.setTime(sample,time);
        return sample;  
    }  
    
    public static void main(String[] args) throws IOException {
		KMeansClustering kmc = new KMeansClustering();
		
		//��ʼ��ʵ��
		int centerNumber = 3;
		kmc.initDataSet();
		Map<Sample, List<Sample>> result = kmc.kcluster(centerNumber);
		Clustering.display(result);
		/*double sumDBI = 0.0;
		for(int i=0;i<5;i++){
			kmc.initDataSet();
			Map<Sample, List<Sample>> result = kmc.kcluster(centerNumber);
			Clustering.display(result);
			double dbi = Clustering.daviesBouldinIndex(result);
			sumDBI = sumDBI + dbi; 
			System.out.println(dbi);
		}*/
		//System.out.println(sumDBI/5);
	}
    
    

	public List<Sample> getDataset() {
		return dataset;
	}

	public void setDataset(List<Sample> dataset) {
		this.dataset = dataset;
	}
}
