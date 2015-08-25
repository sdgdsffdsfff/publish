package com.xiaomi.plugin.controller;

import com.xiaomi.plugin.Constant;
import com.xiaomi.plugin.model.Type;
import com.xiaomi.plugin.service.TypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 页面跳转
 * Created by lijie on 2015-08-14.
 */
@Controller
@RequestMapping(value = "/view")
public class ViewController {


    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/app")
    public ModelAndView appView(HttpServletRequest request) {

        ModelMap modelMap = new ModelMap();
        List<Type> typeList = typeService.getListByHQL("from Type order by orderNo");
        modelMap.addAttribute("type", typeList);//查询侧边栏目树
        String tid = request.getParameter("tid");//代表侧边栏目点击的哪一个
        if (StringUtils.isEmpty(tid)) {
            if (typeList != null && typeList.size() > 0) {
                tid = typeList.get(0).getName();
            }
        }
        modelMap.put("tid", tid);

        String fid = request.getParameter("fid");//代表是 应用软件更新包 或者应用软件资源包

        if (fid == null) {
            fid = Constant.APP_SOFT_UPGRADE;
        }
        modelMap.put("fid", fid);

        return new ModelAndView("appFrame", modelMap);

    }

    @RequestMapping(value = "/bus")
    public ModelAndView busView(HttpServletRequest request) {

        ModelMap modelMap = new ModelMap();

        String fid = request.getParameter("fid");//代表是 应用软件更新包 或者应用软件资源包

        if (fid == null) {
            fid = Constant.APP_SOFT_UPGRADE;
        }
        modelMap.put("fid", fid);

        return new ModelAndView("busFrame", modelMap);

    }

}
