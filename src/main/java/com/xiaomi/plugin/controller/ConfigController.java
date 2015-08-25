package com.xiaomi.plugin.controller;

import com.xiaomi.plugin.bean.PageResults;
import com.xiaomi.plugin.model.Bar;
import com.xiaomi.plugin.service.BarService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置,其他配置
 * Created by lijie on 2015-08-13.
 */
@Controller
@RequestMapping(value = "/config")
public class ConfigController {

    @Autowired
    private BarService barService;

    @RequestMapping(value = "/getbar")
    @ResponseBody
    public Map getBarList(String page, String rows, String id) {

        String hql = "from Bar";

        if (StringUtils.isNotEmpty(id)) {
            hql = "from Bar where id=" + id;
        }

        PageResults<Bar> barPageResults = barService.findPageByFetchedHql(hql, null, Integer.valueOf(page), Integer.valueOf(rows));

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("total", barPageResults.getTotalCount());

        map.put("rows", barPageResults.getResults());

        return map;
    }

}
