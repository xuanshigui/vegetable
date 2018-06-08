package com.aquatic.service.preprocessing.common;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    //public static int NUMBEROFPARAS = 5;

    public static int WINDOWSIZE = 6;

    public static Map<Integer,String> Type_MAP;
    static{
        Type_MAP = new HashMap<>();
        Type_MAP.put(0, "TEMP");
        Type_MAP.put(1, "DO");
        Type_MAP.put(2, "PH");
        Type_MAP.put(3, "TRANS");
        Type_MAP.put(4, "SALT");
    }
}
