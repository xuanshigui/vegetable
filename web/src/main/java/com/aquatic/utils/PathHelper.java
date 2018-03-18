package com.aquatic.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * created by zbs on 2018/3/10
 */
public class PathHelper {

    public static final String SEPARATOR = File.separator;

    public static String getResourcePath() {
        String path = "";
        try {
            path = ResourceUtils.getFile("classpath:").getParent() + SEPARATOR + "resources" + SEPARATOR;
        } catch (Exception e) {

        }
        return path;
    }

    public static String getExamplePath() {
        return getResourcePath() + "examples" + SEPARATOR;
    }
}
