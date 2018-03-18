package com.aquatic.service.script;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aquatic.utils.PathHelper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * created by zbs on 2018/3/18
 */
@Service
public class ScriptService {

    private static final String SCRIPT_DIR = PathHelper.getResourcePath() + PathHelper.SEPARATOR + "script" + PathHelper.SEPARATOR;
    private static final String PY_SCRIPT = SCRIPT_DIR + "waterQualityEvaluation.py";
    private static final String SOURCE_DIR = SCRIPT_DIR + "source" + PathHelper.SEPARATOR;

    private static final String CONFIG = "{\"do_catch\":{\"file\":\"DOCatch.csv\", \"seeds\":\"20,15,45,37\"},\n" +
            "\"do_size\":{\"file\":\"DOSize.csv\", \"seeds\":\"8,12,19,28,36,100\"},\n" +
            "\"do_yield\":{\"file\":\"DOYield.csv\", \"seeds\":\"0,1,5,12,16,60\"},\n" +
            "\"temp_catch\":{\"file\":\"TempCatch.csv\", \"seeds\":\"4,45,33,35\"},\n" +
            "\"temp_size\":{\"file\":\"TempSize.csv\", \"seeds\":\"33,0,10,16,25\"},\n" +
            "\"temp_yield\":{\"file\":\"TempYield.csv\", \"seeds\":\"1,30,0,18,357\"}}";


    public String evaluateWaterQuality(String env, String profit) {
        String file = getSourceFile(env, profit);
        String filePath = SOURCE_DIR + file;
        String seeds = getSeeds(env, profit);

        return cal(filePath, seeds);
    }

    private String cal(String file, String seeds) {
        String command = "python " + PY_SCRIPT + " " + file + " " + seeds;
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            result = input.readLine();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    private String getSeeds(String env, String profit) {
        JSONObject attr = getAttr(env, profit);
        return attr.getString("seeds");
    }

    private String getSourceFile(String env, String profit) {
        JSONObject attr = getAttr(env, profit);
        return attr.getString("file");
    }

    private JSONObject getAttr(String env, String profit) {
        JSONObject config = JSON.parseObject(CONFIG);
        String item = config.getString(env + "_" + profit);

        return JSON.parseObject(item);
    }
}
