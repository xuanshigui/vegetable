package com.aquatic.service.preprocessing.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.aquatic.service.preprocessing.common.Constant;
import com.aquatic.service.preprocessing.entity.Parameters;
import com.aquatic.service.preprocessing.entity.Sample;
import com.aquatic.service.preprocessing.utils.CsvUtils;
import com.aquatic.utils.PathHelper;

public class CutDataBySlideWindow {
	//ѭ����ȡ�ļ��ٷָ�
	public static void getSamples(String filePath){
		//�ļ�������
        int fileNumber = CsvUtils.countFiles(filePath);
        int serial = 0;
        for(int i=0;i<(fileNumber-1);i++){
        	//�ļ���ƴ��
        	String fileName = filePath + "/fiveparam" + i + ".csv";
        	//�õ��ļ��еĲ�����
        	List<Parameters> entityList = Preprocessing.getEntityList(fileName);
        	//���ļ��ָ������,Sample����windowSize��parameter
        	//��sampleList���ж��ٸ�sample��һ��,i��������ĸ��ļ�
        	List<Sample> sampleList = getSampleList(entityList,Constant.WINDOWSIZE);
        	//������ת����String���Ͳ����
        	//��¼�ļ����
        	serial = getStringData(sampleList,serial);
        }
	}
	
	public static int getStringData(List<Sample> sampleList,int sampleSeries){
		//count�ǵ�ǰ�ļ����ָ��������
		int count = 0;
		for(Sample sample:sampleList){
			List<String> dataList = new ArrayList<>();
			List<Parameters> paraList = sample.getSeries();
			for(Parameters present:paraList){
				dataList.add(present.display());
			}
			String fileName = PathHelper.getExamplePath() + "fiveparam/cut/fiveparam" + (sampleSeries + count) + ".csv";
			count++;
			boolean isSuccess=CsvUtils.exportCsv(new File(fileName), dataList);
		    System.out.println(isSuccess);
		}
		sampleSeries = sampleSeries+count;
		return sampleSeries;
	}
	
	//�ָ����У���Ϊ������
	public static List<Sample> getSampleList(List<Parameters> paraList,int windowSize){
		List<Sample> sampleList = new ArrayList<>();
		int numberOfSamples = paraList.size()-windowSize+1;
		for(int index=0;index<numberOfSamples;index++){
			Sample sample = new Sample();
			sample.setNumberOfNodes(windowSize);
			List<Parameters> parametersList = new ArrayList<>();
			for(int j=0;j<windowSize;j++){
				parametersList.add(paraList.get(index+j));
			}
			sample.setSeries(parametersList);
			sampleList.add(sample);
		}
		return sampleList;
	}

	/*public static void main(String[] args) {
		CutDataBySlideWindow.getSamples("E:/prediction/fiveparam");
	}*/
}
