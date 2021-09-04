package com.vege.controller;

import com.vege.service.FileService;
import com.vege.service.preprocessing.entity.Parameters;
import com.vege.service.preprocessing.process.Preprocessing;
import com.vege.utils.PathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by zbs on 2018/3/11
 */
@RestController
@RequestMapping(path = "/")
public class ProcessController extends BaseController {
    private final FileService fileService;

    @Autowired
    public ProcessController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(value = "/upload_atmosphere.json", method = RequestMethod.POST)
    public Map<String, Object> uploadAtmosphere(@RequestParam("file") MultipartFile file) {
        boolean res = fileService.uploadFile(file, "atmosphere.csv");
        return buildResponse(res);
    }

    @RequestMapping(value = "/upload_water.json", method = RequestMethod.POST)
    public Map<String, Object> uploadWater(@RequestParam("file") MultipartFile file) {
        boolean res = fileService.uploadFile(file, "fiveall.csv");
        return buildResponse(res);
    }

    @RequestMapping(value = "/process.json", method = RequestMethod.GET)
    public Map<String, Object> add(HttpServletRequest request, HttpServletResponse response) {
        boolean res = false;
        try {
            Preprocessing.preprocessing();
            res = true;
        } catch (Exception e) {
        }

        return buildResponse(res);
    }

    @RequestMapping(value = "/upload_file.json", method = RequestMethod.POST)
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("file_name") String fileName) {
        fileName = "uploaded" + PathHelper.SEPARATOR + fileName;
        boolean res = fileService.uploadFile(file, fileName);

        if (!res) {
            return buildResponse(false);
        }

        String filePath = PathHelper.getExamplePath() + fileName;
        List<String> fileContent = fileService.getFileData(filePath);

        return buildResponse(fileContent);
    }

    @RequestMapping(value = "/abnormal_detection.json", method = RequestMethod.POST)
    public Map<String, Object> abnormalDetection(@RequestParam("fields") String fields) {
        try {
            List<Integer> fieldList = Arrays.stream(fields.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            List<Parameters> entityList = Preprocessing.getEntityList(PathHelper.getExamplePath() + "uploaded" + PathHelper.SEPARATOR + "abnormal_detection.csv");
            for (Parameters parameters : entityList) {
                List<Double> paraList = parameters.getParaList();
                int length = paraList.size();
                for (int j = 0; j < length; j++) {
                    if (!fieldList.contains(j)) {
                        paraList.remove(j);
                    }
                }
            }

            return buildResponse(entityList);
        } catch (Exception e) {
            e.printStackTrace();
            return buildResponse(false);
        }

    }

    @RequestMapping(value = "/data_conversion.json", method = RequestMethod.POST)
    public Map<String, Object> dataConversion(@RequestParam("fields") String fields) {
        try {
            List<Integer> fieldList = Arrays.stream(fields.split(",")).map(Integer::valueOf).collect(Collectors.toList());

            return buildResponse(true);
        } catch (Exception e) {
            e.printStackTrace();
            return buildResponse(false);
        }

    }

    @RequestMapping(value = "/data_fusion.json", method = RequestMethod.POST)
    public Map<String, Object> dataFusion(@RequestParam("fields") String fields) {
        try {
            List<Integer> fieldList = Arrays.stream(fields.split(",")).map(Integer::valueOf).collect(Collectors.toList());

            return buildResponse(true);
        } catch (Exception e) {
            e.printStackTrace();
            return buildResponse(false);
        }

    }


    @RequestMapping(value = "/data_normalization.json", method = RequestMethod.POST)
    public Map<String, Object> dataNormalization(@RequestParam("fields") String fields) {
        try {
            List<Integer> fieldList = Arrays.stream(fields.split(",")).map(Integer::valueOf).collect(Collectors.toList());

            return buildResponse(true);
        } catch (Exception e) {
            e.printStackTrace();
            return buildResponse(false);
        }

    }

}
