package com.vege.utils;

import java.util.List;

public class CustomStringUtils
{
    public static String listToString(List<String> objectList){

        String result = "";
        int length = 5;
        if(objectList.size()<5){
            length = objectList.size();
        }
        for(int index=0;index<length;index++){
            if(index==0){
                result = objectList.get(index);
            }else {
                result = result + "，" + objectList.get(index);
            }
        }
        return result;
    }
}
