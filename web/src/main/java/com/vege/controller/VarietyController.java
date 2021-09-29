package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.CultivateMode;
import com.vege.model.Variety;
import com.vege.model.VegeInfo;
import com.vege.service.CultivateModeService;
import com.vege.service.ImageService;
import com.vege.service.VarietyService;
import com.vege.service.VegeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class VarietyController extends BaseController {

    private final VarietyService varietyService;
    private final VegeInfoService vegeInfoService;
    private final ImageService imageService;
    private final CultivateModeService cultivateModeService;

    @Autowired
    public VarietyController(VarietyService varietyService, VegeInfoService vegeInfoService,ImageService imageService,CultivateModeService cultivateModeService) {
        this.varietyService = varietyService;
        this.vegeInfoService = vegeInfoService;
        this.imageService = imageService;
        this.cultivateModeService = cultivateModeService;
    }

    @RequestMapping(value = "/add_variety.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList( "varietyName", "varietyImg", "introduction", "vegeId", "area", "source", "cultivateModes");
        Map<String, String> data = buildData(request,fields);
        Variety variety = new Variety();
        variety.setVarietyName(data.get("varietyName"));
        VegeInfo vegeInfo = vegeInfoService.queryById(data.get("vegeId"));
        variety.setVegeInfo(vegeInfo);
        //存储图片
        String imgUuid = imageService.add(data.get("varietyImg"),Variety.class.getSimpleName());
        //添加养殖模式
        String[] cm = data.get("cultivateModes").split(",");
        List<CultivateMode> cultivateModeList = new ArrayList<>();
        for(String cmId:cm){
            cultivateModeList.add(cultivateModeService.queryById(cmId));
        }
        variety.setCultivateModes(cultivateModeList);
        variety.setImgUuid(imgUuid);
        variety.setDescription(data.get("introduction"));
        variety.setArea(data.get("area"));
        variety.setSource(data.get("source"));
        variety.setTimestamp(new Timestamp(System.currentTimeMillis()));
        boolean flag = varietyService.add(variety);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_variety.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String varietyId = request.getParameter("varietyId");
        boolean flag = varietyService.delete(varietyId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_variety.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("varietyId", "varietyName", "varietyImg", "imguuid", "introduction", "vegeId", "area", "source");
        Map<String, String> data = buildData(request,fields);
        Variety variety = varietyService.queryById(data.get("varietyId"));
        VegeInfo vegeInfo = vegeInfoService.queryById(data.get("vegeId"));
        variety.setVegeInfo(vegeInfo);
        variety.setVarietyName(data.get("varietyName"));
        if(data.size()==8){
            //旧的图片保存
            variety.setImgUuid(data.get("imguuid"));
        }else {
            //建立图片
            String imgUuid = imageService.add(data.get("varietyImg"),variety.getClass().getSimpleName());
            variety.setImgUuid(imgUuid);
        }
        variety.setDescription(data.get("introduction"));
        variety.setArea(data.get("area"));
        variety.setSource(data.get("source"));
        variety.setTimestamp(new Timestamp(System.currentTimeMillis()));
        boolean flag = varietyService.update(variety);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_variety.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("varietyName","vegeName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        Page<Variety> result = varietyService.query(condition);
        long total = varietyService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_varietybyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String varietyid = request.getParameter("varietyId");

        Variety variety = varietyService.queryById(varietyid);
        JSONObject data = new JSONObject();
        data.put("varietyId", variety.getVarietyId());
        data.put("vegeName", variety.getVegeInfo().getVegeName());
        data.put("varietyName", variety.getVarietyName());
        data.put("cultivatemodes",variety.getCultivateModes());
        data.put("description", variety.getDescription());
        data.put("area", variety.getArea());
        String imgPath = "";
        imgPath = imageService.queryPathByUuid(variety.getImgUuid());
        data.put("imgPath", "http://127.0.0.1:8080/show_img?imgPath="+imgPath);
        data.put("imgUuId", variety.getImgUuid());
        data.put("source", variety.getSource());

        //载入列表选项
        Map<String,String> vegeNameMap = vegeInfoService.getVegeIdAndName();
        data.put("vegeNameMap",vegeNameMap);
        Map<String,String> cultivateModes = cultivateModeService.getCultivateModeName();
        data.put("modeNameMap",cultivateModes);
        return buildResponse(data);
    }

    @RequestMapping(value = "/load_vegename.json", method = {RequestMethod.GET})
    public Map getVegeNames(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> vegeNames = vegeInfoService.getVegeIdAndName();
        Map<String,String> cultivateModes = cultivateModeService.getCultivateModeName();
        //List<CultivateMode> cultivateModeList = cultivateModeService.findAll();
        JSONObject data = new JSONObject();
        data.put("vegeNameMap",vegeNames);
        data.put("modeNameMap",cultivateModes);
        //data.put("cultivateModeList",cultivateModeList);
        return buildResponse(data);
    }
}
