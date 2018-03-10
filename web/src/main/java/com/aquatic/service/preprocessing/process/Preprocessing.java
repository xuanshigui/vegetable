package com.aquatic.service.preprocessing.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.aquatic.service.preprocessing.common.Constant;
import com.aquatic.service.preprocessing.entity.Parameters;
import com.aquatic.service.preprocessing.utils.CsvUtils;
import com.aquatic.service.preprocessing.utils.TimeFormat;
import com.aquatic.utils.PathHelper;

public class Preprocessing {

    public static void preprocessing() {
        //导入气象参数，并将其转换成List<Parameters>
        List<Parameters> entityList1 = getEntityList(PathHelper.getExamplePath() + "atmosphere.csv");

        //预处理水质参数
        List<Parameters> entityList2 = preprocessWater();

        //数据融合
        List<Parameters> fusedList = Preprocessing.dataFusion(entityList1, entityList2);
        //归一化操作
        List<Parameters> normalizedList = Normalization.normalization(fusedList);
        Preprocessing.exportByDays(normalizedList, PathHelper.getExamplePath() + "fiveparam");
        //按照滑动窗口切割数据集
        CutDataBySlideWindow.getSamples(PathHelper.getExamplePath() + "fiveparam");
    }

    public static void export(List<Parameters> paraList, String fileName) {
        List<String> dataList = new ArrayList<>();
        for (Parameters present : paraList) {
            dataList.add(present.display());
        }
        boolean isSuccess = CsvUtils.exportCsv(new File(fileName), dataList);
        System.out.println(isSuccess);
    }

    public static List<Parameters> dataFusion(
            List<Parameters> normalizedList1, List<Parameters> normalizedList2) {
        List<Parameters> fusedList = new ArrayList<>();
        for (int i = 0; i < normalizedList1.size(); i++) {
            Parameters params1 = normalizedList1.get(i);
            for (int j = 0; j < normalizedList2.size(); j++) {
                Parameters params2 = normalizedList2.get(j);
                boolean flag = isCorresponding(params1, params2);
                if (flag) {
                    //新的Parameters
                    Parameters parameters = new Parameters();
                    parameters.setDate(params1.getDate());
                    parameters.setTime(params2.getTime());
                    List<Double> paraList = params2.getParaList();
                    paraList.addAll(params1.getParaList());
                    parameters.setParaList(paraList);
                    fusedList.add(parameters);
                    System.out.println("-------------------");
                    System.out.println(i + "-" + j);
                    break;
                }
            }
        }
        return fusedList;
    }

