package com.aquatic.service.range;

/**
 * created by zbs on 2018/3/25
 */
public class Range {
    private double min;
    private double max;

    public Range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public boolean inRange(double value) {
        return value >= min && value <= max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
