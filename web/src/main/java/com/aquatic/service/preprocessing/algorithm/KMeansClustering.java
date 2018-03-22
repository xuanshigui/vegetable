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
import com.aquatic.utils.PathHelper;


public class KMeansClustering {

    private List<Sample> dataset = null;

    public KMeansClustering() throws IOException {
        initDataSet( PathHelper.getResourcePath() + "fiveparam/cut");
    }

    public void initDataSet(String filePath) throws IOException {
        dataset = new ArrayList<Sample>();
        int fileNumber = CsvUtils.countFiles(filePath);
        for (int i = 0; i < fileNumber; i++) {
            String fileName = filePath + "/fiveparam" + i + ".csv";
            List<Parameters> entityList = Preprocessing.getEntityList(fileName);
            Sample sample = new Sample();
            sample.setNumberOfNodes(entityList.size());
            sample.setSeries(entityList);
            dataset.add(sample);
        }
    }

    public Map<Sample, List<Sample>> kcluster(int k) {
        // 随机从样本集合中选取k个样本点作为聚簇中心
        // 每个聚簇中心有哪些点
        Map<Sample, List<Sample>> nowClusterCenterMap = new HashMap<Sample, List<Sample>>();
        for (int i = 0; i < k; i++) {
            Random random = new Random();
            int num = random.nextInt(dataset.size());
            nowClusterCenterMap.put(dataset.get(num), new ArrayList<Sample>());
        }

        //上一次的聚簇中心
        Map<Sample, List<Sample>> lastClusterCenterMap = null;

        // 找到离中心最近的点,然后加入以该中心为map键的list中
        while (true) {
            //遍历dataset sample代表dataset中的每一个sample
            for (Sample sample : dataset) {
                double shortest = Double.MAX_VALUE;
                Sample key = null;
                //当前的聚类中心键值对，有k个
                Set<Entry<Sample, List<Sample>>> entrySet = nowClusterCenterMap.entrySet();
                //遍历聚类中心
                for (Entry<Sample, List<Sample>> entry : entrySet) {
                    //当前的聚类中心键（一个Sample）
                    Sample sampleKey = entry.getKey();
                    double distance = distance(sample, sampleKey, 0.5, 0.5);
                    if (distance < shortest) {
                        shortest = distance;
                        key = entry.getKey();
                    }
                }
                nowClusterCenterMap.get(key).add(sample);
            }
            Set<Entry<Sample, List<Sample>>> entrySet = nowClusterCenterMap.entrySet();
            int no = 0;
            for (Entry<Sample, List<Sample>> entry : entrySet) {
                Sample sampleKey = entry.getKey();
                List<Sample> list = nowClusterCenterMap.get(sampleKey);
                System.out.println(no + "-" + list.size());
                no++;
            }
            //如果结果与上一次相同，则整个过程结束
            if (isEqualCenter(lastClusterCenterMap, nowClusterCenterMap)) {
                break;
            }
            lastClusterCenterMap = nowClusterCenterMap;
            nowClusterCenterMap = new HashMap<Sample, List<Sample>>();
            //把中心点移到其所有成员的平均位置处,并构建新的聚簇中心
            for (Entry<Sample, List<Sample>> entry : lastClusterCenterMap.entrySet()) {
                List<Sample> entryVaule = entry.getValue();
                if (entryVaule.size() != 0) {
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
        } else {
            for (Entry<Sample, List<Sample>> entry : lastClusterCenterMap.entrySet()) {
                boolean contain = false;
                List<Parameters> lastParaList = entry.getKey().getSeries();
                Set<Entry<Sample, List<Sample>>> entrySet = nowClusterCenterMap.entrySet();
                for (Entry<Sample, List<Sample>> set : entrySet) {
                    List<Parameters> nowParaList = set.getKey().getSeries();
                    if (lastParaList.size() == nowParaList.size()) {
                        int count = 0;
                        for (int i = 0; i < lastParaList.size(); i++) {

                            if (lastParaList.get(i).equals(nowParaList.get(i))) {
                                count++;
                            }
                        }
                        if (count == lastParaList.size()) {
                            contain = true;
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

    private double distance(Sample sample1, Sample sample2, double alpha, double beta) {
        double distance = Double.MAX_VALUE;
        double euclideanDistance = EuclideanDistance.getEuclideanDistance(sample1, sample2);
        double dtwDistance = DynamicTimeWarping.dtwDistance(sample1, sample2);
        distance = alpha * euclideanDistance + beta * dtwDistance;
        return distance;
    }

    //获得新的中心点(获得均值向量)
    private Sample getNewCenterSample(List<Sample> value) {
        int sampleNumber = value.size();
        int cols = value.get(0).getNumberOfNodes();
        int rows = value.get(0).getSeries().get(0).getNumberOfParas();
        //距离积累矩阵
        double[][] sumArray = new double[rows][cols];
        //初始化距离积累矩阵
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sumArray[i][j] = 0.0;
            }
        }
        for (Sample sample : value) {
            double[][] array = new double[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    array[i][j] = sample.getListValue(i, j);
                    sumArray[i][j] = sumArray[i][j] + array[i][j];
                }
            }
        }
        //均值Array
        double[][] avgArray = new double[rows][cols];
        Sample sample = new Sample();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                avgArray[i][j] = sumArray[i][j] / sampleNumber;
            }
        }
        double nearest = Double.POSITIVE_INFINITY;
        sample.arrayToList(avgArray);
        Sample time = value.get(0);
        for (Sample current : value) {
            double dis = Clustering.distance(sample, current, 0.5, 0.5);
            if (dis < nearest) {
                time = current;
            }
        }
        sample.setTime(sample, time);
        return sample;
    }

    public static void kMeansClustering(int centerNumber) {
        try {
            KMeansClustering kmc = new KMeansClustering();

            //初始化实验
            kmc.initDataSet( PathHelper.getResourcePath() + "fiveparam/cut");
            Map<Sample, List<Sample>> result = kmc.kcluster(centerNumber);
            Clustering.display(result);

        } catch (Exception e) {
            System.out.println("数据读取失败。");
        }


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
