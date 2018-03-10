package com.aquatic.utils;

import org.springframework.util.ResourceUtils;

/**
 * created by zbs on 2018/3/10
 */
public class PathHelper {
    public static String getResourcePath() {
        String path = "";
        try {
            path = ResourceUtils.getFile("classpath:").getParent() + "/resources/";
        } catch (Exception e) {

        }
        return path;
    }

    public static String getExamplePath() {
        return getResourcePath() + "examples/";
    }
}
