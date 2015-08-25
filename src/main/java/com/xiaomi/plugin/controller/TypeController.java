package com.xiaomi.plugin.controller;

import com.xiaomi.plugin.bean.PageResults;
import com.xiaomi.plugin.model.Type;
import com.xiaomi.plugin.service.TypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 类型管理
 * Created by lijie on 2015-06-25.
 */
@Controller
@RequestMapping(value = "/type")
public class TypeController extends BaseController {

    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/list")
    public ModelAndView list() {

        ModelMap modelMap = new ModelMap();
        PageResults<Type> typePageResults = typeService.findPageByFetchedHql("from Type order by orderNo", null, getPagenumber(), getPagesize());
        modelMap.addAttribute("typeList", typePageResults.getResults());

        modelMap.put("pagenumber", getPagenumber());
        modelMap.put("pagecount", typePageResults.getPagecount());

        return new ModelAndView("type", modelMap);
    }

    @RequestMapping(value = "/add")
    public String add(Type type) {
        Type entity;
        if (type.getId() != null) {
            entity = typeService.get(type.getId());
            entity.setDesc(type.getDesc());
            entity.setName(type.getName());
            entity.setOrderNO(type.getOrderNO());
        } else {
            entity = type;
        }
        typeService.saveOrUpdate(entity);
        return "redirect:/type/list";
    }

    @RequestMapping(value = "/delete")
    public String delete(String ids) {
        List<Integer> idsList = new ArrayList<Integer>();
        for (String id : ids.split(",")) {
            if (StringUtils.isNotEmpty(id)) {
                idsList.add(Integer.valueOf(id));
            }
        }
        typeService.deleteByIds(idsList);
        return "redirect:/type/list";
    }

}
