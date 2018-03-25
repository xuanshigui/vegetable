package com.aquatic.service.range;

import java.util.HashMap;
import java.util.Map;

/**
 * created by zbs on 2018/3/25
 */
class Config {
    public static final String BEST = "best";
    public static final String PROPER = "proper";
    public static final String CRITICAL = "critical";
    public static final String WORST = "worst";

    private static Config config;
    private Map<String, Map<String, Range>> configs = new HashMap<>();

    private Config() {
        Map<String, Range> DOP = new HashMap<>();
        DOP.put(BEST, new Range(5.5, 6.5));
        DOP.put(PROPER, new Range(5, 7));
        DOP.put(CRITICAL, new Range(2, 8));
        configs.put("DO", DOP);

        Map<String, Range> TEMP = new HashMap<>();
        TEMP.put(BEST, new Range(25, 28));
        TEMP.put(PROPER, new Range(20, 28));
        TEMP.put(CRITICAL, new Range(0, 30));
        configs.put("TEMP", TEMP);

        Map<String, Range> NH3 = new HashMap<>();
        NH3.put(BEST, new Range(0.05, 0.08));
        NH3.put(PROPER, new Range(0.04, 0.09));
        NH3.put(CRITICAL, new Range(0.02, 0.1));
        configs.put("NH3", NH3);

        Map<String, Range> PH = new HashMap<>();
        PH.put(BEST, new Range(7.8, 8.2));
        PH.put(PROPER, new Range(7.5, 8.5));
        PH.put(CRITICAL, new Range(7, 9));
        configs.put("PH", PH);

        Map<String, Range> TRANS = new HashMap<>();
        TRANS.put(BEST, new Range(60, Double.MAX_VALUE));
        TRANS.put(PROPER, new Range(30, Double.MAX_VALUE));
        TRANS.put(CRITICAL, new Range(10, Double.MAX_VALUE));
        configs.put("TRANS", TRANS);

        Map<String, Range> SALT = new HashMap<>();
        SALT.put(BEST, new Range(1, 1.5));
        SALT.put(PROPER, new Range(0.9, 1.8));
        SALT.put(CRITICAL, new Range(0.7, 2));
        configs.put("SALT", SALT);
    }

    public Map<String, Range> getCongfig(String key) {
        return configs.get(key);
    }

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }

        return config;
    }
}
