package com.aquatic.service.range;

import java.util.Map;

/**
 * created by zbs on 2018/3/25
 */
public class RangeService {

    public double getMax(String arg, String type) {
        return getRange(arg, type).getMax();
    }

    public double getMin(String arg, String type) {
        return getRange(arg, type).getMin();
    }

    public Range getRange(String arg, String type) {
        Map<String, Range> config = Config.getInstance().getCongfig(arg);

        return config.get(type);
    }

    public String getRangeName(String arg, double value) {
        Map<String, Range> config = Config.getInstance().getCongfig(arg);
        if (config.get(Config.BEST).inRange(value)) {
            return Config.BEST;
        }

        if (config.get(Config.PROPER).inRange(value)) {
            return Config.PROPER;
        }

        if (config.get(Config.CRITICAL).inRange(value)) {
            return Config.CRITICAL;
        }

        return Config.WORST;
    }
}

