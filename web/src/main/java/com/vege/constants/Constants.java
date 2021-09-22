package com.vege.constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static Map<String,String> ENTITY_TABLE_MAP;

    public static Map<String,String> VEGE_CLASS_MAP;

    public static Map<String,String> CLASS_VEGE_MAP;

    static{
        //ENTITY_TABLE_MAP
        ENTITY_TABLE_MAP = new HashMap<String, String>();
        ENTITY_TABLE_MAP.put("VegeInfo","tb_vegeinfo");
        ENTITY_TABLE_MAP.put("Variety","tb_variety");

        //VEGE_CLASS_MAP
        VEGE_CLASS_MAP = new HashMap<String,String>();
        VEGE_CLASS_MAP.put("0","根菜类");
        VEGE_CLASS_MAP.put("1","白菜和甘蓝类");
        VEGE_CLASS_MAP.put("2","芥菜类");
        VEGE_CLASS_MAP.put("3","茄果类");
        VEGE_CLASS_MAP.put("4","瓜类");
        VEGE_CLASS_MAP.put("5","豆类");
        VEGE_CLASS_MAP.put("6","葱蒜类");
        VEGE_CLASS_MAP.put("7","绿叶菜类");
        VEGE_CLASS_MAP.put("8","薯芋类");
        VEGE_CLASS_MAP.put("9","水生蔬菜");
        VEGE_CLASS_MAP.put("10","多年生蔬菜");
        VEGE_CLASS_MAP.put("11","食用菌类");
        VEGE_CLASS_MAP.put("12","芽苗菜类");
        VEGE_CLASS_MAP.put("13","野生蔬菜");
        //CLASS_VEGE_MAP
        CLASS_VEGE_MAP = new HashMap<String,String>();
        CLASS_VEGE_MAP.put("根菜类","0");
        CLASS_VEGE_MAP.put("白菜和甘蓝类","1");
        CLASS_VEGE_MAP.put("芥菜类","2");
        CLASS_VEGE_MAP.put("茄果类","3");
        CLASS_VEGE_MAP.put("瓜类","4");
        CLASS_VEGE_MAP.put("豆类","5");
        CLASS_VEGE_MAP.put("葱蒜类","6");
        CLASS_VEGE_MAP.put("绿叶菜类","7");
        CLASS_VEGE_MAP.put("薯芋类","8");
        CLASS_VEGE_MAP.put("水生蔬菜","9");
        CLASS_VEGE_MAP.put("多年生蔬菜","10");
        CLASS_VEGE_MAP.put("食用菌类","11");
        CLASS_VEGE_MAP.put("芽苗菜类","12");
        CLASS_VEGE_MAP.put("野生蔬菜","13");
    }
}