    public static boolean isCorresponding(Parameters para1, Parameters para2) {
        String date1 = para1.getDate();
        String time1 = para1.getTime();
        String[] hour_minute1 = time1.split(":");
        String hour1 = hour_minute1[0];
        String minute1 = hour_minute1[1];
        String date2 = para2.getDate();
        String time2 = para2.getTime();
        String[] hour_minute2 = time2.split(":");
        String hour2 = hour_minute2[0];
        String minute2 = hour_minute2[1];
        if (date1.equals(date2)) {
            if (hour1.equals(hour2)) {
                //同一个小时
                if (Math.abs((Integer.parseInt(minute1) - Integer.parseInt(minute2))) < 15) {
                    return true;
                } else {
                    return false;
                }
            } else if ((Integer.parseInt(hour1) - Integer.parseInt(hour2)) == 1) {
                //前一个小时
                if (Integer.parseInt(minute2) > 45 && Integer.parseInt(minute1) < 10) {
                    return true;
                } else {
                    return false;
                }
            } else if ((Integer.parseInt(hour1) - Integer.parseInt(hour2)) == -1) {
                //后一个小时
                if (Integer.parseInt(minute2) < 15 && Integer.parseInt(minute1) > 31) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public static List<Parameters> preprocessWater() {
        ////导入数据集,即导入fiveall.csv文件，注意是文件全名，即"E:/prediction/fiveall.csv"
        List<Parameters> entityList = Preprocessing.getEntityList(PathHelper.getExamplePath() + "fiveall.csv");
        //每一个参数是一个List
        List<List<Double>> paraLists = Preprocessing.getParaList(entityList);
        //数据异常检测
        for (List<Double> oneList : paraLists) {
            AbnormalDetection.abnormalDetect(oneList, 0.01, 10);
        }
        //数据清洗
        for (List<Double> oneList : paraLists) {
            FillUp.fillUpByMeanValue(oneList);
        }
        List<Parameters> resultList = Preprocessing.reverseToEntityList(paraLists, entityList);
        //数据变换
        List<Parameters> converseByAverage = DataConversion.converseByAverage(resultList, 6);
        return converseByAverage;
    }

    private static List<Parameters> reverseToEntityList(List<List<Double>> entityList, List<Parameters> originList) {
        List<Parameters> paraList = new ArrayList<>();
        int paraNumber = entityList.size();
        double[][] paraArray = new double[paraNumber][entityList.get(0).size()];
        for (int i = 0; i < entityList.size(); i++) {
            for (int j = 0; j < entityList.get(0).size(); j++) {
                paraArray[i][j] = entityList.get(i).get(j);
            }
        }
        //ˮ��	�ܽ���	ph	    �絼��	�Ƕ�
        for (int i = 0; i < paraArray[0].length; i++) {
            Parameters parameters = new Parameters();
            String time = originList.get(i).getTime();
            String date = originList.get(i).getDate();
            parameters.setTime(time);
            parameters.setDate(date);
            List<Double> paras = new ArrayList<>();
            for (int j = 0; j < paraArray.length; j++) {
                paras.add(paraArray[j][i]);
            }
            parameters.setParaList(paras);
            paraList.add(parameters);
        }
        return paraList;
    }

    public static List<List<Double>> getParaList(List<Parameters> entityList) {
        List<List<Double>> parasList = new ArrayList<List<Double>>();
        int rows = entityList.get(0).getNumberOfParas();
        int cols = entityList.size();
        Double[][] paraArray = new Double[rows][cols];
        //ˮ��	�ܽ���	ph	    �絼��	�Ƕ�
        for (int i = 0; i < cols; i++) {
            Parameters parameter = entityList.get(i);
            List<Double> item = parameter.getParaList();
            for (int j = 0; j < rows; j++) {
                paraArray[j][i] = item.get(j);
            }
        }
        for (int i = 0; i < rows; i++) {
            List<Double> series = new ArrayList<>();
            for (int j = 0; j < entityList.size(); j++) {
                series.add(paraArray[i][j]);
            }
            parasList.add(series);
        }
        return parasList;
    }

    public static List<String[]> cutItems(String position) {
        List<String> dataList = CsvUtils.importCsv(new File(position));
        List<String[]> lineList = new ArrayList();
        for (String item : dataList) {
            String[] day = item.split(",");
            lineList.add(day);
        }
        return lineList;
    }

    public static List<Parameters> getEntityList(String path) {
        List<String[]> lineList = Preprocessing.cutItems(path);
        List<Parameters> paramList = new ArrayList();
        for (String[] items : lineList) {
            String date_time = TimeFormat.format(items[0]);
            String[] cut = date_time.split(" ");
            String date = cut[0];
            String time = cut[1];
            Parameters param = new Parameters();
            param.setDate(date);
            param.setTime(time);
            List<Double> list = new ArrayList<Double>();
            for (int i = 1; i < items.length; i++) {
                list.add(Double.parseDouble(items[i]));
            }
            param.setParaList(list);
            paramList.add(param);
        }
        return paramList;
    }

    public static void exportByDays(List<Parameters> paramList, String filePath) {
        //boolean isSuccess=CsvUtils.exportCsv(new File("E:/prediction/fiveall2.csv"), dataList);
        //System.out.println(isSuccess);
        boolean isSuccess = false;
        int start = 0;
        int count = 0;
        for (int index = 0; index < paramList.size() - 1; index++) {

            int end = index;
            Parameters param = paramList.get(index);
            Parameters param_next = paramList.get(index + 1);
            if (!param.getDate().equals(param_next.getDate())) {
                //�Ȳ���
                List<String> dataList = new ArrayList<>();
                String fileName = filePath + "/fiveparam" + count + ".csv";
                for (int j = start; j <= end; j++) {
                    Parameters present = paramList.get(j);
                    dataList.add(present.display());
                }
                if (dataList.size() >= Constant.WINDOWSIZE) {
                    isSuccess = CsvUtils.exportCsv(new File(fileName), dataList);
                    System.out.println(isSuccess);
                } else {
                    count = count - 1;
                }
                //�ٸı�״̬
                start = index + 1;
                count++;
            }
        }

    }
}
